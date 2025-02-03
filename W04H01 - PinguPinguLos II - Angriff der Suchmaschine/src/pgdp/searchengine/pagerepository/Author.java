package pgdp.searchengine.pagerepository;

import pgdp.searchengine.util.Date;

import static pgdp.searchengine.util.Date.*;

public class Author {
    private String firstName;
    private String lastName;
    private String address;
    private String email;
    private final Date birthday;

    public Author(String firstName, String lastName, String address, String email, Date birthday) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.email = email;
        this.birthday = birthday;
    }

    public boolean equals(Author other) {
        return firstName.equals(other.firstName) && lastName.equals(other.lastName) && birthday.equals(other.birthday);
    }

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }

    public String toPrintText() {
        return "<|==== " + this + " ====|>" + "\nBorn " + birthday + "\nResident in " + address + "\nE-Mail: " + email;
    }

    //Getter
    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }

    public Date getBirthday() {
        return birthday;
    }

    //Setter
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public int getAge(){
        return -1 * today().yearsUntil(birthday) -1;
    }

}
