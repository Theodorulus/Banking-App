package service;

import model.Bank;
import model.Client;
import model.Currency;

public class BankService {
    private static BankService INSTANCE;

    private BankService () { }

    public static BankService getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new BankService();
        }
        return INSTANCE;
    }

    public void addClient(Bank bank, Client client) {
        bank.getClients().add(client);
        AuditService auditService = AuditService.getInstance();
        auditService.logAction("Add Client to Bank");
    }

    public void addCurrency(Bank bank, Currency currency) {
        bank.getCurrencies().add(currency);
        AuditService auditService = AuditService.getInstance();
        auditService.logAction("Add Currency to Bank");
    }

    public Currency findCurrencyByName(Bank bank, String currencyName) throws Exception {
        for (Currency currency : bank.getCurrencies()) {
            if(currency.getAbbreviation().equals(currencyName)) {
                return currency;
            }
        }
        throw new Exception("No such currency.");
    }

    public Client findClientByEmail(Bank bank, String email) throws Exception {
        for(Client client : bank.getClients()) {
            if(client.getEmail().equals(email)) {
                return client;
            }
        }
        throw new Exception("There isn't a client with that e-mail in this Bank.");
    }
}
