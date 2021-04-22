package model;


import java.util.Set;
import java.util.TreeSet;

public class Bank {
    protected Set<Client> clients;

    public Bank() {
        clients = new TreeSet<>();
    }
}
