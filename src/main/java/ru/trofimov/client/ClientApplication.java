package ru.trofimov.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

@SpringBootApplication
public class ClientApplication implements CommandLineRunner {
    private static Logger log = LoggerFactory.getLogger(ClientApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(ClientApplication.class, args);
    }

    @Override
    public void run(String... args) {
        try {
            try(
                Socket clientSocket = new Socket("localhost", 8080);
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {
                do {
                    printAnswerFromServer(in);
                    String input = readInputFromConsole();
                    sendInputToServer(out, input);
                } while (true);
            }
        } catch (IOException e) {
            log.error("Error occurred: " + e);
        }
    }

    private void printAnswerFromServer(BufferedReader in) throws IOException {
        String message = in.readLine();
        System.out.println(message);
    }

    private void sendInputToServer(PrintWriter out, String message) {
        out.println(message);
    }

    private String readInputFromConsole() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        return reader.readLine();
    }
}
