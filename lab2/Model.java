

/**The Model class for ARM Instructions MVC
 * @author Mary Lun
 * @version 1.0
 */
public class Model {
    /**To utilize Control variables */
    Control c;
    
    /**Constructor for Model
     * @param fromC The address for the Control object called
     */
    public Model(Control fromC){
        c = fromC;
    }

    /**Encodes assembly language into binary and hex */
    public void encode(){
        toBinary(c.armInstruct.getText());
        toHex(c.bInstruct.getText());
    }
    
    /**Converts assembly language into binary
     * @param s The address for the String object called,
     * which in this case is the text input in the assembly language JTextField
     */
    public void toBinary(String s){
        try{
            s = s.toLowerCase();
            if(s.charAt(3)==' ' && !(s.substring(3).contains("rr"))){
                StringBuilder binary = new StringBuilder(32);
                String upper = s.toUpperCase();
                String sub = upper.substring(0,3);
                while(c.b1.containsKey(checkValue(sub))){
                    binary.append("1110000"+ c.b1.get(sub) +"0");
                    sub=upper.substring(6,11);
                    binary.append(c.b2.get(checkValue(sub)));
                    sub=upper.substring(3,8);
                    binary.append(c.b2.get(checkValue(sub)) + "00000000");
                    sub=upper.substring(upper.length()-4, upper.length());
                    binary.append(c.b2.get(checkValue(sub)));
                    break;
                }
                c.bInstruct.setText(binary.toString());
                if(c.bInstruct.getText().contains("null")){
                    c.message.setText("Please provide proper ARM instructions."); c.hInstruct.setText(""); c.bInstruct.setText("");
                }
            
            }
            else{
                c.message.setText("Please provide proper ARM instructions."); c.hInstruct.setText(""); c.bInstruct.setText("");
            }
            
        }
        catch(NumberFormatException e){ c.message.setText("Please provide proper ARM instructions."); c.hInstruct.setText(""); c.bInstruct.setText("");}
        catch(StringIndexOutOfBoundsException e){  c.message.setText("Please provide proper ARM instructions."); c.hInstruct.setText(""); c.bInstruct.setText("");}
    }

    /**Converts binary into hex
     * @param b The address for the String object called,
     * which in this case is the text input in the binary JTextField
     */
    public void toHex(String b){
        try{
            Long decimal = Long.parseLong(b,2);
            String hex = Long.toHexString(decimal);
            hex = hex.toUpperCase();
            c.hInstruct.setText(hex);
        }
        catch(NumberFormatException e){ c.message.setText("Please provide proper ARM instructions."); c.hInstruct.setText(""); c.bInstruct.setText("");}
    }

    /**Converts binary into hex and assembly language
     * or hex into binary and assembly language
     * @param  d The address for the String object called,
     * which in this case is the text input in the binary or hex JTextField
     */
    public void decode(String d){ 
        try{
            if(d.equals(c.bInstruct.getText())){
                if(d.length() == 32){
                    toHex(c.bInstruct.getText());
                    StringBuilder assemble = new StringBuilder();
                    String sub = d.substring(7,11);
                    while(c.b1.containsValue(checkValue(sub))){
                        assemble.append(c.b1.get(checkValue(sub)) + " ");
                        sub = d.substring(16,20);
                        assemble.append(c.b2.get(checkValue(sub))+ ",");
                        sub = d.substring(12,16);
                        assemble.append(c.b2.get(checkValue(sub)) + ",");
                        sub = d.substring(d.length()-4,d.length());
                        assemble.append(c.b2.get(checkValue(sub)));
                        break;
                    }
                    c.armInstruct.setText(assemble.toString());
                }
            else{
                c.message.setText("Please provide proper ARM instructions."); c.hInstruct.setText(""); c.bInstruct.setText("");
            }        
            }
            if(d.equals(c.hInstruct.getText())){
                Long i = Long.parseLong(c.hInstruct.getText(), 16);
                String binary = Long.toBinaryString(i);
                c.bInstruct.setText(binary);
                decode(c.bInstruct.getText());
            }
        }
        catch(NumberFormatException e){ c.message.setText("Please provide proper ARM instructions."); c.hInstruct.setText(""); c.bInstruct.setText(""); }
        
    }

    /**Checks if string contains ARM7 instruction or register,
     * and if assembly language input follows appropriate rules
     * @param s Address of String object called,
     * the substring of assembly language string to evaluate each instruction/register
     * @return r The instruction or register, or null if input is not correctly formatted 
     */
    public String checkValue(String s){
        String[] values = {"AND", "EOR", "SUB", "RSB", "ADD", "ADC", "SBC", "RSC", "TST", "TEQ",
                            "CMP","CMN", "ORR", "MOV", "BIC", "MVN",
                            "R0", "R1", "R2", "R3", "R4", "R5", "R6", "R7", "R8", "R9",
                            "R10","R11", "R12", "R13", "R14", "R15",
                            "0000", "0001", "0010", "0011", "0100", "0101", "0110", "0111", "1000", "1001",
                            "1010","1011", "1100", "1101", "1110", "1111"};
        String r = "";
        for(int i=0;i<values.length;i++){
            if(s.contains(values[i])){
                r=values[i];
            }
        }

        String exclusions = "ABCDEFGHIJXLMNOPQSTUV!@#$%^&*()-+=./;'[]`";
        for(int i=0;i<exclusions.length();i++){
            if(s.substring(3).contains(String.valueOf(exclusions.charAt(i)))){
                r="null";
            }

        }

        int position = s.lastIndexOf(',');
        if(r.length() == 3 && !c.b1.containsKey(r) ){            
            if(position-3 != s.indexOf(r) && position-1 != s.indexOf(r) && s.indexOf(r)+3 != s.length()){
                r="null";
            }
        }
    
        if(r.length() == 2 && !c.b1.containsKey(r) ){
            if(position-2 != s.indexOf(r) && position-1 != s.indexOf(r) && s.indexOf(r)+2 != s.length()){
                r="null";
            }
        }
        
        return r;
    }
    
}
