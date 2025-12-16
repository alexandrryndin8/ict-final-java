import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

//        PaymentRepository repo = new PostgresPaymentRepository();
        PaymentRepository repo = new MongoPaymentRepository();

        while (true) {
            System.out.println("\n=== Payment Management ===");
            System.out.println("1. Create Payment");
            System.out.println("2. Read Payment by ID");
            System.out.println("3. Read All Payments");
            System.out.println("4. Update Payment");
            System.out.println("5. Delete Payment");
            System.out.println("6. Exit");
            System.out.print("Choose option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {

                case 1 -> {
                    System.out.print("Enter payment id: ");
                    int id = scanner.nextInt();

                    System.out.print("Enter user id: ");
                    int userId = scanner.nextInt();

                    System.out.print("Enter amount: ");
                    double amount = scanner.nextDouble();
                    scanner.nextLine();

                    System.out.print("Enter provider: ");
                    String provider = scanner.nextLine();

                    System.out.print("Enter status: ");
                    String status = scanner.nextLine();

                    Payment p = new Payment(id, userId, amount, provider, status);
                    repo.create(p);
                    System.out.println("Payment created.");
                }

                case 2 -> {
                    System.out.print("Enter payment id: ");
                    int id = scanner.nextInt();
                    Payment p = repo.read(id);
                    System.out.println(p != null ? p : "Payment not found.");
                }

                case 3 -> {
                    List<Payment> all = repo.readAll();
                    if (all.isEmpty()) {
                        System.out.println("No payments found.");
                    } else {
                        all.forEach(System.out::println);
                    }
                }

                case 4 -> {
                    System.out.print("Enter payment id to update: ");
                    int id = scanner.nextInt();

                    System.out.print("Enter new user id: ");
                    int userId = scanner.nextInt();

                    System.out.print("Enter new amount: ");
                    double amount = scanner.nextDouble();
                    scanner.nextLine();

                    System.out.print("Enter new provider: ");
                    String provider = scanner.nextLine();

                    System.out.print("Enter new status: ");
                    String status = scanner.nextLine();

                    Payment updated = new Payment(id, userId, amount, provider, status);
                    repo.update(updated);

                    System.out.println("Payment updated.");
                }

                case 5 -> {
                    System.out.print("Enter payment id to delete: ");
                    int id = scanner.nextInt();
                    repo.delete(id);
                    System.out.println("Payment deleted.");
                }

                case 6 -> {
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                }

                default -> System.out.println("Invalid option.");
            }
        }
    }
}
