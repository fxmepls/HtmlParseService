package model;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class Product {
    private String name;
    private double price;
    private ArrayList<Feedback> feedbacks;

    public Product() {
        feedbacks = new ArrayList<>();
    }

    public Product(@NotNull String name, double price, @NotNull ArrayList<Feedback> feedbacks) {
        if (price == 0) {
            throw new NullPointerException("Устанавливаемая цена не может быть равна 0");
        }

        this.name = name;
        this.price = price;
        this.feedbacks = feedbacks;
    }

    public @NotNull String getName() {
        return name;
    }

    public void setName(@NotNull String name) {
        this.name = name;
    }

    public double getPrice() {
        if (price == 0) {
            throw new NullPointerException("Стоимость не была установлена");
        }

        return price;
    }

    public void setPrice(double price) {
        if (price == 0) {
            throw new NullPointerException("Устанавливаемая цена не может быть равна 0");
        }

        this.price = price;
    }

    public @NotNull ArrayList<Feedback> getFeedbacks() {
        return feedbacks;
    }

    public void setFeedbacks(@NotNull ArrayList<Feedback> feedbacks) {
        this.feedbacks = feedbacks;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder(name + "\n" + price + " руб.\n\n");
        for (Feedback feedback : feedbacks) {
            result.append(feedback.toString()).append("\n");
        }
        return result.toString();
    }
}