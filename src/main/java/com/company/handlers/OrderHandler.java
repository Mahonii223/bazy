package com.company.handlers;

import com.company.entities.OrderDetails;
import com.company.entities.Orders;
import com.company.repoImpl.OrderDetailsRepositoryImpl;
import com.company.repoImpl.OrdersRepositoryImpl;
import com.company.repoImpl.ProductsRepositoryImpl;
import com.company.repositories.OrderDetailsRepository;
import com.company.repositories.OrdersRepository;
import com.company.repositories.ProductsRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class OrderHandler implements iHandler {

    private static SessionFactory sessionFactory = null;

    private ProductsRepositoryImpl prod;
    private OrdersRepositoryImpl orderImpl;
    private OrderDetailsRepositoryImpl ordd;
    private String customerId = null;
    private byte employeeId = 0;
    private List<Map<String, Integer>> productsAndQuantities = null;
    private Date orderDate = null;
    private Date requiredDate = null;
    private Date shippedDate = null;
    private byte shipVia = 0;
    private byte freight = 0;
    private String shipName = null;
    private String shipAddress = null;
    private String shipCity = null;
    private String shipRegion = null;
    private String shipPostalCode = null;
    private String shipCountry = null;
    @Override
    public void handle(String[] args) throws  IOException{

        EntityManagerFactory emf = Persistence.
                createEntityManagerFactory("persistence");
        EntityManager em = emf.createEntityManager();
        prod = new ProductsRepositoryImpl(em);
        orderImpl = new OrdersRepositoryImpl(em);
        ordd = new OrderDetailsRepositoryImpl(em);

        sessionFactory = getSessionFactory();

        Session session = sessionFactory.openSession();

        Transaction tx = session.beginTransaction();

        setParameters(args[1]);
        checkAmounts(productsAndQuantities);
        createOrders(productsAndQuantities);

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


    public void setParameters(String fileName) throws IOException {
        try {
            Gson gson = new Gson();
            JsonReader reader = new JsonReader(new FileReader(fileName));

            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> map = mapper.readValue(Paths.get(fileName).toFile(), Map.class);
            // Properties properties = gson.fromJson(reader, Properties.class);

            customerId =(String) map.get("customerId");
            employeeId = ((Integer) map.get("employeeId")).byteValue();
            productsAndQuantities =  (List)map.get("products");
            orderDate = java.sql.Date.valueOf(LocalDate.parse((String) map.get("OrderDate")));
            requiredDate = java.sql.Date.valueOf(LocalDate.parse((String) map.get("RequiredDate")));
            shippedDate = java.sql.Date.valueOf(LocalDate.parse((String) map.get("ShippedDate")));
            shipVia =((Integer) map.get("ShipVia")).byteValue();
            freight = ((Integer) map.get("Freight")).byteValue();
            shipName = (String)map.get("ShipName");
            shipAddress =(String) map.get("ShipAddress");
            shipCity = (String) map.get("ShipCity");
            shipRegion = (String) map.get("ShipRegion");
            shipPostalCode = (String) map.get("ShipPostalCode");
            shipCountry = (String) map.get("ShipCountry");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public void checkAmounts(List<Map<String, Integer>> productsAndQuantities) {
        int flag = 0;

        for (Map<String, Integer> entry : productsAndQuantities) {
            byte productId = (entry.get("productId")).byteValue();
            byte quantity = (entry.get("quantity")).byteValue();
            byte actualQuantity =prod.getUnitsOnStock(productId);
            if (actualQuantity < quantity) {
                System.out.println("Available quanity of product: " + prod.getById(productId).getProductName() + " (" +actualQuantity+ ") is lower than required");
                flag = -1;
                break;
            }
        }
        if (flag == 0) {
            System.out.println("All products are available for purchase in requested amount");
            for (Map<String, Integer> entry : productsAndQuantities) {
                System.out.println("Product: " + prod.getById((entry.get("productId")).byteValue()).getProductName()
                        + " Quantity: " + entry.get("quantity"));
            }
        } else {
            System.err.println("Requested amounts exceed available amount of products");
            throw new RuntimeException("Product amount exceeded");
        }
    }

    public void createOrders(List<Map<String, Integer>> productsAndQuantities) {
        Orders order = new Orders();
        order.setOrderId(-1);
        order.setCustomerId(customerId);
        order.setEmployeeId(employeeId);
        order.setOrderDate(orderDate);
        order.setRequiredDate(requiredDate);
        order.setShippedDate(shippedDate);
        order.setShipVia(shipVia);
        order.setFreight(freight);
        order.setShipName(shipName);
        order.setShipAddress(shipAddress);
        order.setShipCity(shipCity);
        order.setShipRegion(shipRegion);
        order.setShipPostalCode(shipPostalCode);
        order.setShipCountry(shipCountry);
        orderImpl.saveOrder(order);
        for (Map<String, Integer> entry : productsAndQuantities) {
            byte productId = (entry.get("productId")).byteValue();
            byte quantity = (entry.get("quantity")).byteValue();
            OrderDetails od = new OrderDetails();
            od.setOrderId(order.getOrderId().shortValue());
            od.setProductId(productId);
            //od.setUnitPrice(unitPrice);
            od.setQuantity(quantity);
            //od.setDiscount(discount);
            prod.setUnitsOnStock(productId, quantity);
            ordd.save(od);
        }
    }
}