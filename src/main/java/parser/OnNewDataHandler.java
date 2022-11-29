package parser;

import org.jetbrains.annotations.NotNull;

public interface OnNewDataHandler<T> {
    void onNewData(@NotNull Object sender, @NotNull T e);
}