package dao.custom.impl;

import dao.CrudUtil;
import dao.custom.ItemDAO;
import entity.Item;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ItemDAOImpl implements ItemDAO {

    @Override
    public boolean add(Item dto) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("INSERT INTO Item (itemCode, description,packSize, unitPrice, qtyOnHand) VALUES (?,?,?,?,?)", dto.getItemCode(), dto.getDescription(),dto.getPackSize(), dto.getUnitPrice(), dto.getQtyOnHand());
    }

    @Override
    public boolean delete(String code) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("DELETE FROM Item WHERE itemCode=?", code);
    }

    @Override
    public boolean update(Item dto) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("UPDATE Item SET description=?,packSize=?, unitPrice=?, qtyOnHand=? WHERE ItemCode=?", dto.getDescription(),dto.getPackSize(), dto.getUnitPrice(), dto.getQtyOnHand(), dto.getItemCode());
    }

    @Override
    public Item search(String code) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM Item WHERE itemCode=?", code);
        rst.next();
        return new Item(code, rst.getString("description"),rst.getString("packSize"), rst.getDouble("unitPrice"),rst.getInt("qtyOnHand"));
    }

    @Override
    public ArrayList<Item> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<Item> allItems = new ArrayList<>();
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM Item");
        while (rst.next()) {
            allItems.add(new Item(rst.getString("itemCode"), rst.getString("description"),rst.getString("packSize") ,rst.getDouble("unitPrice"), rst.getInt("qtyOnHand")));
        }
        return allItems;
    }


    @Override
    public boolean ifItemExist(String code) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeQuery("SELECT itemCode FROM Item WHERE itemCode=?", code).next();
    }


    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT itemCode FROM Item ORDER BY itemCode DESC LIMIT 1;");
        if (rst.next()) {
            String id = rst.getString("itemCode");
            return id;
        }
        return  null;
    }

    @Override
    public Item getItem(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst=CrudUtil.executeQuery("SELECT * FROM Item  WHERE itemCode=?",id);

        if (rst.next()) {
            return new Item(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getDouble(4),
                    rst.getInt(5)
            );
        } else {
            return null;
        }
    }
}