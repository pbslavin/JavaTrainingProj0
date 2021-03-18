package dev.slavin.models;

import java.io.Serializable;
import java.util.Objects;

public class Composition implements Serializable {

    private int id;
    private String title;
    private int composerId;
    private int yearComposed;
    private Genre genre;
    private boolean multiMovement;

    public Composition() {
        super();
    }

    public Composition(String title, int composerId, int yearComposed, Genre genre, Boolean multiMovement) {
        this.title = title;
        this.composerId = composerId;
        this.yearComposed = yearComposed;
        this.genre = genre;
        this.multiMovement = multiMovement;
    }

    public Composition(int id, String title, int composerId, int yearComposed, Genre genre, Boolean multiMovement) {
        this.id = id;
        this.title = title;
        this.composerId = composerId;
        this.yearComposed = yearComposed;
        this.genre = genre;
        this.multiMovement = multiMovement;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getComposerId() {
        return composerId; }

    public void setComposerId(int composerId) {
        this.composerId = composerId; }

    public int getYearComposed() {
        return yearComposed;
    }

    public void setYearComposed(int yearComposed) {
        this.yearComposed = yearComposed;
    }

    public Genre getGenre() {
        return genre; }

    public void setGenre(Genre genre) {
        this.genre = genre; }

    public boolean getMultiMovement() {
        return multiMovement;
    }

    public void setMultiMovement(boolean multiMovement) {
        this.multiMovement = multiMovement;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Composition that = (Composition) o;
        return id == that.id && multiMovement == that.multiMovement && Objects.equals(title, that.title) && Objects.equals(composerId, that.composerId) && Objects.equals(yearComposed, that.yearComposed) && genre == that.genre;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, composerId, yearComposed, genre, multiMovement);
    }

    @Override
    public String toString() {
        return "Composition{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", composerId='" + composerId + '\'' +
                ", yearComposed=" + yearComposed +
                ", genre=" + genre +
                ", multiMovement=" + multiMovement +
                '}';
    }
}