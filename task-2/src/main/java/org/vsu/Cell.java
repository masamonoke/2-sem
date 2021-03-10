package org.vsu;

public class Cell<T> {
    public T item;
    Cell<T> next = null;
    Cell<T> prev = null;

    public Cell(T value, Cell<T> prev, Cell<T> next) {
        this.item = value;
        this.prev = prev;
        this.next = next;
    }

    public Cell() {}
}
