import java.util.Scanner;

class Process {
    int id;
    boolean isCoordinator;
    boolean isActive;

    public Process(int id) {
        this.id = id;
        this.isActive = true;
        this.isCoordinator = false;
    }

    public void crash() {
        isActive = false;
        isCoordinator = false;
        System.out.println("Process " + id + " has crashed.");
    }

    public void recover() {
        isActive = true;
        System.out.println("Process " + id + " has recovered and is starting an election.");
    }
}

public class BullyAlgorithm {
    private Process[] processes;
    private int coordinator;

    public BullyAlgorithm(int numberOfProcesses) {
        processes = new Process[numberOfProcesses];

        // Assign process IDs (1 to N)
        for (int i = 0; i < numberOfProcesses; i++) {
            processes[i] = new Process(i + 1);
        }

        // Initially, the highest process is the coordinator
        coordinator = numberOfProcesses;
        processes[coordinator - 1].isCoordinator = true;
    }

    public void startElection(int initiator) {
        System.out.println("\nProcess " + initiator + " has started an election.");
        boolean leaderChanged = false;

        for (int i = initiator; i < processes.length; i++) {
            if (processes[i].isActive) {
                System.out.println("Process " + initiator + " sends election message to " + (i + 1));

                // If a higher process is active, it responds and takes over
                if (i + 1 > initiator) {
                    System.out.println("Process " + (i + 1) + " responds OK.");
                    leaderChanged = true;
                    startElection(i + 1); // The higher process continues the election
                    return;
                }
            }
        }

        // If no higher process is active, the initiator becomes the coordinator
        processes[initiator - 1].isCoordinator = true;
        coordinator = initiator;
        System.out.println("Process " + initiator + " becomes the new coordinator.");
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
                for (int i = processes.length - 1; i >= 0; i--) {
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

        BullyAlgorithm bully = new BullyAlgorithm(numProcesses);
        bully.displayCoordinator();

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
                bully.startElection(initiator);
            } else if (choice == 2) {
                System.out.print("Enter process number to crash: ");
                int crashProcess = scanner.nextInt();
                bully.crashProcess(crashProcess);
            } else if (choice == 3) {
                System.out.print("Enter process number to recover: ");
                int recoverProcess = scanner.nextInt();
                bully.recoverProcess(recoverProcess);
            } else if (choice == 4) {
                System.out.println("Exiting...");
                break;
            } else {
                System.out.println("Invalid choice! Try again.");
            }

            bully.displayCoordinator();
        }

        scanner.close();
    }
}
