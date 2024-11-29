import java.rmi.Naming;

public class Server {
    public static void main(String[] args) {
        try {
            // Create and export a remote object
            HelloImpl obj = new HelloImpl();
            
            // Bind the remote object in the registry with the name "Hello"
            Naming.rebind("rmi://localhost:1099/Hello", obj);
            
            System.out.println("Hello Server is ready.");
        } catch (Exception e) {
            System.out.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
