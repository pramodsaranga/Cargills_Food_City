package dao.custom.impl;

import dao.CrudUtil;
import dao.custom.OrderDAO;
import entity.Order;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDAOImpl implements OrderDAO {
    @Override
    public boolean add(Order dto) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("INSERT INTO Orders  VALUES (?,?,?,?)", dto.getOrderId(), dto.getOrderDate(), dto.getOrderTime(),dto.getCustomerId());
    }

    @Override
    public boolean delete(String s) throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("Not Supported Yet");
    }

    @Override
    public boolean update(Order orderDTO) throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("Not Supported Yet");
    }

    @Override
    public Order search(String oid) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM Orders WHERE orderId=?", oid);
        rst.next();
        return new Order(rst.getString("orderId"), rst.getString("orderDate"), rst.getString("orderTime"),rst.getString("customerId"));
    }

    @Override
    public ArrayList<Order> getAll() throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("Not Supported Yet");
    }

    @Override
    public boolean ifOrderExist(String oid) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT orderId FROM Orders WHERE orderId=?", oid);
        return rst.next();
    }

    @Override
    public String generateNewOrderId() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT orderId FROM Orders ORDER BY orderId DESC LIMIT 1;");
        if (rst.next()) {
            String id = rst.getString("orderId");
            return id;
        }
        return  null;
    }
}

