# Script simple para verificar y arreglar SQL Server
Write-Host "=== Diagnostico SQL Server ===" -ForegroundColor Green

# 1. Verificar estado del servicio
Write-Host "`n1. Verificando estado de SQL Server..." -ForegroundColor Yellow
$service = Get-Service -Name "MSSQLSERVER" -ErrorAction SilentlyContinue

if ($service) {
    Write-Host "   Servicio: $($service.Name)" -ForegroundColor Cyan
    Write-Host "   Estado: $($service.Status)" -ForegroundColor Green
    
    if ($service.Status -ne "Running") {
        Write-Host "   Iniciando SQL Server..." -ForegroundColor Yellow
        Start-Service -Name "MSSQLSERVER"
        Start-Sleep -Seconds 2
        Write-Host "   SQL Server iniciado" -ForegroundColor Green
    }
} else {
    Write-Host "   SQL Server no encontrado" -ForegroundColor Red
}

# 2. Verificar puerto
Write-Host "`n2. Verificando puerto 1433..." -ForegroundColor Yellow
$port = netstat -ano 2>$null | Select-String ":1433" | Select-String "LISTENING"
if ($port) {
    Write-Host "   Puerto 1433 escuchando" -ForegroundColor Green
} else {
    Write-Host "   Puerto 1433 NO escuchando" -ForegroundColor Red
}

# 3. Connection string
Write-Host "`n3. Connection String para SQL Server:" -ForegroundColor Yellow
Write-Host "   Server=localhost;Integrated Security=true;Connection Timeout=15;Encrypt=false;TrustServerCertificate=true;" -ForegroundColor Cyan

Write-Host "`nPara iniciar SQL Server manualmente:" -ForegroundColor Yellow
Write-Host "   Start-Service -Name MSSQLSERVER" -ForegroundColor White
Write-Host "   (Ejecutar como Administrador)" -ForegroundColor Red
