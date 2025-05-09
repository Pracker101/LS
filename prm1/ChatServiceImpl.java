import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ChatServiceImpl extends UnicastRemoteObject implements ChatService {
    private static final long serialVersionUID = 1L;
    private final ExecutorService executor;

    protected ChatServiceImpl() throws RemoteException {
        super();
        executor = Executors.newCachedThreadPool(); // Thread pool to handle multiple clients
    }

    @Override
    public String sendMessage(String clientName, String message) throws RemoteException {
        executor.execute(() -> { // Runs in a separate thread
            System.out.println("[" + clientName + "]: " + message);
        });
        return "Message received by server!";
    }
}
