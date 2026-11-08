#!/usr/bin/env python3
"""
install_modpack.py
Безопасный автоматический установщик сборки Minecraft Fantasy RPG для TLauncher и ванильного Forge 1.20.1.

Особенности:
- Автоматически находит директорию .minecraft (Windows / Linux / macOS).
- Полностью сохраняет миры (папка saves/), личные ресурспаки, скриншоты и сторонние моды.
- Создает резервную копию заменяемых конфигураций.
- Устанавливает 4 скомпилированных JAR-мода и русские конфигурации по умолчанию.
"""

import os
import sys
import shutil
import platform
from datetime import datetime

def get_minecraft_dir():
    # Проверка переменной окружения или стандартных путей
    system = platform.system()
    if system == "Windows":
        appdata = os.environ.get("APPDATA")
        if appdata:
            return os.path.join(appdata, ".minecraft")
    elif system == "Darwin":
        home = os.path.expanduser("~")
        return os.path.join(home, "Library", "Application Support", "minecraft")
    else:
        home = os.path.expanduser("~")
        return os.path.join(home, ".minecraft")
    return None

def main():
    print("====================================================================")
    print("   УСТАНОВЩИК СБОРКИ MINECRAFT FANTASY RPG (FORGE 1.20.1 / TLAUNCHER)")
    print("====================================================================")

    mc_dir = get_minecraft_dir()
    if len(sys.argv) > 1:
        mc_dir = sys.argv[1]

    if not mc_dir or not os.path.exists(mc_dir):
        print(f"[!] Директория .minecraft не найдена автоматически: {mc_dir}")
        user_input = input("Пожалуйста, введите полный путь к вашей папке .minecraft: ").strip()
        if user_input and os.path.exists(user_input):
            mc_dir = user_input
        else:
            print("[-] Ошибка: указанная директория не существует. Установка отменена.")
            sys.exit(1)

    print(f"[+] Целевая директория игры: {mc_dir}")

    # Создание необходимых подпапок
    target_mods = os.path.join(mc_dir, "mods")
    target_config = os.path.join(mc_dir, "config")
    target_defaultconfigs = os.path.join(mc_dir, "defaultconfigs")

    os.makedirs(target_mods, exist_ok=True)
    os.makedirs(target_config, exist_ok=True)
    os.makedirs(target_defaultconfigs, exist_ok=True)

    script_dir = os.path.dirname(os.path.abspath(__file__))
    src_mods = os.path.join(script_dir, "mods")
    src_config = os.path.join(script_dir, "config")
    src_defaultconfigs = os.path.join(script_dir, "defaultconfigs")

    # Копирование модов без удаления сторонних
    if os.path.exists(src_mods):
        for item in os.listdir(src_mods):
            if item.endswith(".jar"):
                s_path = os.path.join(src_mods, item)
                d_path = os.path.join(target_mods, item)
                shutil.copy2(s_path, d_path)
                print(f"  [+] Установлен мод: {item}")

    # Копирование конфигураций с созданием бэкапа
    if os.path.exists(src_config):
        for item in os.listdir(src_config):
            s_path = os.path.join(src_config, item)
            d_path = os.path.join(target_config, item)
            if os.path.exists(d_path):
                backup_path = d_path + f".bak_{datetime.now().strftime('%Y%m%d_%H%M%S')}"
                shutil.copy2(d_path, backup_path)
                print(f"  [*] Создан бэкап старого конфига: {os.path.basename(backup_path)}")
            shutil.copy2(s_path, d_path)
            print(f"  [+] Установлен конфиг: {item}")

    if os.path.exists(src_defaultconfigs):
        for item in os.listdir(src_defaultconfigs):
            s_path = os.path.join(src_defaultconfigs, item)
            d_path = os.path.join(target_defaultconfigs, item)
            shutil.copy2(s_path, d_path)

    print("\n[+] УСТАНОВКА УСПЕШНО ЗАВЕРШЕНА!")
    print("Миры (папка saves/), ресурспаки и скриншоты ПОЛНОСТЬЮ СОХРАНЕНЫ.")
    print("В TLauncher выберите версию 'Forge 1.20.1' и нажмите 'Войти в игру'!")

if __name__ == "__main__":
    main()
