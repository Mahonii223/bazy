package main.java.com.company.handlers;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class ReportHandler implements main.java.com.company.handlers.iHandler {

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

        if(args.length < 4){
            System.out.println("Please provide timeframe in DD/MM/YYYY format");
        }

        String startDate = args[2];
        String endDate = args[3];

        sessionFactory = getSessionFactory();

        String option = args[1].toLowerCase();

        switch (option){
            case "products":
                generateProductReport(startDate, endDate);
                break;
            case "employees":
                generateEmployeeReport(startDate, endDate);
                break;
            case "clients":
                generateClientReport(startDate, endDate);
                return;
            default:
                System.out.println("Invalid option: \"" + option + "\". " + generalHelpText);
                return;
        }

    }

    private void generateProductReport(String startDate, String endDate){

        Session session = sessionFactory.openSession();

        List<Object[]> results= session.createNativeQuery(
                "SELECT " +
                        "p.ProductID, SUM(od.UnitPrice*CAST(od.Quantity AS float)) totalRevenue, " +
                        "p.ProductName, " +
                        "s.CompanyName, " +
                        "SUM(od.Quantity) totalQuantity " +
                        "FROM orderDetails od, products p, orders o, suppliers s " +
                        "WHERE " +
                        "od.ProductID = p.ProductID AND " +
                        "od.OrderID = o.OrderID AND " +
                        "p.SupplierID = s.SupplierID AND " +
                        "o.OrderDate BETWEEN \'" + startDate + "\' AND \'" + endDate + "\' " +
                        "GROUP BY p.ProductID, p.ProductName, s.CompanyName " +
                        "ORDER BY totalRevenue desc"
        ).list();
        Integer index = 1;
        System.out.println("\tFound " + results.size() + " total.");
        System.out.format("\n%5s%5s%40s%40s%10s%10s", "index", "ID", "Product name", "Supplier", "Quantity", "Revenue");
        for (Object[] record : results) {
            generateProductReportLine(record, index);
            index++;
        }

        session.close();

    }

    private void generateProductReportLine(Object[] record, Integer index){
        String productId = String.valueOf(record[0]);
        String totalRevenue = String.valueOf(record[1]);
        String prodName = String.valueOf(record[2]);
        String compName = String.valueOf(record[3]);
        String totalQuantity = String.valueOf(record[4]);
        System.out.format("\n%5s%5s%40s%40s%10s%10s", index, productId, prodName, compName, totalQuantity, totalRevenue);
        //System.out.println(index + "\t" + productId + "\t" + prodName + "\t" + compName + "\t" + totalRevenue);
    }

    private void generateEmployeeReport(String startDate, String endDate){

        Session session = sessionFactory.openSession();

        List<Object[]> results= session.createNativeQuery(
                "SELECT " +
                        "e.EmployeeID, " +
                        "SUM(od.UnitPrice*CAST(od.Quantity AS float)) totalRevenue, " +
                        "e.FirstName, " +
                        "e.LastName, " +
                        "COUNT(DISTINCT(o.OrderID)) totalOrders,  " +
                        "SUM(od.UnitPrice*CAST(od.Quantity AS float))/COUNT(DISTINCT(o.OrderID)) avgOrderValue " +
                        "FROM orderDetails od, empl e, orders o " +
                        "WHERE o.EmployeeID = e.EmployeeID AND " +
                        "od.OrderID = o.OrderID AND " +
                        "o.OrderDate BETWEEN \'" + startDate + "\' AND \'" + endDate + "\' " +
                        "GROUP BY e.EmployeeID, e.FirstName, e.LastName ORDER BY totalRevenue desc"
        ).list();

        System.out.println("\tFound " + results.size() + " total.");
        System.out.format("\n%5s%5s%30s%10s%10s%20s", "index", "ID", "Full name", "Revenue", "Orders", "Average order value");


        Integer index = 1;
        for (Object[] record : results) {
            generateEmployeeReportLine(record, index);
            index++;
        }

        session.close();
    }

    private void generateEmployeeReportLine(Object[] record, Integer index){

        String id = String.valueOf(record[0]);
        String totalRevenue = String.valueOf(record[1]);
        String fName = String.valueOf(record[2]);
        String lName = String.valueOf(record[3]);
        String totalOrders = String.valueOf(record[4]);
        String avgOrderValue = String.format("%.2f", record[5]);

        System.out.format("\n%5s%5s%30s%10s%10s%20s", index, id, fName+" "+lName, totalRevenue, totalOrders, avgOrderValue);

    }

    private void generateClientReport(String startDate, String endDate){

        Session session = sessionFactory.openSession();

        List<Object[]> results= session.createNativeQuery(
                "SELECT " +
                        "c.CustomerID, " +
                        "SUM(od.UnitPrice*CAST(od.Quantity AS float)) totalRevenue, " +
                        "c.CompanyName, " +
                        "COUNT(DISTINCT(o.OrderID)) totalOrders, " +
                        "SUM(od.UnitPrice*CAST(od.Quantity AS float))/COUNT(DISTINCT(o.OrderID)) avgOrder " +
                        "FROM orderDetails od, customers c, orders o " +
                        "WHERE o.CustomerID = c.CustomerID AND " +
                        "od.OrderID = o.OrderID AND " +
                        "o.OrderDate BETWEEN \'" + startDate + "\' AND \'" + endDate + "\' " +
                        "GROUP BY c.CustomerID, c.CompanyName " +
                        "ORDER BY totalRevenue desc"
        ).list();

        System.out.println("\tFound " + results.size() + " total.");
        System.out.format("\n%5s%5s%40s%10s%10s%20s", "index", "ID", "Customer name", "Revenue", "Orders", "Average order value");


        Integer index = 1;
        for (Object[] record : results) {
            generateClientReportLine(record, index);
            index++;
        }

        session.close();
    }

    private void generateClientReportLine(Object[] record, Integer index){

        String id = String.valueOf(record[0]);
        String totalRevenue = String.valueOf(record[1]);
        String name = String.valueOf(record[2]);
        String totalOrders = String.valueOf(record[3]);
        String avgOrderValue = String.format("%.2f", record[4]);
        System.out.format("\n%5s%5s%40s%10s%10s%20s", index, id, name, totalRevenue, totalOrders, avgOrderValue);


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
