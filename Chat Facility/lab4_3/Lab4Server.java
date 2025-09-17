import java.net.*;
import java.io.*;
import java.awt.*;
import java.util.*;
public class Lab4Server {
    private ServerSocket serverSocket;
   
    public Lab4Server(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }
    public void startServer() {
        System.out.println("Server started");


        try {


            while (!serverSocket.isClosed()) {


                Socket socket = serverSocket.accept();
                System.out.println("client has connected");
                ClientHandler clientHandler = new ClientHandler(socket);


                Thread thread = new Thread(clientHandler);
                thread.start();
            }


        } catch (IOException e) {
           System.out.println("Error:" + e.getMessage());
        }
            }


            public void closeServerSocket() {
                try {
                    if (serverSocket != null) {
                        serverSocket.close();
                        System.out.println("Socket closed.");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                 
                }
            }
            class ClientHandler implements Runnable {


                public static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();
                public static HashSet<String> usernames = new HashSet<String>();
                private Socket socket;
                private BufferedReader bufferedReader;
                private BufferedWriter bufferedWriter;
                private String clientUsername;
               
                public ClientHandler(Socket socket) {
                    try{
                        this.socket = socket;
                        this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                        this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));


                       while (true) {
                         this.clientUsername = bufferedReader.readLine(); //for usernames


                        if (this.clientUsername.isEmpty()) {
                        closeEverything(socket, bufferedReader, bufferedWriter);
                         return;
                }
                synchronized (usernames) {
                    if (!usernames.contains(this.clientUsername)) {
                        usernames.add(this.clientUsername);
                        break; //accepts username
                    } else {
                        bufferedWriter.write("Username taken!"); //if username is taken
                        bufferedWriter.newLine();
                        bufferedWriter.flush();
                        socket.close();
                        return;
                    }
                }}
                        clientHandlers.add(this); //add client to the arraylist, represents client handler object
                        broadcastMessage("SERVER: " + clientUsername + " has entered the chat."); //broadcast to other clients
                    }
                    catch (IOException e) {
                        closeEverything(socket, bufferedReader, bufferedWriter);
                    }
                }
           
           
            @Override
            public void run() { //everything is run on a seperate thread
                String messageFromClient;
           
                  //while socket is connected, program hold until recieve message from client
                    try {
                        while (socket.isConnected()) {
                        messageFromClient = bufferedReader.readLine();  //run in seperate thread to stop at that line of code
           
                    if (messageFromClient == null) {
                        break; //client disconnect
                    }
                        broadcastMessage(messageFromClient);  
                }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    closeEverything(socket, bufferedReader, bufferedWriter);
                }                                          
            }
            public void broadcastMessage(String messageToSend) {
                //loop through arraylist
                for (ClientHandler clientHandler : clientHandlers) {
                    try { //if doesn't equal the username then message is broadcasted
                       
                            clientHandler.bufferedWriter.write(messageToSend);
                            clientHandler.bufferedWriter.newLine();
                            clientHandler.bufferedWriter.flush();
                         
                    } catch (IOException e) {
                        closeEverything(socket, bufferedReader, bufferedWriter);
                    }
                }
            }
            public void removeClientHandler(){
                clientHandlers.remove(this);
                synchronized (usernames) {
                    usernames.remove(this.clientUsername);
                }
            }
            public void closeEverything(Socket socket, BufferedReader bufferedReader , BufferedWriter bufferedWriter) {
                removeClientHandler();
                broadcastMessage("SERVER: " + clientUsername + " left the chat.");
                usernames.remove(clientUsername);
           
                try {
                    if (bufferedReader != null) {
                        bufferedReader.close();
                    }
                    if (bufferedWriter != null ) {
                        bufferedWriter.close();
                    }
                    if (socket != null) {
                        socket.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
           
            }
   
            public static void main(String[] args) {
               
                try {
                ServerSocket serverSocket = new ServerSocket(2025);
                Lab4Server server = new Lab4Server(serverSocket);
               server.startServer();
            } catch (IOException e) {
                System.out.println("Unable to start server: " + e.getMessage());
            }


        }
    }
