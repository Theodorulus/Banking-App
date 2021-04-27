package model;

import java.util.Date;

public class BuyingTransaction extends Transaction {
    private Card senderCard;
    private Account destinationCompanyAccount;


    public BuyingTransaction(double value, String status, Date time, Card senderCard, Account destinationCompanyAccount) {
        super(value, status, time);
        this.destinationCompanyAccount = destinationCompanyAccount;
        this.senderCard = senderCard;
    }

    public Account getDestinationCompanyAccount() {
        return destinationCompanyAccount;
    }

    public void setDestinationCompany(Account destinationCompanyAccount) {
        this.destinationCompanyAccount = destinationCompanyAccount;
    }

    public Card getSenderCard() {
        return senderCard;
    }

    public void setSenderCard(Card senderCard) {
        this.senderCard = senderCard;
    }

    @Override
    public String toString() {
        return "Customer " + senderCard.getAccount().getClient() +
                " made from account (" + senderCard.getAccount() +
                ") with card (" + senderCard +
                ") a transaction with the value of " + value +
                " to " + destinationCompanyAccount.getClient() +
                "'s account (" + destinationCompanyAccount +
                ") at " + time +
                ". Status of the transaction is: " + status;
    }
}
