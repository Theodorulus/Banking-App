package model;

import java.util.Date;

public class Transfer extends Transaction {
    private Account senderAccount;
    private Client destionationClient;
    private String message;

    public Transfer(double value, String status, Date time, Account senderAccount, Client destionationClient, String message) {
        super(value, status, time);
        this.destionationClient = destionationClient;
        this.senderAccount = senderAccount;
        this.message = message;
    }

    @Override
    public String toString() {
        return "Client " + senderAccount.getClient() +
                " sent " + value +
                " " + senderAccount.getCurrency() +
                " from account (" + senderAccount +
                ") to " + destionationClient +
                " with the message: '" + message +
                "' at " + time +
                ". Status of the tranfer: " + status;
    }

    public Client getDestionationClient() {
        return destionationClient;
    }

    public void setDestionationClient(Client destionationClient) {
        this.destionationClient = destionationClient;
    }

    public Account getSenderAccount() {
        return senderAccount;
    }

    public void setSenderAccount(Account senderAccount) {
        this.senderAccount = senderAccount;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}
