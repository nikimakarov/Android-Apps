package ru.surf.nikita_makarov.jotter;

public class NoteStruct {
    public int id;
    public String theme;
    public String text;
    public String date;
    public int color;
    
    public NoteStruct(){}
    
    public NoteStruct(int id, String theme, String text, String date, int color){
        super();
        this.id = id;
        this.theme = theme;
        this.text = text;
        this.date = date;
        this.color = color;
    }
}
