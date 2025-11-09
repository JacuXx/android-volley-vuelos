# Reescribir Historial de Git - Eliminar Credenciales

## ⚠️ ADVERTENCIA
Este script reescribe el historial de Git. Asegúrate de tener un backup.

## Opción 1: Usando BFG Repo-Cleaner (Recomendado)

### Instalar BFG
```powershell
# Descargar BFG desde: https://rtyley.github.io/bfg-repo-cleaner/
# O usar chocolatey:
choco install bfg-repo-cleaner
```

### Usar BFG para limpiar
```powershell
# Crear un archivo con las palabras a reemplazar
echo 'root==>USUARIO_DB' > credentials.txt
echo 'localhost==>DB_HOST' >> credentials.txt

# Ejecutar BFG
bfg --replace-text credentials.txt

# Limpiar y forzar push
git reflog expire --expire=now --all
git gc --prune=now --aggressive
git push origin --force --all
```

## Opción 2: Reescritura Manual con git filter-repo

### Instalar git-filter-repo
```powershell
pip install git-filter-repo
```

### Limpiar archivos específicos
```powershell
git filter-repo --path backend/consulta.php --path backend/registro.php --invert-paths --force
```

## Opción 3: Solución Rápida - Squash y Force Push

Si el historial no es crítico, la forma más simple:

```powershell
# 1. Crear una nueva rama desde el estado actual
git checkout --orphan nuevo-main

# 2. Agregar todos los archivos actuales (sin historial)
git add -A

# 3. Hacer un commit inicial limpio
git commit -m "Initial commit: Sistema de vuelos (historial limpio)"

# 4. Eliminar la rama main anterior
git branch -D main

# 5. Renombrar la nueva rama a main
git branch -m main

# 6. Forzar push al remoto
git push -f origin main
```

## ⚠️ Después de Reescribir el Historial

Todos los colaboradores deben:
```powershell
git fetch origin
git reset --hard origin/main
```

## Verificar que las credenciales fueron eliminadas

```powershell
# Buscar en todo el historial
git log -p -S "root" -- backend/*.php
git log -p -S "localhost" -- backend/*.php
```
