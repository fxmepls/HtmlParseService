package tools;

import org.jetbrains.annotations.NotNull;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;

public class ImageDownloader {

    private static String savePath = "uploads/";

    public static void setSavePath(@NotNull String savePath) {
        if (savePath.isEmpty()) {
            throw new NullPointerException("Устанавливаемый путь пустой");
        }

        //устанавливает путь в папку uploads/yandex/название запроса
        ImageDownloader.savePath = savePath;

        //если такой папки нет в директории, то создает папку с названием savePath
        if (new File(ImageDownloader.savePath).mkdirs()) {
            System.out.println("Директория для сохранения изображений успешно создана\n");
        }
    }


    //функция для скачивания картинок
    public static void download(@NotNull String imageUrl, @NotNull String title) throws Exception {
        //проверка на существовании ссылки с заданным запросом
        if (imageUrl.isEmpty()) {
            throw new IllegalArgumentException("Пустая входная ссылка");
        }

    //использование библиотеки ImageIO для получения изображений
        BufferedImage input = ImageIO.read(new URL(imageUrl));
        String imageExtension = getImageExtension(imageUrl);//расширение картинки

        //путь к сохранению картинки
        File output = new File(savePath + title + "." + imageExtension);
        try {
            //сохранение файла из ссылки на картинку в директорию
            ImageIO.write(input, imageExtension, output);
        } catch (Exception e) {
            System.out.println("Ошибка при сохранении изображения " + title + ": " + e.getMessage() + "\n");
        }
    }


    //убирает из ссылки на картинку расширение, возвращает полученное расширение
    private @NotNull
    static String getImageExtension(@NotNull String imageUrl) {
        String afterPoint = imageUrl.substring(imageUrl.lastIndexOf(".") + 1);
        return afterPoint.contains("&") ? afterPoint.substring(0, afterPoint.indexOf("&")) : afterPoint;
    }
}