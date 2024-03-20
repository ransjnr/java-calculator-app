import java.util.Scanner;

public class CalculatorApp {

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);

    System.out.println("Welcome to Java Calculator!");

    while (true) {
      // Display menu
      printMenu();

      // Get user choice
      System.out.print("Enter your choice (1-5): ");
      int choice = (int) getNumber(scanner);

      // Perform operation based on choice
      double result = 0;
      try {
        switch (choice) {
          case 1:
            result = performOperation(scanner, '+');
            break;
          case 2:
            result = performOperation(scanner, '-');
            break;
          case 3:
            result = performOperation(scanner, '*');
            break;
          case 4:
            result = performOperation(scanner, '/');
            break;
          case 5:
            System.out.println("Exiting calculator...");
            break;
          default:
            System.out.println("Invalid choice. Please try again.");
        }
      } catch (Exception e) {
        System.out.println("Error: " + e.getMessage());
      }

      // Display result (if applicable)
      if (choice != 5) {
        System.out.println("Result: " + result);
      }

      // Ask for another calculation
      System.out.print("Do you want to perform another calculation? (y/n): ");
      String answer = scanner.next();
      if (answer.equalsIgnoreCase("n")) {
        break;
      }
    }

    scanner.close();
    System.out.println("Thanks for using Advanced Java Calculator!");
  }

  // Function to display menu options
  public static void printMenu() {
    System.out.println("\nMenu:");
    System.out.println("1. Addition");
    System.out.println("2. Subtraction");
    System.out.println("3. Multiplication");
    System.out.println("4. Division");
    System.out.println("5. Exit");
  }

  // Function to get a valid number from the user
  public static double getNumber(Scanner scanner) {
    while (!scanner.hasNextDouble()) {
      System.out.println("Invalid input. Please enter a number: ");
      scanner.next();
    }
    return scanner.nextDouble();
  }

  // Function to perform calculation based on operator
  public static double performOperation(Scanner scanner, char operator) throws Exception {
    System.out.print("Enter the first number: ");
    double firstNumber = getNumber(scanner);

    System.out.print("Enter the second number: ");
    double secondNumber = getNumber(scanner);

    switch (operator) {
      case '+':
        return firstNumber + secondNumber;
      case '-':
        return firstNumber - secondNumber;
      case '*':
        return firstNumber * secondNumber;
      case '/':
        if (secondNumber == 0) {
          throw new ArithmeticException("Division by zero!");
        }
        return firstNumber / secondNumber;
      default:
        throw new IllegalArgumentException("Invalid operator!");
    }
  }
}