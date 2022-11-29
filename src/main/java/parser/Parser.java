package parser;

import org.jetbrains.annotations.NotNull;
import org.jsoup.nodes.Document;

public interface Parser<T> {
    T Parse(@NotNull Document document, @NotNull ParserSettings parserSettings) throws Exception;
}