package nanegative;

import parser.ParserSettings;

public class NanegativeSettings extends ParserSettings {

    public NanegativeSettings(int startPageOnlineStore, int endPageOnlineStore, int startPageFeedback, int endPageFeedback) {
        if (startPageOnlineStore <= 0 || endPageOnlineStore < startPageOnlineStore || startPageFeedback <= 0 || endPageFeedback < startPageFeedback) {
            throw new IllegalArgumentException("Некорректные входные данные");
        }
        externalStartPoint = startPageOnlineStore;
        externalEndPoint = endPageOnlineStore;

        internalStartPoint = startPageFeedback;
        internalEndPoint = endPageFeedback;

        BASE_URL = "https://nanegative.ru/mobilnye-telefony-otzivy";

        SEPARATOR = "?";

        PREFIX = "page={CurrentId}";
    }
}