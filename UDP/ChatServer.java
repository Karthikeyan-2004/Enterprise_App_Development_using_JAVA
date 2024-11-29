import java.io.IOException;
import java.net.*;
import java.util.HashSet;
import java.util.Set;

public class ChatServer {
    private static final int PORT = 12345;
    private static Set<InetSocketAddress> clientAddresses = new HashSet<>();

    public static void main(String[] args) {
        try (DatagramSocket socket = new DatagramSocket(PORT)) {
            byte[] buffer = new byte[1024];

            System.out.println("Chat server started on port " + PORT + "...");

            while (true) {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);

                String message = new String(packet.getData(), 0, packet.getLength());
                InetSocketAddress clientAddress = new InetSocketAddress(packet.getAddress(), packet.getPort());

                if (message.startsWith("NAME:")) {
                    if (!clientAddresses.contains(clientAddress)) {
                        clientAddresses.add(clientAddress);
                        String clientName = message.substring(5);
                        System.out.println(clientName + " has joined the chat.");
                        broadcastMessage(socket, clientAddress, clientName + " has joined the chat.");
                    }
                } else if (message.equals("exit")) {
                    clientAddresses.remove(clientAddress);
                    String clientName = clientAddress.toString();
                    System.out.println(clientName + " has left the chat.");
                    broadcastMessage(socket, clientAddress, clientName + " has left the chat.");
                } else {
                    System.out.println(clientAddress + ": " + message);
                    broadcastMessage(socket, clientAddress, message);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void broadcastMessage(DatagramSocket socket, InetSocketAddress sender, String message) throws IOException {
        byte[] buffer = message.getBytes();
        for (InetSocketAddress clientAddress : clientAddresses) {
            if (!clientAddress.equals(sender)) {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length, clientAddress.getAddress(), clientAddress.getPort());
                socket.send(packet);
            }
        }
    }
}
