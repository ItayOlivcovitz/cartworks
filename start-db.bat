@echo off
echo Starting MySQL containers...

docker run -d --name ordersdb -p 3308:3306 -e MYSQL_DATABASE=ordersdb -e MYSQL_ROOT_PASSWORD=root mysql:latest
if %ERRORLEVEL% neq 0 (
    echo Failed to start ordersdb container.
    goto :end
)

docker run -d --name productsdb -p 3307:3306 -e MYSQL_DATABASE=productsdb -e MYSQL_ROOT_PASSWORD=root mysql:latest
if %ERRORLEVEL% neq 0 (
    echo Failed to start productsdb container.
    goto :end
)

docker run -d --name usersdb -p 3306:3306 -e MYSQL_DATABASE=usersdb -e MYSQL_ROOT_PASSWORD=root mysql:latest
if %ERRORLEVEL% neq 0 (
    echo Failed to start usersdb container.
    goto :end
)

echo All containers started successfully!
:end
pause
