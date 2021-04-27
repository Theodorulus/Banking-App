package service;

import java.util.Date;

public class AuditService {
    private static AuditService INSTANCE;

    private AuditService () { }

    public static AuditService getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new AuditService();
        }
        return INSTANCE;
    }

    public void logAction(String actionName) {
        Date date = new Date();
        WritingService writingService = WritingService.getInstance();
        writingService.writeAction(actionName, date);
    }
}
