package client;

import interaction.DataWriter;
import protocol.Message;
import protocol.MessageWithFile;

import java.net.Socket;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Scanner;

import static phrases.Phrases.*;

public class ClientThread extends Thread {
    private final Socket socket;
    private final DataWriter writer;
    private final Scanner scanner;
    private final Queue<MessageWithFile> writerQueue = new ArrayDeque<>();

    public ClientThread(Socket socket, DataWriter writer, Scanner scanner) {
        this.socket = socket;
        this.writer = writer;
        this.scanner = scanner;
    }

    @Override
    public void run() {
        while (true) {
            if (socket.isConnected()) {
                MessageWithFile message = createMessage();
                if (message != null) {
                    writer.write(message);
                    if (message.getMessage().getText().equals(EXIT.getPhrase())) {
                        writer.close();
                        interrupt();
                        break;
                    }
                }
            } else {
                writer.close();
                interrupt();
            }
        }
    }

    private MessageWithFile createMessage() {
        if (scanner.hasNext()) {
            String text = scanner.nextLine();
            if (text.trim().length() > 0) {
                return new MessageWithFile(new Message(null,null, text,null,null), null);
            }
        }
        return null;
    }
}
