package service;

import model.Account;
import model.Client;
import model.Vault;

public class ClientService {
    public void addClient(Client[] clients, Client client) {
        boolean ok = false;
        for(int i = 0; i < clients.length && !ok; i++) {
            if(clients[i] == null) {
                clients[i] = client;
                ok = true;
            }
        }
        if(!ok) {
            System.out.println("Can't add another client. Array is already full.");
        }
    }

    public void addVault(Client client, Vault vault) {
        client.getVaults().add(vault);
    }

    public void viewClients(Client[] clients) {
        System.out.println("Clients in the app:");
        for(int i = 0; i < clients.length; i++) {
            if(clients[i] != null) {
                System.out.println(i + ": " + clients[i]);
            }
        }
    }

    /*public int getLastAvailableIndexAccounts(Client client) {
        for(int i = 0; i < client.getAccounts().length; i++) {
            if (client.getAccounts()[i] == null) {
                return i;
            }
        }
        return -1;
    }*/

    /*public int getLastAvailableIndexVaults(Client client) {
        for(int i = 0; i < client.getVaults().length; i++) {
            if (client.getVaults()[i] == null) {
                return i;
            }
        }
        return -1;
    }*/
}
