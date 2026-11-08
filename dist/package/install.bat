@echo off
chcp 65001 > nul
echo ====================================================================
echo   УСТАНОВКА MINECRAFT FANTASY RPG ДЛЯ TLAUNCHER (WINDOWS)
echo ====================================================================
python install_modpack.py
if %ERRORLEVEL% NEQ 0 (
    echo.
    echo Запуск через Python не удался, выполняем прямое копирование файлов...
    if exist "%APPDATA%\.minecraft" (
        if not exist "%APPDATA%\.minecraft\mods" mkdir "%APPDATA%\.minecraft\mods"
        if not exist "%APPDATA%\.minecraft\config" mkdir "%APPDATA%\.minecraft\config"
        copy /Y "mods\*.jar" "%APPDATA%\.minecraft\mods\"
        copy /Y "config\*.json" "%APPDATA%\.minecraft\config\"
        echo [OK] Файлы модов и конфигурации скопированы в %%APPDATA%%\.minecraft
    ) else (
        echo [!] Папка %%APPDATA%%\.minecraft не найдена! Пожалуйста скопируйте папки вручную.
    )
)
echo.
echo Установка завершена! Запустите TLauncher, выберите версию 'Forge 1.20.1' и играйте!
pause
