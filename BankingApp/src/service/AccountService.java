package service;

import model.*;

import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Collectors;

public class AccountService {
    private static AccountService INSTANCE;

    private AccountService () { }

    public static AccountService getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new AccountService();
        }
        return INSTANCE;
    }

    public void createAccount(Bank bank, Client client, Currency currency, double firstDeposit) {
        boolean ok = true;
        String iban;
        do {
            StringBuilder ibanBuilder = new StringBuilder(Account.getIbanPrefix());
            Random rand = new Random();
            ibanBuilder.append(rand.nextInt(10));
            ibanBuilder.append(rand.nextInt(10));
            ibanBuilder.append("THBN");
            for (int i = 0; i < 13; i++) {
                ibanBuilder.append(rand.nextInt(10));
            }
            ibanBuilder.append(currency.getAbbreviation());
            iban = ibanBuilder.toString();

            List<Client> clients = bank.getClients();
            List<List<Account>> accountsListOfLists = clients.stream()
                    .map(client1 -> client1.getAccounts())
                    .collect(Collectors.toList());
            for (List<Account> accounts : accountsListOfLists)
                for (Account account : accounts)
                    if (account.getIban().equals(iban))
                        ok = false;
        } while(!ok);
        Account account = new Account(iban, client, currency, firstDeposit);

        client.getAccounts().add(account);

        AuditService auditService = AuditService.getInstance();
        auditService.logAction("Create Account");
    }

    public void closeAccount(Client client) {

        System.out.println("List of your accounts:");
        for(int i = 0; i < client.getAccounts().size(); i++) {
            System.out.println(String.valueOf(i + 1) + ": "+ client.getAccounts().get(i));
        }
        System.out.println("Which account would you like to close?(Please type the number before the iban)");
        Scanner scanner = new Scanner(System.in);
        int closedAccountIndex = scanner.nextInt();
        System.out.println("In which account would you like your funds to go?(Please type the number before the iban)");
        int transferAccountIndex = scanner.nextInt();
        Account transferAccount = client.getAccounts().get(transferAccountIndex - 1);
        Account closedAccount = client.getAccounts().get(closedAccountIndex - 1);
        Currency transferAccountCurrency = transferAccount.getCurrency();
        Currency closedAccountCurrency = closedAccount.getCurrency();
        double sumNewCurrency = closedAccount.getNumberOfUnits() * closedAccountCurrency.getValueDependingOnDollar() / transferAccountCurrency.getValueDependingOnDollar();
        transferAccount.setNumberOfUnits(transferAccount.getNumberOfUnits() + sumNewCurrency);

        client.getAccounts().remove(closedAccountIndex - 1);

        AuditService auditService = AuditService.getInstance();
        auditService.logAction("Close Account");
    }

    public void addTransaction(Account account, Transaction transaction) {
        account.getTransactions().add(transaction);
    }

}
