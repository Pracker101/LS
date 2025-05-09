import java.rmi.*;
import java.rmi.registry.*;

public class ChatServer {
    public static void main(String[] args) {
        try {
            // Start RMI registry on port 5000
            LocateRegistry.createRegistry(5000);

            // Create and bind the service
            ChatService service = new ChatServiceImpl(); 
            Naming.rebind("rmi://localhost:5000/ChatService", service);

            System.out.println("Chat Server is running on port 5000...");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
