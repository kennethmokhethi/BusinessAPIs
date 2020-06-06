import com.businessapi.dao.DAO;
import com.businessapi.dto.UserDTO;
import com.businessapi.model.request.BondRequest;
import org.hibernate.Transaction;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.persistence.PersistenceException;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

public class MYSQLDAOTest {
    private static DAO database;

    @BeforeClass
    public static void initialise() {
        database = new MYSQLDAOsub();
    }


    private UserDTO initialize() {
        UserDTO userDTO = new UserDTO();
        userDTO.setFirstname("Test");
        userDTO.setLastname("Oracle");
        userDTO.setSalt("53426dgdbsg453254");
        userDTO.setUserId("userspublic3");
        userDTO.setEncrytedPassword("gstsfatsfs");
        userDTO.setEmail("test@gmail.com");
        userDTO.setPassword("tests");
        userDTO.setToken("GFT");

        ArrayList<BondRequest> bondRequestArrayList = new ArrayList<>();
        BondRequest bondRequest = new BondRequest();
        bondRequest.setNoRooms(1);
        bondRequest.setAddress("Unite");

        bondRequestArrayList.add(bondRequest);
        userDTO.setBondRequestArrayList(bondRequestArrayList);
        return userDTO;
    }

    private UserDTO getUserDTOforupdateUser() {
        UserDTO userDTO = new UserDTO();
        userDTO.setFirstname("Test");
        userDTO.setLastname("Oracle");
        userDTO.setSalt("53426dgdbsg453254");
        userDTO.setUserId("userspublic3");
        userDTO.setEncrytedPassword("gstsfatsfs");
        userDTO.setEmail("Oracle@gmail.com");
        userDTO.setPassword("tests");
        userDTO.setToken("GFT");

        ArrayList<BondRequest> bondRequestArrayList = new ArrayList<>();
        BondRequest bondRequest = new BondRequest();
        bondRequest.setNoRooms(1);
        bondRequest.setAddress("Unite");

        bondRequestArrayList.add(bondRequest);
        userDTO.setBondRequestArrayList(bondRequestArrayList);
        return userDTO;
    }

    @Test
    public void saveUser() {
        Transaction transaction = this.database.getActiveTransaction();
        UserDTO returnVal = null;
        UserDTO userDTO = initialize();
        try {
            returnVal = this.database.saveUser(userDTO);
            transaction.commit();
        } catch (Exception ex) {
            transaction.rollback();
            ex.printStackTrace();
        }

        assertEquals(userDTO.getFirstname(), returnVal.getFirstname());
        assertEquals(userDTO.getLastname(), returnVal.getLastname());
        assertEquals(userDTO.getEmail(), returnVal.getEmail());
        assertEquals(userDTO.getEncrytedPassword(), returnVal.getEncrytedPassword());
        assertEquals(userDTO.getSalt(), returnVal.getSalt());
        assertEquals(userDTO.getBondRequestArrayList().get(0).getAddress(), returnVal.getBondList().get(0).getAddress());
        assertEquals(userDTO.getBondRequestArrayList().get(0).getNoRooms(), returnVal.getBondList().get(0).getNoRooms());
    }

    @Test
    public void getUser() {
        Transaction transaction = this.database.getActiveTransaction();
        UserDTO returnVal = null;
        UserDTO userDTO1 = getUserDTOupdate();
        try {
            this.database.saveUser(userDTO1);
            returnVal = this.database.getUser(userDTO1.getUserId());
            transaction.commit();
        } catch (Exception ex) {
            transaction.rollback();
            ex.printStackTrace();
        }

        assertEquals(userDTO1.getFirstname(), returnVal.getFirstname());
        assertEquals(userDTO1.getLastname(), returnVal.getLastname());
        assertEquals(userDTO1.getEmail(), returnVal.getEmail());
        assertEquals(userDTO1.getEncrytedPassword(), returnVal.getEncrytedPassword());
        assertEquals(userDTO1.getSalt(), returnVal.getSalt());
    }


    private UserDTO getUserDTOupdate() {
        UserDTO userDTO = new UserDTO();
        userDTO.setFirstname("Test");
        userDTO.setLastname("Oracle");
        userDTO.setSalt("53426dgdbsg453254");
        userDTO.setUserId("userspublic3fg");
        userDTO.setEncrytedPassword("gstsfatsfs");
        userDTO.setEmail("testde4@gmail.com");
        userDTO.setPassword("tests");
        userDTO.setToken("GFT");

        ArrayList<BondRequest> bondRequestArrayList = new ArrayList<>();
        BondRequest bondRequest = new BondRequest();
        bondRequest.setNoRooms(1);
        bondRequest.setAddress("Unite");

        bondRequestArrayList.add(bondRequest);
        userDTO.setBondRequestArrayList(bondRequestArrayList);
        return userDTO;
    }


    @Test(expected = PersistenceException.class)
    public void deleteUser() {
        Transaction transaction = this.database.getActiveTransaction();
        UserDTO userDTO = getUserDTOforupdateUser();
        this.database.saveUser(userDTO);
        Optional<UserDTO> returnVal = Optional.ofNullable(this.database.getUser(userDTO.getUserId()));
        this.database.deleteUser(returnVal.get());
        Optional.ofNullable(this.database.getUser(userDTO.getUserId()));
        transaction.commit();
    }
}
