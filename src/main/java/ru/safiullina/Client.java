package ru.safiullina;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Client {
    private static final String SERVER_IP = "netology.homework";

    public static void main(String[] args) {

        List<String> messages = new ArrayList<>();
        messages.add("start");
        messages.add("Ben");
        messages.add("port");
        messages.add("Bye");

        // Создаем клиентский сокет, и потоки на чтение и запись
        try (Socket clientSocket = new Socket(SERVER_IP, Server.PORT);
             PrintWriter printWriter = new PrintWriter(clientSocket.getOutputStream(), true);
             BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))
        ) {

            // Отправляем разные команды серверу
            for (String message : messages) {
                System.out.printf("I've said: %s \n", message);
                // Метод потока на запись отправляет сообщение
                printWriter.println(message);
                // Из потока на чтение получаем строку, которую прислал сервер
                System.out.printf("Server has sent: %s \n", bufferedReader.readLine());
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
