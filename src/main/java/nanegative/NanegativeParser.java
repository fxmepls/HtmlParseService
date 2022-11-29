package nanegative;

import model.Feedback;
import model.Phone;
import org.jetbrains.annotations.NotNull;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import parser.Parser;
import parser.ParserSettings;
import tools.HtmlLoader;

import java.io.IOException;
import java.util.ArrayList;

public class NanegativeParser implements Parser<ArrayList<Phone>> {

    @Override
    public @NotNull ArrayList<Phone> Parse(@NotNull Document document, @NotNull ParserSettings parserSettings) throws IOException {

        ArrayList<Phone> phones = new ArrayList<>();

        Elements phonesEl = document.getElementsByClass("find-list-box");

        for (Element phoneEl : phonesEl) {

            //берет текст из тегов "a", причем обрезает его
            String name = phoneEl.getElementsByTag("a").text().substring(9);


            //сохраняет ссылку на отзывы, ссылка хранится в теге "a" с атрибутом href
            String phonePath = phoneEl.getElementsByTag("a").get(0).attr("href");

            //парсит список отзывов по найденной ранее ссылке на отзывы
            ArrayList<Feedback> feedbacks = parseFeedbacks(phonePath, parserSettings);

            phones.add(new Phone(name, feedbacks));
        }

        return phones;
    }

    //парсинг отзывов
    private @NotNull ArrayList<Feedback> parseFeedbacks(@NotNull String onlineStorePath, @NotNull ParserSettings parserSettings) throws IOException {
        ArrayList<Feedback> feedbacks = new ArrayList<>();

        for (int i = parserSettings.getInternalStartPoint(); i <= parserSettings.getInternalEndPoint(); i++) {

            //загрузка html документа по адресу https://nanegative.ru (21 символ) + путь к отзыву телефона + сепаратор ? + префикс page={i}
            HtmlLoader.setUrl(NanegativeSettings.BASE_URL.substring(0, 21) + onlineStorePath + NanegativeSettings.SEPARATOR + NanegativeSettings.PREFIX);

            //в документ записываем html страницу под номером i
            Document feedbacksPage = HtmlLoader.getSourceByPageId(i);

            //получаем все элементы, которые находятся в боксе с классом reviewers-box (там находится html блок со всеми отзывами)
            Elements feedbacksEl = feedbacksPage.getElementsByClass("reviewers-box");

            //проходит по каждому элементу блока reviewers-box
            for (Element feedbackEl : feedbacksEl) {
                //берет текст отзыва с атрибутом itemprop и значением pro, что соответсвует блоку отзыва "плюсы"
                String pros = feedbackEl.getElementsByAttributeValue("itemprop", "pro").text();

                //берет текст отзыва с атрибутом itemprop и значением contra, что соответсвует блоку отзыва "минусы"
                String cons = feedbackEl.getElementsByAttributeValue("itemprop", "contra").text();

                //берет текст отзыва с атрибутом itemprop и значением reviewBody, что соответсвует блоку отзыва "отзыв"
                String text = feedbackEl.getElementsByAttributeValue("itemprop", "reviewBody").text();

                //добавляет в список отзыв, в котором указаны плюсы, минусы, отзыв
                feedbacks.add(new Feedback(pros, cons, text));
            }
        }

        return feedbacks;
    }
}