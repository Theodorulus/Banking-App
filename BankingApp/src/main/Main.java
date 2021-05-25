package main;

import model.*;
import model.Currency;
import repository.AccountRepository;
import repository.CardRepository;
import repository.ClientRepository;
import repository.CurrencyRepository;
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
        //readData();
        //app();

        //Before running the test, run the dbscript.sql from BankingApp/resources/DBScripts
        dbTest();


    }

    public static void dbTest() {
        Bank bank = Bank.getInstance();
        BankService bankService = BankService.getInstance();


        //CURRENCY
        CurrencyRepository currencyRepository = new CurrencyRepository();

        //CREATE CURRENCY (UNCOMMENT TO TEST)
        //Currency chf = new Currency("Swiss Franc", "CHF", 1.1769715);
        //currencyRepository.createCurrency(chf);

        //READ CURRENCIES
        currencyRepository.readCurrencies();
        System.out.println(bank);

        //READ CURRENCY BY NAME
        System.out.println();
        System.out.println(currencyRepository.readCurrencyByName("Romanian Leu"));
        System.out.println();

        //UPDATE CURRENCY (UNCOMMENT TO TEST)
        /*
        Currency oldCurrency = new Currency();

        try {
            oldCurrency = bankService.findCurrencyByName(bank, "RON");
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        Currency newCurrency = new Currency("ROMANIAN LEU", "RON", 0.249);
        currencyRepository.updateCurrency(newCurrency, oldCurrency);
        */


        //DELETE CURRENCY (UNCOMMENT TO TEST)
        /*
        Currency currency = new Currency();
        try {
            currency = bankService.findCurrencyByName(bank, "CHF");
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        currencyRepository.deleteCurrency(currency.getCurrencyName());
        System.out.println(bank);
        System.out.println();
        */


        //CLIENT
        ClientRepository clientRepository = new ClientRepository();

        //CREATE CLIENT (UNCOMMENT TO TEST)
        /*
        Client individual = new Individual("hans_landa@gmail.com", "0766666666", "1990520995398", "Hans", "Landa");
        Client company = new Company("office@hal-technologies.com", "0788556555", "23902351", "Hal Technologies");
        clientRepository.createClient(individual);
        clientRepository.createClient(company);
        */

        //READ CLIENTS
        clientRepository.readClients();
        System.out.println(bank);

        //READ CLIENT BY EMAIL
        System.out.println();
        System.out.println(clientRepository.readClientByEmail("marcelus@gmail.com"));
        System.out.println(clientRepository.readClientByEmail("office@th_experts.com"));
        System.out.println();

        //UPDATE CLIENT (UNCOMMENT TO TEST)
        /*
        Client hansLanda = bank.getClients().get(0);
        clientRepository.updateClient(new Individual("hans_landa@gmail.com", "0766666666", "1990520995398", "Hans", "Lambada"), hansLanda);
        Client thIndustries = bank.getClients().get(5);
        clientRepository.updateClient(new Company("office@th_industries.com", "0777777766", "22111139", "HT Industries"), thIndustries);
        System.out.println(bank);
        System.out.println();
        */

        //DELETE CLIENT (UNCOMMENT TO TEST)
        /*
        clientRepository.deleteClient("john@gmail.com");
        clientRepository.deleteClient("contabilitate@mirano.com");
        System.out.println(bank);
        System.out.println();
        */

        //ACCOUNT
        AccountRepository accountRepository = new AccountRepository();

        //CREATE ACCOUNT (UNCOMMENT TO TEST)
        Currency usd = new Currency();

        AccountService accountService = AccountService.getInstance();
        accountService.createAccount(bank, bank.getClients().get(2), usd, 1000);
        accountService.createAccount(bank, bank.getClients().get(5), usd, 300);
        accountRepository.createAccount(bank.getClients().get(2).getAccounts().get(0));
        accountRepository.createAccount(bank.getClients().get(5).getAccounts().get(0));


        //READ ACCOUNTS
        accountRepository.readAccounts();
        bank.getClients().forEach(client -> System.out.println(client.getAccounts()));


        //READ ACCOUNT BY IBAN
        System.out.println();
        System.out.println(accountRepository.readAccountByIban("RO26THBN2753908718931USD"));
        System.out.println();

        //UPDATE ACCOUNT (UNCOMMENT TO TEST)
        /*
        Account account = bank.getClients().get(0).getAccounts().get(0);
        accountRepository.updateAccount(new Account("RO40THBN2697604393945USD", bank.getClients().get(0), usd, 10000), account);
        System.out.println(bank.getClients().get(0).getAccounts().get(0));
        */

        //DELETE ACCOUNT(UNCOMMENT TO TEST)
        //accountRepository.deleteAccount("RO81THBN1704883885422USD");
        //System.out.println(bank.getClients().get(4).getAccounts());
        //System.out.println();


        //CARD
        CardRepository cardRepository = new CardRepository();

        //CREATE CARD (UNCOMMENT TO TEST)
        /*
        CardService cardService = CardService.getInstance();
        cardService.createCard(bank.getClients().get(0).getAccounts().get(0), "credit");
        cardService.createCard(bank.getClients().get(0).getAccounts().get(0), "debit");
        cardRepository.createCard(bank.getClients().get(0).getAccounts().get(0).getCards().get(0));
        cardRepository.createCard(bank.getClients().get(0).getAccounts().get(0).getCards().get(1));
        */

        //READ CARDS
        cardRepository.readCards();
        bank.getClients().forEach(client -> client.getAccounts().forEach(account1 -> System.out.println(account1.getCards())));

        //READ CARD BY CARD NUMBER
        System.out.println();
        System.out.println(cardRepository.readCardByCardNumber("0703509990891319"));
        System.out.println(cardRepository.readCardByCardNumber("0989854900108314"));
        System.out.println();

        //UPDARE CARD (UNCOMMENT TO TEST)
        /*
        Card creditCard = bank.getClients().get(0).getAccounts().get(0).getCards().get(0);
        Card debitCard = bank.getClients().get(0).getAccounts().get(0).getCards().get(4);
        System.out.println(creditCard.toString() + "\n" + debitCard);
        cardRepository.updateCard(new CreditCard("0989854900108314", new ExpirationDate(9,2025), "Hans Landa", "489", bank.getClients().get(0).getAccounts().get(0), false, 6000), creditCard);
        cardRepository.updateCard(new DebitCard("0432423555325391", new ExpirationDate(10,2025), "Hans Landa", "424", bank.getClients().get(0).getAccounts().get(0), false), debitCard);
        System.out.println(bank.getClients().get(0).getAccounts().get(0).getCards().get(0));
        System.out.println(bank.getClients().get(0).getAccounts().get(0).getCards().get(4));
        */

        //DELETE CARD (UNCOMMENT TO TEST)
        //cardRepository.deleteCard("1132442342325391");
        //bank.getClients().forEach(client -> client.getAccounts().forEach(account2 -> System.out.println(account2.getCards())));

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
