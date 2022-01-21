package main.java.com.company;

import com.company.entities.Orders;

import main.java.com.company.handlers.ReportHandler;
import main.java.com.company.handlers.iHandler;

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

        if(args.length == 0){
            System.out.println("Must specify option!");
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
                System.out.println("Invalid option: " + args[0]);
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
