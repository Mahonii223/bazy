package main.java.com.company.handlers;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class ReportHandler implements iHandler {

    private static SessionFactory sessionFactory = null;

    private static final String generalHelpText = "Choose report type:" +
            "\nproducts - Most popular products" +
            "\nemployees - Employees by revenue" +
            "\nclients - Clients by revenue";

    public void handle(String[] args){

        if(args.length < 2){
            System.out.println(generalHelpText);
            return;
        }
        sessionFactory = getSessionFactory();

        String option = args[1].toLowerCase();

        switch (option){
            case "products":
                generateProductReport();
                break;
            case "employees":
                generateEmployeeReport();
                break;
            case "clients":
                generateClientReport();
                return;
            default:
                System.out.println("Invalid option: \"" + option + "\". " + generalHelpText);
                return;
        }

    }

    private void generateProductReport(){
        Session session = sessionFactory.openSession();

        List<Object[]> results= session.createNativeQuery(
                "SELECT p.ProductID, SUM(od.UnitPrice*CAST(od.Quantity AS float)) totalRevenue, p.ProductName\n" +
                        "FROM orderDetails od, products p, orders o \n" +
                        "WHERE od.ProductID = p.ProductID AND od.OrderID = o.OrderID AND o.OrderDate BETWEEN '1/01/1996' AND '2/22/2012'\n" +
                        "GROUP BY p.ProductID, p.ProductName\n" +
                        "ORDER BY totalRevenue desc"
        ).list();
        for (Object[] objects : results) {
            String productId=(String)objects[0];
            String totalRev=(String)objects[1];
            String productName=(String)objects[2];
            System.out.println("Employee["+productId+","+totalRev+","+productName+"]");
        }

        session.close();
    }

    private void generateEmployeeReport(){
        System.out.println("employee report");
    }

    private void generateClientReport(){
        System.out.println("client report");
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
