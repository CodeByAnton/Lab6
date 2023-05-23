package com.anton.client;



import com.anton.client.commandLine.BlankConsole;
import com.anton.client.commandLine.Console;
import com.anton.client.commandLine.Printable;
import com.anton.client.utils.Client;
import com.anton.client.utils.RuntimeManager;
import com.anton.common.exceptions.IllegalArgumentsException;


import java.net.Socket;
import java.util.Scanner;

public class ClientApp {
    private static String host;
    private static int port;
    private static Printable console=new BlankConsole();

    public static boolean isServerRunning(String host, int port) {
        try (Socket socket = new Socket(host, port)) {
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean parseHostPort(String[] args){
        try{
            if(args.length != 2) throw new IllegalArgumentsException("Передайте хост и порт в аргументы ");
            host = args[0];
            try {
                port = Integer.parseInt(args[1]);
            } catch (NumberFormatException e){
                console.printError("Порт должен быть числом типа Integer");
            }
            if(port < 0) throw new IllegalArgumentsException("Порт должен быть натуральным числом");
            return true;
        } catch (IllegalArgumentsException e) {
            console.printError(e.getMessage());
        }
        return false;
    }
    public static void main(String[] args){



            if (!parseHostPort(args)) return;

            console = new Console();
            if (isServerRunning(host,port)){
                Client client = new Client(host, port, 2000, 5, console);
                console.println("Клиент успешно создан");
                new RuntimeManager(console, new Scanner(System.in), client).interactiveMode();
            }
            else {
                console.printError("Введите другой host и port, тк на данных нет сервера");
            }



    }
}
