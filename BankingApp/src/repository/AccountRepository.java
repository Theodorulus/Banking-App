package repository;

import config.DatabaseConnection;
import model.*;
import service.AuditService;
import service.BankService;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class AccountRepository {

    public void createAccount(Account account) {
        String sql = "insert into accounts values (?, ?, ?, ?) ";
        try (PreparedStatement statement = DatabaseConnection.getInstance().prepareStatement(sql)) {//try with resources
            statement.setString(1, account.getIban());
            if(account.getClient() instanceof Individual) {
                statement.setString(2, ((Individual) account.getClient()).getIdentityNumber());
            } else if (account.getClient() instanceof Company) {
                statement.setString(2, ((Company) account.getClient()).getFiscalCode());
            }
            statement.setString(3, account.getCurrency().getCurrencyName());
            statement.setDouble(4, account.getNumberOfUnits());
            statement.executeUpdate();

            AuditService auditService = AuditService.getInstance();
            auditService.logAction("Create account");

            //readAccounts();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public void readAccounts() {
        String sql = "select * from accounts";
        try (PreparedStatement statement = DatabaseConnection.getInstance().prepareStatement(sql)) {//try with resources
            ResultSet result = statement.executeQuery();
            BankService bankService = BankService.getInstance();
            Bank bank = Bank.getInstance();
            bankService.deleteAllAccounts(bank);
            while(result.next()) {
                //i have at least one record in the result set
                String iban = result.getString("iban");
                String clientId = result.getString("clientId");
                String currencyName = result.getString("currencyName");
                double numberOfUnits = result.getDouble("numberOfUnits");
                try {
                    Client client = bankService.findClientById(bank, clientId);
                    Currency currency = bankService.findCurrencyByFullName(bank, currencyName);
                    Account account = new Account(iban, client, currency, numberOfUnits);
                    client.getAccounts().add(account);
                } catch(Exception e) {
                    System.out.println(e.toString());
                }
            }

            AuditService auditService = AuditService.getInstance();
            auditService.logAction("Read accounts");
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public Optional<Account> readAccountByIban(String iban) {
        String sql = "select * from accounts a where a.iban = ?";
        try (PreparedStatement statement = DatabaseConnection.getInstance().prepareStatement(sql)) {//try with resources
            statement.setString(1, iban);
            ResultSet result = statement.executeQuery();
            if(result.next()) {
                //i have at least one record in the result set
                String accountIban = result.getString("iban");
                String clientId = result.getString("clientId");
                String currencyName = result.getString("currencyName");
                double numberOfUnits = result.getDouble("numberOfUnits");
                BankService bankService = BankService.getInstance();
                Bank bank = Bank.getInstance();
                Client client = bankService.findClientById(bank, clientId);
                Currency currency = bankService.findCurrencyByFullName(bank, currencyName);

                AuditService auditService = AuditService.getInstance();
                auditService.logAction("Read account by iban");

                return Optional.of(new Account(accountIban, client, currency, numberOfUnits));
            }
        } catch(SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        AuditService auditService = AuditService.getInstance();
        auditService.logAction("Read account by iban");

        return Optional.empty();
    }

    public void updateAccount(Account newAccount, Account oldAccount) {
        String sql = "update accounts set iban = ?, clientId = ?, currencyName = ?, numberOfUnits = ? where iban = ?";
        try (PreparedStatement statement = DatabaseConnection.getInstance().prepareStatement(sql)) {//try with resources
            statement.setString(1, newAccount.getIban());
            if(newAccount.getClient() instanceof Individual) {
                statement.setString(2, ((Individual) newAccount.getClient()).getIdentityNumber());
            } else if (newAccount.getClient() instanceof Company) {
                statement.setString(2, ((Company) newAccount.getClient()).getFiscalCode());
            }
            statement.setString(3, newAccount.getCurrency().getCurrencyName());
            statement.setDouble(4, newAccount.getNumberOfUnits());
            statement.setString(5, oldAccount.getIban());
            statement.executeUpdate();

            AuditService auditService = AuditService.getInstance();
            auditService.logAction("Update account");

            readAccounts();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteAccount(String iban) {
        String sql = "delete from accounts where iban = ?";
        try (PreparedStatement statement = DatabaseConnection.getInstance().prepareStatement(sql)) {//try with resources
            statement.setString(1, iban);
            statement.executeUpdate();

            AuditService auditService = AuditService.getInstance();
            auditService.logAction("Delete account");

            readAccounts();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

}
