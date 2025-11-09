# 🔐 Guía de Seguridad

## Archivos Sensibles

Este proyecto utiliza buenas prácticas de seguridad para proteger credenciales y datos sensibles.

### ⚠️ Archivos que NO deben subirse a GitHub:

1. **`backend/config.php`** - Contiene credenciales de base de datos
2. **`local.properties`** - Configuración local de Android
3. **Archivos `.env`** - Variables de entorno
4. **Archivos de compilación** - Build outputs de Android

Todos estos archivos están protegidos en `.gitignore`

## 📝 Configuración Segura

### Para Desarrollo Local:

1. **Copia el archivo de ejemplo:**
   ```bash
   cp backend/config.example.php backend/config.php
   ```

2. **Edita con tus credenciales locales:**
   ```php
   define('DB_HOST', 'localhost');
   define('DB_USER', 'root');
   define('DB_PASS', ''); // O tu contraseña local
   define('DB_NAME', 'vuelos');
   ```

3. **Verifica que no se suba a Git:**
   ```bash
   git status  # No debe aparecer config.php
   ```

### Para Producción:

1. **NUNCA uses credenciales de desarrollo en producción**
2. **Usa contraseñas fuertes:**
   - Mínimo 16 caracteres
   - Mezcla de mayúsculas, minúsculas, números y símbolos
   
3. **Crea un usuario de base de datos específico:**
   ```sql
   CREATE USER 'vuelos_app'@'localhost' IDENTIFIED BY 'contraseña_segura';
   GRANT SELECT, INSERT, UPDATE, DELETE ON vuelos.* TO 'vuelos_app'@'localhost';
   FLUSH PRIVILEGES;
   ```

4. **Configura permisos correctos:**
   ```bash
   chmod 600 backend/config.php  # Solo propietario puede leer/escribir
   ```

## 🛡️ Checklist de Seguridad

- [ ] `config.php` está en `.gitignore`
- [ ] No hay credenciales hardcodeadas en el código
- [ ] Se usa `config.example.php` como plantilla
- [ ] Las contraseñas de producción son diferentes a desarrollo
- [ ] Los permisos de archivos están configurados correctamente
- [ ] Se usan prepared statements en consultas SQL (✓ ya implementado)
- [ ] Se validan todas las entradas de usuario

## 🚨 ¿Qué hacer si expusiste credenciales?

Si accidentalmente subiste credenciales a GitHub:

1. **Cambia inmediatamente las contraseñas**
2. **Elimina el archivo del historial de Git:**
   ```bash
   git filter-branch --force --index-filter \
   "git rm --cached --ignore-unmatch backend/config.php" \
   --prune-empty --tag-name-filter cat -- --all
   ```
3. **Fuerza el push:**
   ```bash
   git push origin --force --all
   ```
4. **Notifica a tu equipo del cambio de credenciales**

## 📚 Recursos Adicionales

- [OWASP Top 10](https://owasp.org/www-project-top-ten/)
- [PHP Security Cheat Sheet](https://cheatsheetseries.owasp.org/cheatsheets/PHP_Configuration_Cheat_Sheet.html)
- [Android Security Best Practices](https://developer.android.com/topic/security/best-practices)

## 🤝 Contribuidores

Si vas a contribuir al proyecto:
- **NO** incluyas tu `config.php` en los commits
- Usa `config.example.php` como referencia
- Documenta cualquier nueva variable de configuración
- Revisa que no estés exponiendo datos sensibles antes del commit
