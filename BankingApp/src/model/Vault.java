package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Vault {
    private String identificationNumber;
    private List<Client> members;
    private Currency currency;
    private double numberOfUnits;
    private List<Transaction> transactions;

    public Vault(String identificationNumber, Client foundingMember, Currency currency, double numberOfUnits) {
        this.identificationNumber = identificationNumber;
        this.members = new ArrayList<>();
        this.members.add(foundingMember);
        this.currency = currency;
        this.numberOfUnits = numberOfUnits;
        this.transactions = new ArrayList<>();
    }

    @Override
    public String toString() {
        return identificationNumber + " " + members.toString() + " " + numberOfUnits + " " + currency;
    }

    public String getIdentificationNumber() {
        return identificationNumber;
    }

    public void setIdentificationNumber(String identificationNumber) {
        this.identificationNumber = identificationNumber;
    }

    public List<Client> getMembers() {
        return members;
    }

    /*
    public void setMembersAccounts(Account[] membersAccounts) {
        this.membersAccounts = membersAccounts;
    }
     */

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

    public List<Transaction> getTransactions() {
        return transactions;
    }

    /*
    public void setTransactions(Transaction[] transactions) {
        this.transactions = transactions;
    }
     */
}
