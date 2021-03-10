package org.vsu;

public class Main {

    public static void main(String[] args) {
        ListSimulacrum<Integer> list = new LinkedListSimulacrum<>();

        list.add(0);
        list.add(1);
        list.add(2);
        list.add(124);
        list.add(4);

        Solver<Integer> solver = new Solver<>();
        if (solver.swapMinMax(list)) {

            for (Cell<Integer> cell : list) {
                System.out.println(cell.item);
            }

        }

    }

}
