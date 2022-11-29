package yandex;

import model.Image;
import org.jetbrains.annotations.NotNull;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import parser.Parser;
import parser.ParserSettings;

import java.util.ArrayList;

public class YandexParser implements Parser<ArrayList<Image>> {
//класс для картинок с яндекса, спускаясь по нодам, получаем ссылку и название картиинки
    @Override
    public @NotNull ArrayList<Image> Parse(@NotNull Document document, @NotNull ParserSettings parserSettings) {
        ArrayList<Image> images = new ArrayList<>();//объявление списка классов Image

        //получение всех элементов страницы которые имеют класс serp-item (список картинок)
        Elements elements = document.getElementsByClass("serp-item");
        for (Element element : elements) {//проходит по каждой картинке
            try {
                //берет у картинки класс data-bem, в ней хранится ссылка на сайт сс этой картинеой
                String data_bem = element.attr("data-bem");

                String imageUrl;
                do {
                    //находит первую ссылку на картинку (url) (она находится после слова "origin":
                    String originNode = data_bem.substring(data_bem.indexOf("\"origin\":") + 9);

                    //берем саму ссылку, без "url":" ....
                    String urlNode = originNode.substring(originNode.indexOf("\"url\":") + 7);

                    //убираем с конца ссылка знак "
                    imageUrl = urlNode.substring(0, urlNode.indexOf("\""));
                    data_bem = originNode;
                } while (data_bem.contains("\"origin\":") && imageUrl.lastIndexOf(".") == -1);//делаем это пока
                //не получим ссылку

                if (imageUrl.equals("")) { //если ссылка пустая, то картинку не скачать, идем к след. картинке
                    continue;
                }
                //название картинки без расширения, хранится оно после последнего символа / в ссылке
                String title = imageUrl.substring(imageUrl.lastIndexOf("/") + 1, imageUrl.lastIndexOf("."));

                //добавляет в список картинок новый класс с текущей картинкой и сразу скачивает ее
                images.add(new Image(title, imageUrl));
            } catch (Exception ignored) {
            }
        }

        return images;
    }
}