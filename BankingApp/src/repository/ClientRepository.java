package repository;

import config.DatabaseConnection;
import model.*;
import service.AuditService;
import service.BankService;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class ClientRepository {
    public void createClient(Client client) {
        if(client instanceof Individual) {
            String sql = "insert into individuals values (?, ?, ?, ?, ?) ";
            try (PreparedStatement statement = DatabaseConnection.getInstance().prepareStatement(sql)) {//try with resources
                statement.setString(1, client.getEmail());
                statement.setString(2, client.getPhoneNumber());
                statement.setString(3, ((Individual) client).getIdentityNumber());
                statement.setString(4, ((Individual) client).getFirstName());
                statement.setString(5, ((Individual) client).getLastName());
                statement.executeUpdate();

                AuditService auditService = AuditService.getInstance();
                auditService.logAction("Create client (Individual)");

                //readClients();
            } catch(SQLException e) {
                e.printStackTrace();
            }
        } else if(client instanceof Company) {
            String sql = "insert into companies values (?, ?, ?, ?) ";
            try (PreparedStatement statement = DatabaseConnection.getInstance().prepareStatement(sql)) {//try with resources
                statement.setString(1, client.getEmail());
                statement.setString(2, client.getPhoneNumber());
                statement.setString(3, ((Company) client).getFiscalCode());
                statement.setString(4, ((Company) client).getCompanyName());
                statement.executeUpdate();

                AuditService auditService = AuditService.getInstance();
                auditService.logAction("Create client (Company)");

                //readClients();
            } catch(SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void readClients() {
        String sql1 = "select * from individuals";
        String sql2 = "select * from companies";
        try (PreparedStatement statement = DatabaseConnection.getInstance().prepareStatement(sql1)) {//try with resources
            ResultSet result = statement.executeQuery();
            BankService bankService = BankService.getInstance();
            Bank bank = Bank.getInstance();

            bankService.deleteAllBankClients(bank);

            while(result.next()) {
                //i have at least one record in the result set
                String email = result.getString("email");
                String phoneNumber = result.getString("phoneNumber");
                String identityNumber = result.getString("identityNumber");
                String firstName = result.getString("firstName");
                String lastName = result.getString("lastName");
                Client client = new Individual(email, phoneNumber, identityNumber, firstName, lastName);
                bankService.addClient(bank, client);

                AuditService auditService = AuditService.getInstance();
                auditService.logAction("Read clients (individuals)");
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
                String email = result.getString("email");
                String phoneNumber = result.getString("phoneNumber");
                String fiscalCode = result.getString("fiscalCode");
                String companyName = result.getString("companyName");
                Client client = new Company(email, phoneNumber, fiscalCode, companyName);
                bankService.addClient(bank, client);

                AuditService auditService = AuditService.getInstance();
                auditService.logAction("Read clients (companies)");
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public Optional<Client> readClientByEmail(String email) {
        String sql1 = "select * from individuals i where i.email = ?";

        try (PreparedStatement statement = DatabaseConnection.getInstance().prepareStatement(sql1)) {//try with resources
            statement.setString(1, email);
            ResultSet result = statement.executeQuery();
            if(result.next()) {
                //i have at least one record in the result set
                String clientEmail = result.getString("email");
                String phoneNumber = result.getString("phoneNumber");
                String identityNumber = result.getString("identityNumber");
                String firstName = result.getString("firstName");
                String lastName = result.getString("lastName");

                AuditService auditService = AuditService.getInstance();
                auditService.logAction("Read client by name");

                return Optional.of(new Individual(clientEmail, phoneNumber, identityNumber, firstName, lastName));
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }

        String sql2 = "select * from companies c where c.email = ?";

        try (PreparedStatement statement = DatabaseConnection.getInstance().prepareStatement(sql2)) {//try with resources
            statement.setString(1, email);
            ResultSet result = statement.executeQuery();
            if(result.next()) {
                //i have at least one record in the result set
                String clientEmail = result.getString("email");
                String phoneNumber = result.getString("phoneNumber");
                String fiscalCode = result.getString("fiscalCode");
                String companyName = result.getString("companyName");

                AuditService auditService = AuditService.getInstance();
                auditService.logAction("Read client by name");

                return Optional.of(new Company(clientEmail, phoneNumber, fiscalCode, companyName));
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }

        AuditService auditService = AuditService.getInstance();
        auditService.logAction("Read client by name");

        return Optional.empty();
    }

    public void updateClient(Client newClient, Client oldClient) {
        if(oldClient instanceof Individual && newClient instanceof Individual) {
            String sql = "update individuals set email = ?, phoneNumber = ?, identityNumber = ?, firstName = ?, lastName = ? where identityNumber = ?";
            try (PreparedStatement statement = DatabaseConnection.getInstance().prepareStatement(sql)) {//try with resources
                statement.setString(1, newClient.getEmail());
                statement.setString(2, newClient.getPhoneNumber());
                statement.setString(3, ((Individual) newClient).getIdentityNumber());
                statement.setString(4, ((Individual) newClient).getFirstName());
                statement.setString(5, ((Individual) newClient).getLastName());
                statement.setString(6, ((Individual) oldClient).getIdentityNumber());
                statement.executeUpdate();

                AuditService auditService = AuditService.getInstance();
                auditService.logAction("Update client");

                readClients();
            } catch(SQLException e) {
                e.printStackTrace();
            }
        } else if (oldClient instanceof Company && newClient instanceof Company) {
            String sql = "update companies set email = ?, phoneNumber = ?, fiscalCode = ?, companyName = ? where fiscalCode = ?";
            try (PreparedStatement statement = DatabaseConnection.getInstance().prepareStatement(sql)) {//try with resources
                statement.setString(1, newClient.getEmail());
                statement.setString(2, newClient.getPhoneNumber());
                statement.setString(3, ((Company) newClient).getFiscalCode());
                statement.setString(4, ((Company) newClient).getCompanyName());
                statement.setString(5, ((Company) oldClient).getFiscalCode());
                statement.executeUpdate();

                AuditService auditService = AuditService.getInstance();
                auditService.logAction("Update client");

                readClients();
            } catch(SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("I'm sorry! You cannot modify the type of a client.");
        }
    }

    public void deleteClient(String email) {
        String sql1 = "delete from individuals where email = ?";
        String sql2 = "delete from companies where email = ?";
        try (PreparedStatement statement = DatabaseConnection.getInstance().prepareStatement(sql1)) {//try with resources
            statement.setString(1, email);
            statement.executeUpdate();

            readClients();
        } catch(SQLException e) {
            e.printStackTrace();
        }

        try (PreparedStatement statement = DatabaseConnection.getInstance().prepareStatement(sql2)) {//try with resources
            statement.setString(1, email);
            statement.executeUpdate();

            readClients();
        } catch(SQLException e) {
            e.printStackTrace();
        }

        AuditService auditService = AuditService.getInstance();
        auditService.logAction("Delete client");
    }
}
