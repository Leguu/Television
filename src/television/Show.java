/*
 * Asil Erturan (40164714)
 * COMP249
 * Assignment #4
 * 2021-04-24
 */

package television;

import java.util.Objects;

/**
 * A Data class that holds id, name, start, and end of a show.
 */
public class Show implements Watchable {
    private final String id;
    private final String name;
    private final double start;
    private final double end;

    public Show(String id, String name, double start, double end) {
        this.id = id;
        this.name = name;
        this.start = start;
        this.end = end;
    }

    public Show(Show other, String id) {
        this.id = id;
        this.name = other.name;
        this.start = other.start;
        this.end = other.end;
    }

    public Show(Show other) {
        this.id = other.id;
        this.name = other.name;
        this.start = other.start;
        this.end = other.end;
    }

    public Show clone(String id) {
        return new Show(this, id);
    }

    /**
     * Checks the compatibility between this show and another.
     * @param s The show to compare to.
     * @return The compatibility between the two shows.
     */
    @Override
    public Compatibility compatible(Show s) {
        if (start > s.end || s.start > end)
            return Compatibility.DIFFERENT;

        if (start == s.start && end == s.end)
            return Compatibility.SAME;

        return Compatibility.OVERLAP;
    }
    
    public String id() {
        return id;
    }
    
    public String name() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Show show = (Show) o;
        return Double.compare(show.start, start) == 0 && Double.compare(show.end, end) == 0 && Objects.equals(name, show.name);
    }

    @Override
    public String toString() {
        return "Show{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", start=" + start +
                ", end=" + end +
                '}';
    }
}
