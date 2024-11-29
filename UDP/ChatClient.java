import java.io.IOException;
import java.net.*;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ChatClient {
    private static final int SERVER_PORT = 12345;
    private String name;
    private DatagramSocket socket;
    private InetAddress serverAddress;
    private volatile boolean running = true; // Flag to control the client's running state

    public ChatClient(String name, String serverHost) throws IOException {
        this.name = name;
        this.socket = new DatagramSocket();
        this.serverAddress = InetAddress.getByName(serverHost);
    }

    public void start() {
        Thread receiveThread = new Thread(new ReceiveMessagesTask());
        receiveThread.start();

        try (Scanner scanner = new Scanner(System.in)) {
            // Send a message indicating the client has joined
            sendMessage("NAME:" + name);
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                sendMessage("exit");
                closeResources();
            }));

            while (running) {
                if (scanner.hasNextLine()) {
                    String message = scanner.nextLine();
                    if (message.equalsIgnoreCase("exit")) {
                        running = false;
                        sendMessage("exit");
                    } else {
                        sendMessage(name + ": " + message);
                    }
                }
            }
        } catch (NoSuchElementException e) {
            System.out.println("Client input stream closed.");
        } finally {
            closeResources();
        }
    }

    private void sendMessage(String message) {
        if (!socket.isClosed()) {
            byte[] buffer = message.getBytes();
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, serverAddress, SERVER_PORT);
            try {
                socket.send(packet);
            } catch (IOException e) {
                System.out.println("Failed to send message: " + e.getMessage());
            }
        }
    }

    private class ReceiveMessagesTask implements Runnable {
        @Override
        public void run() {
            byte[] buffer = new byte[1024];
            while (running) {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                try {
                    socket.receive(packet);
                    String message = new String(packet.getData(), 0, packet.getLength());
                    System.out.println(message);
                } catch (IOException e) {
                    if (running) { // Only print if the client is still running
                        System.out.println("Connection to server lost.");
                    }
                    break;
                }
            }
        }
    }

    private void closeResources() {
        if (socket != null && !socket.isClosed()) {
            socket.close();
        }
    }

    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println("Usage: java ChatClient <name> <server host>");
            System.exit(1);
        }

        String name = args[0];
        String serverHost = args[1];

        try {
            ChatClient client = new ChatClient(name, serverHost);
            client.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
