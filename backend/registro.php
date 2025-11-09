<?php
// Incluir archivo de configuración
require_once 'config.php';

// Crear conexión usando las constantes del archivo de configuración
$conexion = new mysqli(DB_HOST, DB_USER, DB_PASS, DB_NAME, DB_PORT);

if ($conexion->connect_error) {
    die("Error de conexión: " . $conexion->connect_error);
}

$id = isset($_POST['id']) ? $_POST['id'] : null;
$o = isset($_POST['o']) ? $_POST['o'] : null;
$d = isset($_POST['d']) ? $_POST['d'] : null;

if (!empty($o) && !empty($d)) {
    $sql = "INSERT INTO rutas (id, o, d) VALUES (?, ?, ?)";
    $stmt = $conexion->prepare($sql);
    
    if ($stmt) {
        $stmt->bind_param("iss", $id, $o, $d);
        
        if ($stmt->execute()) {
            echo "Registro insertado correctamente.";
        } else {
            echo "Error al insertar el registro: " . $stmt->error;
        }
        
        $stmt->close();
    } else {
        echo "Error en la preparación de la consulta: " . $conexion->error;
    }
} else {
    echo "Los parámetros 'o' y 'd' son requeridos.";
}

$conexion->close();
?>
