package model;


import service.AuditService;
import service.ReadingService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class Bank {
    private List<Client> clients;
    private Set<Currency> currencies;
    private static Bank INSTANCE;

    private Bank() {
        clients = new ArrayList<>();
        currencies = new TreeSet<>();
    }

    public static Bank getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new Bank();
        }
        return INSTANCE;
    }

    public List<Client> getClients() {
        return clients;
    }

    public Set<Currency> getCurrencies() {
        return currencies;
    }

    public void setClients(List<Client> clients) {
        this.clients = clients;
    }

    public void setCurrencies(Set<Currency> currencies) {
        this.currencies = currencies;
    }

    @Override
    public String toString() {
        StringBuilder bank = new StringBuilder("Bank clients: ");
        clients.forEach(client -> bank.append("\n-" + client));
        bank.append("\n\nBank currencies: ");
        currencies.forEach(currency -> bank.append("\n-" + currency));

        AuditService auditService = AuditService.getInstance();
        auditService.logAction("View Bank Clients and Currencies");

        return bank.toString();
        //return "Bank clients: " +  clients + "\nBank currencies: " + currencies;
    }
}
