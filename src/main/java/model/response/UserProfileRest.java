package model.response;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;

//Response class that will be sent back to the user
@XmlRootElement
public class UserProfileRest {

    private String userId;
    private String firstname;
    private String lastname;
    private String email;
    private String href;

    private ArrayList<BondResponse> bondBondArrayResponseList = new ArrayList<>();

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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getHref() {
        return href;
    }

    public ArrayList<BondResponse> getBondBondArrayResponseList() {
        return bondBondArrayResponseList;
    }

    public void setBondBondArrayResponseList(ArrayList<BondResponse> bondBondArrayResponseList) {
        this.bondBondArrayResponseList = bondBondArrayResponseList;
    }

    public void setHref(String href) {
        this.href = href;
    }
}
