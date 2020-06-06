package model.request;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class LoginCredentials {

    private String username;
    private String userpassword;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserpassword() {
        return userpassword;
    }

    public void setUserpassword(String userpassword) {
        this.userpassword = userpassword;
    }
}
