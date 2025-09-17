import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import javax.swing.*;
public class Lab4Client extends JFrame implements ActionListener {
JTextArea result = new JTextArea(20,40);
JTextField userInput = new JTextField(30);
JLabel serverLabel = new JLabel("Server:");
JTextField serverField = new JTextField("localhost",10);
JLabel portLabel = new JLabel("Port:");
JLabel userLabel = new JLabel("Username:");
JTextField userField = new JTextField(20);
JTextField portField = new JTextField("2025", 5);
JButton connectButton = new JButton("Connect");
JButton sendButton = new JButton("Send");
JLabel errors = new JLabel();
JScrollPane scroller = new JScrollPane();
Socket socket;
String username = "";
BufferedReader bufferedReader;
BufferedWriter bufferedWriter;
PrintWriter out;
Thread thread;


public Lab4Client() {
    setTitle("Simple Chat Client");
    setSize(500,500);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setLayout(new FlowLayout());


    result.setEditable(false);
    scroller.getViewport().add(result);
    add (scroller);
    add(userInput); userInput.addActionListener(this);
    add (sendButton); sendButton.addActionListener(this); sendButton.setEnabled(true);
    JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    bottomPanel.add (serverLabel); bottomPanel.add (serverField);
    bottomPanel.add (portLabel); bottomPanel.add (portField);
    bottomPanel.add(connectButton); connectButton.addActionListener(this);
    JPanel user = new JPanel(new FlowLayout(FlowLayout.LEFT));
    user.add(userLabel); user.add(userField);


   JPanel notifications = new JPanel(new FlowLayout(FlowLayout.LEFT));
    notifications.add(errors);
    add (user, BorderLayout.NORTH);
    add( notifications, BorderLayout.NORTH);
    add(bottomPanel, BorderLayout.SOUTH);
}
@Override
public void actionPerformed(ActionEvent evt) {
   
    try {


   
        if (evt.getActionCommand().equals("Connect") ||
                connectButton.getText().equals("Connect") && evt.getSource() == userInput) {




            String host = serverField.getText().trim();
            int port = Integer.parseInt(portField.getText().trim());




            socket = new Socket(host, port);
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
           
           
            username = userField.getText().trim();
            if (username.isEmpty()) {
             errors.setText("Username required.");
            return;
            }
            bufferedWriter.write(username);
            bufferedWriter.newLine();
            bufferedWriter.flush();


            thread = new ReadThread(bufferedReader, result, this);
            thread.start();
            sendButton.setEnabled(true);
            connectButton.setText("Disconnect");
            userInput.setText("");


            if (socket.isClosed()) {
                errors.setText("Connection closed. Try a different username.");
                connectButton.setText("Connect");
                sendButton.setEnabled(false);
            }
        }
        else if (evt.getActionCommand().equals("Disconnect")) {
            thread.interrupt();
            socket.close();
            bufferedReader.close();
            bufferedWriter.close();
            sendButton.setEnabled(false);
            connectButton.setText("Connect");
        }
       
         
        else if (evt.getActionCommand().equals("Send") ||
            sendButton.isEnabled() && evt.getSource() == userInput) {
            String message = userInput.getText().trim();
            if(!message.isEmpty()) {
                bufferedWriter.write(username + ": " + message);
                bufferedWriter.newLine();
                bufferedWriter.flush();
                userInput.setText("");
        }}
       
    } catch(UnknownHostException uhe) {
        errors.setText(uhe.getMessage());
    } catch(IOException ioe) {
        errors.setText(ioe.getMessage());
    }
   


   }
   public void resetConnection() {
    try {
        if (thread != null) thread.interrupt();
        if (socket != null) socket.close();
        if (bufferedReader != null) bufferedReader.close();
        if (bufferedWriter != null) bufferedWriter.close();
    } catch (IOException e) {
        errors.setText("Error closing resources.");
    }


    sendButton.setEnabled(false);
    connectButton.setText("Connect");
    errors.setText("Choose another username.");
}


    public static void main(String[] args) throws IOException {
        Lab4Client display = new Lab4Client();
        display.setVisible(true);
       Lab4Server.main(null);
    }


}


class ReadThread extends Thread {
    BufferedReader in;
    JTextArea display;
    Lab4Client gui;
    public ReadThread(BufferedReader br, JTextArea jta, Lab4Client gui) {
        this.in = br;
        this.display = jta;
        this.gui = gui;


    }
    public void run() {
        String s;
        try {
            while ((s = in.readLine()) != null) {
                if (s.equals("Username taken!")) {
                    JOptionPane.showMessageDialog(null,"Username taken, choose another."); //error for taken user
                    gui.resetConnection(); //connection stays
                    break;
                }
                display.append(s + '\n');
            }
        } catch (IOException ioe) {
            System.out.println("Error reading from socket");
        }
    }
   
   
}
