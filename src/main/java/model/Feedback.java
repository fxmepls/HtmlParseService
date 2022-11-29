package model;

import org.jetbrains.annotations.NotNull;

public class Feedback {
    private String pros; //строка с положительным качеством в отзыве
    private String cons; // строка с отрицательним качеством в отзыве
    private String text;//строка с самим отзывом

    public Feedback(@NotNull String pros, @NotNull String cons, @NotNull String text) {
        this.pros = pros;
        this.cons = cons;
        this.text = text;
    }

    //геттеры и сеттеры

    public @NotNull String getPros() {
        return pros;
    }

    public void setPros(@NotNull String pros) {
        this.pros = pros;
    }

    public @NotNull String getCons() {
        return cons;
    }

    public void setCons(@NotNull String cons) {
        this.cons = cons;
    }

    public @NotNull String getText() {
        return text;
    }

    public void setText(@NotNull String text) {
        this.text = text;
    }

    //переопределенный метод toString, чтобы красиво вывести отзыв

    @Override
    public String toString() {
        return "Плюсы: " + pros + "\nМинусы: " + cons + "\nОтзыв: " + text + "\n";
    }
}