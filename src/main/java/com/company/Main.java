package com.company;

import com.company.handlers.CrudHandler;
import com.company.handlers.OrderHandler;
import com.company.handlers.ReportHandler;
import com.company.handlers.iHandler;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.IOException;

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

        if (args.length == 0) {
            System.out.println("Must specify option!");
            return;
        }

        iHandler handler;

        switch (args[0]) {
            case "-crud":
                handler = new CrudHandler();
                break;
            case "-report":
                handler = new ReportHandler();
                break;
            case "-order":
                handler = new OrderHandler();
                break;
            default:
                System.out.println("Invalid option: " + args[0]);
                return;
        }
        try {
            handler.handle(args);
        } catch (IOException e) {
            System.out.println("IO exception");
        }
    }
}
