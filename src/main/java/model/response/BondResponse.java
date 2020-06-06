package model.response;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class BondResponse {
    private int noRooms;

    private String address;

    public int getNoRooms() {
        return noRooms;
    }

    public void setNoRooms(int noRooms) {
        this.noRooms = noRooms;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }




}
