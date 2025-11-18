# Script para diagnosticar y arreglar conexión a SQL Server
# Ejecutar como Administrador

Write-Host "=== Diagnóstico y Solución de Conexión SQL Server ===" -ForegroundColor Green

# 1. Verificar estado del servicio
Write-Host "`n1. Verificando estado de SQL Server..." -ForegroundColor Yellow
$service = Get-Service -Name "MSSQLSERVER" -ErrorAction SilentlyContinue

if ($service) {
    Write-Host "   Servicio encontrado: $($service.Name)" -ForegroundColor Cyan
    Write-Host "   Estado actual: $($service.Status)" -ForegroundColor $(if ($service.Status -eq "Running") {"Green"} else {"Red"})
    
    if ($service.Status -ne "Running") {
        Write-Host "`n   Iniciando SQL Server..." -ForegroundColor Yellow
        try {
            Start-Service -Name "MSSQLSERVER" -ErrorAction Stop
            Write-Host "   ✓ SQL Server iniciado exitosamente" -ForegroundColor Green
            Start-Sleep -Seconds 3
        }
        catch {
            Write-Host "   ✗ Error al iniciar: $_" -ForegroundColor Red
        }
    }
    else {
        Write-Host "   ✓ SQL Server ya está ejecutándose" -ForegroundColor Green
    }
}
else {
    Write-Host "   ✗ Servicio MSSQLSERVER no encontrado" -ForegroundColor Red
    Write-Host "   Buscando SQL Server Express..." -ForegroundColor Yellow
    $service = Get-Service -Name "MSSQL`$SQLEXPRESS" -ErrorAction SilentlyContinue
    
    if ($service) {
        Write-Host "   Servicio encontrado: $($service.Name)" -ForegroundColor Cyan
        Write-Host "   Estado actual: $($service.Status)" -ForegroundColor $(if ($service.Status -eq "Running") {"Green"} else {"Red"})
        
        if ($service.Status -ne "Running") {
            Write-Host "`n   Iniciando SQL Server Express..." -ForegroundColor Yellow
            try {
                Start-Service -Name "MSSQL`$SQLEXPRESS" -ErrorAction Stop
                Write-Host "   ✓ SQL Server Express iniciado exitosamente" -ForegroundColor Green
                Start-Sleep -Seconds 3
            }
            catch {
                Write-Host "   ✗ Error al iniciar: $_" -ForegroundColor Red
            }
        }
    }
}

# 2. Verificar puertos
Write-Host "`n2. Verificando puerto 1433 (SQL Server)..." -ForegroundColor Yellow
try {
    $port = netstat -ano | Select-String ":1433" | Select-String "LISTENING"
    if ($port) {
        Write-Host "   ✓ Puerto 1433 está escuchando" -ForegroundColor Green
        Write-Host "   $port" -ForegroundColor Cyan
    } else {
        Write-Host "   ✗ Puerto 1433 NO está escuchando" -ForegroundColor Red
    }
} catch {
    Write-Host "   ✗ Error al verificar puerto: $_" -ForegroundColor Red
}

# 3. Intentar conexión local
Write-Host "`n3. Intentando conexión a localhost..." -ForegroundColor Yellow
try {
    $connectionString = "Server=localhost;Integrated Security=true;Connection Timeout=5;"
    $connection = New-Object System.Data.SqlClient.SqlConnection($connectionString)
    $connection.Open()
    Write-Host "   ✓ Conexión exitosa a localhost" -ForegroundColor Green
    
    # Obtener información del servidor
    $query = "SELECT @@SERVERNAME as ServerName, @@VERSION as Version"
    $command = New-Object System.Data.SqlClient.SqlCommand($query, $connection)
    $reader = $command.ExecuteReader()
    while ($reader.Read()) {
        Write-Host "   Servidor: $($reader['ServerName'])" -ForegroundColor Cyan
        Write-Host "   Versión: $($reader['Version'].Substring(0, 60))..." -ForegroundColor Cyan
    }
    $reader.Close()
    $connection.Close()
}
catch {
    Write-Host "   ✗ No se pudo conectar: $($_.Exception.Message.Split([Environment]::NewLine)[0])" -ForegroundColor Red
}

