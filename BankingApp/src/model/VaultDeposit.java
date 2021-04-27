package model;

import java.util.Date;

public class VaultDeposit extends Transaction {
    private Account senderAccount;
    private Vault destionationVault;

    public VaultDeposit( double value, String status, Date time, Account senderAccount, Vault destionationVault) {
        super(value, status, time);
        this.destionationVault = destionationVault;
        this.senderAccount = senderAccount;
    }

    @Override
    public String toString() {
        return "Client " + senderAccount.getClient() +
                " deposited " + value +
                " " + senderAccount.getCurrency() +
                " from account( " + senderAccount +
                ") in vault (" + destionationVault +
                ") at " + time +
                ". Status of the deposit: " + status;
    }

    public Vault getDestionationVault() {
        return destionationVault;
    }

    public void setDestionationVault(Vault destionationVault) {
        this.destionationVault = destionationVault;
    }

    public Account getSenderAccount() {
        return senderAccount;
    }

    public void setSenderAccount(Account senderAccount) {
        this.senderAccount = senderAccount;
    }
}
