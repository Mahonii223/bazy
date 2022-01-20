package com.company;

import com.company.entities.Orders;
import com.company.handlers.CrudHandler;
import com.company.handlers.ReportHandler;
import com.company.handlers.iHandler;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class Main {

    private static SessionFactory sessionFactory = null;

    public static void main(String[] args) {
        /*sessionFactory = getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

        Orders order = session.get(Orders.class, 10298);
        System.out.println(order.getOrderDate());

        tx.commit();
        session.close();

        */
        if (args.length == 0) {
            System.out.println("No option provided. Available: '-crud', '-report', '-order'");
            return;
        }

        iHandler handler;

        switch(args[0]) {
            case "-crud":
                handler = new CrudHandler();
                break;
            case "-report":
                handler = new ReportHandler();
                break;
            case "-order":
                return;
            default:
                System.out.println("Must specify existing option. Available: '-crud', '-report', '-order'");
                return;
        }

        handler.handle(args);
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
