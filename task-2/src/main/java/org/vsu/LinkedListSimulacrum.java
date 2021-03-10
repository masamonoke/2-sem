package org.vsu;

import java.util.*;

public class LinkedListSimulacrum <T> implements Iterable<Cell<T>>, ListSimulacrum<T> {

    //TODO rename to head
    private Cell<T> first = null;
    //TODO rename to butt
    private Cell<T> last = null;
    private int size = 0;

    //TODO
    public LinkedListSimulacrum(Collection<T> asList) {}

    public LinkedListSimulacrum() {}

    public void swap(Cell<T> c1, Cell<T> c2) {
        Cell<T> tmp = new Cell<>();

        tmp.item = c1.item;
        c1.item = c2.item;
        c2.item = tmp.item;

    }

    public Cell<T> getCell(int index) {
        return cell(index);
    }

    private void linkFirst(T value) {
        first = new Cell<>(value, null, first);
        if (size == 0) {
            last = first;
        }
        size++;
    }

    private void linkLast(T value) {
        final Cell<T> l = last;
        final Cell<T> newCell = new Cell<>(value, l, null);
        last = newCell;
        if (l == null) {
            first = newCell;
        } else {
            l.next = newCell;
        }
        size++;
    }

    private void linkBefore(T value, Cell<T> cell) {
        final Cell<T> prev = cell.prev;
        final Cell<T> newNode = new Cell<>(value, prev, cell);
        cell.prev = newNode;
        if (prev == null) {
            first = newNode;
        } else {
            prev.next = newNode;
        }
    }

    public T getFirst() {
        final Cell<T> f = first;
        if (f == null) {
            throw new NoSuchElementException();
        }
        return f.item;
    }

    public T getLast() {
        final Cell<T> l = last;
        if (l == null) {
            throw new NoSuchElementException();
        }
        return first.item;
    }

    public int size() {
        return size;
    }

    public Iterator<Cell<T>> iterator() {
        class ListIterator implements Iterator<Cell<T>> {
            Cell<T> current;

            public ListIterator(Cell<T> first) {
                current = first;
            }

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public Cell<T> next() {
                Cell<T> cur = current;
                current = current.next;
                return cur;
            }
        }
        return new ListIterator(first);
    }

    public boolean add(T value) {
        linkLast(value);
        return true;
    }

    public void clear() {
        first = last = null;
        size = 0;
    }

    public T get(int index) {
        checkIndex(index);
        return cell(index).item;
    }

    public T set(int index, T element) {
        checkIndex(index);
        Cell<T> c = cell(index);
        T oldValue = c.item;
        c.item = element;
        return oldValue;
    }

    public void add(int index, T element) {
        checkIndex(index);

        if (index == size) {
            linkLast(element);
        } else {
            linkBefore(element, cell(index));
        }
    }

    private Cell<T> cell(int index) {
        Cell<T> c;
        if (index < (size >> 1)) {
            c = first;
            for (int i = 0; i < index; i++) {
                c = c.next;
            }
        } else {
            c = last;
            for (int i = size - 1; i > index; i--) {
                c = c.prev;
            }
        }
        return c;
    }
    private void checkIndex(int index) {
        if (!isElementIndex(index)) {
            throw new IndexOutOfBoundsException();
        }
    }
    private boolean isElementIndex(int index) {
        return index >= 0 && index < size;
    }

    public boolean isEmpty() {
        return size == 0;
    }
}
