package service;

import model.*;

import java.util.Date;

public class TransactionService {
    private static TransactionService INSTANCE;

    private TransactionService () { }

    public static TransactionService getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new TransactionService();
        }
        return INSTANCE;
    }

    public void makeTransfer(double value, Account senderAccount, Client destinationClient, String message) {
        Date date = new Date();
        if(senderAccount.getNumberOfUnits() >= value) {
            if (destinationClient.getAccounts().get(0) != null) {
                Account destinationAccount = destinationClient.getAccounts().get(0);
                Currency destinationCurrency = destinationAccount.getCurrency();
                Currency senderCurrency = senderAccount.getCurrency();
                //Client sender = senderAccount.getClient();
                double valueInNewCurrency = value * senderCurrency.getValueDependingOnDollar() / destinationCurrency.getValueDependingOnDollar();
                destinationAccount.setNumberOfUnits(destinationAccount.getNumberOfUnits() + valueInNewCurrency);
                senderAccount.setNumberOfUnits(senderAccount.getNumberOfUnits() - value);
                Transaction transfer = new Transfer(value, "successful", date, senderAccount, destinationClient, message);
                AccountService accountService = AccountService.getInstance();
                accountService.addTransaction(senderAccount, transfer);
                accountService.addTransaction(destinationAccount, transfer);
            } else {
                System.out.println("You cannot transfer money to this individual because they don't have an account.");
                Transaction transfer = new Transfer(value, "failed", date, senderAccount, destinationClient, message);
                AccountService accountService = AccountService.getInstance();
                accountService.addTransaction(senderAccount, transfer);
            }
        }
        else{
            System.out.println("Insufficient funds.");
            Transaction transfer = new Transfer(value, "failed", date, senderAccount, destinationClient, message);
            AccountService accountService = AccountService.getInstance();
            accountService.addTransaction(senderAccount, transfer);
        }
        AuditService auditService = AuditService.getInstance();
        auditService.logAction("Make Transfer");
    }

    public void makeVaultDeposit(double value, Account senderAccount, Vault destinationVault) {
        boolean ok = false;
        Client sender = senderAccount.getClient();
        for(Client x: destinationVault.getMembers()) {
            if (x != null && x.equals(sender)){
                ok = true;
            }
        }
        Date date = new Date();
        if(ok && senderAccount.getNumberOfUnits() >= value) {
            Currency destinationCurrency = destinationVault.getCurrency();
            Currency senderCurrency = senderAccount.getCurrency();
            double valueInNewCurrency = value * senderCurrency.getValueDependingOnDollar() / destinationCurrency.getValueDependingOnDollar();
            destinationVault.setNumberOfUnits(destinationVault.getNumberOfUnits() + valueInNewCurrency);
            senderAccount.setNumberOfUnits(senderAccount.getNumberOfUnits() - value);
            Transaction vaultDeposit = new VaultDeposit(value, "successful", date, senderAccount, destinationVault);
            AccountService accountService = AccountService.getInstance();
            VaultService vaultService = VaultService.getInstance();
            accountService.addTransaction(senderAccount, vaultDeposit);
            vaultService.addTransaction(destinationVault, vaultDeposit);
        } else {
            System.out.println("Transaction failed");
            Transaction vaultDeposit = new VaultDeposit(value, "failed", date, senderAccount, destinationVault);
            AccountService accountService = AccountService.getInstance();
            VaultService vaultService = VaultService.getInstance();
            accountService.addTransaction(senderAccount, vaultDeposit);
            vaultService.addTransaction(destinationVault, vaultDeposit);
        }
        AuditService auditService = AuditService.getInstance();
        auditService.logAction("Make Vault Deposit");
    }

    public void makeBuyingTransaction(double value, Card senderCard, Account destinationAccount) {
        Account senderAccount = senderCard.getAccount();
        Date date = new Date();
        String status = "";
        if (!senderCard.isFrozen()){
            if(senderAccount.getNumberOfUnits() >= value) {
                Currency destinationCurrency = destinationAccount.getCurrency();
                Currency senderCurrency = senderAccount.getCurrency();
                double valueInNewCurrency = value * senderCurrency.getValueDependingOnDollar() / destinationCurrency.getValueDependingOnDollar();
                destinationAccount.setNumberOfUnits(destinationAccount.getNumberOfUnits() + valueInNewCurrency);
                senderAccount.setNumberOfUnits(senderAccount.getNumberOfUnits() - value);
                status = "successful";
            } else if (senderCard instanceof CreditCard) {
                if (((CreditCard) senderCard).getLimit() + senderAccount.getNumberOfUnits() >= value) {
                    Currency destinationCurrency = destinationAccount.getCurrency();
                    Currency senderCurrency = senderAccount.getCurrency();
                    double valueInNewCurrency = value * senderCurrency.getValueDependingOnDollar() / destinationCurrency.getValueDependingOnDollar();
                    destinationAccount.setNumberOfUnits(destinationAccount.getNumberOfUnits() + valueInNewCurrency);
                    senderAccount.setNumberOfUnits(senderAccount.getNumberOfUnits() - value);
                    status = "successful";
                }
            } else {
                System.out.println("Insufficient funds.");
                status = "failed";
            }
        } else {
            System.out.println("Card is frozen. No transactions accepted!");
            status = "failed";

        }
        Transaction buyingTransaction = new BuyingTransaction(value, status, date, senderCard, destinationAccount);
        AccountService accountService = AccountService.getInstance();
        accountService.addTransaction(senderAccount, buyingTransaction);
        accountService.addTransaction(destinationAccount, buyingTransaction);

        AuditService auditService = AuditService.getInstance();
        auditService.logAction("Make Buying Transaction");
    }
}
