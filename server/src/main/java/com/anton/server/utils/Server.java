package com.anton.server.utils;



import com.anton.common.network.Request;
import com.anton.common.network.Response;
import com.anton.server.exceptions.ConnectionException;
import com.anton.server.exceptions.OpenServerException;
import com.anton.server.managers.CollectionManager;
import com.anton.server.managers.FileManager;

import java.io.*;
import java.net.*;

import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class Server {
    private int port;
    private int soTimeout;
    private Printable console;
    private ServerSocketChannel ss;
    private SocketChannel socketChannel;
    private RequestHandler requestHandler;

    static final Logger serverLogger = Logger.getLogger(CollectionManager.class.getName());

    private FileManager fileManager;

    public Server(int port, int soTimeout, RequestHandler handler, FileManager fileManager) {
        this.port = port;
        this.soTimeout = soTimeout;
        this.console = new BlankConsole();
        this.requestHandler = handler;
        this.fileManager = fileManager;
    }

    public void run() {
        try {
            openServerSocket();
            serverLogger.info("Создание соединение с клиентом");
            while (true) {
                try {
                    if (System.in.available() > 0) {
                        BufferedReader scanner = new BufferedReader(new InputStreamReader(System.in));
                        String line = scanner.readLine();
                        if (line.equals("save") || line.equals("s")) {
                            fileManager.saveObjects();

                        }
                    }
                } catch (IOException ignored) {
                }
                try (SocketChannel clientSocket = connectToClient()) {
                    if (!processClientRequest(clientSocket)) break;
                } catch (ConnectionException | SocketTimeoutException ignored) {
                } catch (IOException exception) {
                    console.printError("Произошла ошибка при попытке завершить соединение с клиентом!");
                    serverLogger.warning("Произошла ошибка при попытке завершить соединение с клиентом!");
                }
            }
            stop();
            serverLogger.info("Соединение закрыто");
        } catch (OpenServerException e) {
            console.printError("Сервер не может быть запущен");
            serverLogger.severe("Сервер не может быть запущен");
        }
    }

    private void openServerSocket() throws OpenServerException {
        try {
            SocketAddress socketAddress = new InetSocketAddress(port);
            serverLogger.fine("Создан сокет");
            ss = ServerSocketChannel.open();
            serverLogger.fine("Создан канал");
            ss.bind(socketAddress);
            serverLogger.fine("Открыт канал сокет");
        } catch (IllegalArgumentException exception) {
            console.printError("Порт '" + port + "' находится за пределами возможных значений!");
            serverLogger.warning("Порт находится за пределами возможных значений");
            throw new OpenServerException();
        } catch (IOException exception) {
            serverLogger.warning("Произошла ошибка при попытке использовать порт");
            console.printError("Произошла ошибка при попытке использовать порт '" + port + "'!");
            throw new OpenServerException();
        }
    }

    private SocketChannel connectToClient() throws ConnectionException, SocketTimeoutException {
        try {
            ss.socket().setSoTimeout(soTimeout);
            socketChannel = ss.accept();
            console.println("Соединение с клиентом успешно установлено.");
            serverLogger.info("Соединение с клиентом успешно установлено.");
            return socketChannel;
        } catch (SocketTimeoutException exception) {
            throw new SocketTimeoutException();
        } catch (IOException exception) {
            serverLogger.severe("Произошла ошибка при соединении с клиентом!");
            throw new ConnectionException();
        }
    }

    private boolean processClientRequest(SocketChannel clientSocket) {
        Request userRequest = null;
        Response responseToUser = null;
        try (ObjectInputStream clientReader = new ObjectInputStream(clientSocket.socket().getInputStream());
             ObjectOutputStream clientWriter = new ObjectOutputStream(clientSocket.socket().getOutputStream())) {
            serverLogger.info("Открыты потоки ввода вывода");
            do {
                userRequest = (Request) clientReader.readObject();
                serverLogger.info("Получен реквест с командой");
                console.println(userRequest.toString());
                responseToUser = requestHandler.handle(userRequest);
                clientWriter.writeObject(responseToUser);
                serverLogger.info("Отправлен ответ");
                clientWriter.flush();
            } while (true);
        } catch (ClassNotFoundException exception) {
            console.printError("Произошла ошибка при чтении полученных данных!");
            serverLogger.severe("Произошла ошибка при чтении полученных данных!");
        } catch (InvalidClassException | NotSerializableException exception) {

            console.printError("Произошла ошибка при отправке данных на клиент!");
            serverLogger.warning("Произошла ошибка при отправке данных на клиент!");
        } catch (IOException exception) {
            if (userRequest == null) {
                console.printError("Непредвиденный разрыв соединения с клиентом!");
                //serverLogger.warning("Непредвиденный разрыв соединения с клиентом!");
            }
        }

        return true;
    }

    private void stop() {
        try {
            if (socketChannel != null) socketChannel.close();
            if (ss != null) ss.close();
            serverLogger.info("Все соединения закрыты");
        } catch (IOException exception) {
            console.printError("Произошла ошибка при завершении работы сервера!");
            serverLogger.severe("Произошла ошибка при завершении работы сервера!");
        }
    }
}