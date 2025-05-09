import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Process extends Thread {
    private int id;
    private TokenRing ring;

    public Process(int id, TokenRing ring) {
        this.id = id;
        this.ring = ring;
    }

    public void run() {
        while (true) {
            try {
                ring.requestToken(id);
                criticalSection();
                ring.releaseToken(id);
                Thread.sleep(2000); // Simulate waiting time
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void criticalSection() {
        System.out.println("Process " + id + " is in the critical section.");
        try {
            Thread.sleep(1000); // Simulate some work in the critical section
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Process " + id + " is leaving the critical section.");
    }
}

class TokenRing {
    private int numProcesses;
    private volatile int tokenHolder; // The process currently holding the token
    private Lock lock = new ReentrantLock();

    public TokenRing(int numProcesses) {
        this.numProcesses = numProcesses;
        this.tokenHolder = 0; // Initial token is with process 0
    }

    public void requestToken(int processId) throws InterruptedException {
        while (processId != tokenHolder) {
            Thread.sleep(500); // Wait for the token
        }
        lock.lock(); // Acquire the lock
    }

    public void releaseToken(int processId) {
        lock.unlock(); // Release the lock
        tokenHolder = (processId + 1) % numProcesses; // Pass token to next process
        System.out.println("Token passed to Process " + tokenHolder);
    }
}

public class TokenRingMain {
    public static void main(String[] args) {
        int numProcesses = 5; // Number of processes in the ring
        TokenRing ring = new TokenRing(numProcesses);

        // Create and start processes
        for (int i = 0; i < numProcesses; i++) {
            new Process(i, ring).start();
        }
    }
}
