package comicbookrentalshop;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Arrays;

/**
 * @author GROUP 2 - SE1604 Pham Nhat Quang - CE170036 (group leader) 
 * Le Duy Khanh - CE160277 (secretary)
 * Giang Trung Nghia Nhan - CE150722 
 * Lam Nhut Tan - CS160260 
 * Tasks: 
 * Quang: Loading data file, add, search by title, name of  author, update rental price, quit (saving data). 
 * Khanh: Show menu and choice  selection (including exception catching for choice).
 * Nhan : Data file, ComicBook class. 
 * Tan : Delete comic book function, debug.
 * All members: give opinions on the final product and tweak it.
 */
public class ComicBookRentalShop {

    /**
     * @param args the command line arguments
     */
    static ArrayList<ComicBook> comicBooks = new ArrayList<>();
    static int numberOfBooks;
    static Scanner input = new Scanner(System.in);

    /**
     * Show all comic books in the shop
     */
    static void ShowComicBooks() {
        if (comicBooks == null || numberOfBooks == 0) {
            System.out.println("\t!!! The shop is currently empty! !!!\n");
        } else {
            System.out.printf("                           ----- COMIC BOOKS RENTAL SERVICE -----\n");
            System.out.println("+-----+--------------------------------------------------+------------------------+---------+------+");
            System.out.printf("|%-5s|%-50s|%-24s|%-9s|%-6s|", " ID", "                      Title", "          Author", " Price($)", "Volume");
            System.out.println("\n+-----+--------------------------------------------------+------------------------+---------+------+");
            for (int i = 0; i < numberOfBooks; i++) { //Display comicBooks from index 0 to numberOfBooks - 1
                System.out.print("|");
                System.out.printf("%4d ", comicBooks.get(i).getID());                 //ID
                System.out.print("|");
                System.out.printf(" %-49s", comicBooks.get(i).getTitle());            //Title
                System.out.print("|");
                System.out.printf(" %-23s", comicBooks.get(i).getAuthor());           //Author
                System.out.print("|");
                System.out.printf("%8.2f ", comicBooks.get(i).getBookRentalPrice()); //Price
                System.out.print("|");
                System.out.printf("%5d ", comicBooks.get(i).getVolume());           //Volume
                System.out.print("|");
                System.out.println("\n+-----+--------------------------------------------------+------------------------+---------+------+");
            }
        }
    }

    /**
     * Check if a book already exist before
     *
     * @param book
     * @return true or false
     */
    static boolean Check_duplicate(ComicBook book) {
        int count = 0;
        for (int i = 0; i < numberOfBooks - 1; i++) {
            //If two books have the same id (shouldn't happen in this program)
            if (book.getID() == comicBooks.get(i).getID()) {
                return true;
            }

            if (book.getAuthor().toLowerCase().equals(comicBooks.get(i).getAuthor().toLowerCase())) {
                count++;
            }
            if (book.getTitle().toLowerCase().equals(comicBooks.get(i).getTitle().toLowerCase())) {
                count++;
            }
            if (book.getVolume() == comicBooks.get(i).getVolume()) {
                count++;
            }
            //If a the book has the same author, title and volume as another book, it is a duplicate
            if (count >= 3) {
                break; //Stop checking
            }
            count = 0;
        }
        if (count < 3) {
            return false;
        }
        return true;
    }

    /**
     * Remove unnecessary space in a string
     *
     * @param str
     * @return
     */
    static String remove_space(String str) {
        if (str.equals("")) return "";
        char result[];
        result = str.toCharArray();
        int count = 0;
        int length = result.length;
        int i = 0;
        while (i < result.length - 1) {
            if (result[i] == ' ' && result[i + 1] == ' ') {
                for (int j = i; j < result.length - 1; j++) {
                    result[j] = result[j + 1];
                }
                result[result.length - 1] = '\0';
                i--;
                length--;
            }
            i++;
        }
        if (length > 1) {
            if (result[length - 1] == ' ') {
                result[length - 1] = '\0';
                length--;
            }
        }
        if (length == 1 && result[0] == ' ') {
            return "";
        }
        if (result[0] == ' ') {
            return String.valueOf(Arrays.copyOfRange(result, 1, length));
        }
        return String.valueOf(Arrays.copyOf(result, length));
    }

