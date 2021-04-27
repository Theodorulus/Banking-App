package service;

import model.*;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class VaultService {
    private static VaultService INSTANCE;

    private VaultService () { }

    public static VaultService getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new VaultService();
        }
        return INSTANCE;
    }

    public void createVault(Bank bank, Client client, Currency currency) {
        boolean ok = true;
        String id;
        do {
            StringBuilder idBuilder = new StringBuilder("");
            Random rand = new Random();
            for (int i = 0; i < 16; i++) {
                idBuilder.append(rand.nextInt(10));
            }
            id = idBuilder.toString();
            List<Client> clients = bank.getClients();
            List<List<Vault>> vaultsListOfLists = clients.stream()
                    .map(client1 -> client1.getVaults())
                    .collect(Collectors.toList());
            for (List<Vault> vaults : vaultsListOfLists)
                for (Vault vault : vaults)
                    if (vault.getIdentificationNumber().equals(id))
                        ok = false;
        } while (!ok);
        ClientService clientService = ClientService.getInstance();
        Vault vault = new Vault(id, client, currency, 0);
        clientService.addVault(client, vault);

        AuditService auditService = AuditService.getInstance();
        auditService.logAction("Create Vault");
    }

    public void addMemberToVault(Vault vault, Client client) {
        if(!vault.getMembers().contains(client)) {
            vault.getMembers().add(client);
            AuditService auditService = AuditService.getInstance();
            auditService.logAction("Add Member to Vault");
        }
        else
            System.out.println("Member has already access to this vault.");
    }

    public void addTransaction(Vault vault, Transaction transaction) {
        vault.getTransactions().add(transaction);
    }

}
