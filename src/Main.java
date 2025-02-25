import java.util.Scanner;

public class Main {
    private static PasswordManager manager = new PasswordManager();
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter master password: ");
        String masterPassword = scanner.nextLine();
        
        if (!manager.loadPasswords(masterPassword)) {
            System.out.println("Incorrect master password or file corrupt!");
            return;
        }
        
        while (true) {
            System.out.println("\nOptions: [1] Add Password [2] Retrieve [3] Exit");
            int choice = scanner.nextInt();
            scanner.nextLine();
            
            switch (choice) {
                case 1:
                    System.out.print("Enter site: ");
                    String site = scanner.nextLine();
                    System.out.print("Enter password: ");
                    String password = scanner.nextLine();
                    manager.addPassword(site, password);
                    break;
                case 2:
                    System.out.print("Enter site: ");
                    site = scanner.nextLine();
                    System.out.println("Password: " + manager.getPassword(site));
                    break;
                case 3:
                    manager.savePasswords();
                    System.out.println("Exiting...");
                    return;
            }
        }
    }
}
