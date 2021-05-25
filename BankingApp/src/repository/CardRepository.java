package repository;

import config.DatabaseConnection;
import model.*;
import service.AccountService;
import service.AuditService;
import service.BankService;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.Optional;

public class CardRepository {
    public void createCard(Card card) {
        if(card instanceof CreditCard) {
            String sql = "insert into creditCards values (?, ?, ?, ?, ?, ?, ?) ";
            try (PreparedStatement statement = DatabaseConnection.getInstance().prepareStatement(sql)) {//try with resources
                statement.setString(1, card.getCardNumber());
                Date date = new Date(card.getExpirationDate().getYear(), card.getExpirationDate().getMonth(), 1);
                statement.setDate(2, date);
                statement.setString(3, card.getHolderName());
                statement.setString(4, card.getCvv());
                statement.setString(5, card.getAccount().getIban());
                statement.setBoolean(6, card.isFrozen());
                statement.setDouble(7, ((CreditCard) card).getLimit());
                statement.executeUpdate();

                AuditService auditService = AuditService.getInstance();
                auditService.logAction("Create Card (Credit Card)");

                //readCards();
            } catch(SQLException e) {
                e.printStackTrace();
            }
        } else if(card instanceof DebitCard) {
            String sql = "insert into debitCards values (?, ?, ?, ?, ?, ?) ";
            try (PreparedStatement statement = DatabaseConnection.getInstance().prepareStatement(sql)) {//try with resources
                statement.setString(1, card.getCardNumber());
                Date date = new Date(card.getExpirationDate().getYear(), card.getExpirationDate().getMonth(), 1);
                statement.setDate(2, date);
                statement.setString(3, card.getHolderName());
                statement.setString(4, card.getCvv());
                statement.setString(5, card.getAccount().getIban());
                statement.setBoolean(6, card.isFrozen());
                statement.executeUpdate();

                AuditService auditService = AuditService.getInstance();
                auditService.logAction("Create Card (Debit Card)");

                //readCards();
            } catch(SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void readCards() {
        String sql1 = "select * from creditCards";
        String sql2 = "select * from debitCards";
        try (PreparedStatement statement = DatabaseConnection.getInstance().prepareStatement(sql1)) {//try with resources
            ResultSet result = statement.executeQuery();
            BankService bankService = BankService.getInstance();
            Bank bank = Bank.getInstance();

            bankService.deleteAllCards(bank);

            while(result.next()) {
                //i have at least one record in the result set
                String cardNumber = result.getString("cardNumber");
                Date date = result.getDate("expirationDate");
                ExpirationDate expirationDate = new ExpirationDate(date.getMonth(), date.getYear());
                String holderName = result.getString("holderName");
                String cvv = result.getString("cvv");
                String accountIban = result.getString("accountIban");
                try {
                    Account account = bankService.findAccountByIban(bank, accountIban);
                    boolean frozen = result.getBoolean("frozen");
                    double limit = result.getDouble("cardLimit");


                    Card card = new CreditCard(cardNumber, expirationDate, holderName, cvv, account, frozen, limit);
                    account.getCards().add(card);
                } catch (Exception e) {
                    System.out.println(e.toString());
                }

                AuditService auditService = AuditService.getInstance();
                auditService.logAction("Read Cards (Credit Cards)");
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }

        try (PreparedStatement statement = DatabaseConnection.getInstance().prepareStatement(sql2)) {//try with resources
            ResultSet result = statement.executeQuery();
            BankService bankService = BankService.getInstance();
            Bank bank = Bank.getInstance();

            while(result.next()) {
                //i have at least one record in the result set
                String cardNumber = result.getString("cardNumber");
                Date date = result.getDate("expirationDate");
                ExpirationDate expirationDate = new ExpirationDate(date.getMonth(), date.getYear());
                String holderName = result.getString("holderName");
                String cvv = result.getString("cvv");
                String accountIban = result.getString("accountIban");
                try {
                    Account account = bankService.findAccountByIban(bank, accountIban);
                    boolean frozen = result.getBoolean("frozen");

                    Card card = new DebitCard(cardNumber, expirationDate, holderName, cvv, account, frozen);
                    account.getCards().add(card);
                } catch (Exception e) {
                    System.out.println(e.toString());
                }

                AuditService auditService = AuditService.getInstance();
                auditService.logAction("Read Cards (Debit Cards)");
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public Optional<Card> readCardByCardNumber(String number) {
        String sql1 = "select * from creditCards cc where cc.cardNumber = ?";

        try (PreparedStatement statement = DatabaseConnection.getInstance().prepareStatement(sql1)) {//try with resources
            statement.setString(1, number);
            ResultSet result = statement.executeQuery();
            if(result.next()) {
                //i have at least one record in the result set
                String cardNumber = result.getString("cardNumber");
                Date date = result.getDate("expirationDate");
                ExpirationDate expirationDate = new ExpirationDate(date.getMonth(), date.getYear());
                String holderName = result.getString("holderName");
                String cvv = result.getString("cvv");
                String accountIban = result.getString("accountIban");
                BankService bankService = BankService.getInstance();
                Bank bank = Bank.getInstance();
                Account account = bankService.findAccountByIban(bank, accountIban);
                boolean frozen = result.getBoolean("frozen");
                double limit = result.getDouble("cardLimit");

                AuditService auditService = AuditService.getInstance();
                auditService.logAction("Read card by card number");

                return Optional.of(new CreditCard(cardNumber, expirationDate, holderName, cvv, account, frozen, limit));
            }
        } catch(SQLException e) {
            e.printStackTrace();
        } catch(Exception e) {
            System.out.println(e.toString());
        }

        String sql2 = "select * from debitCards dc where dc.cardNumber = ?";

        try (PreparedStatement statement = DatabaseConnection.getInstance().prepareStatement(sql2)) {//try with resources
            statement.setString(1, number);
            ResultSet result = statement.executeQuery();
            if(result.next()) {
                //i have at least one record in the result set
                String cardNumber = result.getString("cardNumber");
                Date date = result.getDate("expirationDate");
                ExpirationDate expirationDate = new ExpirationDate(date.getMonth(), date.getYear());
                String holderName = result.getString("holderName");
                String cvv = result.getString("cvv");
                String accountIban = result.getString("accountIban");
                BankService bankService = BankService.getInstance();
                Bank bank = Bank.getInstance();
                Account account = bankService.findAccountByIban(bank, accountIban);
                boolean frozen = result.getBoolean("frozen");

                AuditService auditService = AuditService.getInstance();
                auditService.logAction("Read card by card number");

                return Optional.of(new DebitCard(cardNumber, expirationDate, holderName, cvv, account, frozen));
            }
        } catch(SQLException e) {
            e.printStackTrace();
        } catch(Exception e) {
            System.out.println(e.toString());
        }

        AuditService auditService = AuditService.getInstance();
        auditService.logAction("Read client by card number");

        return Optional.empty();
    }

    public void updateCard(Card newCard, Card oldCard) {
        if(oldCard instanceof CreditCard && newCard instanceof CreditCard) {
            String sql = "update creditCards set cardNumber = ?, expirationDate = ?, holderName = ?, cvv = ?, accountIban = ?, frozen = ?, cardLimit = ? where cardNumber = ?";
            try (PreparedStatement statement = DatabaseConnection.getInstance().prepareStatement(sql)) {//try with resources
                statement.setString(1, newCard.getCardNumber());
                Date date = new Date(newCard.getExpirationDate().getYear(), newCard.getExpirationDate().getMonth(), 1);
                statement.setDate(2, date);
                statement.setString(3, newCard.getHolderName());
                statement.setString(4, newCard.getCvv());
                statement.setString(5, newCard.getAccount().getIban());
                statement.setBoolean(6, newCard.isFrozen());
                statement.setDouble(7, ((CreditCard) newCard).getLimit());
                statement.setString(8, oldCard.getCardNumber());
                statement.executeUpdate();

                AuditService auditService = AuditService.getInstance();
                auditService.logAction("Update card");

                readCards();
            } catch(SQLException e) {
                e.printStackTrace();
            }
        } else if(oldCard instanceof DebitCard && newCard instanceof DebitCard) {
            String sql = "update debitCards set cardNumber = ?, expirationDate = ?, holderName = ?, cvv = ?, accountIban = ?, frozen = ? where cardNumber = ?";
            try (PreparedStatement statement = DatabaseConnection.getInstance().prepareStatement(sql)) {//try with resources
                statement.setString(1, newCard.getCardNumber());
                Date date = new Date(newCard.getExpirationDate().getYear(), newCard.getExpirationDate().getMonth(), 1);
                statement.setDate(2, date);
                statement.setString(3, newCard.getHolderName());
                statement.setString(4, newCard.getCvv());
                statement.setString(5, newCard.getAccount().getIban());
                statement.setBoolean(6, newCard.isFrozen());
                statement.setString(7, oldCard.getCardNumber());
                statement.executeUpdate();

                AuditService auditService = AuditService.getInstance();
                auditService.logAction("Update card");

                readCards();
            } catch(SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("I'm sorry! You cannot modify the type of a card.");
        }
    }

    public void deleteCard(String cardNumber) {
        String sql1 = "delete from creditCards where cardNumber = ?";
        String sql2 = "delete from debitCards where cardNumber = ?";
        try (PreparedStatement statement = DatabaseConnection.getInstance().prepareStatement(sql1)) {//try with resources
            statement.setString(1, cardNumber);
            statement.executeUpdate();

            readCards();
        } catch(SQLException e) {
            e.printStackTrace();
        }

        try (PreparedStatement statement = DatabaseConnection.getInstance().prepareStatement(sql2)) {//try with resources
            statement.setString(1, cardNumber);
            statement.executeUpdate();

            readCards();
        } catch(SQLException e) {
            e.printStackTrace();
        }

        AuditService auditService = AuditService.getInstance();
        auditService.logAction("Delete card");
    }
}
