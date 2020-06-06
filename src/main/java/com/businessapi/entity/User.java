package com.businessapi.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "Users")
public class User implements Serializable {

    private static final long serialVersionUID = 4865965339190150223L;

    @Id
    @SequenceGenerator(name = "user_seq_gen", sequenceName = "user_seq_id", initialValue = 1000, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq_gen")
    private long u_Id;

    private String userId;

    @Column(nullable = false)
    private String firstname;

    @Column(nullable = false)
    private String lastname;

    @Column(nullable = false, unique = true)
    private String email;
    private String token;
    private String salt;
    private String encrytedPassword;

    @OneToMany(mappedBy = "userI", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Bond> bondList = new ArrayList<>();

    public List<Bond> getBondList() {
        return bondList;
    }

    public void setBondList(List<Bond> bondList) {
        this.bondList = bondList;
    }

    public void addBond(Bond bond) {
        this.bondList.add(bond);
    }

    public long getUId() {
        return u_Id;
    }

    public void setUId(long id) {
        this.u_Id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getEncrytedPassword() {
        return encrytedPassword;
    }

    public void setEncrytedPassword(String encrytedPassword) {
        this.encrytedPassword = encrytedPassword;
    }
}
