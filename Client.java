
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
	public static void main(String[] args) {

		// Define server address and port
		String serverAddress = "10.100.144.62";
		int serverPort = 6789;

		// Create a scanner to read user input
		Scanner scanner = new Scanner(System.in);

		while (true) {
			try (Socket socket = new Socket(serverAddress, serverPort)) {
				System.out.println("Connected to the server on port " + serverPort);

				// Initialize input and output streams to communicate with the server
				BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

				// Display available commands to the user
				System.out.println("Commands:");
				System.out.println("B: Convert to binary");
				System.out.println("H: Convert to hexadecimal");
				System.out.println("Q: Quit");

				// Prompt the user to enter a command
				System.out.print("Enter command: ");
				String command = scanner.nextLine().toUpperCase();

				// If the command is to quit, exit the loop and close the program
				if (command.equals("Q")) {
					System.out.print("Succesfully quitted the program");
					break;
				}

				// Prompt the user to enter a number
				System.out.print("Enter a number: ");
				String number = scanner.nextLine();

				// Send the command and number to the server
				out.println((String) (command + " " + number).trim());

				// Receive response from the server
				String response = in.readLine();

				// Check if the server is shutting down
				if (response.equals("Q")) {
					System.out.println("Server shutting down.");
					break;
				}
				// Display the server's response
				System.out.println("Server response: " + response);
			} catch (IOException e) {

				// Handle server connection errors
				System.out.println("Server is down, please try later.");
				break;
			}
		}
		// Close the scanner
		scanner.close();
	}
}
