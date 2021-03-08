package solution;

import java.util.Scanner;
import java.util.Stack;
import java.util.regex.Pattern;


/**  * An expression is a sequence of double numbers.
 * 
 *
 * @author ???
 * @version ???
 * 
 */
public class ExpressionEvaluator {

    public static final Pattern UNSIGNED_DOUBLE =
        Pattern.compile("((\\d+\\.?\\d*)|(\\.\\d+))([Ee][-+]?\\d+)?.*?");
    public static final Pattern CHARACTER = Pattern.compile("\\S.*?");
    //public Stack<Double> numbers = new Stack<Double>();

    /**
     * Takes an infix expression and converts it to postfix.
     * 
     * @param expression
     *            The infix expression.
     * @return the postfix expression.
     */
    public String toPostfix(String expression) {
        // ... other local variables
        Scanner input = new Scanner(expression);
        String next;
        char symbol;
        String postfixExpression = "";
        Stack<String> postie = new Stack<String>();

        do {
            if (input.hasNext(UNSIGNED_DOUBLE)) {
                next = input.findInLine(UNSIGNED_DOUBLE);
                // TODO: do what you want to with a String that
                // holds a number
                postfixExpression += next + " "; 
            } else {
                next = input.findInLine(CHARACTER);
                symbol = next.charAt(0);

                // TODO: do what you want to with a symbol
                // such as (, ), *, /, +, -

                // (, +, (   
                if (symbol == '*' || symbol == '/') {
                    while (!postie.isEmpty() && !postie.peek().equals("+") 
                        && !postie.peek().equals("-") && !postie.peek().equals("(")) {
                        postfixExpression += postie.pop() + " ";
                    }
                    postie.push(next);
                } else if (symbol == '+' || symbol == '-') {
                    while (!postie.isEmpty() && !postie.peek().equals("(")) {   
                        postfixExpression += postie.pop() + " ";
                    }
                    postie.push(next);
                } else if (symbol == ')') {                        
                    while (!postie.peek().equals("(")) {
                        postfixExpression += postie.pop() + " ";
                    }
                    postie.pop();
                } else if (symbol == '(') {

                    postie.push(next);

                }      

            } 
        } while (input.hasNext());


        while (!postie.isEmpty()) {
            postfixExpression += postie.pop() + " ";
        }
        return postfixExpression;
    }



    /**
     * Evaluates a postfix expression and returns the result.
     * 
     * @param postfixExpression
     *            The postfix expression.
     * @return The result of the expression.
     */
    public double evaluate(String postfixExpression) {
        // other local variables you may need
        Scanner input = new Scanner(postfixExpression);
        String next;
        char operator;
        double answer = Double.NaN;
        Stack<Double> postie = new Stack<Double>();
        
        double rightVal;
        double leftVal;
        while (input.hasNext()) {
            if (input.hasNext(UNSIGNED_DOUBLE)) {
                next = input.findInLine(UNSIGNED_DOUBLE);
                // TODO: do what you want to with a String that
                // holds a number
                
                postie.push(Double.parseDouble(next));
            } else {
                next = input.findInLine(CHARACTER);
                operator = next.charAt(0);

                // TODO: do what you want to with an operator
                // such as *, /, +, -
                
                rightVal = postie.pop();
                leftVal = postie.pop();
                
                switch (operator) {
                    case '+':
                        postie.push(leftVal + rightVal);
                        break;
                    case '-':
                        postie.push(leftVal - rightVal);
                        break;
                    case '*':
                        postie.push(leftVal * rightVal);
                        break;
                    case '/':
                        postie.push(leftVal / rightVal);
                        break;
                    default:
                        break;
                }
            }
        }
        answer = postie.pop();
        if (!postie.isEmpty()) {
            return Double.NaN;
        }
        return answer;

    }

}
