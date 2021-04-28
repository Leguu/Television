/*
 * Asil Erturan (40164714)
 * COMP249
 * Assignment #4
 * 2021-04-24
 */

package television;

import java.util.Iterator;
import java.util.Objects;

/**
 * A ShowList linked list class that contains shows.
 */
public class ShowList implements Iterable<Show> {
    private ShowNode head = new ShowNode();
    private int size = 0;

    public ShowList() {
    }

    public ShowList(ShowList other) {
        this.head = new ShowNode(other.head);
        this.size = other.size;
    }

    /**
     * Adds an object to the start of the list.
     * @param o The show to add.
     */
    public void prepend(Show o) {
        insert(o, 0);
    }

    /**
     * Inserts a show to a specific index.
     * @param show The show to insert.
     * @param i Where to insert it.
     * @throws IndexOutOfBoundsException When i > list size.
     */
    public void insert(Show show, int i) {
        if (i > size) throw new IndexOutOfBoundsException("index " + i + " is out of bounds for length " + size);

        var current = head;
        for (; i > 0; i--) current = current.next;

        current.next = new ShowNode(show, current.next);
        size += 1;
    }

    /**
     * Gets a show at some point
     * @param i The show index to get.
     * @return The show at index i.
     */
    public Show get(int i) {
        if (i > size) throw new IndexOutOfBoundsException("index " + i + " is out of bounds for length " + size);
        var current = head;
        for (; i >= 0; i--) current = current.next;
        return current.data;
    }

    /**
     * Remove and return a show at the index.
     * @param i The index to remove.
     * @return The show at that index.
     */
    public Show remove(int i) {
        if (i > size - 1) throw new IndexOutOfBoundsException("index " + i + " is out of bounds for length " + size);

        var current = head;
        for (; i > 0; i--) current = current.next;

        var temp = current;
        current.next = current.next.next;
        size -= 1;
        return temp.data;
    }

    /**
     * Remove the first item in the list.
     * @return The show that was removed.
     */
    public Show pop() {
        return remove(0);
    }

    /**
     * Finds a show inside the showlist by id.
     * @param id The id to wind in the list.
     * @return The show, if found, or null.
     */
    public Show find(String id) {
        for (Show show : this)
            if (show.id().equals(id)) return show;
        return null;
    }

    /**
     * Whether a show exists in the list.
     * @param id The id to check.
     * @return Whether the show exists in the list.
     */
    public boolean contains(String id) {
        return find(id) != null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShowList showList = (ShowList) o;
        return size == showList.size && Objects.equals(head, showList.head);
    }

    public int size() {
        return size;
    }

    @Override
    public Iterator<Show> iterator() {
        return new ShowListIterator(this);
    }

    /**
     * Private Node class for ShowList.
     */
    private static class ShowNode {
        private Show data = null;
        private ShowNode next = null;

        public ShowNode() {
        }

        public ShowNode(Show data) {
            this.data = data;
        }

        public ShowNode(Show data, ShowNode next) {
            this.data = data;
            this.next = next;
        }

        public ShowNode(ShowNode other) {
            this.data = (other.data == null) ? null : new Show(other.data);
            this.next = (other.next == null) ? null : new ShowNode(other.next);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ShowNode showNode = (ShowNode) o;
            return Objects.equals(data, showNode.data) && Objects.equals(next, showNode.next);
        }
    }

    /**
     * An iterator for ShowList, to enable foreach loops.
     */
    private static class ShowListIterator implements Iterator<Show> {
        private ShowNode current;

        public ShowListIterator(ShowList list) {
            current = list.head;
        }

        @Override
        public boolean hasNext() {
            return current.next != null;
        }

        @Override
        public Show next() {
            current = current.next;
            return current.data;
        }
    }
}
