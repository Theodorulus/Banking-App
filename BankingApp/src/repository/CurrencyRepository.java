package repository;

import config.DatabaseConnection;
import model.Bank;
import model.Currency;
import service.AuditService;
import service.BankService;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

public class CurrencyRepository {

    public void createCurrency(Currency currency) {
        String sql = "insert into currencies values (?, ?, ?) ";
        try (PreparedStatement statement = DatabaseConnection.getInstance().prepareStatement(sql)) {//try with resources
            statement.setString(1, currency.getCurrencyName());
            statement.setString(2, currency.getAbbreviation());
            statement.setDouble(3, currency.getValueDependingOnDollar());
            statement.executeUpdate();

            AuditService auditService = AuditService.getInstance();
            auditService.logAction("Create currency");

            readCurrencies();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public void readCurrencies() {
        String sql = "select * from currencies";
        try (PreparedStatement statement = DatabaseConnection.getInstance().prepareStatement(sql)) {//try with resources
            ResultSet result = statement.executeQuery();
            BankService bankService = BankService.getInstance();
            Bank bank = Bank.getInstance();
            bankService.deleteAllBankCurrencies(bank);
            while(result.next()) {
                //i have at least one record in the result set
                String currencyName = result.getString("currencyName");
                String abbreviation = result.getString("abbreviation");
                double valueDependingOnDollar = result.getDouble("valueDependingOnDollar");
                Currency currency = new Currency(currencyName, abbreviation, valueDependingOnDollar);
                bankService.addCurrency(bank, currency);

                AuditService auditService = AuditService.getInstance();
                auditService.logAction("Read currencies");
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public Optional<Currency> readCurrencyByName(String name) {
        String sql = "select * from currencies c where c.currencyName = ?";
        try (PreparedStatement statement = DatabaseConnection.getInstance().prepareStatement(sql)) {//try with resources
            statement.setString(1, name);
            ResultSet result = statement.executeQuery();
            if(result.next()) {
                //i have at least one record in the result set
                String currencyName = result.getString("currencyName");
                String abbreviation = result.getString("abbreviation");
                double valueDependingOnDollar = result.getDouble("valueDependingOnDollar");

                AuditService auditService = AuditService.getInstance();
                auditService.logAction("Read currency by name");

                return Optional.of(new Currency(currencyName, abbreviation, valueDependingOnDollar));
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }

        AuditService auditService = AuditService.getInstance();
        auditService.logAction("Read currency by name");

        return Optional.empty();
    }

    public void updateCurrency(Currency newCurrency, Currency oldCurrency) {
        String sql = "update currencies set currencyName = ?, abbreviation = ?, valueDependingOnDollar = ? where currencyName = ?";
        try (PreparedStatement statement = DatabaseConnection.getInstance().prepareStatement(sql)) {//try with resources
            statement.setString(1, newCurrency.getCurrencyName());
            statement.setString(2, newCurrency.getAbbreviation());
            statement.setDouble(3, newCurrency.getValueDependingOnDollar());
            statement.setString(4, oldCurrency.getCurrencyName());
            statement.executeUpdate();

            AuditService auditService = AuditService.getInstance();
            auditService.logAction("Update currency");

            readCurrencies();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteCurrency(String currencyName) {
        String sql = "delete from currencies where currencyName = ?";
        try (PreparedStatement statement = DatabaseConnection.getInstance().prepareStatement(sql)) {//try with resources
            statement.setString(1, currencyName);
            statement.executeUpdate();

            AuditService auditService = AuditService.getInstance();
            auditService.logAction("Delete currency");

            readCurrencies();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }
}
