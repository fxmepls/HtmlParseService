package sitilink;

import model.Feedback;
import model.Product;
import org.jetbrains.annotations.NotNull;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import parser.Parser;
import parser.ParserSettings;
import tools.HtmlLoader;

import java.io.IOException;
import java.util.ArrayList;

public class SitilinkParser implements Parser<ArrayList<Product>> {

    @Override
    public @NotNull ArrayList<Product> Parse(@NotNull Document document, @NotNull ParserSettings parserSettings) throws IOException {
        ArrayList<Product> products = new ArrayList<>();

        Elements productsEl = document.getElementsByClass("product_data__gtm-js product_data__pageevents-js ProductCardHorizontal js--ProductCardInListing js--ProductCardInWishlist");
            int i = 0;
            for (Element productEl : productsEl) {
                String name = productEl.getElementsByClass("ProductCardHorizontal__title  Link js--Link Link_type_default").text();

                String pricestr = productEl.getElementsByClass("ProductCardHorizontal__price_current-price js--ProductCardHorizontal__price_current-price ").text();

                double price = Double.parseDouble(pricestr.replace(" ", "").replace("₽", ""));

                String productPath = productEl.getElementsByTag("a").get(1).attr("href");
                ArrayList<Feedback> feedbacks = parseFeedbacks(productPath);

                products.add(new Product(name, price, feedbacks));
                i++;
                if (i == 6) break;
        }

        return products;
    }
    private @NotNull ArrayList<Feedback> parseFeedbacks(@NotNull String productPath) throws IOException {
        ArrayList<Feedback> feedbacks = new ArrayList<>();
        HtmlLoader.setUrl(SitilinkSettings.BASE_URL.substring(0, 23) + productPath +"/otzyvy/");
        Document feedbacksPage = HtmlLoader.getSource();

        Elements feedbacksEl = feedbacksPage.getElementsByClass("OpinionText");

        for (Element feedbackEl : feedbacksEl) {


            Elements titlesHead = feedbackEl.getElementsByClass("OpinionText__title");
            Elements textTitles =  feedbackEl.getElementsByClass("OpinionText__text");
            feedbacks.add(setFeedback(titlesHead, textTitles));
        }

        return feedbacks;
    }

    private static Feedback setFeedback(Elements titlesHead, Elements textTitles) {
        String pros = "", cons = "", text = "";
        int i = 0;
        for(Element title : titlesHead) {
            switch (title.getElementsByClass("OpinionText__title").get(0).text()) {
                case "Достоинства": {
                    pros = textTitles.get(i).text();
                }break;
                case "Недостатки": {
                    cons = textTitles.get(i).text();
                }break;
                case "Комментарий": {
                    text = textTitles.get(i).text();
                }break;
            }
            i++;
        }
        return new Feedback(pros, cons, text);
    }
}
