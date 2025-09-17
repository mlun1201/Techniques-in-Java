

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

/**The View class for ARM Instructions MVC
 * @author Mary Lun
 * @version 1.0
 */
public class View extends JFrame {
    /**All necessary variables to construct app */
    public JTextField armInstruct = new JTextField(10);
    public JButton encode = new JButton("Encode");
    public JLabel toAL = new JLabel("Assembly Language");
    public JTextField bInstruct = new JTextField(10);
    public JTextField hInstruct = new JTextField(10);
    public JLabel binary = new JLabel("Binary Instruction");
    public JLabel hex = new JLabel("Hex Instruction");
    public JButton decodeB = new JButton("Decode Binary");
    public JButton decodeH = new JButton("Decode Hex");
    public JTextField message = new JTextField(10);

    public JPanel p1 = new JPanel();
    public JPanel p2 = new JPanel(new GridLayout(4,2,2,2));
    public JPanel p3 = new JPanel(new GridLayout(1,1,2,2));

    /**Constructor with no parameters, builds app window and panels */
    public View(){
        setTitle("ARM Instructions");
        setSize(480,200);
        setLayout(new BorderLayout());
        add(toAL);
        add(p1, BorderLayout.NORTH);
            p1.add(toAL);
            p1.setBorder(new EmptyBorder(2, 2, 2, 2)); 

        add(p2, BorderLayout.CENTER);
        p2.add(armInstruct);
            p2.add(encode);
            p2.add(binary);
            p2.add(hex);
            p2.add(bInstruct);
            p2.add(hInstruct);
            p2.add(decodeB);
            p2.add(decodeH);
            p2.setBorder(new EmptyBorder(2, 2, 2, 2)); 

        add(p3, BorderLayout.SOUTH); 
            p3.add(message);
             

        setVisible(true);
    }

    /**Static method to load class as object in memory
     * @param args String[] with console arguments
    */
    public static void main(String args[]){
        new View();
    }
}
