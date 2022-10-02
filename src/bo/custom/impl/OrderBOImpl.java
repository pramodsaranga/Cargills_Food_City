package bo.custom.impl;

import bo.custom.OrderBO;
import dao.DAOFactory;
import dao.custom.*;
import db.DbConnection;
import dto.CustomerDTO;
import dto.ItemDTO;
import dto.OrderDTO;
import dto.OrderDetailDTO;
import entity.Customer;
import entity.Item;
import entity.Order;
import entity.OrderDetail;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderBOImpl implements OrderBO {
    private final CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.CUSTOMER);
    private final ItemDAO itemDAO = (ItemDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.ITEM);
    private final OrderDAO orderDAO = (OrderDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.ORDER);
    private final OrderDetailDAO orderDetailsDAO = (OrderDetailDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.ORDERDETAIL);
    private final QueryDAO queryDAO = (QueryDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.QUERYDAO);

    @Override
    public boolean purchaseOrder(OrderDTO dto) throws SQLException, ClassNotFoundException {
        /*Transaction*/
        Connection connection = null;

        connection = DbConnection.getDbConnection().getConnection();
        boolean orderAvailable = orderDAO.ifOrderExist(dto.getOrderId());
        if (orderAvailable) {
            return false;
        }

        connection.setAutoCommit(false);
        /*Add Order*/
        Order order = new Order(dto.getOrderId(), dto.getOrderDate(), dto.getOrderTime(),dto.getCustomerId());
        boolean orderAdded = orderDAO.add(order);
        if (!orderAdded) {
            connection.rollback();
            connection.setAutoCommit(true);
            return false;
        }

        for (OrderDetailDTO detail : dto.getOrderDetail()) {
            OrderDetail orderDetailDTO = new OrderDetail(dto.getOrderId(), detail.getItemCode(), detail.getOrderQty(), detail.getDiscount(),
                    detail.getOrderDate(),detail.getOrderTime(),detail.getCustomerId(),detail.getTotal());
            boolean orderDetailsAdded = orderDetailsDAO.add(orderDetailDTO);
            if (!orderDetailsAdded) {
                connection.rollback();
                connection.setAutoCommit(true);
                return false;
            }

            //Search & Update Item
            Item search = itemDAO.search(detail.getItemCode());
            search.setQtyOnHand(search.getQtyOnHand() - detail.getOrderQty());
            boolean update = itemDAO.update(search);
            if (!update) {
                connection.rollback();
                connection.setAutoCommit(true);
                return false;
            }
        }

        connection.commit();
        connection.setAutoCommit(true);
        return true;

    }

    @Override
    public String generateNewOrderId() throws SQLException, ClassNotFoundException {
        return orderDAO.generateNewOrderId();
    }

    @Override
    public ArrayList<CustomerDTO> getAllCustomers() throws SQLException, ClassNotFoundException {
        ArrayList<CustomerDTO> allCustomers = new ArrayList<>();
        ArrayList<Customer> all = customerDAO.getAll();
        for (Customer c : all) {
            allCustomers.add(new CustomerDTO(c.getCustomerId(), c.getCustomerTitle(), c.getCustomerName(),c.getAddress(),
                    c.getCity(),c.getProvince(),c.getPostalCode()));
        }
        return allCustomers;

    }

    @Override
    public ArrayList<ItemDTO> getAllItems() throws SQLException, ClassNotFoundException {
        ArrayList<ItemDTO> allItems = new ArrayList<>();
        ArrayList<Item> all = itemDAO.getAll();
        for (Item item : all) {
            allItems.add(new ItemDTO(item.getItemCode(), item.getDescription(),item.getPackSize() ,item.getUnitPrice(), item.getQtyOnHand()));
        }
        return allItems;

    }

    @Override
    public ItemDTO searchItem(String code) throws SQLException, ClassNotFoundException {
        Item item = itemDAO.search(code);
        return new ItemDTO(item.getItemCode(), item.getDescription(),item.getPackSize() ,item.getUnitPrice(), item.getQtyOnHand());
    }

    @Override
    public boolean ifItemExist(String code) throws SQLException, ClassNotFoundException {
        return itemDAO.ifItemExist(code);
    }

    @Override
    public boolean ifCustomerExist(String id) throws SQLException, ClassNotFoundException {
        return customerDAO.ifCustomerExist(id);
    }

    @Override
    public CustomerDTO searchCustomer(String s) throws SQLException, ClassNotFoundException {
        Customer c = customerDAO.search(s);
        return new CustomerDTO(c.getCustomerId(), c.getCustomerTitle(), c.getCustomerName(),c.getAddress(),
                c.getCity(),c.getProvince(),c.getPostalCode());
    }
    @Override
    public Item getItem(String id) throws SQLException, ClassNotFoundException {
        Item item= itemDAO.getItem(id);
        return item;
    }
    @Override
    public Customer getCustomer(String id) throws SQLException, ClassNotFoundException {
        Customer customer= customerDAO.getCustomer(id);
        return customer;
    }
}

