package ru.surf.nikita_makarov.githubtrends.utils;

public class Element {
    public long id;
    public String theme;
    public String text;
    public String date;
    public int color;

    public Element(){}

    public Element(int id, String theme, String text, String date, int color){
        super();
        this.id = id;
        this.theme = theme;
        this.text = text;
        this.date = date;
        this.color = color;
    }
}
