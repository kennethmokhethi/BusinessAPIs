package model.request;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;

@XmlRootElement
public class CreateUserRequestModel {
    private String firstname;
    private String lastname;
    private String email;
    private String password;

    private ArrayList<BondRequest> bondRequestArrayList = new ArrayList<>();

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ArrayList<BondRequest> getBondRequestArrayList() {
        return bondRequestArrayList;
    }

    public void setBondRequestArrayList(ArrayList<BondRequest> bondRequestArrayList) {
        this.bondRequestArrayList = bondRequestArrayList;
    }
}
