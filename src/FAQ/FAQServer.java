package FAQ;
import java.io.*;
import java.net.*;
import java.util.HashMap;

public class FAQServer {
    private static final int PORT = 5000; // Server port
    private static HashMap<String, String> faqMap;

    public FAQServer() {
        initializeFAQ(); // Initialize the FAQs
    }

    // Start the FAQ Server
    public void startServer() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("FAQ Server is running on port " + PORT + "...");

            while (true) {
                // Accept client connections
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress());

                // Handle the client in a separate thread
                new Thread(new ClientHandler(clientSocket)).start();
            }
        } catch (IOException e) {
            System.out.println("Server error: " + e.getMessage());
        }
    }

    // Initialize FAQs
    private void initializeFAQ() {
        faqMap = new HashMap<>();
        faqMap.put("What is Cheery Seats?", "Cheery Seats is an event management and ticketing system.");
        faqMap.put("How can I buy tickets?", "Log in, browse events, and click 'Buy Tickets' for your desired event.");
        faqMap.put("What payment methods are accepted?", "We accept credit cards, debit cards, and PayPal.");
        faqMap.put("Can I refund my tickets?", "Refunds are available within 24 hours of purchase.");
        faqMap.put("How do I contact support?", "You can contact support at support@cheeryseats.com.");
    }

    // ClientHandler to manage each client's connection
    private static class ClientHandler implements Runnable {
        private Socket clientSocket;

        public ClientHandler(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }

        @Override
        public void run() {
            try (
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)
            ) {
                String clientQuery;
                while ((clientQuery = in.readLine()) != null) {
                    System.out.println("Client asked: " + clientQuery);
                    String response = faqMap.getOrDefault(clientQuery, "Sorry, I don't have an answer for that.");
                    out.println(response);
                }
            } catch (IOException e) {
                System.out.println("Client connection error: " + e.getMessage());
            } finally {
                try {
                    clientSocket.close();
                } catch (IOException e) {
                    System.out.println("Error closing client socket: " + e.getMessage());
                }
            }
        }
    }
}
