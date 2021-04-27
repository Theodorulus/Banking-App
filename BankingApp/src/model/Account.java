package model;

import java.util.ArrayList;
import java.util.List;

public class Account implements Comparable<Account> {
    private final static String IBAN_PREFIX = "RO";
    private String iban;
    private Client client;
    private Currency currency;
    private double numberOfUnits;
    private List<Card> cards;
    private List<Transaction> transactions;

    public Account(String iban, Client client, Currency currency, double numberOfUnits) {
        this.iban = iban;
        this.client = client;
        this.currency = currency;
        this.numberOfUnits = numberOfUnits;
        cards = new ArrayList<>();
        transactions = new ArrayList<>();
    }

    @Override
    public String toString() {
        return iban + "; " + numberOfUnits + " " + currency;
    }

    public static String getIbanPrefix() {
        return IBAN_PREFIX;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public double getNumberOfUnits() {
        return numberOfUnits;
    }

    public void setNumberOfUnits(double numberOfUnits) {
        this.numberOfUnits = numberOfUnits;
    }

    public List<Card> getCards() {
        return cards;
    }

    /*
    public void setCards(Card[] cards) {
        this.cards = cards;
    }
    */

    public List<Transaction> getTransactions() {
        return transactions;
    }

    /*
    public void setTransactions(Transaction[] transactions) {
        this.transactions = transactions;
    }
     */

    @Override
    public int compareTo(Account account) {
        double balanceThis = this.numberOfUnits/this.currency.getValueDependingOnDollar();
        double balanceAccount = account.numberOfUnits/account.currency.getValueDependingOnDollar();
        if (balanceThis > balanceAccount) {
            return 1;
        } else if (balanceThis < balanceAccount) {
            return -1;
        } else {
            return this.iban.compareTo(account.iban);
        }

    }
}
