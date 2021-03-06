package model;

public class Individual extends Client {//implements Comparable <Client> {
    private String identityNumber;
    private String firstName;
    private String lastName;

    public Individual() {

    }

    public Individual(String email, String phoneNumber, String identityNumber, String firstName, String lastName) {
        super(email, phoneNumber);
        this.identityNumber = identityNumber;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    public String getIdentityNumber() {
        return identityNumber;
    }

    public void setIdentityNumber(String identityNumber) {
        this.identityNumber = identityNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String getName(){
        return firstName + " " + lastName;
    }

    /*
    @Override
    public int compareTo(Client client) {
        Individual individual = (Individual) client;
        String nameThis = this.firstName + " " + this.lastName;
        String nameIndividual = individual.firstName + " " + individual.lastName;
        if (nameThis.equals(nameIndividual)) {
            return nameThis.compareTo(nameIndividual);
        } else {
            return this.identityNumber.compareTo(individual.identityNumber);
        }
    }*/
}
