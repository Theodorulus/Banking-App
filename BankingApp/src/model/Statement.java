package model;

import java.util.ArrayList;
import java.util.List;

public class Statement {
    private Account account;
    private List<Transaction> transactions;

    public Statement(Account account) {
        this.account = account;
        this.transactions = new ArrayList<>();
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
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
