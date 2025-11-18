# Script para detectar SQL Server local y sus bases de datos
# Ejecutar en PowerShell como administrador

Write-Host "=== Detectando SQL Server Local ===" -ForegroundColor Green

# 1. Buscar instancias de SQL Server en el registro
Write-Host "`n1. Buscando instancias de SQL Server instaladas..." -ForegroundColor Yellow
$sqlInstances = @()

try {
    $regPath = "HKLM:\SOFTWARE\Microsoft\Microsoft SQL Server"
    if (Test-Path $regPath) {
        $instances = Get-ItemProperty "$regPath\Instance Names\SQL" -ErrorAction SilentlyContinue
        if ($instances) {
            foreach ($instance in $instances.PSObject.Properties) {
                if ($instance.Name -ne "PSPath" -and $instance.Name -ne "PSParentPath" -and $instance.Name -ne "PSChildName" -and $instance.Name -ne "PSDrive" -and $instance.Name -ne "PSProvider") {
                    $sqlInstances += $instance.Value
                }
            }
        }
    }
    
    if ($sqlInstances.Count -eq 0) {
        Write-Host "   - MSSQLSERVER (instancia por defecto)" -ForegroundColor Cyan
        $sqlInstances = @("MSSQLSERVER")
    } else {
        foreach ($inst in $sqlInstances) {
            Write-Host "   - $inst" -ForegroundColor Cyan
        }
    }
} catch {
    Write-Host "   ⚠ Error al leer registro: $_" -ForegroundColor Red
}

# 2. Intentar conectar a cada instancia
Write-Host "`n2. Intentando conectar a las instancias..." -ForegroundColor Yellow

foreach ($instance in $sqlInstances) {
    $serverName = if ($instance -eq "MSSQLSERVER") { "localhost" } else { "localhost\$instance" }
    Write-Host "`n   Probando: $serverName" -ForegroundColor Cyan
    
    try {
        $connectionString = "Server=$serverName;Integrated Security=true;Connection Timeout=3;"
        $connection = New-Object System.Data.SqlClient.SqlConnection($connectionString)
        $connection.Open()
        Write-Host "   ✓ Conectado exitosamente (Autenticación Windows)" -ForegroundColor Green
        
        # Obtener versión
        $query = "SELECT @@VERSION as Version"
        $command = New-Object System.Data.SqlClient.SqlCommand($query, $connection)
        $result = $command.ExecuteScalar()
        Write-Host "   Versión: $($result.Substring(0, 50))..." -ForegroundColor Green
        
        # Listar bases de datos
        Write-Host "   Bases de datos disponibles:" -ForegroundColor Cyan
        $query = "SELECT name FROM sys.databases WHERE database_id > 4 ORDER BY name"
        $command = New-Object System.Data.SqlClient.SqlCommand($query, $connection)
        $reader = $command.ExecuteReader()
        while ($reader.Read()) {
            Write-Host "      - $($reader['name'])" -ForegroundColor White
        }
        $reader.Close()
        
        $connection.Close()
        
    } catch {
        Write-Host "   ✗ No se pudo conectar: $($_.Exception.Message.Split([Environment]::NewLine)[0])" -ForegroundColor Red
    }
}

# 3. Verificar si SQL Server Express está instalado
Write-Host "`n3. Verificando SQL Server Express..." -ForegroundColor Yellow
$expressPath = "C:\Program Files\Microsoft SQL Server"
if (Test-Path $expressPath) {
    $folders = Get-ChildItem $expressPath -Directory | Where-Object { $_.Name -match "MSSQL\d+" }
    if ($folders) {
        Write-Host "   ✓ SQL Server Express detectado" -ForegroundColor Green
        foreach ($folder in $folders) {
            Write-Host "      - $($folder.Name)" -ForegroundColor Cyan
        }
    }
} else {
    Write-Host "   ✗ No se encontró SQL Server Express" -ForegroundColor Red
}

# 4. Información de configuración recomendada
Write-Host "`n=== Configuración Recomendada para Java ===" -ForegroundColor Green
Write-Host "
Para conectarte desde Java, usa:
- Server: localhost (o localhost\NOMBREINSTANCIA si no es la por defecto)
- Port: 1433 (por defecto)
- Authentication: Windows (Integrated Security) o SQL Server
- Driver: com.microsoft.sqlserver.jdbc.SQLServerDriver

Connection String ejemplo (Windows Auth):
jdbc:sqlserver://localhost:1433;databaseName=TuBaseDatos;integratedSecurity=true;

Connection String ejemplo (SQL Server Auth):
jdbc:sqlserver://localhost:1433;databaseName=TuBaseDatos;user=sa;password=TuContraseña;
" -ForegroundColor Yellow
