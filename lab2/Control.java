

import java.util.HashMap;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**The Control class for ARM Instructions MVC
 * @author Mary Lun
 * @version 1.0
 */
public class Control extends View implements ActionListener{
    /**HashMaps to hold conversions from assembly language to binary/hex and reverse */
    HashMap<String, String> b1 = new HashMap<>();
    HashMap<String, String> b2 = new HashMap<>();
    /**To utilize methods in Model class */
    Model m;

    /**Static method that loads class as object in memory
     * @param args String[] with console arguments
     */
    public static void main(String args[]){
        new Control();
    }
    /**Constructor with no parameters that calls convertCommand() to assign keys to Hashmaps, 
     * assigns new Model object, and calls setButtons() to assign actions to buttons on app
     */
    public Control(){
        convertCommand();
        m = new Model(this);
        setButtons();
    }

    /**Adds action listeners for encode/decode buttons when they are clicked
    */
    public void setButtons(){
        encode.addActionListener(this);
        decodeB.addActionListener(this);;
        decodeH.addActionListener(this);
    }

    /**Assigns action performed when button is clicked 
     * @param e Object that causes the interaction
    */
    public void actionPerformed(ActionEvent e){
        String button = e.getActionCommand();
        if(button.equals("Encode")){
            message.setText("");
            m.encode();
        }
        if(button.equals("Decode Binary")){
            message.setText("");
            m.decode(bInstruct.getText());
        }
        if(button.equals("Decode Hex")){
            message.setText("");
            m.decode(hInstruct.getText());
        }
    }

    /**Assigns binary values to assembly language and vice versa into Hashmaps*/
    public void convertCommand(){
        //operations
        b1.put("AND", "0000");
        b1.put("EOR", "0001");
        b1.put("SUB", "0010");
        b1.put("RSB", "0011");
        b1.put("ADD", "0100");
        b1.put("ADC", "0101");
        b1.put("SBC", "0110");
        b1.put("RSC", "0111");
        b1.put("TST", "1000");
        b1.put("TEQ", "1001");
        b1.put("CMP", "1010");
        b1.put("CMN", "1011");
        b1.put("ORR", "1100");
        b1.put("MOV", "1101");
        b1.put("BIC", "1110");
        b1.put("MVN", "1111");

        b1.put("0000", "AND");
        b1.put("0001","EOR");
        b1.put( "0010","SUB");
        b1.put( "0011","RSB");
        b1.put( "0100","ADD");
        b1.put( "0101","ADC");
        b1.put("0110","SBC");
        b1.put( "0111","RSC");
        b1.put( "1000","TST");
        b1.put( "1001","TEQ");
        b1.put( "1010","CMP");
        b1.put( "1011","CMN");
        b1.put( "1100","ORR");
        b1.put( "1101","MOV");
        b1.put( "1110","BIC");
        b1.put( "1111","MVN");
        //registers
        b2.put("R0", "0000");
        b2.put("R1", "0001");
        b2.put("R2", "0010");
        b2.put("R3", "0011");
        b2.put("R4", "0100");
        b2.put("R5", "0101");
        b2.put("R6", "0000");
        b2.put("R7", "0111");
        b2.put("R8", "1000");
        b2.put("R9", "1001");
        b2.put("R10", "1010");
        b2.put("R11", "1011");
        b2.put("R12", "1100");
        b2.put("R13", "1101");
        b2.put("R14", "1110");
        b2.put("R15", "1111");

        b2.put("0000","R0");
        b2.put("0001","R1");
        b2.put("0010","R2");
        b2.put( "0011","R3");
        b2.put( "0100","R4");
        b2.put( "0101","R5");
        b2.put( "0000","R6");
        b2.put( "0111","R7");
        b2.put( "1000","R8");
        b2.put( "1001","R9");
        b2.put( "1010","R10");
        b2.put( "1011","R11");
        b2.put( "1100","R12");
        b2.put( "1101","R13");
        b2.put( "1110","R14");
        b2.put( "1111","R15");
    }
}