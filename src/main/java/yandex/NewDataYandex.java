package yandex;

import model.Image;
import org.jetbrains.annotations.NotNull;
import parser.OnNewDataHandler;

import java.util.ArrayList;

public class NewDataYandex implements OnNewDataHandler<ArrayList<Image>> {

    @Override
    public void onNewData(@NotNull Object sender, @NotNull ArrayList<Image> args) {
        for (Image image : args) {
            System.out.println(image);
        }
    }
}