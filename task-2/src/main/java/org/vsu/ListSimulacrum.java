package org.vsu;

import java.util.Iterator;

public interface ListSimulacrum<T> extends Iterable<Cell<T>>{

    public T getFirst();

    public T getLast();

    public int size();

    public Iterator<Cell<T>> iterator();

    public boolean add(T value);

    public void clear();

    public T get(int index);

    public T set(int index, T element);

    public void add(int index, T element);

    public boolean isEmpty();

    void swap(Cell<T> c1, Cell<T> c2);
}
