
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	public static void main(String[] args) {
		int portNumber = 6789;

		try {
			// Create a ServerSocket to listen for incoming connections on the specified
			// port
			ServerSocket serverSocket = new ServerSocket(portNumber);
			System.out.println("Server is listening for incoming connections on port " + portNumber + "...");

			while (true) {

				// Accept a connection from a client
				Socket clientSocket = serverSocket.accept();
				System.out.println("Accepted connection from " + clientSocket.getInetAddress());

				try {
					// Set up input and output streams for communication with the client
					BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
					PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

					// Read a line of data sent by the client
					String data = in.readLine();
					System.out.println("Received data from client: " + data);

					// Process the client's request and generate a response
					String response = handleRequest(data);

					// Send the response back to the client
					out.println(response);

				} finally {
					// Close the client socket
					clientSocket.close();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Handle the client's request and generate an appropriate response
	private static String handleRequest(String request) {
		if (request == null || request.isEmpty()) {
			return "500 Request is empty";
		}

		// Split the request into parts
		String[] parts = request.split(" ");

		// Check if the request has exactly two parts
		if (parts.length != 2) {

			try {
				// Attempt to parse the first part as an integer
				int val = Integer.parseInt(parts[0]);
				return "300 Bad request";

			} catch (Exception e) {
				// If parsing as integer fails, return an error message
				return "400 The number is missing or invalid";
			}
		}

		// Extract the command and number from the request
		String command = parts[0];
		String numberStr = parts[1];
		int number;

		// Attempt to parse the number as an integer

		try {
			number = Integer.parseInt(numberStr);
		} catch (Exception e) {

			// If parsing as integer fails, return an error message
			return "400 The number is missing or invalid";
		}

		// Process the request based on the command given from the client
		switch (command) {
		case "B":
			return "200, " + Integer.toBinaryString(number);
		case "H":
			return "200, " + Integer.toHexString(number).toUpperCase();
		default:
			return "300 Bad request";
		}
	}
}
