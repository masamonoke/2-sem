package org.vsu;

import org.vsu.experimental.PrinterTerminal;

public class Main {
    public static void main(String[] args) {

        PrinterTerminal printerTerminal = new PrinterTerminal();

        Session session = new Session("pocz", "kek-computer", printerTerminal);
        session.shellProcess();

    }

}