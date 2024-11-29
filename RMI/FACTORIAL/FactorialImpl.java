// FactorialImpl.java
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
public class FactorialImpl extends UnicastRemoteObject implements Factorial {
    protected FactorialImpl() throws RemoteException {
        super();
    }
    public long factorial(int number) throws RemoteException {
        long result = 1;
        for (int i = 1; i <= number; i++) {
            result *= i;
        }
        return result;
    }
    public static void main(String[] args) {
        try {
            FactorialImpl obj = new FactorialImpl();
            java.rmi.Naming.rebind("FactorialService", obj);
            System.out.println("Factorial Server is ready.");
        } catch (Exception e) {
            System.out.println("Server exception: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
