package ru.surf.nikita_makarov.jotter.util;

import android.graphics.Color;

public class Border {
    private int width;
    private int color = Color.BLACK;
    private int style;

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getStyle() {
        return style;
    }

    public void setStyle(int style) {
        this.style = style;
    }

}