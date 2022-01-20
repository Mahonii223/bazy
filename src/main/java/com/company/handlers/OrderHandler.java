package com.company.handlers;

import com.company.entities.OrderDetails;
import com.company.entities.Orders;
import com.company.repoImpl.OrderDetailsRepositoryImpl;
import com.company.repoImpl.OrdersRepositoryImpl;
import com.company.repoImpl.ProductsRepositoryImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Date;
import java.util.Map;

public class OrderHandler implements iHandler {
    private ProductsRepositoryImpl prod;
    private OrdersRepositoryImpl orderImpl;
    private OrderDetailsRepositoryImpl ordd;
    private String customerId = null;
    private byte employeeId = 0;
    private Map<Byte, Byte> productsAndQuantities = null;
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
    private byte quantity = 0;
    private byte unitPrice = 0;
    private byte discount = 0;
    @Override
    public void handle(String[] args) {


        setParameters(args[1]);
        checkAmounts(productsAndQuantities);
        createOrders(productsAndQuantities);

    }


    public void setParameters(String fileName) {
        try {
            Gson gson = new Gson();
            JsonReader reader = new JsonReader(new FileReader(fileName));
            ObjectMapper mapper = new ObjectMapper();
            // Properties properties = gson.fromJson(reader, Properties.class);

            String customerId = mapper.readValue("customerId", String.class);
            byte employeeId = mapper.readValue("customerId", Byte.class);
            Map<Byte, Integer> productsAndQuantities = mapper.readValue("products", Map.class);
            Date orderDate = mapper.readValue("orderDate", Date.class);
            Date requiredDate = mapper.readValue("requiredDate", Date.class);
            Date shippedDate = mapper.readValue("shippedDate", Date.class);
            byte shipVia = mapper.readValue("shipVia", Byte.class);
            byte freight = mapper.readValue("freight", Byte.class);
            String shipName = mapper.readValue("shipName", String.class);
            String shipAddress = mapper.readValue("shipAddress", String.class);
            String shipCity = mapper.readValue("shipCity", String.class);
            String shipRegion = mapper.readValue("shipRegion", String.class);
            String shipPostalCode = mapper.readValue("shipPostalCode", String.class);
            String shipCountry = mapper.readValue("shipCountry", String.class);
            byte quantity = mapper.readValue("quantity", Byte.class);
            byte unitPrice = mapper.readValue("unitPrice", Byte.class);
            byte discount = mapper.readValue("discount", Byte.class);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public void checkAmounts(Map<Byte, Byte> productsAndQuantities) {
        int flag = 0;

        for (Map.Entry<Byte, Byte> entry : productsAndQuantities.entrySet()) {
            byte productId = entry.getKey();
            int quantity = entry.getValue();
            if (prod.getUnitsOnStock(productId) < quantity) {
                //System.out.println("Available quanity of product: " + prod.getById(productId).getProductName() + "is lower than required");
                flag = -1;
                break;
            }
        }
        if (flag == 0) {
            System.out.println("All products are available for purchase in requested amount");
            for (Map.Entry<Byte, Byte> entry : productsAndQuantities.entrySet()) {
                System.out.println("Product: " + prod.getById(entry.getKey()).getProductName()
                        + " Quantity: " + entry.getKey());
            }
        } else {
            System.err.println("Requested amounts exceed available amount of products");
        }
    }

    public void createOrders(Map<Byte, Byte> productsAndQuantities) {
        Orders order = new Orders();
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
        for (Map.Entry<Byte, Byte> entry : productsAndQuantities.entrySet()) {
            byte productId = entry.getKey();
            byte quantity = entry.getValue();
            OrderDetails od = new OrderDetails();
            od.setOrderId(order.getOrderId());
            od.setProductId(productId);
            od.setUnitPrice(unitPrice);
            od.setQuantity(quantity);
            od.setDiscount(discount);
            prod.setUnitsOnStock(productId, quantity);
            ordd.save(od);
        }
    }
}
