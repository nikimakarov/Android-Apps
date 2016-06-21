package ru.surf.nikita_makarov.jotter.util;

public class Note {
    public long id;
    public String theme;
    public String text;
    public String date;
    public int color;
    
    public Note(){}
    
    public Note(int id, String theme, String text, String date, int color){
        super();
        this.id = id;
        this.theme = theme;
        this.text = text;
        this.date = date;
        this.color = color;
    }
}
