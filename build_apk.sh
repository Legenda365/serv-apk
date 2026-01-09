#!/bin/bash

echo "Сборка APK файла..."

# Проверяем наличие Android SDK
if [ -z "$ANDROID_HOME" ]; then
    echo "Ошибка: ANDROID_HOME не установлен"
    echo "Установите Android SDK и добавьте ANDROID_HOME в переменные среды"
    exit 1
fi

# Переходим в директорию проекта
cd /root/ServerManagerApp

# Делаем gradlew исполняемым
chmod +x gradlew

# Собираем debug APK
./gradlew assembleDebug

if [ $? -eq 0 ]; then
    echo "✅ APK успешно собран!"
    echo "📱 Файл находится в: app/build/outputs/apk/debug/app-debug.apk"
    
    # Копируем APK в удобное место
    cp app/build/outputs/apk/debug/app-debug.apk /root/ServerManager.apk
    echo "📋 Скопирован в: /root/ServerManager.apk"
    
    # Показываем размер файла
    ls -lh /root/ServerManager.apk
else
    echo "❌ Ошибка сборки APK"
    exit 1
fi
