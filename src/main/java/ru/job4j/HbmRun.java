package ru.job4j;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;
import org.hibernate.service.Service;
import org.hibernate.service.ServiceRegistry;

public class HbmRun {

    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();
            /*
            Candidate one = new Candidate(25, "Evgeny", "год", (float) 123.40);
            Candidate two = new Candidate(26, "Vika", "год", (float) 15.6);
            Candidate three = new Candidate(27, "Kate", "год", (float) 1045.40);
            session.save(one);
            session.save(two);
            session.save(three);
            */
            session.getTransaction().commit();
            Query query = session.createQuery("from Candidate");
            for (Object candidate: query.list()) {
                System.out.println(candidate);
            }
            Query queryId = session.createQuery("from Candidate c where c.id = 2");
            System.out.println(queryId.uniqueResult());
            Query queryName = session.createQuery("from Candidate c where c.name = 'Kate'");
            System.out.println(queryName.list());
            Query queryUpdate = session.createQuery("update Candidate c set c.experience = :exp where c.id = :fid");
            queryUpdate.setParameter("exp", "два года");
            queryUpdate.setParameter("fid", 3);
            session.beginTransaction();
            queryUpdate.executeUpdate();
            Query queryDelete = session.createQuery("delete from Candidate where id = :fid");
            queryDelete.setParameter("fid", 2);
            queryDelete.executeUpdate();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}
