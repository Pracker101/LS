import java.util.*;

class Process {
    int id;
    boolean isActive;

    public Process(int id) {
        this.id = id;
        this.isActive = true;
    }

    public void crash() {
        isActive = false;
        System.out.println("Process " + id + " has crashed.");
    }

    public void recover() {
        isActive = true;
        System.out.println("Process " + id + " has recovered.");
    }
}

public class RingAlgorithm {
    private Process[] processes;
    private int coordinator;

    public RingAlgorithm(int numProcesses) {
        processes = new Process[numProcesses];

        // Assign process IDs (1 to N)
        for (int i = 0; i < numProcesses; i++) {
            processes[i] = new Process(i + 1);
        }

        // Initially, the highest process is the coordinator
        coordinator = numProcesses;
    }

    public void startElection(int initiator) {
        System.out.println("\nProcess " + initiator + " has started an election.");
        List<Integer> electionList = new ArrayList<>();

        int current = initiator - 1; // Convert to 0-based index
        do {
            if (processes[current].isActive) {
                electionList.add(processes[current].id);
                System.out.println("Process " + processes[current].id + " adds itself to the election list.");
            }
            current = (current + 1) % processes.length; // Move to next process in the ring
        } while (current != initiator - 1); // Stop when it returns to initiator

        // Find the highest ID in the election list
        int newCoordinator = Collections.max(electionList);
        coordinator = newCoordinator;
        System.out.println("Process " + newCoordinator + " wins the election and is the new coordinator.");

        // Announce new coordinator
        announceCoordinator();
    }

    private void announceCoordinator() {
        System.out.println("Process " + coordinator + " announces itself as the new coordinator.");
    }

    public void crashProcess(int processNumber) {
        if (processNumber > 0 && processNumber <= processes.length) {
            processes[processNumber - 1].crash();
            if (coordinator == processNumber) {
                System.out.println("\nCoordinator " + coordinator + " has crashed! Starting new election.");
                for (int i = 0; i < processes.length; i++) {
                    if (processes[i].isActive) {
                        startElection(processes[i].id);
                        return;
                    }
                }
            }
        }
    }

    public void recoverProcess(int processNumber) {
        if (processNumber > 0 && processNumber <= processes.length) {
            processes[processNumber - 1].recover();
            startElection(processNumber);
        }
    }

    public void displayCoordinator() {
        System.out.println("\nCurrent Coordinator: " + coordinator);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter number of processes: ");
        int numProcesses = scanner.nextInt();

        RingAlgorithm ring = new RingAlgorithm(numProcesses);
        ring.displayCoordinator();

        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1. Start Election");
            System.out.println("2. Crash a Process");
            System.out.println("3. Recover a Process");
            System.out.println("4. Exit");
            System.out.print("Enter choice: ");
            int choice = scanner.nextInt();

            if (choice == 1) {
                System.out.print("Enter process number to start election: ");
                int initiator = scanner.nextInt();
                ring.startElection(initiator);
            } else if (choice == 2) {
                System.out.print("Enter process number to crash: ");
                int crashProcess = scanner.nextInt();
                ring.crashProcess(crashProcess);
            } else if (choice == 3) {
                System.out.print("Enter process number to recover: ");
                int recoverProcess = scanner.nextInt();
                ring.recoverProcess(recoverProcess);
            } else if (choice == 4) {
                System.out.println("Exiting...");
                break;
            } else {
                System.out.println("Invalid choice! Try again.");
            }

            ring.displayCoordinator();
        }

        scanner.close();
    }
}
