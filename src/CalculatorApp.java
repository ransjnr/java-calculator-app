//

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class CalculatorApp extends JFrame implements ActionListener {

    JTextField displayField;
    JButton[] buttons = new JButton[16]; // Increased array size to accommodate the division button
    String[] buttonLabels = {"7", "8", "9", "/", // Added "/" to the array
                             "4", "5", "6", "*",
                             "1", "2", "3", "-",
                             "0", ".", "+", "%"}; // Rearranged the order
    double firstNumber, secondNumber, result;
    char operator;
    StringBuilder currentInput = new StringBuilder();
    JTextArea historyArea;

    public CalculatorApp() {
        setTitle("Andelle's Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create display field with increased size
        displayField = new JTextField("", 35);
        displayField.setEditable(false);
        displayField.setHorizontalAlignment(SwingConstants.RIGHT);
        add(displayField, BorderLayout.NORTH);

        // Create button panel with reduced grid size
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 4, 5, 5)); // Changed grid layout to 4x4

        // Create buttons with labels and add listeners
        for (int i = 0; i < 16; i++) { // Loop for 16 buttons
            buttons[i] = new JButton(buttonLabels[i]);
            buttons[i].addActionListener(this);
            buttonPanel.add(buttons[i]);
        }

        // Add additional buttons (C, =)
        JButton clearButton = new JButton("C");
        clearButton.addActionListener(this);
        buttonPanel.add(clearButton);

        JButton equalsButton = new JButton("=");
        equalsButton.addActionListener(this);
        buttonPanel.add(equalsButton);

        add(buttonPanel, BorderLayout.CENTER);

        historyArea = new JTextArea(10, 25); // 10 rows, 25 columns
        historyArea.setEditable(false);
        add(new JScrollPane(historyArea), BorderLayout.EAST);

        pack();
        setVisible(true);
    
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton clickedButton = (JButton) e.getSource();
        String clickedLabel = clickedButton.getText();

        if (Character.isDigit(clickedLabel.charAt(0)) || clickedLabel.equals(".")) {
            appendNumber(clickedLabel);
        } else if (clickedLabel.equals("C")) {
            clearDisplay();
        } else if (clickedLabel.equals("=")) {
            performCalculation();
        } else {
            switch (clickedLabel) {
                case "+":
                case "-":
                case "*":
                case "/":
                    setOperator(clickedLabel.charAt(0));
                    break;
                default:
                    // Handle other buttons like "%" (optional)
            }
        }
    }

    void appendNumber(String number) {
        currentInput.append(number);
        displayField.setText(currentInput.toString());
    }

    void clearDisplay() {
        currentInput.setLength(0);
        displayField.setText("");
        firstNumber = 0;
        secondNumber = 0;
        operator = ' ';
    }

    void setOperator(char op) {
        operator = op;
        try {
            firstNumber = Double.parseDouble(currentInput.toString());
        } catch (NumberFormatException e) {
            // Handle invalid input (optional)
            return;
        }
        currentInput.setLength(0); // Clear for second number input
        displayField.setText(displayField.getText() + op); // Display operation on screen
    }

    void performCalculation() {
        try {
            secondNumber = Double.parseDouble(currentInput.toString());
        } catch (NumberFormatException e) {
            return;
        }

        switch (operator) {
            case '+':
                result = firstNumber + secondNumber;
                break;
            case '-':
                result = firstNumber - secondNumber;
                break;
            case '*':
                result = firstNumber * secondNumber;
                break;
            case '/':
                if (secondNumber == 0) {
                    throw new ArithmeticException("Division by zero!");
                }
                result = firstNumber / secondNumber;
                break;
            default:
                return;
        }

        String calculation = firstNumber + " " + operator + " " + secondNumber + " = " + result;
        historyArea.append(calculation + "\n"); // Add calculation to history

        displayField.setText(String.valueOf(result));
        currentInput.setLength(0);
        firstNumber = result; // Save result for further calculations (optional)
    }

    public static void main(String[] args) {
        new CalculatorApp();
    }
}