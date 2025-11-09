#!/bin/bash
# Script para reemplazar credenciales en el historial de Git

sed -i 's/\$host = "localhost";/\/\/ Incluir archivo de configuración\nrequire_once '\''config.php'\'';/g' "$1"
sed -i 's/\$usuario = "root";.*//g' "$1"
sed -i 's/\$contrasena = "";.*//g' "$1"
sed -i 's/\$base_datos = "vuelos";.*//g' "$1"
sed -i 's/\$conexion = new mysqli(\$host, \$usuario, \$contrasena, \$base_datos, 3306);/\/\/ Crear conexión usando las constantes del archivo de configuración\n\$conexion = new mysqli(DB_HOST, DB_USER, DB_PASS, DB_NAME, DB_PORT);/g' "$1"
