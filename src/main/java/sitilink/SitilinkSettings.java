package sitilink;

import parser.ParserSettings;

public class SitilinkSettings extends ParserSettings {

    public SitilinkSettings(int startPage, int endPage, String category) {
        if (startPage <= 0 || endPage < startPage || category.isEmpty()) {
            throw new IllegalArgumentException("Некорректные входные данные");
        }

        //установка пагинации
        externalStartPoint = startPage;
        externalEndPoint = endPage;
        internalStartPoint = 1;
        internalEndPoint = 1;

        BASE_URL = "https://www.citilink.ru/catalog/" + category + "/?view_type=list";

        SEPARATOR = "&";

        PREFIX = "p={CurrentId}";
    }
}
