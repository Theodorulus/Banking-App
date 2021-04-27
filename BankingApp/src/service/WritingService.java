package service;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Date;

public class WritingService {
    private static final String AUDIT_PATH = "BankingApp/resources/audit/audit.csv";
    private static WritingService INSTANCE;

    private WritingService () { }

    public static WritingService getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new WritingService();
        }
        return INSTANCE;
    }

    public void writeAction(String actionName, Date date) {
        BufferedWriter writer = null;
        try {
            writer = Files.newBufferedWriter(Paths.get(AUDIT_PATH), StandardOpenOption.APPEND);
            writer.write(actionName + "," + date + "\n");
            writer.flush();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
