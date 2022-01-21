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
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.Date;
import java.util.List;
import java.util.Map;

public class OrderHandler implements iHandler {
    private ProductsRepositoryImpl prod;
    private OrdersRepositoryImpl orderImpl;
    private OrderDetailsRepositoryImpl ordd;
    private String customerId = null;
    private byte employeeId = 0;
    private List<Map<String, Byte>> productsAndQuantities = null;
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
    public void handle(String[] args) throws  IOException{


        setParameters(args[1]);
        checkAmounts(productsAndQuantities);
        createOrders(productsAndQuantities);

    }


    public void setParameters(String fileName) throws IOException {
        try {
            Gson gson = new Gson();
            JsonReader reader = new JsonReader(new FileReader(fileName));

            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> map = mapper.readValue(Paths.get(fileName).toFile(), Map.class);
            // Properties properties = gson.fromJson(reader, Properties.class);

            customerId =(String) map.get("customerId");
            employeeId = (byte) map.get("customerId");
             productsAndQuantities =  (List)map.get("products");
             orderDate =(Date)  map.get("orderDate");
            requiredDate = (Date) map.get("requiredDate");
             shippedDate = (Date) map.get("shippedDate");
             shipVia =(byte) map.get("shipVia");
             freight = (byte)map.get("freight");
             shipName = (String)map.get("shipName");
             shipAddress =(String) map.get("shipAddress");
             shipCity = (String) map.get("shipCity");
             shipRegion = (String) map.get("shipRegion");
             shipPostalCode = (String) map.get("shipPostalCode");
             shipCountry = (String) map.get("shipCountry");
            quantity = (byte) map.get("quantity");
            unitPrice =(byte)  map.get("unitPrice");
            discount = (byte) map.get("discount");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public void checkAmounts(List<Map<String, Byte>> productsAndQuantities) {
        int flag = 0;

        for (Map<String, Byte> entry : productsAndQuantities) {
            byte productId = entry.get("productId");
            byte quantity = entry.get("quantity");
            if (prod.getUnitsOnStock(productId) < quantity) {
                //System.out.println("Available quanity of product: " + prod.getById(productId).getProductName() + "is lower than required");
                flag = -1;
                break;
            }
        }
        if (flag == 0) {
            System.out.println("All products are available for purchase in requested amount");
            for (Map<String, Byte> entry : productsAndQuantities) {
                System.out.println("Product: " + prod.getById(entry.get("productId")).getProductName()
                        + " Quantity: " + entry.get("quantity"));
            }
        } else {
            System.err.println("Requested amounts exceed available amount of products");
        }
    }

    public void createOrders(List<Map<String, Byte>> productsAndQuantities) {
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
        for (Map<String, Byte> entry : productsAndQuantities) {
            byte productId = entry.get("productId");
            byte quantity = entry.get("quantity");
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