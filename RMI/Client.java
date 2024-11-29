import java.rmi.Naming;

public class Client {
    public static void main(String[] args) {
        try {
            // Look up the remote object from the registry
            Hello stub = (Hello) Naming.lookup("rmi://localhost:1099/Hello");
            
            // Call the remote method
            String response = stub.sayHello();
            
            System.out.println("Response: " + response);
        } catch (Exception e) {
            System.out.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
