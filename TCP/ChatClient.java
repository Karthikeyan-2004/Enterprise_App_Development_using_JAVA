import java.io.*;
import java.net.*;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ChatClient {
    private String name;
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    public ChatClient(String name, String serverAddress, int serverPort) throws IOException {
        this.name = name;
        this.socket = new Socket(serverAddress, serverPort);
        this.out = new PrintWriter(socket.getOutputStream(), true);
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public void start() {
        new Thread(new ReceiveMessagesTask()).start();
        try (Scanner scanner = new Scanner(System.in)) {
            // Send client's name to the server with a special prefix
            out.println("NAME:" + name);
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                out.println(name + " has left the chat.");
                closeResources();
            }));

            while (true) {
                if (scanner.hasNextLine()) {
                    String message = scanner.nextLine();
                    out.println(message);
                } else {
                    break;
                }
            }
        } catch (NoSuchElementException e) {
            System.out.println("Client input stream closed.");
        } finally {
            closeResources();
        }
    }

    private class ReceiveMessagesTask implements Runnable {
        @Override
        public void run() {
            String message;
            try {
                while ((message = in.readLine()) != null) {
                    System.out.println(message);
                }
            } catch (IOException e) {
                System.out.println("Connection to server lost.");
            } finally {
                closeResources();
            }
        }
    }

    private void closeResources() {
        try {
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
            if (out != null) {
                out.close();
            }
            if (in != null) {
                in.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        if (args.length != 3) {
            System.err.println("Usage: java ChatClient <name> <server address> <server port>");
            System.exit(1);
        }

        String name = args[0];
        String serverAddress = args[1];
        int serverPort = Integer.parseInt(args[2]);

        try {
            ChatClient client = new ChatClient(name, serverAddress, serverPort);
            client.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