    public static void main(String[] args) {

        //LOAD DATA FILE
        try {
            System.out.print("Loading data file ComicBooks.txt...");
            File CB_File = new File("src/Data/ComicBooks.txt");

            if (!CB_File.exists()) { //If data file doesn't exist
                System.out.print("Data file not found. Creating new file... ");
                CB_File.createNewFile(); //Create new file
                System.out.print("Done!\n");
                System.out.println("There is currently no data in the data file, please add more data to the program.");
                numberOfBooks = 0;
            } else {
                System.out.print(" Data file has been found. Loading data... ");
                BufferedReader br = new BufferedReader(new FileReader("src/Data/ComicBooks.txt"));
                try {
                    //Variable to store data of individual books
                    String ID, Title, rentalPrice, Author, Volume;

                    numberOfBooks = Integer.parseInt(br.readLine()); //Get total number of books
                    if (numberOfBooks == 0) {
                        System.out.println("\nThere is currently no data in the data file, please add more data to the program.\n");
                    }
                    //Read each next line for data
                    for (int i = 0; i < numberOfBooks; i++) {
                        ID = br.readLine();          //ID
                        Title = br.readLine();       //Title
                        rentalPrice = br.readLine(); //Price
                        Author = br.readLine();      //Author name
                        Volume = br.readLine();      //Volume

                        //Add new comic book to comicBooks bank
                        comicBooks.add(new ComicBook(Integer.parseInt(ID), Title,
                                Double.parseDouble(rentalPrice), Author, Integer.parseInt(Volume)));
                    }
                    if (numberOfBooks != 0) {
                        System.out.print("Done! Data for " + numberOfBooks + " books loaded.\n");
                    }

                } catch (NumberFormatException nfe) {

                } finally {
                    br.close();
                }
            }
        } catch (Exception e) {
            System.out.println("### An error occurred and we couldn't find or create a data file. ###\n");
        }
        //ShowComicBooks();

        //Function menu for user
        int menuChoice = 0;
        do {
            System.out.println("***** COMIC BOOK RENTAL SHOP *****");
            System.out.println("1. Add new comic book.");
            System.out.println("2. Search book by title.");
            System.out.println("3. Search book of an author.");
            System.out.println("4. Update book rental price.");
            System.out.println("5. Delete comic book.");
            System.out.println("6. Show comic bank.");
            System.out.println("7. Quit.");

            //Get function choice
            while (true) {
                try {
                    System.out.print("\tPlease select a function: ");
                    menuChoice = Integer.parseInt(remove_space(input.nextLine()));
                    break;
                } catch (NumberFormatException ime) { //Not integer exception
                    System.out.println("Error: Menu choice must be an integer from 1 to 7!");
                }
            }
            switch (menuChoice) {
                case 1:
                    //Variables to store data for new book
                    numberOfBooks++;
                    String Title;
                    double Price;
                    String Author;
                    int Volume;

                    System.out.println("\t+++ ADD NEW COMIC BOOK +++");

                    //Get book title
                    while (true) {
                        try {
                            System.out.print("Title: ");
                            Title = remove_space(input.nextLine());
                            if (Title.equals("")) {
                                throw new InputMismatchException();
                            }
                            if (Title.length() > 49) {
                                throw new ComicBookException("Error: Title too long!");
                            }
                            break;
                        } catch (InputMismatchException ime) {
                            System.out.println("Error: Title of book can't be blank!");
                        } catch (ComicBookException cbe) {
                            System.out.println(cbe.getMessage());
                        }
                    }

                    //Get book rental price
                    while (true) {
                        try {
                            System.out.print("Rental price: ");
                            Price = Double.parseDouble(input.nextLine());
                            if (Price <= 0) {
                                throw new NumberFormatException();
                            }
                            if (Price > 99999) {
                                throw new ComicBookException("Error: Rental price too large, please try again.");
                            }
                            break;
                        } catch (NumberFormatException ime) {
                            System.out.println("Error: Rental price must be a number that is greater than 0!");
                        } catch (ComicBookException cbe) {
                            System.out.println(cbe.getMessage());
                        }
                    }

                    //Get book's author name
                    while (true) {
                        try {
                            System.out.print("Author: ");
                            Author = remove_space(input.nextLine());
                            if (Author.equals("")) {
                                throw new InputMismatchException();
                            }
                            if (Author.length() > 23) {
                                throw new ComicBookException("Error: Author name too long!");
                            }
                            break;
                        } catch (InputMismatchException ime) {
                            System.out.println("Error: Author name can't be blank!");
                        } catch (ComicBookException cbe) {
                            System.out.println(cbe.getMessage());
                        }
                    }

                    //Get book's volume number 
                    while (true) {
                        try {
                            System.out.print("Volume number: ");
                            Volume = Integer.parseInt(input.nextLine());
                            if (Volume <= 0) {
                                throw new InputMismatchException();
                            }
                            if (Volume > 99999) {
                                throw new ComicBookException("Error: Volume number too large!");
                            }
                            break;
                        } catch (NumberFormatException nfe) {
                            System.out.println("Error: Volume must be an integer greater than 0!");
                        } catch (ComicBookException cbe) {
                            System.out.println(cbe.getMessage());
                        }
                    }
                    int id_add;
                    if (numberOfBooks > 1){
                         id_add = comicBooks.get(numberOfBooks-2).getID() + 1;
                    } else id_add = 1;
                    //Add new book to array list
                    try {
                        comicBooks.add(new ComicBook(id_add, Title, Price, Author, Volume));
                        if (Check_duplicate(comicBooks.get(numberOfBooks - 1))) {
                            System.out.println("This book already exists in this shop.");
                            comicBooks.remove(numberOfBooks - 1);
                            numberOfBooks--;
                        } else {
                            System.out.println("+++ Your new book has been added to the shop!");
                            System.out.println("");
                        }

                        //Show the comic bank after adding new book
                        ShowComicBooks();
                    } catch (Exception e) {
                        System.out.println("### Something went wrong and your book couldn't be added. ###");
                    }
                    break;
                case 2:
                    if (numberOfBooks != 0) {
                        System.out.println("\t---- SEARCH COMIC BY TITLE ----");
                        String token_title;
                        while (true) {
                            //Get token from user
                            System.out.print("Please enter title / part of title to search for books: ");
                            token_title = remove_space(input.nextLine());
                            if (token_title.equals("")) { //If token is blank
                                System.out.println("Your search term must not be blank!");
                            } else {
                                int count = 0;
                                for (int i = 0; i < numberOfBooks; i++) {
                                    //If a book title contains the token, display info of the book
                                    if (comicBooks.get(i).getTitle().toLowerCase().contains(token_title.toLowerCase())) {
                                        count++;
                                        if (count == 1) {
                                            System.out.println("+-----+--------------------------------------------------+------------------------+---------+------+");
                                            System.out.printf("|%-5s|%-50s|%-24s|%-9s|%-6s|", "  ID", "                      Title", "          Author", " Price($)", "  Vol");
                                            System.out.println("\n+-----+--------------------------------------------------+------------------------+---------+------+");
                                        }
                                        System.out.print("|");
                                        System.out.printf("%4d ", comicBooks.get(i).getID());                 //ID
                                        System.out.print("|");
                                        System.out.printf(" %-49s", comicBooks.get(i).getTitle());            //Title
                                        System.out.print("|");
                                        System.out.printf(" %-23s", comicBooks.get(i).getAuthor());           //Author
                                        System.out.print("|");
                                        System.out.printf("%8.2f ", comicBooks.get(i).getBookRentalPrice()); //Price
                                        System.out.print("|");
                                        System.out.printf("%5d ", comicBooks.get(i).getVolume());           //Volume
                                        System.out.print("|");
                                        System.out.println("\n+-----+--------------------------------------------------+------------------------+---------+------+");
                                    }
                                }
                                if (count == 0) { //If no books have been found with the token
                                    System.out.println("### There are no titles that fit your search term. ###");
                                }
                                System.out.println("");
                                break;
                            }
                        }
                    } else {
                        System.out.println("### There are currently no books to find ###");
                        System.out.println(" Please add more books to use this function.\n");
                    }
                    break;
                case 3:
                    if (numberOfBooks != 0) {
                        System.out.println("\t---- SEARCH COMIC BY AUTHOR ----");
                        String token_author;
                        while (true) {
                            //Get token from user
                            System.out.print("Please enter author name to search for books: ");
                            token_author = remove_space(input.nextLine());
                            if (token_author.equals("")) { //If token is blank
                                System.out.println("Your search term must not be blank!");
                            } else {
                                int count = 0;
                                for (int i = 0; i < numberOfBooks; i++) {
                                    if (comicBooks.get(i).getAuthor().toLowerCase().equals(token_author.toLowerCase())) {
                                        //If a book has the same author as in the token, display info of the book
                                        count++;
                                        if (count == 1) {
                                            System.out.println("+-----+--------------------------------------------------+------------------------+---------+------+");
                                            System.out.printf("|%-5s|%-50s|%-24s|%-9s|%-6s|", "  ID", "                      Title", "          Author", " Price($)", "  Vol");
                                            System.out.println("\n+-----+--------------------------------------------------+------------------------+---------+------+");
                                        }
                                        System.out.print("|");
                                        System.out.printf("%4d ", comicBooks.get(i).getID());                 //ID
                                        System.out.print("|");
                                        System.out.printf(" %-49s", comicBooks.get(i).getTitle());            //Title
                                        System.out.print("|");
                                        System.out.printf(" %-23s", comicBooks.get(i).getAuthor());           //Author
                                        System.out.print("|");
                                        System.out.printf("%8.2f ", comicBooks.get(i).getBookRentalPrice()); //Price
                                        System.out.print("|");
                                        System.out.printf("%5d ", comicBooks.get(i).getVolume());           //Volume
                                        System.out.print("|");
                                        System.out.println("\n+-----+--------------------------------------------------+------------------------+---------+------+");
                                    }
                                }
                                if (count == 0) { //If no books have been found with the token
                                    System.out.println("### There are no books that fit your search term. ###");
                                }
                                System.out.println("");
                                break;
                            }
                        }
                    } else {
                        System.out.println("### There are currently no books to find ###");
                        System.out.println(" Please add more books to use this function.\n");
                    }
                    break;
                case 4:
                    if (numberOfBooks != 0) {
                        System.out.print("\n\t---- UPDATE RENTAL PRICE ----");
                        int ID_update;
                        double price_update;
                        System.out.println("");
                        while (true) {
                            try {
                                //Get book ID from user
                                System.out.print("Please enter the ID of the book you want to update: ");
                                ID_update = Integer.parseInt(remove_space(input.nextLine()));
                                if (ID_update <= 0) {
                                    throw new InputMismatchException(); //ID must be greater than 0
                                }
                                //Find book with the ID
                                for (int i = 0; i < numberOfBooks; i++) {
                                    if (comicBooks.get(i).getID() == ID_update) {
                                        while (true) {
                                            //Get new price from the user
                                            try {
                                                System.out.print("Please enter new price: ");
                                                price_update = Double.parseDouble(input.nextLine());
                                                if (price_update <= 0) {
                                                    throw new NumberFormatException(); //Price must be greater than 0
                                                }
                                                if (price_update > 99999) {
                                                    throw new ComicBookException("Error: Price too large! Please try again.");
                                                }
                                                break;
                                            } catch (NumberFormatException nfe) {
                                                System.out.println("Error: Rental price must be a number greater than 0 and smaller than 100,000");
                                            } catch (ComicBookException cbe) {
                                                System.out.println(cbe.getMessage());
                                            }
                                        }
                                        //Update price with set method
                                        comicBooks.get(i).setBookRentalPrice(price_update);
                                        System.out.println("\t~~~ Price has been updated successfully. ~~~");
                                        //Show the comic bank after update
                                        ShowComicBooks();
                                        break;
                                    }
                                    if (i == numberOfBooks - 1) {
                                        System.out.println("### The book you want to update is not found. ###");
                                    }
                                }
                                break;
                            } catch (NumberFormatException | InputMismatchException ime) {
                                System.out.println("Error: ID must be an integer greater than 0!");
                            }
                        }
                    } else {
                        System.out.println("### There are currently no books to update ###");
                        System.out.println("  Please add more books to use this function.\n");
                    }
                    break;
                case 5:
                    if (numberOfBooks != 0) {
                        System.out.println("\t---- DELETE A COMIC BOOK ----");
                        int delete_id = 0;
                        while (true) {
                            //Get ID from the user
                            try {
                                System.out.print("Please enter the ID of the book you want to delete: ");
                                delete_id = Integer.parseInt(remove_space(input.nextLine()));
                                System.out.println("Are you sure you want to delete the book with ID " + delete_id + "?");
                                System.out.print("YES / NO: "); 
                                String agree = input.nextLine();
                                if (remove_space(agree.toLowerCase()).equals("yes")){
                                    if (delete_id <= 0) {
                                        throw new InputMismatchException(); //ID must be greater than 0
                                    }
                                    for (int i = 0; i < numberOfBooks; i++) { //Find book with the ID
                                        if (comicBooks.get(i).getID() == delete_id) {
                                            comicBooks.remove(i); //Remove book from ArrayList
                                            numberOfBooks--; //Updating total number of books

                                            //Ditched feature.
    //                                        Update ID of subsequent books  
    //                                        for (int j = i; j < numberOfBooks; j++) {
    //                                            comicBooks.get(j).setID(comicBooks.get(j).getID() - 1);
    //                                        }
                                            System.out.println("\t--- The book has been deleted. ---\n");
                                            ShowComicBooks();
                                            break;
                                        }
                                        if (i == numberOfBooks - 1) {
                                            System.out.println("### The book with the ID " + delete_id + " is not found. ###\n");
                                        }
                                    }
                                } else System.out.println("You didn't choose yes, the book won't be deleted.");
                                break;
                            } catch (NumberFormatException | InputMismatchException nfe) {
                                System.out.println("Error: ID must be a positive integer!");
                            }
                        }
                    } else {
                        System.out.println("### There are currently no books to delete ###");
                        System.out.println("  Please add more books to use this function.\n");
                    }
                    break;
                case 6:
                    if (numberOfBooks != 0) {
                        ShowComicBooks();
                    } else {
                        System.out.println("### There are currently no books to show. ###");
                        System.out.println("  Please add more books to use this function.\n");
                    }
                    break;
                case 7:
                    System.out.println("\nThank you for using our program.");
                    System.out.println("We hope to see you again!");

                    //Save data into data file
                    System.out.print("Saving data into data file...");
                    try (FileWriter fw = new FileWriter(new File("src/Data/ComicBooks.txt"), false)) {
                        fw.append(String.valueOf(numberOfBooks) + "\n");
                        for (int i = 0; i < numberOfBooks; i++) {
                            int ID = comicBooks.get(i).getID();
                            String Author_save = comicBooks.get(i).getAuthor();
                            String Title_save = comicBooks.get(i).getTitle();
                            double rentalPrice_save = comicBooks.get(i).getBookRentalPrice();
                            int Volume_save = comicBooks.get(i).getVolume();

                            fw.append(String.valueOf(ID) + "\n");
                            fw.append(String.valueOf(Title_save) + "\n");
                            fw.append(String.valueOf(rentalPrice_save) + "\n");
                            fw.append(String.valueOf(Author_save) + "\n");
                            fw.append(String.valueOf(Volume_save) + "\n");
                        }
                        System.out.print(" Done! Data for " + numberOfBooks + " books has been saved.\n");
                    } catch (Exception e) {
                        System.out.println("Something went wrong. Your data couldn't be saved.");
                    }
                    break;
                default:
                    System.out.println("Error: Menu choice must be from 1 to 7!");
            }
        } while (menuChoice != 7);
    }

}
