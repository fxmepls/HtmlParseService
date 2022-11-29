
import model.Phone;
import sitilink.SitilinkParser;
import sitilink.SitilinkSettings;
import sitilink.NewDataProducts;
import model.Image;
import model.Product;
import nanegative.NanegativeParser;
import nanegative.NanegativeSettings;
import nanegative.NewDataOnlineStores;
import org.jetbrains.annotations.NotNull;
import parser.Completed;
import parser.ParserWorker;
import tools.ImageDownloader;

import yandex.NewDataYandex;
import yandex.YandexParser;
import yandex.YandexSettings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

    //сканнер для того, чтобы считать что-то введенное из консоли
    private static final Scanner IN = new Scanner(System.in);

    public static void main(String[] args) {
        int menu = -1;
        while (menu != 0) {
            printMenu();
            System.out.print("Выберите: ");
            try {
                menu = IN.nextInt();
            } catch (Exception e) {
                System.out.println("Ошибка при считывании с клавиатуры, повторите попытку");
            }
            switch (menu) {
                case 0 -> System.out.println("Выход");
                case 1 -> parseNanegative();
                case 2 -> parserSitilink();
                case 3 -> parseYandex();
            }
        }
    }

    private static void printMenu() {
        System.out.println("Parsing:");
        System.out.println("1. https://nanegative.ru/mobilnye-telefony-otzivy");
        System.out.println("2. https://www.citilink.ru");
        System.out.println("3. Images");
        System.out.println("0. Exit");
    }

    private static void parseNanegative() {
        try {
            int startPageOnlineStore = readPagination("Начало пагинации: ");
            int endPageOnlineStore = readPagination("Конец пагинации: ");

            int startPageFeedback = readPagination("Введите начало пагинации отзывов: ");
            int endPageFeedback = readPagination("Введите конец пагинации отзывов: ");

            ParserWorker<ArrayList<Phone>> parser = new ParserWorker<>(new NanegativeParser(),
                    new NanegativeSettings(startPageOnlineStore, endPageOnlineStore, startPageFeedback, endPageFeedback));

            parser.onCompletedList.add(new Completed());
            parser.onNewDataList.add(new NewDataOnlineStores());

            System.out.println("\nЗагрузка началась\n\n");
            parser.start();
            parser.abort();
        } catch (Exception e) {
            System.out.println("Что-то пошло не так...\n" + e.getMessage() + "\n");
        }
    }

    //считывание пагинации
    private static int readPagination(String message) {
        System.out.print(message);
        int value = 0;
        if (IN.hasNextInt()) {
            value = IN.nextInt();
            if (value > 0) {
                return value;
            }
            System.out.println("Введите целочисленное значение больше 0");
        }
        return value;
    }

    private static void parserSitilink() {
        try {

            System.out.print("Категория: ");
            String category = IN.next();

            int startPage = readPagination("Начало пагинации: ");
            int endPage = readPagination("Конец пагинации: ");


            ParserWorker<ArrayList<Product>> parser = new ParserWorker<>(new SitilinkParser(),
                    new SitilinkSettings(startPage, endPage, category));

            parser.onCompletedList.add(new Completed());
            parser.onNewDataList.add(new NewDataProducts());

            parseWork(parser);
        } catch (Exception e) {
            System.out.println("Error!...\n" + e.getMessage() + "\n" + Arrays.toString(e.getStackTrace()) + "\n");
        }
    }

    //парсер для скачивания картинок в яндексе
    private static void parseYandex() {
        //оболочку try, catch для того, чтобы не вышла непредвиенная ошибка и не сломала ничего
        try {
            System.out.print("Введите запрос: ");
            String query = IN.next();//считывание запроса на закачку картинки

            int startPage = readPagination("Введите начало пагинации: ");//считывание начала пагинации
            int endPage = readPagination("Введите конец пагинации: ");//считывание конца пагинации

            //устанавливает в классе ImageDownloader путь в папку, в который будут загружаться картинки
            //+query+ - добавляет в название папки запрос, который ввел полльз-ователь (папка будет называться запросом)
            ImageDownloader.setSavePath("uploads/yandex/" + query + "/");


            ParserWorker<ArrayList<Image>> parser = new ParserWorker<>(new YandexParser(),
                    new YandexSettings(query, startPage - 1, endPage - 1));

            parser.onCompletedList.add(new Completed());
            parser.onNewDataList.add(new NewDataYandex());

            parseWork(parser);
        } catch (Exception e) {
            System.out.println("Что-то пошло не так...\n" + e.getMessage() + "\n" + Arrays.toString(e.getStackTrace()) + "\n");
        }
    }


    //отдельная функция для скачивания картинок, чтобы вывести сообщение загрузка началась
    private static void parseWork(@NotNull ParserWorker parser) throws Exception {
        System.out.println("\nЗагрузка началась\n\n");
        parser.start();
        parser.abort();
    }
}