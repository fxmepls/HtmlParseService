package yandex;

import org.jetbrains.annotations.NotNull;
import parser.ParserSettings;

public class YandexSettings extends ParserSettings {
    public YandexSettings(@NotNull String query, int startPoint, int endPoint) {
        if (startPoint < 0 || endPoint < startPoint) {
            throw new IllegalArgumentException("Некорректные входные данные");
        }
        externalStartPoint = startPoint;
        externalEndPoint = endPoint;

        internalStartPoint = 1;
        internalEndPoint = 1;

        BASE_URL = "https://yandex.ru/images/search";


        SEPARATOR = "?";

        PREFIX = "text=" + query + "&p={CurrentId}";
    }
}