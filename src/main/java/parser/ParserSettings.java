package parser;

public abstract class ParserSettings {

    public static String BASE_URL;      // Адрес сайта
    public static String SEPARATOR;     // Разделитель
    public static String PREFIX;        // Префикс страницы
    protected int externalStartPoint;   // Начало пагинации
    protected int externalEndPoint;     // Конец пагинации
    protected int internalStartPoint;   // Начало пагинации
    protected int internalEndPoint;     // Конец пагинации

    public int getExternalStartPoint() {
        return externalStartPoint;
    }

    public int getExternalEndPoint() {
        return externalEndPoint;
    }

    public int getInternalStartPoint() {
        return internalStartPoint;
    }

    public int getInternalEndPoint() {
        return internalEndPoint;
    }
}