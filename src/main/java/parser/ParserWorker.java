package parser;

import org.jetbrains.annotations.NotNull;
import org.jsoup.nodes.Document;
import tools.HtmlLoader;

import java.util.ArrayList;

//класс - шаблон парсеров
public class ParserWorker<T> {

    private Parser<T> parser; //сам парсер
    private final ParserSettings parserSettings; //настройки парсера
    private boolean isActive; //активность парсера

    public ArrayList<OnNewDataHandler<T>> onNewDataList = new ArrayList<>();
    public ArrayList<OnCompleted> onCompletedList = new ArrayList<>();

    public ParserWorker(@NotNull Parser<T> parser, @NotNull ParserSettings parserSettings) {
        this.parser = parser;
        this.parserSettings = parserSettings;
    }

    public @NotNull Parser<T> getParser() {
        return parser;
    }

    public void setParser(@NotNull Parser<T> parser) {
        this.parser = parser;
    }

    //начинает парсинг
    public void start() throws Exception {
        isActive = true;
        parse();
    }

    public void abort() {
        isActive = false;
    }

    //парсинг
    private void parse() throws Exception {
        for (int i = parserSettings.getExternalStartPoint(); i <= parserSettings.getExternalEndPoint(); i++) {

            if (!isActive) {
                onCompletedList.get(0).onCompleted(this);
                return;
            }

            Document document;

            //HtmlLoader загружает html страницу по заданному URL+separator+prefix
            HtmlLoader.setUrl(ParserSettings.BASE_URL + ParserSettings.SEPARATOR + ParserSettings.PREFIX);

            //добавляет в документ страницу html с номером i
            document = HtmlLoader.getSourceByPageId(i);

            //парсинг данных в зависимости от выбора пользователя (nanegative, sitilink, images), вызов метода Parse
            //из этих классов, передает документ с загруженной страницей и настройки парсера
            T result = parser.Parse(document, parserSettings);

            //передает полученный результат парсинга для вывода его в консоль
            onNewDataList.get(0).onNewData(this, result);
        }
        onCompletedList.get(0).onCompleted(this);
        isActive = false;
    }
}