package service;

import model.Account;
import model.Client;
import model.Currency;
import model.Vault;

import java.util.List;
import java.util.stream.Collectors;

public class ClientService {
    private static ClientService INSTANCE;

    private ClientService () { }

    public static ClientService getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new ClientService();
        }
        return INSTANCE;
    }

    public void addVault(Client client, Vault vault) {
        client.getVaults().add(vault);
    }

    public Account findAccountByCurrencyName(Client client, String currencyName) throws Exception {
        List<Account> accounts = client.getAccounts();
        List<Account> accountsWithRequestedCurrency = accounts.stream()
                .filter(account -> account.getCurrency().getAbbreviation().equals(currencyName))
                .collect(Collectors.toList());
        if(accountsWithRequestedCurrency.size() > 0) {
            return accountsWithRequestedCurrency.get(0);
        }
        throw new Exception("No accounts with given currency");
    }
}
