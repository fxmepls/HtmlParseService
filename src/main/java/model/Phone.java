package model;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class Phone {
    private String name; //название
    private ArrayList<Feedback> feedbacks;//список отзывов

    public Phone(@NotNull String name, @NotNull ArrayList<Feedback> feedbacks) {
        this.name = name;
        this.feedbacks = feedbacks;
    }

    public @NotNull String getName() {
        return name;
    }

    public void setName(@NotNull String name) {
        this.name = name;
    }

    public @NotNull ArrayList<Feedback> getFeedbacks() {
        return feedbacks;
    }

    public void setFeedbacks(@NotNull ArrayList<Feedback> feedbacks) {
        this.feedbacks = feedbacks;
    }

    //переопределение метода toString() для более красивого вывода отзывов
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder(name + "\n\n");
        for (Feedback feedback : feedbacks) {
            result.append(feedback.toString()).append("\n");
        }
        return result.toString();
    }
}