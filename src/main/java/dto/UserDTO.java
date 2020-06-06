package dto;

import entity.Bond;
import model.request.BondRequest;
import model.response.BondResponse;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UserDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private long u_Id;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private String salt;
    private String userId;
    private List<Bond> bondList = new ArrayList<>();
    private ArrayList<BondRequest> bondRequestArrayList = new ArrayList<>();
    private ArrayList<BondResponse> bondBondArrayResponseList = new ArrayList<>();
    private String encrytedPassword;
    private String token;

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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public ArrayList<BondRequest> getBondRequestArrayList() {
        return bondRequestArrayList;
    }

    public void setBondRequestArrayList(ArrayList<BondRequest> bondRequestArrayList) {
        this.bondRequestArrayList = bondRequestArrayList;
    }

    public long getU_Id() {
        return u_Id;
    }

    public void setU_Id(long u_Id) {
        this.u_Id = u_Id;
    }

    public List<Bond> getBondList() {
        return bondList;
    }

    public void addBond(Bond bond) {
        this.bondList.add(bond);
    }

    public ArrayList<BondResponse> getBondBondArrayResponseList() {
        return bondBondArrayResponseList;
    }

    public void setBondBondArrayResponseList(ArrayList<BondResponse> bondBondArrayResponseList) {
        this.bondBondArrayResponseList = bondBondArrayResponseList;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEncrytedPassword() {
        return encrytedPassword;
    }

    public void setEncrytedPassword(String encrytedPassword) {
        this.encrytedPassword = encrytedPassword;
    }

}
