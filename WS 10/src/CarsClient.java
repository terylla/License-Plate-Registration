//a simple client application that exchanges OBJECTS
import java.net.*;
import java.io.*;


public class CarsClient {
    public static void main(String[] args) throws IOException {

        Socket clientSocket;

        try {

            // Connect to the server name: "localhost" port number: 8000 */
            clientSocket = new Socket(InetAddress.getByName("localhost"), 8000);
            System.out.println("Connected to " + clientSocket.getInetAddress().getHostName());

            // Connect input and output streams to the socket
            ObjectInputStream oisFromServer = new ObjectInputStream(clientSocket.getInputStream());
            ObjectOutputStream oosToServer = new ObjectOutputStream(clientSocket.getOutputStream());
            BufferedReader inFromUser = new BufferedReader(
                    new InputStreamReader(System.in));

            System.out.println("I/O streams connected to the socket");

            // Create Car objects for Client to pass to Server for registration
            Car[] cars = new Car[4];
            cars[0] = new Car("2021 Jeep Wrangler 4XE", "Matte Army Green", 1000);
            cars[1] = new Car("Audi 2021 RS7 Sportback", "Black", 2000);
            cars[2] = new Car("Range Rover", "Black/White", 3000);
            cars[3] = new Car("Honda Civic 2019", "Black", 4000);


            // Now loop until no more car to register
            for (int i = 0; i < cars.length; i++) {

                try {
                    System.out.println("\nPress Enter to continue, or type Q/q to quit:");
                    String toClient = inFromUser.readLine();

                    // Close Client if user enters q or Q otherwise to the else{}
                    if (toClient.equals("q") || toClient.equals("Q")) {
                        clientSocket.close();
                        break;
                    } else {
                        // Send the Car object to Server
                        oosToServer.writeObject(cars[i]);
                        oosToServer.flush();

                        //Sending this car to the server for registration
                        System.out.println("\nCar to be registered:\n" + cars[i]);
                        System.out.println("Registering car on server...Please hold.");

                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }

                        // Receive the updated Car object from Server & print it
                        cars[i] = (Car) oisFromServer.readObject();
                        if (cars[i] != null) {
                            System.out.println("Car was successfully registered! New car info:\n" + cars[i]);
                        } else {
                            System.out.println("Car was not registered.");
                        }
                    }

                } catch (ClassNotFoundException cnf) {
                    cnf.printStackTrace();
                } catch (EOFException eof) {
                    System.out.println("No more car to register. The Server has terminated connection!");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            // Closing the connection to Client
            oosToServer.close();
            System.out.println("\n*** Connection to Server has been terminated! ***");
            oisFromServer.close();
            System.out.println("*** Client: Closing the connection... ***");
            clientSocket.close();

            System.out.println("\n*** The Client is going to stop running... ***");

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }




    }
}