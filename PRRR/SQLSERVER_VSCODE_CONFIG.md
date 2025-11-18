# Configuración SQL Server para VS Code

## Archivos creados

- `sql_profiles.json` — Perfiles de conexión en formato JSON
- `.mssqlconfig` — Configuración en formato INI

## Perfiles disponibles

### 1. **LocalSQL_Default** (Recomendado)
- **Profile Name:** LocalSQL_Default
- **Server Name:** localhost
- **Connection Group:** Local Development
- **Input Type:** server name
- **Authentication Type:** Integrated Authentication (Windows)
- **Port:** 1433
- **Database:** master

### 2. **LocalSQL_Express** (Si usas SQL Server Express)
- **Profile Name:** LocalSQL_Express
- **Server Name:** localhost\SQLEXPRESS
- **Connection Group:** Local Development
- **Input Type:** server name
- **Authentication Type:** Integrated Authentication (Windows)
- **Port:** 1433
- **Database:** master

### 3. **LocalSQL_SQLAuth** (SQL Server Authentication con usuario sa)
- **Profile Name:** LocalSQL_SQLAuth
- **Server Name:** localhost
- **Connection Group:** Local Development
- **Input Type:** server name
- **Authentication Type:** SQL Login
- **Username:** sa
- **Password:** [Tu contraseña del usuario sa]
- **Port:** 1433
- **Database:** master

## Cómo usar en VS Code

### Opción 1: Usar la extensión SQL Server (mssql)

1. Instala la extensión: **MS SQL** (Microsoft)
2. Abre la paleta de comandos: `Ctrl+Shift+P`
3. Escribe: `MS SQL: Connect`
4. Elige un perfil de conexión o ingresa manualmente:
   - Profile Name: `LocalSQL_Default`
   - Server Name: `localhost`
   - Connection Group: `Local Development`
   - Input Type: `server name`
   - Authentication Type: `Integrated Authentication`

### Opción 2: Copiar perfil a settings.json

Copia el contenido de `sql_profiles.json` a:
```
%APPDATA%\Code\User\settings.json
```

En la sección `"mssql.connections"`:
```json
"mssql.connections": [
  {
    "profileName": "LocalSQL_Default",
    "serverName": "localhost",
    ...
  }
]
```

### Opción 3: Usar archivo .sql

Crea un archivo `consulta.sql`:
```sql
-- Verificar conexión
SELECT @@SERVERNAME as 'Servidor', @@VERSION as 'Versión';

-- Listar bases de datos
SELECT name FROM sys.databases WHERE database_id > 4;
```

Luego en VS Code:
1. Abre el archivo `.sql`
2. Usa `Ctrl+Shift+P` → `MS SQL: Execute Query`
3. Selecciona el perfil de conexión

## Variables de conexión

| Variable | Valor |
|----------|-------|
| **serverName** | localhost (o localhost\SQLEXPRESS) |
| **port** | 1433 |
| **connectionGroup** | Local Development |
| **inputType** | server name |
| **authenticationType** | Integrated Authentication o SQL Login |
| **encrypt** | false (para conexiones locales) |
| **trustServerCertificate** | true (para desarrollo local) |
| **connectionTimeout** | 15 segundos |

## Solución de problemas

Si recibe error de conexión:

1. **Verificar que SQL Server está ejecutándose:**
   ```powershell
   Get-Service -Name MSSQLSERVER | Select-Object Status
   ```

2. **Si está detenido, iniciar SQL Server:**
   ```powershell
   Start-Service -Name MSSQLSERVER
   ```

3. **Para SQL Server Express:**
   ```powershell
   Start-Service -Name MSSQL$SQLEXPRESS
   ```

4. **Verificar puertos:**
   ```powershell
   netstat -ano | findstr :1433
   ```

5. **Habilitar TCP/IP en SQL Server Configuration Manager:**
   - Abre: SQL Server Configuration Manager
   - SQL Server Network Configuration → Protocols for MSSQLSERVER
   - Habilita "TCP/IP"
   - Reinicia el servicio

---

**Nota:** Asegúrate de que SQL Server está corriendo antes de intentar conectar desde VS Code.
