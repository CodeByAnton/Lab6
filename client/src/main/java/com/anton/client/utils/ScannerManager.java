package com.anton.client.utils;

import java.util.Scanner;

/**
 * Class keeping scanner for program
 */

public class ScannerManager {
    public static Scanner userInputScanner=new Scanner(System.in);
    public static Scanner getUserInputScanner(){
        return userInputScanner;
    }
}
