package com.anton.client.commandLine;



import com.anton.client.utils.ScannerManager;

import java.util.Scanner;

/**
 * Class for output from console
 */

public class ConsoleInput implements UserInput{
    private static final Scanner userScanner= ScannerManager.getUserInputScanner();

    @Override
    public String nextLine() {
        return userScanner.nextLine();
    }
}
