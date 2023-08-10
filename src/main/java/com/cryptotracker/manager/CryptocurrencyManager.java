package com.cryptotracker.manager;

import com.cryptotracker.model.Cryptocurrency;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CryptocurrencyManager {
    private List<Cryptocurrency> trackedCryptocurrencies;

    public CryptocurrencyManager() {
        trackedCryptocurrencies = new ArrayList<>();
    }

    public void addCryptocurrency(Cryptocurrency cryptocurrency) {
        trackedCryptocurrencies.add(cryptocurrency);
    }
    public Cryptocurrency getCryptocurrencyByName(String name) {
        for (Cryptocurrency cryptocurrency : trackedCryptocurrencies) {
            if (cryptocurrency.getName().equalsIgnoreCase(name)) {
                return cryptocurrency;
            }
        }
        return null; // Cryptocurrency with the specified name not found
    }


    public boolean deleteCryptocurrencyByName(String name) {
        Cryptocurrency cryptocurrencyToRemove = null;

        for (Cryptocurrency cryptocurrency : trackedCryptocurrencies) {
            if (cryptocurrency.getName().equalsIgnoreCase(name)) {
                cryptocurrencyToRemove = cryptocurrency;
                break;
            }
        }

        if (cryptocurrencyToRemove != null) {
            trackedCryptocurrencies.remove(cryptocurrencyToRemove);
            return true; // Deletion successful
        } else {
            return false; // Cryptocurrency not found
        }
    }


    public List<Cryptocurrency> getAllTrackedCryptocurrencies() {
        return trackedCryptocurrencies;
    }
}
