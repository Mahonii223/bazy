package com.company;

import com.company.entities.Orders;
import com.company.handlers.OrderHandler;
import com.company.handlers.ReportHandler;
import com.company.handlers.iHandler;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class Main {

    private static SessionFactory sessionFactory = null;

    public static void main(String[] args) {
        sessionFactory = getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();


        iHandler handler;

        switch(args[0]) {
            case "-crud":
                return;
            case "-report":
                handler = new ReportHandler();
                break;
            case "-order":
                handler = new OrderHandler();
                handler.handle(args);
            default:
                System.out.println("Must specify option");
                return;
        }

        //handler.handle(args);


        tx.commit();
        session.close();





    }
    private static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            Configuration configuration = new Configuration();
            sessionFactory =
                    configuration.configure().buildSessionFactory();
        }
        return sessionFactory;
    }
}
