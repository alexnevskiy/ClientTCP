package client;

import protocol.MessageWithFile;

import java.net.Socket;
import java.util.ArrayDeque;
import java.util.Queue;

public class ReceiverThread extends Thread {
    private final Socket socket;
    private final Queue<MessageWithFile> receiverQueue = new ArrayDeque<>();

    public ReceiverThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        while (true) {
            if (socket.isConnected()) {
                if (!receiverQueue.isEmpty()) {
                    MessageWithFile message = receiverQueue.poll();
                    readMessage(message);
                } else {
                    interrupt();
                }
            }
        }
    }

    public void offer(MessageWithFile messageWithFile) {
        receiverQueue.offer(messageWithFile);
    }

    private void readMessage(MessageWithFile message) {
        String text =   "<" + message.getMessage().getTime() + "> " +
                        "[" + message.getMessage().getName() + "] " +
                        message.getMessage().getText();
        System.out.println(text);
    }
}
