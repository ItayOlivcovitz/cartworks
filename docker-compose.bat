@echo off

REM Define the target folder for docker-compose
set TARGET_FOLDER=%~dp0docker-compse\default

REM Navigate to the target folder
cd /d "%TARGET_FOLDER%"

REM Check if the folder contains a docker-compose.yml file
if not exist "docker-compose.yml" (
    echo No docker-compose.yml found in %TARGET_FOLDER%.
    pause
    exit /b 1
)

REM Execute docker-compose up -d
echo Starting Docker Compose in %TARGET_FOLDER%...
docker-compose up -d
if %ERRORLEVEL% neq 0 (
    echo Failed to run docker-compose in %TARGET_FOLDER%. Exiting...
    pause
    exit /b 1
)

echo ========================================
echo Docker Compose successfully executed!
echo ========================================
pause