# 4. Verificar Named Pipes y TCP/IP
Write-Host "`n4. Verificando protocolos de red..." -ForegroundColor Yellow
Write-Host "   Para habilitar TCP/IP y Named Pipes:" -ForegroundColor Cyan
Write-Host "   1. Abre: SQL Server Configuration Manager" -ForegroundColor White
Write-Host "   2. SQL Server Network Configuration → Protocols for MSSQLSERVER" -ForegroundColor White
Write-Host "   3. Habilita: TCP/IP y Named Pipes" -ForegroundColor White
Write-Host "   4. Reinicia el servicio SQL Server" -ForegroundColor White

# 5. Ubicación de archivos de configuración
Write-Host "`n5. Ubicación de archivos SQL Server..." -ForegroundColor Yellow
$sqlPath = "C:\Program Files\Microsoft SQL Server"
if (Test-Path $sqlPath) {
    Write-Host "   ✓ SQL Server instalado en: $sqlPath" -ForegroundColor Green
    $folders = Get-ChildItem $sqlPath -Directory | Where-Object { $_.Name -match "MSSQL\d+" }
    foreach ($folder in $folders) {
        Write-Host "      - $($folder.Name)" -ForegroundColor Cyan
    }
} else {
    Write-Host "   ✗ No se encontró SQL Server en ruta predeterminada" -ForegroundColor Red
}

# 6. Resumen y recomendaciones
Write-Host "`n=== RESUMEN Y RECOMENDACIONES ===" -ForegroundColor Green
Write-Host "Si aun asi no puedes conectar:" -ForegroundColor Yellow
Write-Host ""
Write-Host "1. VERIFICAR SERVICIOS (ejecutar en PowerShell como Admin):" -ForegroundColor Yellow
Write-Host "   Get-Service -Name MSSQLSERVER, MSSQL*" -ForegroundColor White
Write-Host "   Para iniciar:" -ForegroundColor White
Write-Host "   Start-Service -Name MSSQLSERVER" -ForegroundColor White
Write-Host ""
Write-Host "2. HABILITAR TCP/IP:" -ForegroundColor Yellow
Write-Host "   - Abre: SQL Server Configuration Manager" -ForegroundColor White
Write-Host "   - SQL Server Network Configuration -> Protocols for MSSQLSERVER" -ForegroundColor White
Write-Host "   - Habilita TCP/IP si no esta habilitado" -ForegroundColor White
Write-Host "   - Reinicia SQL Server" -ForegroundColor White
Write-Host ""
Write-Host "3. REINICIAR SQL SERVER:" -ForegroundColor Yellow
Write-Host "   Restart-Service -Name MSSQLSERVER -Force" -ForegroundColor White
Write-Host ""
Write-Host "4. ABRIR FIREWALL (si es necesario):" -ForegroundColor Yellow
Write-Host '   netsh advfirewall firewall add rule name="SQL Server 1433" dir=in action=allow protocol=tcp localport=1433' -ForegroundColor White
Write-Host ""
Write-Host "5. VERIFICAR CREDENCIALES:" -ForegroundColor Yellow
Write-Host "   - Usa autenticacion Windows (recomendado para desarrollo local)" -ForegroundColor White
Write-Host "   - O verifica usuario/contraseña si usas SQL Auth" -ForegroundColor White
Write-Host ""
Write-Host "6. CONNECTION STRING A USAR:" -ForegroundColor Yellow
Write-Host "   Server=localhost;Integrated Security=true;Connection Timeout=15;Encrypt=false;TrustServerCertificate=true;" -ForegroundColor White
Write-Host ""
Write-Host "Para mas ayuda, consulta:" -ForegroundColor Cyan
Write-Host "https://docs.microsoft.com/en-us/sql/database-engine/configure-windows/troubleshoot-connecting-to-the-sql-server-database-engine" -ForegroundColor Blue
