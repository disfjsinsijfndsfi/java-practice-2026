package ru.itis.shop.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesReader {
    private final String fileName;
    public PropertiesReader(String fileName) {
        this.fileName = fileName;
    }
    public Properties loadProperties() {
        Properties properties = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream(fileName)) {
            if (input == null) {
                throw new IllegalStateException("Файл " + fileName + " не найден в resources");
            }
            properties.load(input);
        } catch (IOException e) {
            throw new IllegalStateException("Ошибка загрузки из файла " + fileName, e);
        }
        return properties;
    }
}
