package com.cryptotracker;

<<<<<<< HEAD
import java.sql.*;

=======
>>>>>>> eb519fbbed1e59ecd9bd67674561484e173134aa
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Scanner;
import java.io.IOException;
<<<<<<< HEAD
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
=======
>>>>>>> eb519fbbed1e59ecd9bd67674561484e173134aa

import com.cryptotracker.manager.CryptocurrencyManager;
import com.cryptotracker.model.Cryptocurrency;
import org.json.JSONException;
import org.json.JSONObject;

public class CryptoTrackerApp {
<<<<<<< HEAD
    // Database connection details
    private static final String DB_URL = "jdbc:mysql://localhost:3306/CryptoTrackerDB";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Rishi#11m";

    // Establish a database connection
    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }
=======
>>>>>>> eb519fbbed1e59ecd9bd67674561484e173134aa

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;
        CryptocurrencyManager manager = new CryptocurrencyManager(); // Create an instance

        System.out.println("Welcome to CryptoTrackerApp!");

<<<<<<< HEAD
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
        executorService.scheduleAtFixedRate(() -> updateInvestmentValues(manager), 0, 1, TimeUnit.SECONDS);

        // Shut down the executor service when the program exits
        Runtime.getRuntime().addShutdownHook(new Thread(executorService::shutdown));

=======
>>>>>>> eb519fbbed1e59ecd9bd67674561484e173134aa
        while (!exit) {
            System.out.println("\nMenu:");
            System.out.println("1. Add/Remove Cryptocurrency Amount (negative numbers will subtract from existsing amount)");
            System.out.println("2. Delete Cryptocurrency");
            System.out.println("3. View Cryptocurrency Info");
            System.out.println("4. View Tracked Cryptocurrencies");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    addCryptocurrency(manager);
                    break;
                case 2:
                    deleteCryptocurrency(manager);
                    break;
                case 3:
                    viewCryptocurrencyInfo();
                    break;
                case 4:
                    viewTrackedCryptocurrencies(manager);
                    break;
                case 5:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

        System.out.println("Thank you for using CryptoTrackerApp!");
    }
