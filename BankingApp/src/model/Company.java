package model;

public class Company extends Client {//implements Comparable <Client> {
    private String fiscalCode;
    private String companyName;

    public Company() {
    }

    public Company(String email, String phoneNumber, String fiscalCode, String companyName) {
        super(email, phoneNumber);
        this.fiscalCode = fiscalCode;
        this.companyName = companyName;
    }

    @Override
    public String toString() {
        return companyName ;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public String getName(){
        return companyName;
    }

    public String getFiscalCode() {
        return fiscalCode;
    }

    public void setFiscalCode(String fiscalCode) {
        this.fiscalCode = fiscalCode;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /*
    @Override
    public int compareTo(Client client) {
        Company company = (Company) client;
        if (!this.companyName.equals(company.companyName)) {
            return this.companyName.compareTo(company.companyName);
        } else {
            return this.fiscalCode.compareTo(company.fiscalCode);
        }
    }*/
}
