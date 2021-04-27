package service;

import model.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class ReadingService {
    private static final String COMMON_PATH = "BankingApp/resources/inputs/";
    private static ReadingService INSTANCE;

    private ReadingService () { }

    public static ReadingService getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new ReadingService();
        }
        return INSTANCE;
    }

    public void readClients() {
        try {
            BufferedReader reader = Files.newBufferedReader(Paths.get(COMMON_PATH + "clients.csv"));
            String line = "";
            while((line = reader.readLine()) != null) {
                String[] attributes = line.split(",");
                String type = attributes[0];
                String email = attributes[1];
                String phoneNumber = attributes[2];
                Client client;
                Bank bank = Bank.getInstance();
                BankService bankService = BankService.getInstance();
                if (type.equals("individual")) {
                    String cnp = attributes[3];
                    String firstName = attributes[4];
                    String lastName = attributes[5];
                    client = new Individual(email, phoneNumber, cnp, firstName, lastName);
                    bankService.addClient(bank, client);
                } else if (type.equals("company")) {
                    String fiscalCode = attributes[3];
                    String companyName = attributes[4];
                    client = new Company(email, phoneNumber, fiscalCode, companyName);
                    bankService.addClient(bank, client);
                }
            }
        } catch(NoSuchFileException e) {
            System.out.println("The file with the name '" + "clients.csv" + "' doesn't exist.");
        } catch (IOException e) {
            System.out.println(e.getClass() + " " + e.getMessage());
        }
    }

    public void readCurrencies() {
        try {
            BufferedReader reader = Files.newBufferedReader(Paths.get(COMMON_PATH + "currencies.csv"));
            String line = "";
            while((line = reader.readLine()) != null) {
                Bank bank = Bank.getInstance();
                String[] attributes = line.split(",");
                String currencyName = attributes[0];
                String abbreviation = attributes[1];
                double valueDependingOnDollar = Double.valueOf(attributes[2]);
                Currency currency = new Currency(currencyName, abbreviation, valueDependingOnDollar);
                BankService bankService = BankService.getInstance();
                bankService.addCurrency(bank, currency);
            }
        } catch(NoSuchFileException e) {
            System.out.println("The file with the name '" + "currencies.csv" + "' doesn't exist.");
        } catch (IOException e) {
            System.out.println(e.getClass() + " " + e.getMessage());
        }
    }

    public void readAccounts() {
        Bank bank = Bank.getInstance();
        try {
            BufferedReader reader = Files.newBufferedReader(Paths.get(COMMON_PATH + "accounts.csv"));
            String line = "";
            while((line = reader.readLine()) != null) {
                String[] attributes = line.split(",");
                String email = attributes[0];
                String currencyName = attributes[1];
                double firstDeposit = Double.valueOf(attributes[2]);
                BankService bankService = BankService.getInstance();
                try {
                    Client client = bankService.findClientByEmail(bank, email);
                    Currency currency = bankService.findCurrencyByName(bank, currencyName);
                    AccountService accountService = AccountService.getInstance();
                    accountService.createAccount(bank, client, currency, firstDeposit);
                } catch (Exception e) {
                    System.out.println(e.toString());
                }

            }
        } catch(NoSuchFileException e) {
            System.out.println("The file with the name '" + "accounts.csv" + "' doesn't exist.");
        } catch (IOException e) {
            System.out.println(e.getClass() + " " + e.getMessage());
        }
    }

    public void readCards() {
        Bank bank = Bank.getInstance();
        try {
            BufferedReader reader = Files.newBufferedReader(Paths.get(COMMON_PATH + "cards.csv"));
            String line = "";
            while((line = reader.readLine()) != null) {
                String[] attributes = line.split(",");
                String email = attributes[0];
                String currencyName = attributes[1];
                String type = attributes[2];
                BankService bankService = BankService.getInstance();
                try {
                    Client client = bankService.findClientByEmail(bank, email);
                    ClientService clientService = ClientService.getInstance();
                    Account account = clientService.findAccountByCurrencyName(client, currencyName);

                    CardService cardService = CardService.getInstance();
                    cardService.createCard(account, type);
                } catch (Exception e) {
                    System.out.println(e.toString());
                }

            }
        } catch(NoSuchFileException e) {
            System.out.println("The file with the name '" + "cards.csv" + "' doesn't exist.");
        } catch (IOException e) {
            System.out.println(e.getClass() + " " + e.getMessage());
        }
    }


}
