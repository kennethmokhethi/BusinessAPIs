package entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Bonds")
public class Bond implements Serializable {

    private static final long serialVersionUID = 8951126902237541295L;

    @Id
    @SequenceGenerator(name = "bond_seq_gen", sequenceName = "user_seq_id", initialValue = 1000, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bond_seq_gen")
    private long id;

    @Column(nullable = false)
    private int noRooms;

    @Column(nullable = false)
    private String address;


    @ManyToOne(optional = false)
    @JoinColumn(name = "u_Id", referencedColumnName = "u_Id")
    private User userI;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUserI() {
        return userI;
    }

    public void setUserI(User userI) {
        this.userI = userI;
    }

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
