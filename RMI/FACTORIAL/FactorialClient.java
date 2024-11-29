// FactorialClient.java
import java.rmi.Naming;
import java.util.Scanner;
public class FactorialClient {
    public static void main(String[] args) {
        try {
            Factorial stub = (Factorial) Naming.lookup("rmi://localhost/FactorialService");
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter a number to find its factorial: ");
            int number = scanner.nextInt();
            long result = stub.factorial(number);
            System.out.println("Factorial of " + number + " is " + result);
            scanner.close();
        } catch (Exception e) {
            System.out.println("Client exception: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
