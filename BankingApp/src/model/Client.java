package model;

import java.util.*;

public abstract class Client implements Comparable<Client> {
    protected String email;
    protected String phoneNumber;
    protected List<Account> accounts;
    protected List<Vault> vaults;

    public Client() {
        accounts = new ArrayList<>();
        vaults = new ArrayList<>();
    }

    public Client(String email, String phoneNumber) {
        this.email = email;
        this.phoneNumber = phoneNumber;
        accounts = new ArrayList<>();
        vaults = new ArrayList<>();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return "";
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public List<Vault> getVaults() {
        return vaults;
    }

    /*
    public void setVaults(List<Vault> vaults) {
        this.vaults = vaults;
    }
    */

    //public void setAccounts(List<Account> accounts) {this.accounts = accounts; }

    public String getName() {
        return "";
    }

    @Override
    public int compareTo(Client client) {
        return this.email.compareTo(client.email);
    }

}
