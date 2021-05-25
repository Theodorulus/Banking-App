package service;

import model.*;

public class BankService {
    private static BankService INSTANCE;

    private BankService () { }

    public static BankService getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new BankService();
        }
        return INSTANCE;
    }

    public void deleteAllBankCurrencies(Bank bank) {
        bank.getCurrencies().clear();
    }

    public void deleteAllBankClients(Bank bank) {
        bank.getClients().clear();
    }

    public void deleteAllAccounts(Bank bank) {
        bank.getClients().forEach(client -> client.getAccounts().clear());
    }

    public void deleteAllCards(Bank bank) {
        bank.getClients().forEach(client -> client.getAccounts().forEach(account -> account.getCards().clear()));
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

    public Currency findCurrencyByFullName(Bank bank, String currencyName) throws Exception {
        for (Currency currency : bank.getCurrencies()) {
            if(currency.getCurrencyName().equals(currencyName)) {
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

    public Client findClientById(Bank bank, String id) throws Exception {
        for(Client client : bank.getClients()) {
            if(client instanceof Individual) {
                if(((Individual) client).getIdentityNumber().equals(id)) {
                    return client;
                }
            } else if (client instanceof Company) {
                if(((Company) client).getFiscalCode().equals(id)) {
                    return client;
                }
            }

        }
        throw new Exception("There isn't a client with that id in this Bank.");
    }

    public Account findAccountByIban(Bank bank, String iban) throws Exception {
        for(Client client : bank.getClients()) {
            for(Account account : client.getAccounts()) {
                if(account.getIban().equals(iban)) {
                    return account;
                }
            }
        }
        throw new Exception("There isn't an account with that iban in this Bank.");
    }
}
