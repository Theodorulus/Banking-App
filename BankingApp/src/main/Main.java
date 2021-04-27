package main;

import model.*;
import model.Currency;
import service.*;

import java.util.*;
import java.time.LocalDate;

public class Main {
    public static void main (String[] args) {
        /*
        Input data:
        3
        4
        yes
        */
        readData();
        app();
    }

    public static void readData() {
        Bank bank = Bank.getInstance();
        ReadingService readingService = ReadingService.getInstance();

        readingService.readClients();
        readingService.readCurrencies();
        readingService.readAccounts();
        readingService.readCards();// should get Exception("No accounts with given currency") for (hans_landa@gmail.com,RON,debit)
    }

    public static void app() {
        Bank bank = Bank.getInstance();
        BankService bankService = BankService.getInstance();

        //Print bank clients and currencies
        System.out.println(bank);

        List<Client> clients = bank.getClients();

        /*
        Currency euro = new Currency(), usd = new Currency();
        try{
            euro = bankService.findCurrencyByName(bank, "EUR");
        } catch(Exception e) {
            System.out.println(e.toString());
        }

        try{
            usd = bankService.findCurrencyByName(bank, "USD");
        } catch(Exception e) {
            System.out.println(e.toString());
        }
        */

        Currency ron = new Currency();
        try{
            ron = bankService.findCurrencyByName(bank, "RON");
        } catch(Exception e) {
            System.out.println(e.toString());
        }

        // Print accounts
        System.out.println("\nAccounts of " + clients.get(0).toString() + ":");

        clients.get(0).getAccounts().forEach(System.out::println);

        // Close account
        AccountService accountService = AccountService.getInstance();
        System.out.println();
        accountService.closeAccount(clients.get(0));
        System.out.println("\nAccounts of " + clients.get(0).toString() + " after closing an account:");

        clients.get(0).getAccounts().forEach(System.out::println);

        // Print cards
        System.out.println("\nCards of " + clients.get(0).toString() + "'s account " + clients.get(0).getAccounts().get(0) + ":");
        clients.get(0).getAccounts().get(0).getCards().forEach(card -> System.out.println(card));

        // Freeze/unfreeze card
        System.out.println();

        CardService cardService = CardService.getInstance();
        cardService.freezeCard(clients.get(0).getAccounts().get(0).getCards().get(0));
        System.out.println("The card is: ");
        if(clients.get(0).getAccounts().get(0).getCards().get(0).isFrozen())
            System.out.println("Frozen");
        else
            System.out.println("Not Frozen");

        cardService.unfreezeCard(clients.get(0).getAccounts().get(0).getCards().get(0));

        System.out.println("The card is: ");

        if(clients.get(0).getAccounts().get(0).getCards().get(0).isFrozen())
            System.out.println("Frozen");
        else
            System.out.println("Not Frozen");

        //Create vault
        VaultService vaultService = VaultService.getInstance();
        vaultService.createVault(bank, clients.get(0), ron);
        vaultService.addMemberToVault(clients.get(0).getVaults().get(0), clients.get(1));

        System.out.println("\nVaults of " + clients.get(0) + ":");
        clients.get(0).getVaults().forEach(System.out::println);


        System.out.println("\nMembers of vault " + clients.get(0).getVaults().get(0) + ":");
        clients.get(0).getVaults().get(0).getMembers().forEach(System.out::println);

        //Make transfer
        TransactionService transactionService = TransactionService.getInstance();
        transactionService.makeTransfer(100, clients.get(0).getAccounts().get(0), clients.get(2),"salut");
        System.out.println("\nTransactions of " + clients.get(0) + "'s account " + clients.get(0).getAccounts().get(0) + ":");
        clients.get(0).getAccounts().get(0).getTransactions().forEach(System.out::println);

        System.out.println("\nTransactions of " + clients.get(2) + "'s account " + clients.get(2).getAccounts().get(0) + ":");
        clients.get(2).getAccounts().get(0).getTransactions().forEach(System.out::println);

        System.out.println("\nThe accounts after the transfer:");

        System.out.println(clients.get(0).getAccounts().get(0));
        System.out.println(clients.get(2).getAccounts().get(0));

        //Make vault deposit
        transactionService.makeVaultDeposit(100, clients.get(0).getAccounts().get(0), clients.get(0).getVaults().get(0));
        System.out.println("\nTransactions of " + clients.get(0) + "'s account " + clients.get(0).getAccounts().get(0) + ":");
        clients.get(0).getAccounts().get(0).getTransactions().forEach(System.out::println);

        //Make buying transaction
        System.out.println();
        transactionService.makeBuyingTransaction(59, clients.get(0).getAccounts().get(0).getCards().get(0), clients.get(1).getAccounts().get(0));
        System.out.println("\nTransactions of " + clients.get(0) + "'s account " + clients.get(0).getAccounts().get(0) + ":");
        clients.get(0).getAccounts().get(0).getTransactions().forEach(System.out::println);

        //Extract statement
        System.out.println();
        StatementService statementService = StatementService.getInstance();
        System.out.println(statementService.extractStatement(clients.get(0).getAccounts().get(0)));
    }
}
