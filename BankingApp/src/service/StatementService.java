package service;

import model.Account;
import model.Client;
import model.Statement;
import model.Transaction;

import java.util.stream.Collectors;

public class StatementService {
    private static StatementService INSTANCE;

    private StatementService () { }

    public static StatementService getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new StatementService();
        }
        return INSTANCE;
    }
    public Statement extractStatement(Account account) {
        Statement statement = new Statement(account);
        statement.setTransactions(account.getTransactions().stream()
                .filter(account1 -> account1.getStatus().equals("successful"))
                .collect(Collectors.toList()));

        AuditService auditService = AuditService.getInstance();
        auditService.logAction("Extract Statement");

        return statement;
    }

}
