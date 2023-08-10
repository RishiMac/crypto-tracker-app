package com.cryptotracker;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Scanner;
import java.io.IOException;

import com.cryptotracker.manager.CryptocurrencyManager;
import com.cryptotracker.model.Cryptocurrency;
import org.json.JSONException;
import org.json.JSONObject;

public class CryptoTrackerApp {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;
        CryptocurrencyManager manager = new CryptocurrencyManager(); // Create an instance

        System.out.println("Welcome to CryptoTrackerApp!");

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

    private static void viewCryptocurrencyInfo() {
        System.out.println("\n");

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter cryptocurrency name to view info: ");
        String symbol = scanner.nextLine();

        // Construct the API URL
        String coinCapApiUrl = "https://api.coincap.io/v2/assets/" + symbol;

        try {
            String apiResponse = fetchDataFromApi(coinCapApiUrl);

            // Parse the JSON response
            JSONObject jsonObject = new JSONObject(apiResponse);
            JSONObject data = jsonObject.getJSONObject("data");

            // Extract relevant data
            String name = data.getString("name");
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

    private static void deleteCryptocurrency(CryptocurrencyManager manager) {
        System.out.println("\n");

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter cryptocurrency name to delete: ");
        String name = scanner.nextLine();

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

