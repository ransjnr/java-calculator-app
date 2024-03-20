import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class CalculatorApp extends JFrame implements ActionListener {

    JTextField displayField;
    JButton[] buttons = new JButton[14];
    String[] buttonLabels = {"7", "8", "9", "+",
                             "4", "5", "6", "-",
                             "1", "2", "3", "*",
                             "0", "."};
    double firstNumber, secondNumber, result;
    char operator;
    StringBuilder currentInput = new StringBuilder();

    public CalculatorApp() {
        setTitle("Simple Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create display field with increased size
        displayField = new JTextField("", 25);
        displayField.setEditable(false);
        displayField.setHorizontalAlignment(SwingConstants.RIGHT);
        add(displayField, BorderLayout.NORTH);

        // Create button panel with reduced grid size
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 7, 5, 5));

        // Create buttons with labels and add listeners
        for (int i = 0; i < 14; i++) {
            buttons[i] = new JButton(buttonLabels[i]);
            buttons[i].addActionListener(this);
            buttonPanel.add(buttons[i]);
        }

        // Add additional buttons (C, =, %)
        JButton clearButton = new JButton("C");
        clearButton.addActionListener(this);
        buttonPanel.add(clearButton);

        JButton equalsButton = new JButton("=");
        equalsButton.addActionListener(this);
        buttonPanel.add(equalsButton);

        add(buttonPanel, BorderLayout.CENTER);
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
            setOperator(clickedLabel.charAt(0));
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

        displayField.setText(String.valueOf(result));
        currentInput.setLength(0); // Clear for next calculation
        firstNumber = result; // Save result for further calculations (optional)
    }

    public static void main(String[] args) {
        new CalculatorApp();
    }
}
