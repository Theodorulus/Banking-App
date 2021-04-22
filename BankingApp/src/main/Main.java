package main;

import model.*;
import service.*;

import java.util.Date;
import java.util.Random;
import java.time.LocalDate;
import java.util.Set;
import java.util.TreeSet;

public class Main {
    public static void main (String[] args) {
        /*
        Date input:
        1
        2
        yes
        */

        /*
        Date date = new Date();
        System.out.println(date);
        Date date1 = new Date();
        System.out.println(date1);
        System.out.println(date1.compareTo(date));
        */

        Set<Client> clients = new TreeSet<> ();

        clients.add(new Individual("marcelus@gmail.com", "0712345678", "5000520999999", "Marcelus", "Wallace"));
        clients.add(new Individual("john@gmail.com", "0712345679", "5000520999998", "John", "Travolta"));
        clients.add(new Company("office@th_experts.com", "0777777777", "22111111", "TH Experts"));

        clients.forEach(System.out::println);
        //app();
    }
    public static void app() {
        // Add Client & View Clients
        Client[] clients = new Client[50];
        ClientService clientService = new ClientService();
        clientService.addClient(clients, new Individual("marcelus@gmail.com", "0712345678", "5000520999999", "Marcelus", "Wallace"));
        clientService.addClient(clients, new Company("office@th_experts.com", "0777777777", "22111111", "TH Experts"));
        clientService.addClient(clients, new Individual("john@gmail.com", "0712345679", "5000520999998", "John", "Travolta"));
        clientService.viewClients(clients);

        Currency[] currencies = new Currency[5];
        currencies[0] = new Currency();
        currencies[1] = new Currency("EURO", "EUR", 1.1769715);
        currencies[2] = new Currency("ROMANIAN LEU", "RON", 0.23978812);

        // Create account
        AccountService accountService = new AccountService();
        accountService.createAccount(clients[0], currencies[2], 1500);
        accountService.createAccount(clients[0], currencies[0], 100);
        accountService.createAccount(clients[0], currencies[1], 150);
        accountService.createAccount(clients[0], currencies[2], 500);
        accountService.createAccount(clients[2], currencies[0], 5000);
        System.out.println("\nAccounts of " + clients[0].toString() + ":");

        clients[0].getAccounts().forEach(System.out::println);

        // Close account

        System.out.println();
        accountService.closeAccount(clients[0]);
        System.out.println("\nAccounts of " + clients[0].toString() + " after closing an account:");

        clients[0].getAccounts().forEach(System.out::println);

        // Create card

        CardService cardService = new CardService();
        cardService.createCard(clients[0].getAccounts().get(0), "credit");
        cardService.createCard(clients[0].getAccounts().get(0), "debit");

        System.out.println("\nCards of " + clients[0].toString() + "'s account " + clients[0].getAccounts().get(0) + ":");
        clients[0].getAccounts().get(0).getCards().forEach(card -> System.out.println(card));

        // Freeze/unfreeze card
        System.out.println();

        cardService.freezeCard(clients[0].getAccounts().get(0).getCards().get(0));
        System.out.println("The card is: ");
        if(clients[0].getAccounts().get(0).getCards().get(0).isFrozen())
            System.out.println("Frozen");
        else
            System.out.println("Not Frozen");

        cardService.unfreezeCard(clients[0].getAccounts().get(0).getCards().get(0));

        System.out.println("The card is: ");

        if(clients[0].getAccounts().get(0).getCards().get(0).isFrozen())
            System.out.println("Frozen");
        else
            System.out.println("Not Frozen");

        //Create vault
        VaultService vaultService = new VaultService();
        vaultService.createVault(clients[0], currencies[2]);
        vaultService.addMemberToVault(clients[0].getVaults().get(0), clients[1]);

        System.out.println("\nVaults of " + clients[0] + ":");
        clients[0].getVaults().forEach(System.out::println);


        System.out.println("\nMembers of vault " + clients[0].getVaults().get(0) + ":");
        clients[0].getVaults().get(0).getMembers().forEach(System.out::println);

        //Make transfer
        TransactionService transactionService = new TransactionService();
        transactionService.makeTransfer(100, clients[0].getAccounts().get(0), clients[2],"salut");
        System.out.println("\nTransactions of " + clients[0] + "'s account " + clients[0].getAccounts().get(0) + ":");
        clients[0].getAccounts().get(0).getTransactions().forEach(System.out::println);

        System.out.println("\nTransactions of " + clients[2] + "'s account " + clients[2].getAccounts().get(0) + ":");
        clients[2].getAccounts().get(0).getTransactions().forEach(System.out::println);

        System.out.println("\nThe accounts after the transfer:");

        System.out.println(clients[0].getAccounts().get(0));
        System.out.println(clients[2].getAccounts().get(0));

        //Make vault deposit
        transactionService.makeVaultDeposit(100, clients[0].getAccounts().get(0), clients[0].getVaults().get(0));
        System.out.println("\nTransactions of " + clients[0] + "'s account " + clients[0].getAccounts().get(0) + ":");
        clients[0].getAccounts().get(0).getTransactions().forEach(System.out::println);

        //Extract statement
        StatementService statementService = new StatementService();
        statementService.extractStatement(clients[0].getAccounts().get(0));
    }
}
