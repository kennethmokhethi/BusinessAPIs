package dao;

import dto.UserDTO;
import entity.Bond;
import entity.User;
import model.request.BondRequest;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.BeanUtils;


import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class MYSQLDAO {

    private static final SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
    public Session session = null;



    public Transaction getActiveTransaction() {
        this.session = sessionFactory.openSession();
        return this.session.beginTransaction();
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public CriteriaQuery<User> getUserCriteriaQuery(String username, String email) {
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<User> profileroot = criteriaQuery.from(User.class);
        criteriaQuery.select(profileroot);
        criteriaQuery.where(criteriaBuilder.equal(profileroot.get(email), username));
        return criteriaQuery;
    }

    public UserDTO saveUser(UserDTO userDTO) {
        User user = new User();
        BeanUtils.copyProperties(userDTO, user);
        addBondToUser(userDTO, user);
        session.persist(user);
        UserDTO returnValue = new UserDTO();
        BeanUtils.copyProperties(user, returnValue);
        user.getBondList().stream().forEach(returnValue::addBond);
        return returnValue;
    }

    private void addBondToUser(UserDTO userDTO, User user) {
        for (BondRequest bondRequest : userDTO.getBondRequestArrayList()) {
            Bond bond = new Bond();
            BeanUtils.copyProperties(bondRequest, bond);
            bond.setUserI(user);
            user.addBond(bond);

        }
    }

    public UserDTO getUser(String id) {
        CriteriaQuery<User> criteriaQuery = getUserCriteriaQuery(id, "userId");
        User query = session.createQuery(criteriaQuery).getSingleResult();
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(query, userDTO);
        userDTO.setU_Id(query.getUId());
        query.getBondList().stream().forEach(userDTO::addBond);
        return userDTO;
    }

    public void updateUser(UserDTO userProfile) {
        User user = new User();
        BeanUtils.copyProperties(userProfile, user); //usage of dto
        user.setUId(userProfile.getU_Id());
        session.update(user);
    }


    public List<UserDTO> getUsers(int start, int limit) {
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<User> userRoot = criteriaQuery.from(User.class);
        criteriaQuery.select(userRoot);
        List<User> searchResults = session.createQuery(criteriaQuery).setFirstResult(start).setMaxResults(limit).getResultList();
        return getInitializedReturnValue(searchResults);
    }

    private List<UserDTO> getInitializedReturnValue(List<User> searchResults) {
        List<UserDTO> returnValue = new ArrayList<>();
        for (User user : searchResults) {
            UserDTO userDTO = new UserDTO();
            BeanUtils.copyProperties(user, userDTO);
            user.getBondList().forEach(userDTO::addBond);
            returnValue.add(userDTO);
        }
        return returnValue;
    }

    public void deleteUser(UserDTO userDetail) {

        User user = session.load(User.class, userDetail.getU_Id());
        user.setUId(userDetail.getU_Id());
        session.delete(user);
    }

}
