package service;

import model.*;

import java.util.Random;

public class VaultService {
    public void createVault(Client client, Currency currency) {
        StringBuilder id = new StringBuilder("");
        Random rand = new Random();
        for(int i = 0; i < 16; i++) {
            id.append(rand.nextInt(10));
        }
        ClientService clientService = new ClientService();
        //int vaultsIndex = clientService.getLastAvailableIndexVaults(client);
        Vault vault = new Vault(id.toString(), client, currency, 0);
        clientService.addVault(client, vault);
    }

    public void addMemberToVault(Vault vault, Client client) {
        //int index = getLastAvailableIndexMembers(vault);
        vault.getMembers().add(client);
    }

    public void addTransaction(Vault vault, Transaction transaction) {
        //int index = getLastAvailableIndexTransactions(vault);
        vault.getTransactions().add(transaction);
    }

    /*public int getLastAvailableIndexMembers(Vault vault) {
        for(int i = 0; i < vault.getMembers().length; i++) {
            if (vault.getMembers()[i] == null) {
                return i;
            }
        }
        return -1;
    }*/

    /*public int getLastAvailableIndexTransactions(Vault vault) {
        for(int i = 0; i < vault.getTransactions().length; i++) {
            if (vault.getTransactions()[i] == null) {
                return i;
            }
        }
        return -1;
    }*/
}
