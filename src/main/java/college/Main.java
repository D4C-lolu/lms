package college;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Scanner;


public class Main {

    private static final Logger logger = LogManager.getLogger(Main.class);
    private static Scanner sc;

    public static void displayMenu() {
        logger.trace("Enter '1' to see the list of available books.");
        logger.trace("Enter '2' to see the list of books in your library.");
        logger.trace("Enter '3' to the books still pending.");
        logger.trace("Enter '4' to see your user history.");
        logger.trace("Enter '5' to see the most checked out books.");
        logger.trace("Or enter 0 to end the program");
    }

    public void setUp() {
    }


    public static void logIn(){

    }
    public static void main(String[] args) throws Exception {

        logger.trace("Welcome user! ");
        logger.trace("\nThis is a Library management application.");
        sc = new Scanner(System.in);
        logger.info("\nPlease enter a valid user ID to log in: ");
        String input = "";
        String endChar = "x";
        do{
            try{
                input = sc.nextLine();
            }
            catch(final NumberFormatException e){
                logger.warn("\nPlease enter a valid number: ");
            }
        }
        while(!input.equals(endChar));
        int choice = -1;
        int end = 0;
        do {
            logger.trace("======================================");
            displayMenu();
            logger.info("\nEnter your response here: ");
            try {
                choice = Integer.parseInt(sc.nextLine());
                switch(choice){
                    case 0:
                        logger.info("Exiting Program...");
                        System.exit(0);
                    case 2:
                        break;
                    case 3:
                        //TODO:
                        break;
                    case 4:
                        //TODO:
                        break;
                    case 5:
                        break;
                    default:
                        logger.warn("\nPlease enter a number between 0 and 5 ");
                }
            } catch (final NumberFormatException e) {
                logger.warn("\nPlease enter a valid number: ");
            }
        }
        while (choice != end);

//       logger.debug("We've just greeted the user!");
//        logger.info("We've just greeted the user!");
//        logger.warn("We've just greeted the user!");
//        logger.error("We've just greeted the user!");
//        logger.fatal("We've just greeted the user!");
    }
}