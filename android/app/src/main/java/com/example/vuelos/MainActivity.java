package com.example.vuelos;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private EditText etId, etOrigen, etDestino;
    private Button bSave, bConsult;
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> datosList;
    private RequestQueue servicio;

    // URLs para diferentes configuraciones
    private static final String URL_EMULADOR_SAVE = "http://10.0.2.2:8012/vuelos/registro.php";
    private static final String URL_EMULADOR_QUERY = "http://10.0.2.2:8012/vuelos/consulta.php";

    private static final String URL_DISPOSITIVO_SAVE = "http://192.168.1.70:8012/vuelos/registro.php";
    private static final String URL_DISPOSITIVO_QUERY = "http://192.168.1.70:8012/vuelos/consulta.php";

    private static final String URL_LOCALHOST_SAVE = "http://localhost:8012/vuelos/registro.php";
    private static final String URL_LOCALHOST_QUERY = "http://localhost:8012/vuelos/consulta.php";

    // URLs actuales (configuradas para emulador con puerto 8012)
    private static final String URL_SAVE = URL_EMULADOR_SAVE;
    private static final String URL_QUERY = URL_EMULADOR_QUERY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Referencias UI
        etId = findViewById(R.id.id);
        etOrigen = findViewById(R.id.origen);
        etDestino = findViewById(R.id.Destino);
        bSave = findViewById(R.id.b1);
        bConsult = findViewById(R.id.b2);
        listView = findViewById(R.id.l1);

        // Lista y adaptador
        datosList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, datosList);
        listView.setAdapter(adapter);

        // Añadir datos de ejemplo para testing (comentado para usar datos reales)
        // cargarDatosEjemplo();

        // Cola de peticiones Volley
        servicio = Volley.newRequestQueue(getApplicationContext());

        // Mostrar información de conexión
        Toast.makeText(this, "✅ Configurado para localhost:8012/vuelos/\nURL consulta: " + URL_QUERY, Toast.LENGTH_LONG).show();

        // Función de diagnóstico (mantén presionado el botón CONSULTAR)
        bConsult.setOnLongClickListener(v -> {
            probarConectividad();
            return true;
        });

        // Botón "Salvar" -> POST
        bSave.setOnClickListener(v -> {
            String clave = etId.getText().toString().trim();
            String origen = etOrigen.getText().toString().trim();
            String destino = etDestino.getText().toString().trim();

            if (clave.isEmpty() || origen.isEmpty() || destino.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Completa todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }

            StringRequest peticion = new StringRequest(Request.Method.POST, URL_SAVE,
                    response -> {
                        Toast.makeText(getApplicationContext(), "Datos guardados: " + response, Toast.LENGTH_LONG).show();
                        // Limpiar campos después de guardar exitosamente
                        etId.setText("");
                        etOrigen.setText("");
                        etDestino.setText("");
                    },
                    error -> {
                        String mensaje = "Error de conexión";
                        if (error.networkResponse != null) {
                            mensaje = "Error del servidor: " + error.networkResponse.statusCode;
                        } else if (error.getMessage() != null) {
                            mensaje = "Error de red: " + error.getMessage();
                        }
                        Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_LONG).show();
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> datos = new HashMap<>();
                    datos.put("id", clave);
                    datos.put("o", origen);
                    datos.put("d", destino);
                    return datos;
                }
            };

            servicio.add(peticion);
        });

        // Botón "Consultar" -> GET JSON Array
        bConsult.setOnClickListener(v -> {
            // Limpiar lista antes de la consulta
            datosList.clear();
            adapter.notifyDataSetChanged();

            JsonArrayRequest peticion = new JsonArrayRequest(Request.Method.GET, URL_QUERY, null,
                    response -> {
                        datosList.clear();

                        if (response.length() == 0) {
                            Toast.makeText(getApplicationContext(), "No se encontraron datos en la base de datos", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject jsonObject = response.getJSONObject(i);
                                String id = jsonObject.optString("id", "Sin ID");
                                String o = jsonObject.optString("o", "Sin origen");
                                String d = jsonObject.optString("d", "Sin destino");
                                String item = "ID: " + id + "\nOrigen: " + o + "\nDestino: " + d;
                                datosList.add(item);
                            } catch (JSONException e) {
                                Toast.makeText(getApplicationContext(), "Error al procesar registro: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                        adapter.notifyDataSetChanged();

                        // Mostrar información sobre los registros encontrados
                        String mensaje = "Se encontraron " + datosList.size() + " registro(s)";
                        Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_SHORT).show();

                        // desplazar al final para ver el último elemento (si existe)
                        listView.post(() -> {
                            if (adapter.getCount() > 0) listView.setSelection(adapter.getCount() - 1);
                        });
                    },
                    error -> {
                        String mensaje;
                        if (error.networkResponse != null) {
                            int statusCode = error.networkResponse.statusCode;
                            mensaje = "Error del servidor (código " + statusCode + ")\n";
                            switch (statusCode) {
                                case 404:
                                    mensaje += "Archivo PHP no encontrado. Verifica que consultar.php existe.";
                                    break;
                                case 500:
                                    mensaje += "Error interno del servidor. Revisa la configuración PHP/base de datos.";
                                    break;
                                case 403:
                                    mensaje += "Acceso denegado. Verifica permisos del servidor.";
                                    break;
                                default:
                                    mensaje += "Verifica la configuración del servidor.";
                            }
                        } else if (error.getMessage() != null && error.getMessage().contains("UnknownHost")) {
                            mensaje = "No se puede conectar al servidor.\nVerifica la URL: " + URL_QUERY;
                        } else {
                            mensaje = "Error de conexión:\n" +
                                     "1. Verifica que el servidor esté ejecutándose\n" +
                                     "2. Comprueba la URL: " + URL_QUERY + "\n" +
                                     "3. Asegúrate de tener conexión a internet";
                        }
                        Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_LONG).show();
                    });

            servicio.add(peticion);
        });
    }

    private void cargarDatosEjemplo() {
        // DATOS DE EJEMPLO PARA TESTING - NO SON DATOS REALES DE LA BASE DE DATOS
        // Este método se usa solo para probar la interfaz cuando no hay servidor disponible
        datosList.clear();
        datosList.add("ID: 85\nOrigen: Tenango\nDestino: Juchitepec");
        datosList.add("ID: 86\nOrigen: Tenango\nDestino: Juchitepec");
        datosList.add("ID: 87\nOrigen: Tenango\nDestino: Juchitepec");
        datosList.add("ID: 88\nOrigen: Tenango\nDestino: Juchitepec");
        datosList.add("ID: 89\nOrigen: Mazatlan\nDestino: Guadalajara");
        adapter.notifyDataSetChanged();

        Toast.makeText(this, "DATOS DE EJEMPLO CARGADOS (no son datos reales)", Toast.LENGTH_LONG).show();

        // Scroll al último elemento
        listView.post(() -> {
            if (adapter.getCount() > 0) listView.setSelection(adapter.getCount() - 1);
        });
    }

    private void probarConectividad() {
        Toast.makeText(this, "🔍 Probando conectividad en todas las URLs...", Toast.LENGTH_SHORT).show();

        // Probar URL del emulador
        probarURL(URL_EMULADOR_QUERY, "Emulador (10.0.2.2)");

        // Esperar un poco y probar URL del dispositivo
        listView.postDelayed(() -> probarURL(URL_DISPOSITIVO_QUERY, "Dispositivo (192.168.1.70)"), 1000);

        // Esperar un poco más y probar localhost
        listView.postDelayed(() -> probarURL(URL_LOCALHOST_QUERY, "Localhost"), 2000);
    }

    private void probarURL(String url, String nombre) {
        StringRequest request = new StringRequest(Request.Method.GET, url,
            response -> {
                Toast.makeText(this, "✅ " + nombre + " funciona!", Toast.LENGTH_LONG).show();
            },
            error -> {
                String mensaje = "❌ " + nombre + " falló: ";
                if (error.networkResponse != null) {
                    mensaje += "Error " + error.networkResponse.statusCode;
                } else {
                    mensaje += "Sin conexión";
                }
                Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show();
            });

        servicio.add(request);
    }
}
