import mpi.*;

public class SumMPI {
    public static void main(String args[]) throws Exception {
        // Initialize MPI
        MPI.Init(args);
        int rank = MPI.COMM_WORLD.Rank(); // Get process rank
        int size = MPI.COMM_WORLD.Size(); // Get total number of processes

        int N = 100;  // Total elements in array
        int elementsPerProc = N / size;
        int[] arr = new int[N];
        int[] localArr = new int[elementsPerProc];
        int[] localSum = new int[1];
        int[] globalSum = new int[1];

        // Master process initializes the array
        if (rank == 0) {
            for (int i = 0; i < N; i++) {
                arr[i] = i + 1; // Example array with values 1 to N
            }
        }

        // Scatter array parts to different processors
        MPI.COMM_WORLD.Scatter(arr, 0, elementsPerProc, MPI.INT, localArr, 0, elementsPerProc, MPI.INT, 0);

        // Compute local sum
        localSum[0] = 0;
        for (int i = 0; i < elementsPerProc; i++) {
            localSum[0] += localArr[i];
        }

        System.out.println("Process " + rank + " computed local sum: " + localSum[0]);

        // Reduce local sums to global sum at process 0
        MPI.COMM_WORLD.Reduce(localSum, 0, globalSum, 0, 1, MPI.INT, MPI.SUM, 0);

        if (rank == 0) {
            System.out.println("Total Sum: " + globalSum[0]);
        }

        // Finalize MPI
        MPI.Finalize();
    }
}
