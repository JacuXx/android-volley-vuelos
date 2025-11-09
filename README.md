# 🛫 Aplicación de Vuelos - Sistema Completo

Sistema completo de gestión de vuelos con aplicación móvil Android y backend en PHP.

## 🚀 Componentes del Sistema

### 📱 Aplicación Android (`/android`)
- Interfaz móvil nativa para Android
- Desarrollada con Android SDK
- Conecta con la API PHP para operaciones CRUD

### 🌐 Backend PHP (`/backend`)
- API RESTful en PHP
- Gestión de registros y consultas de vuelos
- Formularios web para administración

### 🗄️ Base de Datos (`/database`)
- Scripts SQL para crear tablas
- Estructura de base de datos MySQL/MariaDB

## 📁 Estructura del Proyecto

```
├── android/                    # Aplicación Android
│   ├── app/
│   │   ├── src/main/java/     # Código fuente Android
│   │   └── res/               # Recursos (layouts, strings, etc.)
│   ├── build.gradle.kts       # Configuración de build
│   └── gradlew                # Gradle wrapper
├── backend/                   # Servidor PHP
│   ├── consulta.php          # Endpoint para consultas
│   ├── registro.php          # Endpoint para registros
│   └── formulario.html       # Interface web de administración
├── database/                 # Scripts de base de datos
│   └── crear_tabla.sql       # Script para crear tablas
├── README.md
└── .gitignore
```

## 🛠️ Instalación y Configuración

### Prerrequisitos
- Android Studio (para la app móvil)
- Servidor web con PHP (Apache/Nginx)
- MySQL/MariaDB
- Git

### 1. Configurar Base de Datos
```sql
-- Ejecutar el script de la base de datos
mysql -u tu_usuario -p tu_base_de_datos < database/crear_tabla.sql
```

### 2. Configurar Backend PHP
1. Copiar archivos de `/backend` a tu servidor web
2. **Configurar conexión a base de datos:**
   ```bash
   cd backend
   cp config.example.php config.php
   ```
3. Editar `config.php` con tus credenciales de base de datos
4. **NUNCA** subir `config.php` a GitHub (ya está en .gitignore)
5. Asegurar permisos de escritura necesarios

### 3. Configurar Aplicación Android
1. Abrir Android Studio
2. Importar proyecto desde carpeta `/android`
3. Actualizar URLs del servidor en el código Android
4. Compilar y ejecutar

## 🏃‍♂️ Ejecución

### Ejecutar Backend
1. Iniciar servidor web (Apache/Nginx)
2. Verificar que PHP esté funcionando
3. Probar endpoints en: `http://tu-servidor/backend/`

### Ejecutar App Android
1. Conectar dispositivo Android o iniciar emulador
2. En Android Studio: Run > Run 'app'
3. La app se instalará y ejecutará automáticamente

## 🔗 Endpoints de la API

- `GET/POST /backend/consulta.php` - Consultar vuelos
- `POST /backend/registro.php` - Registrar nuevos vuelos
- `GET /backend/formulario.html` - Interface web de administración

## 🔧 Configuración

### Variables de Entorno (Backend)

**⚠️ IMPORTANTE - Seguridad:**
El archivo `config.php` contiene credenciales sensibles y **NO debe ser versionado**.

1. Copia el archivo de ejemplo:
   ```bash
   cp backend/config.example.php backend/config.php
   ```

2. Edita `backend/config.php` con tus credenciales:
   ```php
   define('DB_HOST', 'localhost');     // Host de tu base de datos
   define('DB_USER', 'tu_usuario');    // Usuario de MySQL
   define('DB_PASS', 'tu_contraseña'); // Contraseña de MySQL
   define('DB_NAME', 'vuelos');        // Nombre de la base de datos
   define('DB_PORT', 3306);            // Puerto de MySQL
   ```

3. El archivo `config.php` está excluido en `.gitignore` para proteger tus credenciales

### Configuración Android
Actualizar en el código Android:
- URL base del servidor
- Endpoints de API

## 🤝 Contribución

1. Fork del repositorio
2. Crear rama: `git checkout -b feature/nueva-funcionalidad`
3. Commit: `git commit -m 'Agregar nueva funcionalidad'`
4. Push: `git push origin feature/nueva-funcionalidad`
5. Crear Pull Request

## 📄 Licencia

Este proyecto está bajo la Licencia MIT - ver archivo [LICENSE](LICENSE) para detalles.

## 👨‍💻 Desarrollado por

[Tu nombre/usuario de GitHub]

---

### 📞 Soporte

Si tienes problemas o preguntas, por favor abre un [issue](../../issues) en este repositorio.
