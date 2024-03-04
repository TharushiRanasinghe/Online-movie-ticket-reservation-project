import java.util.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Theatre {
    static ArrayList<Ticket> tickets = new ArrayList<Ticket>();
    private static int row;
    private static int seat;
    public static void main(String[] args) {

        int[][] row_seats = new int[3][];
        row_seats[0] = new int[12];
        row_seats[1] = new int[16];
        row_seats[2] = new int[20];

        //initialize all thr row_seats as free
        for (int i = 0; i < row_seats.length; i++) {
            for (int j = 0; j < row_seats[i].length; j++) {
                row_seats[i][j] = 0;
            }
        }
        System.out.println("Welcome to the New Theatre");

        //Scanner object-to read user input
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("-------------------------------------------------");
            System.out.println("Please select an option : ");
            System.out.println("1) Buy a ticket");
            System.out.println("2) Print seating area");
            System.out.println("3) Cancel ticket");
            System.out.println("4) List available seats");
            System.out.println("5) Save to file");
            System.out.println("6) Load from file");
            System.out.println("7) Print ticket information and total price");
            System.out.println("8) Sort tickets by price");
            System.out.println("0) Quit");
            System.out.println("-------------------------------------------------");
            System.out.println("Enter Option:");

            int option = scanner.nextInt();
            scanner.nextLine(); //new line character

            if (option == 0) {
                break;
            } else if (option == 1) {
                buy_ticket(row_seats);
            } else if (option == 2) {
                print_seating_area(row_seats);
            } else if (option == 3) {
                cancel_ticket(row_seats);
            } else if (option == 4) {
                show_available(row_seats);
            } else if (option == 5) {
                save(row_seats);
            } else if (option == 6) {
                load(row_seats);
            } else if (option == 7) {
                show_tickets_info();
            } else if (option == 8) {
                sort_tickets(tickets);
            } else {
                System.out.println("Invalid option, please try again.");
                continue;
            }
        }

        scanner.close();
    }
    public static void buy_ticket(int[][] row_seats) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            try {
                String name;
                String surname;
                String email;
                double price = 0;

                System.out.println("Enter your name:");
                name = scanner.nextLine();
                if (!name.matches("[a-zA-Z]+")) {
                    System.out.println("Name should only contain letters. Please try again.");
                    continue;
                }
                while (true) {
                    System.out.println("Enter your surname:");
                    surname = scanner.nextLine();
                    if (!surname.matches("[a-zA-Z]+")) {
                        System.out.println("surname should only contain letters. Please try again.");
                        continue;
                    }
                    while (true) {
                        System.out.println("Enter your email:");
                        email = scanner.nextLine();
                        if (!email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
                            System.out.println("Please enter a valid email address.");
                            continue;
                        }
                            while (true) {
                                try {
                                    System.out.println("Enter price:");
                                    price = scanner.nextDouble();
                                    if (price < 0) {
                                        System.out.println("Price should be a positive number.");
                                        continue;
                                    } else {
                                        break;
                                    }
                                } catch (InputMismatchException e) {
                                    System.out.println("Enter valid price");
                                    scanner.next();
                                }
                            }
                                while(true){
                                    // input row number that user want
                                    System.out.print("Please enter row number between 1 to 3: ");
                                    row = scanner.nextInt();
                                    // check that entered row number is valid
                                    if (row < 1 || row > row_seats.length) {
                                        System.out.println("Error: This row number does not exist. Please select 1-3");
                                        continue;
                                    }
                                    System.out.print("Please enter seat number between 1 to " + row_seats[row - 1].length + ": ");
                                    seat = scanner.nextInt();
                                    // check that entered seat number is valid
                                    if (seat < 1 || seat > row_seats[row - 1].length) {
                                        System.out.println("Error: This seat number does not exist. Enter correct number");
                                    }
                                    // check that the user entered seat is available
                                    else if (row_seats[row - 1][seat - 1] == 1) {
                                        System.out.println("Sorry! This seat is already taken. Choose another seat");
                                    }
                                    // record the seat as occupied
                                    else {
                                        row_seats[row - 1][seat - 1] = 1;
                                        System.out.println("Thank you for your purchase! Your seat has been booked.");
                                        break;
                                    }
                                }
                                break;
                    }
                    break;
                }
                //create Person object
                Person person = new Person(name, surname, email);
                //create Ticket object with the Person object
                Ticket ticket = new Ticket(row, seat, price, person);
                tickets.add(ticket);
                System.out.println("\n");
                break;

            } catch (Exception e) {
                System.out.println("invalid input.");;
                scanner.next();
            }
        }
    }
    public static void print_seating_area ( int[][] seating){
        System.out.println("     ***********     ");
        System.out.println("     *  STAGE  *     ");
        System.out.println("     ***********     ");
        for (int i = 0; i < seating.length; i++) {
            if (i == 0) {
                System.out.print("    ");
            } else if (i == 1) {
                System.out.print("  ");
            }
            for (int j = 0; j < seating[i].length; j++) {
                if (seating[i][j] == 0) {
                    System.out.print("O");
                } else {
                    System.out.print("X");
                }
                if (j == 5 && i == 0) {
                    System.out.print(" ");
                } else if (j == 7 && i == 1) {
                    System.out.print(" ");
                } else if (j == 9 && i == 2) {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }
    public static void cancel_ticket ( int[][] row_seats){
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Enter row number: ");
            int row = scanner.nextInt();
            System.out.print("Enter seat number: ");
            int seat = scanner.nextInt();
            if (row < 1 || row > row_seats.length || seat < 1 || seat > row_seats[row - 1].length) {
                System.out.println("Invalid input. Please enter row and seat numbers again.");
            } else if (row_seats[row - 1][seat - 1] == 0) {
                System.out.println("This seat is not sold.");
            } else {
                    row_seats[row - 1][seat - 1] = 0;
                    System.out.println("The seat has been canceled.");
                    for (int i = 0; i < tickets.size(); i++) {
                        Ticket ticket = tickets.get(i);
                        if (ticket.getRow() == row && ticket.getSeat() == seat) {
                            tickets.remove(i);
                            System.out.println("Ticket has been removed from the system.");
                            break;
                        }
                    }
                    return;
                }
        }
    }
    public static void show_available ( int[][] row_seats){
        for (int i = 0; i < row_seats.length; i++) {
            System.out.print("Seats available in row " + (i + 1) + ": ");
            for (int j = 0; j < row_seats[i].length; j++) {
                if (row_seats[i][j] == 0) {
                    System.out.print((j + 1) + " ");
                }
            }
            System.out.println();
        }
    }
    public static void save ( int[][] row_seats){
        try {
            FileWriter file = new FileWriter("text.txt");
            for (int i = 0; i < row_seats.length; i++) {
                for (int j = 0; j < row_seats[i].length; j++) {
                    file.write(row_seats[i][j] + " ");
                }
                file.write("\n");
            }
            file.close();
            System.out.println("Data has been saved to " + "text.txt");
        } catch (IOException e) {
            System.out.println("An error occurred while saving the data.");
            e.printStackTrace();
        }
    }
    public static void load ( int[][] row_seats){
        try {
            File file = new File("text.txt");
            Scanner file_reader = new Scanner(file);
            while (file_reader.hasNextLine()) {
                String text = file_reader.nextLine();
                System.out.println(text);
            }
            file_reader.close();
        } catch (IOException e) {
            System.out.println("Error while reading a file.");
            e.printStackTrace();
        }
    }
    private static void show_tickets_info () {
        double total = 0.0;
        System.out.println("Ticket Information:");
        for (Ticket ticket : tickets) {
            ticket.print();
            total += ticket.getPrice();
        }
        System.out.println("\nTotal price of all tickets: £" + total);
        System.out.println();
    }
    public static void sort_tickets (ArrayList < Ticket > tickets) {
        ArrayList<Ticket> ticket_dupli = new ArrayList<>(tickets);
        Collections.sort(ticket_dupli, new Comparator<Ticket>() {
            @Override
            public int compare(Ticket o1, Ticket o2) {
                return Double.compare(o1.getPrice(), o2.getPrice());
            }
        });
        System.out.println("Sorted Tickets are: ");
        for (Ticket t : ticket_dupli) {
            t.print();
        }
    }
}


