package com.klef.jfsd.exam;

import org.hibernate.*;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.*;

import java.util.List;

public class ClientDemo {
    public static void main(String[] args) {
        // Set up Hibernate Session
        SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
        Session session = sessionFactory.openSession();

        try {
            // Insert records
            session.beginTransaction();
            Customer cust1 = new Customer();
            cust1.setName("Alice");
            cust1.setEmail("alice@example.com");
            cust1.setAge(30);
            cust1.setLocation("New York");

            Customer cust2 = new Customer();
            cust2.setName("Bob");
            cust2.setEmail("bob@example.com");
            cust2.setAge(25);
            cust2.setLocation("Los Angeles");

            session.save(cust1);
            session.save(cust2);
            session.getTransaction().commit();

            // Perform Criteria Queries
            Criteria criteria = session.createCriteria(Customer.class);

            System.out.println("Customers with age > 28:");
            criteria.add(Restrictions.gt("age", 28));
            List<Customer> results = criteria.list();
            for (Customer customer : results) {
                System.out.println(customer.getName());
            }

            System.out.println("Customers located in 'Los Angeles':");
            criteria = session.createCriteria(Customer.class);
            criteria.add(Restrictions.eq("location", "Los Angeles"));
            results = criteria.list();
            for (Customer customer : results) {
                System.out.println(customer.getName());
            }

            System.out.println("Customers with name like 'A%':");
            criteria = session.createCriteria(Customer.class);
            criteria.add(Restrictions.like("name", "A%"));
            results = criteria.list();
            for (Customer customer : results) {
                System.out.println(customer.getName());
            }
        } finally {
            session.close();
            sessionFactory.close();
        }
    }
}
