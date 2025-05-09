import StringApp.StringOperations;
import StringApp.StringOperationsHelper;
import org.omg.CORBA.*;
import org.omg.CosNaming.*;
import org.omg.CosNaming.NamingContextPackage.*;

import java.util.Scanner;

public class StringClient {
    public static void main(String[] args) {
        try {
            // Initialize ORB
            ORB orb = ORB.init(args, null);

            // Get reference to Naming Service
            org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);

            // Resolve the StringOperations object
            StringOperations strOp = StringOperationsHelper.narrow(ncRef.resolve_str("StringOperations"));

            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.println("\n--- String Operations Menu ---");
                System.out.println("1. Concatenate Strings");
                System.out.println("2. String Length");
                System.out.println("3. Reverse String");
                System.out.println("4. Exit");
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1:
                        System.out.print("Enter first string: ");
                        String str1 = scanner.nextLine();
                        System.out.print("Enter second string: ");
                        String str2 = scanner.nextLine();
                        System.out.println("Result: " + strOp.concat(str1, str2));
                        break;

                    case 2:
                        System.out.print("Enter a string: ");
                        String str = scanner.nextLine();
                        System.out.println("Length: " + strOp.length(str));
                        break;

                    case 3:
                        System.out.print("Enter a string: ");
                        String toReverse = scanner.nextLine();
                        System.out.println("Reversed: " + strOp.reverse(toReverse));
                        break;

                    case 4:
                        System.out.println("Exiting...");
                        scanner.close();
                        return;

                    default:
                        System.out.println("Invalid choice. Try again.");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