<<<<<<< HEAD
    private static void updateInvestmentValues(CryptocurrencyManager manager) {
        try (Connection connection = getConnection()) {
            // Fetch tracked cryptocurrencies from the Cryptocurrencies table
            String selectQuery = "SELECT CryptoID, Name, InvestedAmount, CryptoAmount FROM Cryptocurrencies";
            try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
                 ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int cryptoID = resultSet.getInt("CryptoID");
                    String name = resultSet.getString("Name");
                    double investedAmount = resultSet.getDouble("InvestedAmount");
                    double cryptoAmount = resultSet.getDouble("CryptoAmount");
                    double currentPrice = getCurrentPriceFromApi(name);
                    double latestPrice = getLatestPrice(name);
                    double currentValue = cryptoAmount * latestPrice;

                    // Update the database with the new current value
                    String updateQuery = "UPDATE Cryptocurrencies SET CurrentValue = ?, CryptoAmount = ? WHERE CryptoID = ?";
                    try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
                        updateStatement.setDouble(1, currentValue);
                        updateStatement.setDouble(2, cryptoAmount);
                        updateStatement.setInt(3, cryptoID);
                        updateStatement.executeUpdate();

//                        int rowsAffected = updateStatement.executeUpdate();
//                        if (rowsAffected > 0) {
//                            System.out.println("Updated current value for " + name +
//                                    ": " + currentValue);
//                        } else {
//                            System.out.println("Failed to update current value.");
//                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private static double getCurrentPriceFromApi(String name) {
        String coinCapApiUrl = "https://api.coincap.io/v2/assets/" + name;

        try {
            String apiResponse = fetchDataFromApi(coinCapApiUrl);
            JSONObject jsonObject = new JSONObject(apiResponse);
            JSONObject data = jsonObject.getJSONObject("data");
            return data.getDouble("priceUsd");
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            return 0.0;
        }
    }
=======
>>>>>>> eb519fbbed1e59ecd9bd67674561484e173134aa

    private static void viewCryptocurrencyInfo() {
        System.out.println("\n");

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter cryptocurrency name to view info: ");
<<<<<<< HEAD
        String name = scanner.nextLine();

        // Construct the API URL
        String coinCapApiUrl = "https://api.coincap.io/v2/assets/" + name;
=======
        String symbol = scanner.nextLine();

        // Construct the API URL
        String coinCapApiUrl = "https://api.coincap.io/v2/assets/" + symbol;
>>>>>>> eb519fbbed1e59ecd9bd67674561484e173134aa

        try {
            String apiResponse = fetchDataFromApi(coinCapApiUrl);

            // Parse the JSON response
            JSONObject jsonObject = new JSONObject(apiResponse);
            JSONObject data = jsonObject.getJSONObject("data");

            // Extract relevant data
<<<<<<< HEAD
            String symbol = data.getString("symbol");
=======
            String name = data.getString("name");
>>>>>>> eb519fbbed1e59ecd9bd67674561484e173134aa
            String rank = data.getString("rank");
            String priceUsd = data.getString("priceUsd");
            String marketCapUsd = data.getString("marketCapUsd");
            String volumeUsd24Hr = data.getString("volumeUsd24Hr");
            String changePercent24Hr = data.getString("changePercent24Hr");

            // Display relevant information about the cryptocurrency
            System.out.println("Cryptocurrency Information:");
            System.out.println("Name: " + name);
            System.out.println("Symbol: " + symbol);
            System.out.println("Rank: " + rank);
            System.out.println("Price (USD): " + priceUsd);
            System.out.println("Market Cap (USD): " + marketCapUsd);
            System.out.println("24Hr Volume (USD): " + volumeUsd24Hr);
            System.out.println("24Hr Change Percent: " + changePercent24Hr + "%");

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }
    private static void addCryptocurrency(CryptocurrencyManager manager) {
        System.out.println("\n");

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter cryptocurrency name: ");
        String name = scanner.nextLine();

        System.out.print("Enter invested amount: ");
        double investedAmount = scanner.nextDouble();

<<<<<<< HEAD
        double latestPrice = getLatestPrice(name);

        try (Connection connection = getConnection()) {
            int userID = getUserId(connection);


//            double currentVal = getCurrentValue(connection, userID);




            // Check if the cryptocurrency already exists for the user
            String selectQuery = "SELECT CryptoID, InvestedAmount, CurrentValue FROM Cryptocurrencies WHERE UserID = ? AND Name = ?";
            try (PreparedStatement selectStatement = connection.prepareStatement(selectQuery)) {
                selectStatement.setInt(1, userID);
                selectStatement.setString(2, name);

                try (ResultSet resultSet = selectStatement.executeQuery()) {
                    if (resultSet.next()) {
                        int cryptoID = resultSet.getInt("CryptoID");
                        double existingInvestedAmount = resultSet.getDouble("InvestedAmount");
                        double existingCurrentValue = resultSet.getDouble("CurrentValue");

                        // Update the existing row with the new invested amount
                        double newInvestedAmount = existingInvestedAmount + investedAmount;
                        double newCurrentValue = existingCurrentValue + investedAmount;
                        double newCryptoAmount = newCurrentValue/latestPrice;
                        String updateQuery = "UPDATE Cryptocurrencies SET InvestedAmount = ?, CurrentValue = ?, CryptoAmount = ? WHERE CryptoID = ?";
                        try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
                            updateStatement.setDouble(1, newInvestedAmount);
                            updateStatement.setDouble(2, newCurrentValue);
                            updateStatement.setDouble(3, newCryptoAmount);
                            updateStatement.setInt(4, cryptoID);


                            int rowsAffected = updateStatement.executeUpdate();
                            if (rowsAffected > 0) {
                                System.out.println("Investment updated for cryptocurrency: " + name +
                                        ". New invested amount: " + newInvestedAmount);
                            } else {
                                System.out.println("Failed to update cryptocurrency investment.");
                            }
                        }
                    } else {
                        // Get the symbol from the API based on the name
                        String symbol = getSymbolFromApi(name);

                        // Insert data into Cryptocurrencies table
                        String insertQuery = "INSERT INTO Cryptocurrencies (UserID, Symbol, Name, InvestedAmount, CurrentValue, CryptoAmount) VALUES (?, ?, ?, ?, ?, ?)";
                        try (PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {
                            insertStatement.setInt(1, userID);
                            insertStatement.setString(2, symbol); // Use the retrieved symbol
                            insertStatement.setString(3, name);
                            insertStatement.setDouble(4, investedAmount);
                            insertStatement.setDouble(5, investedAmount);
                            insertStatement.setDouble(6, investedAmount/latestPrice);

                            int rowsAffected = insertStatement.executeUpdate();
                            if (rowsAffected > 0) {
                                System.out.println("Cryptocurrency added: " + name +
                                        " at " + investedAmount + " investment");
                            } else {
                                System.out.println("Failed to add cryptocurrency.");
                            }
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private static int getUserId(Connection connection) throws SQLException {
        int userID = -1; // Default value if user is not found

        // Query to retrieve UserID based on the user's email
        String query = "SELECT UserID FROM Users WHERE Email = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, "rishi.machanpalli@gmail.com"); // Replace with user's email
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    userID = resultSet.getInt("UserID");
                }
            }
        }

        return userID;
    }

    private static double getCurrentValue(Connection connection, int cryptoID) {
        double currentValue = 0.0; // Default value if current value is not found

        // Query to retrieve CurrentValue based on the CryptoID
        String query = "SELECT CurrentValue FROM Cryptocurrencies WHERE CryptoID = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, cryptoID);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    currentValue = resultSet.getDouble("CurrentValue");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return currentValue;
    }



=======
        // Check if the cryptocurrency already exists in the manager
        Cryptocurrency existingCryptocurrency = manager.getCryptocurrencyByName(name);

        if (existingCryptocurrency != null) {
            // Update the existing cryptocurrency's investment amount
            existingCryptocurrency.setInvestedAmount(existingCryptocurrency.getInvestedAmount() + investedAmount);
            if(existingCryptocurrency.getInvestedAmount() < 0) {
                existingCryptocurrency.setInvestedAmount(0);
                System.out.println("Cryptocurrency investment is set to 0 because you can't have negative crypto.");
            } else {
                System.out.println("Cryptocurrency investment updated: " + existingCryptocurrency.getName() +
                        " at " + existingCryptocurrency.getInvestedAmount() + " investment");
            }
        } else {

            String symbol = "Placeholder";

            // Create a new Cryptocurrency object
            Cryptocurrency cryptocurrency = new Cryptocurrency(symbol, name, investedAmount);

            // Add the cryptocurrency to the manager
            manager.addCryptocurrency(cryptocurrency);

            System.out.println("Cryptocurrency added: " + cryptocurrency.getName() +
                    " at " + cryptocurrency.getInvestedAmount() + " investment");
        }
    }

>>>>>>> eb519fbbed1e59ecd9bd67674561484e173134aa
    private static void deleteCryptocurrency(CryptocurrencyManager manager) {
        System.out.println("\n");

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter cryptocurrency name to delete: ");
        String name = scanner.nextLine();

<<<<<<< HEAD
        try (Connection connection = getConnection()) {
            // Delete cryptocurrency from the Cryptocurrencies table
            String deleteQuery = "DELETE FROM Cryptocurrencies WHERE Name = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
                preparedStatement.setString(1, name);

                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Cryptocurrency deleted: " + name);
                } else {
                    System.out.println("Cryptocurrency not found in user's tracking.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private static double getLatestPrice(String name) {
        try {
            String apiUrl = "https://api.coincap.io/v2/assets/" + name;
            String apiResponse = fetchDataFromApi(apiUrl);
            JSONObject jsonObject = new JSONObject(apiResponse);
            JSONObject data = jsonObject.getJSONObject("data");
            return data.getDouble("priceUsd");
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            return 0.0;
        }
    }
    private static void viewTrackedCryptocurrencies(CryptocurrencyManager manager) {
        System.out.println("\n");

        try (Connection connection = getConnection()) {
            String selectQuery = "SELECT CryptoID, Name, InvestedAmount, CryptoAmount FROM Cryptocurrencies";
            try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
                 ResultSet resultSet = preparedStatement.executeQuery()) {
                System.out.println("Tracked Cryptocurrencies:");
                while (resultSet.next()) {
                    int cryptoID = resultSet.getInt("CryptoID");
                    String name = resultSet.getString("Name");
                    double investedAmount = resultSet.getDouble("InvestedAmount");
                    double cryptoAmount = resultSet.getDouble("CryptoAmount");
                    String symbol = getSymbolFromApi(name); // Get the symbol from the API based on the name
                    double latestPrice = getLatestPrice(name); // Get the latest price of the cryptocurrency

                    // Calculate the current value of the investment
                    double currentValue = cryptoAmount * latestPrice;

                    System.out.println("Name: " + name);
                    System.out.println("Symbol: " + symbol);
                    System.out.println("Invested Amount: " + investedAmount);
                    System.out.println("Current Value: " + currentValue);
                    System.out.println("latestPrice: " + latestPrice);
                    System.out.println("Cryptocurrency Amount: " + cryptoAmount);
                    System.out.println("------------------------");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
=======
        // Delete the cryptocurrency from the manager based on name
        boolean isDeleted = manager.deleteCryptocurrencyByName(name); // Use the instance to call the method

        if (isDeleted) {
            System.out.println("Cryptocurrency deleted: " + name);
        } else {
            System.out.println("Cryptocurrency not found in user's tracking.");
        }
    }


    private static void viewTrackedCryptocurrencies(CryptocurrencyManager manager) {
        System.out.println("\n");

        List<Cryptocurrency> trackedCryptocurrencies = manager.getAllTrackedCryptocurrencies(); // Use the instance to call the method

        System.out.println("Tracked Cryptocurrencies:");
        for (Cryptocurrency cryptocurrency : trackedCryptocurrencies) {
            String name = cryptocurrency.getName();
            String symbol = getSymbolFromApi(name); // Get the symbol from the API based on the name
            double investedAmount = cryptocurrency.getInvestedAmount();

            System.out.println("Name: " + name);
            System.out.println("Symbol: " + symbol);
            System.out.println("Invested Amount: " + investedAmount);
            System.out.println("------------------------");
        }
    }

>>>>>>> eb519fbbed1e59ecd9bd67674561484e173134aa
    private static String getSymbolFromApi(String name) {
        String coinCapApiUrl = "https://api.coincap.io/v2/assets/" + name;

        try {
            String apiResponse = fetchDataFromApi(coinCapApiUrl);

            // Parse the JSON response
            JSONObject jsonObject = new JSONObject(apiResponse);
            JSONObject data = jsonObject.getJSONObject("data");

            if (data != null) {
                return data.getString("symbol");
            }

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return "N/A"; // Return "N/A" if symbol cannot be retrieved
    }




    private static String fetchDataFromApi(String apiUrl) throws IOException {
        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            Scanner scanner = new Scanner(connection.getInputStream());
            StringBuilder response = new StringBuilder();
            while (scanner.hasNextLine()) {
                response.append(scanner.nextLine());
            }
            scanner.close();
            return response.toString();
        } else {
            throw new IOException("Request failed with response code: " + responseCode);
        }
    }
}

