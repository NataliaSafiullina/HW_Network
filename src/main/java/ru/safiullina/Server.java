package ru.safiullina;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static final Integer PORT = 8080;

    public static void main(String[] args) {

        // Создаем серверный сокет
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            // После создания серверного сокета, сервер сразу стартует
            System.out.println("Server is running");

            // Ожидаем подключений
            while (true) {
                // Создаем клиентский сокет для взаимодействия с клиентами и инициализируем его через метод accept()
                try (Socket clientSocket = serverSocket.accept();
                     // Получаем поток для вывода информации клиенту
                     PrintWriter printWriter = new PrintWriter(clientSocket.getOutputStream(), true);
                     // Получаем поток входящей информации от клиента
                     BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {

                    // Выводим информацию от клиента в консоль и номер порта
                    int port = clientSocket.getPort();
                    System.out.printf("New connection accepted. Port: %d \n", port);

                    boolean doCycle = true;
                    while (doCycle) {
                        // Наш клиент допустим отправляет одну строку, вычитываем её
                        String infoFromClient = bufferedReader.readLine();
                        System.out.printf("Client has sent: %s \n", infoFromClient);

                        // Разбираем что написал клиент
                        switch (infoFromClient) {
                            case "port":
                                // Отправим номер порта клиенту
                                printWriter.printf("Your port is %d \n", port);
                                break;
                            case "start":
                                printWriter.println("Write your name");
                                break;
                            case "Bye":
                                printWriter.println("Bye!");
                                doCycle = false;
                                break;
                            default:
                                printWriter.printf("What can I help you, %s? \n", infoFromClient);
                        }
                    }


                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
