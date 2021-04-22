package model;

import java.util.Date;

public class ExpirationDate {
    private int month;
    private int year;

    public ExpirationDate(int month, int year) {
        this.month = month;
        this.year = year;
    }

    @Override
    public String toString() {
        return month + "/" + year;
    }
}
