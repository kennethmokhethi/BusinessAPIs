import dao.MYSQLDAO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class MYSQLDAOsub extends MYSQLDAO {

    private static final SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
    private Session session = null;

    @Override
    public Transaction getActiveTransaction() {
        this.session = sessionFactory.openSession();
        setSession(this.session);
        return this.session.beginTransaction();
    }




}
