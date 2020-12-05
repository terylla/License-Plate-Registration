//a simple client/server application: car registration
//a SERVER program that uses a stream socket connection to exchange objects

import java.net.*;
import java.io.*;


public class CarsServer {
    public static void main(String[] args) {
        ServerSocket serverSocket; // TCP socket used for listening

        try {
            // Create a ServerSocket
            serverSocket = new ServerSocket(8000);

            // Listen for a connection and create a socket
            System.out.println("*** Opening server to register the car... ***");
            System.out.println("*** Listening for a connection... ***");
            Socket socketConnection = serverSocket.accept();

            // Displaying the addresses & ports which socket is connected
            System.out.println("\nTHE CLIENT IS CONNECTED:" +
                    "\n\t Inet address: "+ socketConnection.getInetAddress().getByName(null)
                    + " || Host address: " + socketConnection.getInetAddress().getHostName()
                    + "\n\t Remote port number: " + socketConnection.getPort()
                    + " || Local port number: " + socketConnection.getLocalPort());

            // Extra warning
            System.out.println("****** Please note: Server will automatically disconnect if there is no more car to register.");

            // Connect input and output streams to the socket
            ObjectOutputStream oosToClient = new ObjectOutputStream(socketConnection.getOutputStream());
            ObjectInputStream oisFromClient = new ObjectInputStream(socketConnection.getInputStream());
            BufferedReader inFromUser = new BufferedReader(
                    new InputStreamReader(System.in));

            // Create Car object to exchange with client
            Car car;

            try {
                while (true) { // Keep getting data from client while true

                    // Read the object from Client side
                    car = (Car) oisFromClient.readObject();
                    System.out.println("\nRegister the following car?\n " + car);
                    System.out.println("Enter Y/y to register, or any letter to skip:");

                    String toClient = inFromUser.readLine();
                     if (toClient.equals("y") || toClient.equals("Y")) {
                            // Register the car using getRegistered method from Car.java
                            car.getRegistered(String.valueOf(car.hashCode()));
                            // Send the registered object Car back to Client
                            oosToClient.writeObject(car);
                            System.out.println("Car was successfully registered. Car's information will display on Client's side.");
                     } else {
                         System.out.println("Car was not registered.");
                         oosToClient.writeObject(null);
                     }
                        oosToClient.flush();

                }

            } catch (ClassNotFoundException cnf) {
                cnf.printStackTrace();
            } catch (EOFException eof) {
                System.out.println("\n*** The Client has terminated connection! ***");
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Closing the connection to Client
            oosToClient.close();
            oisFromClient.close();
            socketConnection.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("*** The Server is going to stop running... ***");
    }
}