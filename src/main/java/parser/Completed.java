package parser;

import org.jetbrains.annotations.NotNull;

public class Completed implements OnCompleted {

    //сообщение о конце загрузки
    @Override
    public void onCompleted(@NotNull Object sender) {
        System.out.println("Загрузка закончена\n");
    }
}