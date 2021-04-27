package model;

public class Currency implements Comparable<Currency> {
    private String currencyName;
    private String abbreviation;
    private double valueDependingOnDollar;

    public Currency() {
        this.valueDependingOnDollar = 1.0;
        this.currencyName = "United States Dollar";
        this.abbreviation = "USD";
    }

    public Currency(String currencyName, String abbreviation, double valueDependingOnDollar) {
        this.valueDependingOnDollar = valueDependingOnDollar;
        this.currencyName = currencyName;
        this.abbreviation = abbreviation;
    }

    @Override
    public String toString() {
        return abbreviation;
    }

    public String getAbbreviation() { return abbreviation; }

    public void setAbbreviation(String abbreviation) { this.abbreviation = abbreviation; }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public double getValueDependingOnDollar() {
        return valueDependingOnDollar;
    }

    public void setValueDependingOnDollar(double valueDependingOnDollar) {
        this.valueDependingOnDollar = valueDependingOnDollar;
    }

    @Override
    public int compareTo(Currency currency) {
        if (this.valueDependingOnDollar > currency.valueDependingOnDollar) {
            return 1;
        } else if(this.valueDependingOnDollar < currency.valueDependingOnDollar) {
            return -1;
        } else {
            return this.currencyName.compareTo(currency.currencyName);
        }
    }
}
