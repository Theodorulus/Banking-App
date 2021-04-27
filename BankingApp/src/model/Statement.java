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

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = new ArrayList<>(transactions);
    }

    @Override
    public String toString() {
        StringBuilder statement = new StringBuilder("Statement of account " + account +  "\nhaving the following transactions: ");
        transactions.forEach(transaction -> statement.append("\n" + transaction));
        return statement.toString();
    }
}
