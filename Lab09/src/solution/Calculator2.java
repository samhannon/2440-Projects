package solution;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Calculator2 {
    private JFrame calcFrame = new JFrame();
    private JTextField input;
    private JLabel jResult; //answer field
    
    public Calculator2() {
        calcFrame.setLocation(100, 100);
        calcFrame.setSize(800, 800);
        calcFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        calcFrame.setTitle("Calculator Time");

        initializeComponents();
        calcFrame.pack();
        //calcFrame.add(butPanel, BorderLayout.PAGE_END);
        calcFrame.setVisible(true);
    }

    public static void main(String[] args) {
        Calculator2 calc = new Calculator2();
    }

    public JFrame getFrame() {
        return calcFrame;
    }

    public void initializeComponents() {
        //button panel
        JPanel butPanel = new JPanel();
        //result panel
        JPanel resPanel = new JPanel();
        //this one should be obvious
        JPanel textPanel = new JPanel();

        JButton calc = new JButton("CALCULATE");
        JButton clear = new JButton("CLEAR");


        calc.setName("calculateButton");
        clear.setName("clearButton");

        butPanel.add(calc);
        butPanel.add(clear);

        input = new JTextField(10);

        input.setName("infixExpression");

        textPanel.add(input);

        calcFrame.add(textPanel, BorderLayout.PAGE_START);
        calcFrame.add(resPanel, BorderLayout.LINE_START);
        calcFrame.add(butPanel, BorderLayout.PAGE_END);

        jResult = new JLabel("Result = ");
        jResult.setName("resultLabel");
        resPanel.add(jResult);

        //using anonymous inner classes to implement action listener
        calc.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    ExpressionEvaluator expr = new ExpressionEvaluator();
                    String infix = input.getText();
                    String postfix = expr.toPostfix(infix);
                    double ans = expr.evaluate(postfix);
                    Double nan = new Double(ans);
                    
                    if (nan.isNaN()) {
                        jResult.setText("Result = Error");
                    } else {
                        jResult.setText("Result = " + ans);
                    }                        
                } catch (Exception ex) {
                    jResult.setText("Result = Error");
                }

            }
        });

        clear.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {            
                jResult.setText("Result = ");
                input.setText("");
            }
        });


    }
}
