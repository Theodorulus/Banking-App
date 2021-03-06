package service;

import model.*;

import java.time.LocalDate;

import java.util.Random;
import java.util.Scanner;

public class CardService {
    private static CardService INSTANCE;

    private CardService () { }

    public static CardService getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new CardService();
        }
        return INSTANCE;
    }

    public void createCard (Account account, String type) {
        Client client = account.getClient();
        String cardNumber = "";
        Random rand = new Random();
        for(int i = 0; i < 16; i++) {
            cardNumber = cardNumber + rand.nextInt(10);
        }
        LocalDate currentdate = LocalDate.now();
        String[] date = currentdate.toString().split("-");
        String holderName = client.getName();
        String cvv = "";
        for(int i = 0; i < 3; i++) {
            cvv = cvv + rand.nextInt(10);
        }
        Card card;
        if(type.equals("credit")) {
            ExpirationDate expirationDate = new ExpirationDate(Integer.parseInt(date[0]) + 3, Integer.parseInt(date[1]));
            double limit = 5000;
            card = new CreditCard(cardNumber, expirationDate, holderName, cvv, account, false, limit);
        } else {
            ExpirationDate expirationDate = new ExpirationDate(Integer.parseInt(date[0]) + 2, Integer.parseInt(date[1]));
            card = new DebitCard(cardNumber, expirationDate, holderName, cvv, account, false);
        }
        account.getCards().add(card);

        AuditService auditService = AuditService.getInstance();
        auditService.logAction("Create Card");
    }

    public void freezeCard(Card card) {
        if(!card.isFrozen()) {
            System.out.println("Are you sure you want to freeze " + card.toString() + "? (yes/no)");
            Scanner scanner = new Scanner(System.in);
            String answear = scanner.nextLine();
            if(answear.equals("yes")) {
                card.setFrozen(true);
                System.out.println("Card successfully frozen.");

                AuditService auditService = AuditService.getInstance();
                auditService.logAction("Freeze Card");
            } else if(answear.compareTo("no") == 0) {
                System.out.println("OK! Card will not be frozen.");
            } else {
                System.out.println("Wrong answear! Card will not be frozen.");
            }
        } else {
            System.out.println("Card already frozen.");
        }

    }

    public void unfreezeCard(Card card) {
        if(card.isFrozen()) {
            card.setFrozen(false);
            System.out.println("Card successfully unfrozen.");

            AuditService auditService = AuditService.getInstance();
            auditService.logAction("Unfreeze Card");
        } else {
            System.out.println("Card is not frozen.");
        }

    }
}
