package org.vsu;

public class Solver <T extends Comparable<T>> {

    public boolean swapMinMax(ListSimulacrum<T> list) {

        if (list.isEmpty()) {
            return false;
        }

        T max = null;
        T min = null;

        boolean isFirstMin = false;
        boolean isFirstValueGot = false;

        Cell<T> maxCell = null;
        Cell<T> minCell = null;

        for (Cell<T> cell : list) {

            if (!isFirstValueGot) {
                min = cell.item;
                max = cell.item;
                minCell = cell;
                maxCell = cell;
                isFirstValueGot = true;
            }

            if (cell.item.compareTo(min) < 0 && !isFirstMin) {
                min = cell.item;
                minCell = cell;
                isFirstMin = true;
            }

            if (cell.item.compareTo(max) > 0) {
                max = cell.item;
                maxCell = cell;
            }
        }

        if (maxCell != null && minCell != null) {
            list.swap(maxCell, minCell);
        }

        return true;
    }
}
