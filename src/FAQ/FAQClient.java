package FAQ;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import User.*;

public class FAQClient extends JFrame {
    private JTextField queryField;
    private JTextArea responseArea;
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    public FAQClient() {
        setTitle("FAQ Client");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Input Panel
        JPanel inputPanel = new JPanel(new BorderLayout());
        queryField = new JTextField();
        JButton sendButton = new JButton("Send");
        inputPanel.add(queryField, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);

        // Response Area
        responseArea = new JTextArea();
        responseArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(responseArea);

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton backButton = new JButton("Sign In");
        buttonPanel.add(backButton);

        // Add components to the frame
        add(scrollPane, BorderLayout.CENTER);
        add(inputPanel, BorderLayout.SOUTH);
        add(buttonPanel, BorderLayout.NORTH);

        // Button Actions
        sendButton.addActionListener(e -> sendQuery());
        backButton.addActionListener(e -> navigateToSignIn());

        // Connect to server
        try {
            socket = new Socket("127.0.0.1", 5000); // Adjust as needed
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            responseArea.append("Connected to FAQ server.\n");
        } catch (IOException ex) {
            responseArea.append("Failed to connect to FAQ server. Please ensure the server is running.\n");
            queryField.setEnabled(false);
            sendButton.setEnabled(false);
        }
    }

    private void sendQuery() {
        String query = queryField.getText();
        if (query.isEmpty()) {
            responseArea.append("Please enter a question.\n");
            return;
        }

        try {
            out.println(query);
            String response = in.readLine();
            responseArea.append("You: " + query + "\n");
            responseArea.append("Server: " + response + "\n");
            queryField.setText("");
        } catch (IOException ex) {
            responseArea.append("Error communicating with server.\n");
        }
    }

    private void navigateToSignIn() {
        SignIn si = new SignIn(); // Assuming you have a SignIn class
        si.setVisible(true);
        dispose(); // Close FAQClient window
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            FAQClient client = new FAQClient();
            client.setLocationRelativeTo(null); // Ensure the window is centered
            client.setVisible(true);
        });
    }
}
