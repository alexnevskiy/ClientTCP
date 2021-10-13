package client;

import interaction.DataReader;
import interaction.DataWriter;
import protocol.Message;
import protocol.MessageWithFile;

import java.io.IOException;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Scanner;

import static phrases.Phrases.ENTER_NAME;
import static phrases.Phrases.WELCOME;

public class Client {
    private final String address;
    private final int port;
    public static SimpleDateFormat simpleDateFormat;
    private DataReader reader;
    private DataWriter writer;
    private final Scanner scanner;

    public Client(String address, int port) {
        this.address = address;
        this.port = port;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        String pattern = "yyyy-MM-dd'T'HH:mm:ssZ";
        simpleDateFormat = new SimpleDateFormat(pattern);
        try (Socket socket = new Socket(address,port)) {
            try {
                reader = new DataReader(socket.getInputStream());
                writer = new DataWriter(socket.getOutputStream());
            } catch (IOException exception) {
                exception.printStackTrace();
            }

            enterName();
            boolean nameVerified = false;
            while (!nameVerified) {
                if (reader.hasMessage()) {
                    MessageWithFile messageNameVerified = reader.read();
                    if (messageNameVerified.getMessage().getText().contains(WELCOME.getPhrase())) {
                        nameVerified = true;
                    }
                    String messageToConsole =   "<" + messageNameVerified.getMessage().getTime() + "> " +
                                                "[" + messageNameVerified.getMessage().getName() + "] " +
                                                messageNameVerified.getMessage().getText();
                    System.out.println(messageToConsole);
                    if (!nameVerified) {
                        enterName();
                    }
                }
            }

            ClientThread clientThread = new ClientThread(socket, writer, scanner);
            clientThread.start();

            ReceiverThread receiverThread = new ReceiverThread(socket);
            receiverThread.start();

            boolean interrupted = clientThread.isInterrupted();
            while (!interrupted) {
                MessageWithFile message = readMessage();
                if (message != null) {
                    receiverThread.offer(message);
                }

                interrupted = clientThread.isInterrupted();
            }

            receiverThread.interrupt();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void enterName() {
        System.out.print(ENTER_NAME.getPhrase());
        String nickname = scanner.nextLine();
        MessageWithFile loginMessage = new MessageWithFile(
                new Message(null,null, nickname,null,null),
                null);
        writer.write(loginMessage);
    }

    private MessageWithFile readMessage() {
        if (reader.hasMessage()) {
            return reader.read();
        }
        return null;
    }
}
