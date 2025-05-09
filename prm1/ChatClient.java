import java.rmi.Naming;
import java.util.Scanner;

public class ChatClient {
    public static void main(String[] args) {
        try {
            ChatService service = (ChatService) Naming.lookup("rmi://localhost:5000/ChatService");

            Scanner scanner = new Scanner(System.in);

            System.out.print("Enter your name: ");
            String clientName = scanner.nextLine();

            while (true) {
                System.out.print("Enter message: ");
                String message = scanner.nextLine();
                if (message.equalsIgnoreCase("exit")) break;

                String response = service.sendMessage(clientName, message);
                System.out.println("Server: " + response);
            }
            
            scanner.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
