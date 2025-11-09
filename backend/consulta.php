<?php
// Incluir archivo de configuración
require_once 'config.php';

// Crear conexión usando las constantes del archivo de configuración
$conexion = new mysqli(DB_HOST, DB_USER, DB_PASS, DB_NAME, DB_PORT);

if ($conexion->connect_error) {
    die("Error de conexión: " . $conexion->connect_error);
}

$sql = "SELECT id, o, d FROM rutas";
$resultado = $conexion->query($sql);

if ($resultado->num_rows > 0) {
    $data = array();
    while ($fila = $resultado->fetch_assoc()) {
        $data[] = $fila;
    }
    header('Content-Type: application/json');
    echo json_encode($data);
} else {
    header('Content-Type: application/json');
    echo json_encode(array("mensaje" => "No hay datos"));
}

$conexion->close();
?>
