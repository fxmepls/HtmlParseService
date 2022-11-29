package tools;

import org.jetbrains.annotations.NotNull;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class HtmlLoader {

    private static String url;

    public static void setUrl(@NotNull String url) {
        if (url.isEmpty()) {
            throw new NullPointerException("Устанавливаемая ссылка пустая");
        }
        HtmlLoader.url = url;
    }

    public static @NotNull Document getSource() throws IOException {
        if (url.isEmpty()) {
            throw new NullPointerException("Ссылка не была установлена");
        }
        return Jsoup.connect(url).get();
    }

    //получение информации со страницы

    public static @NotNull Document getSourceByPageId(int id) throws IOException {
        //проверка на существование ссылки
        if (url.isEmpty()) {
            throw new NullPointerException("Ссылка не была установлена");
        }
        if (id < 0) {
            throw new IllegalArgumentException("id не может быть меньше либо равен 0");
        }
        String currentUrl = url.replace("{CurrentId}", Integer.toString(id));

        return Jsoup.connect(currentUrl).get();
    }
}