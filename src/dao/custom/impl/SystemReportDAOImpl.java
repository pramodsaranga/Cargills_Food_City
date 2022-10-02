package dao.custom.impl;

import dao.CrudUtil;
import dao.custom.SystemReportDAO;
import dto.SystemReportDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SystemReportDAOImpl implements SystemReportDAO {
    @Override
    public boolean add(SystemReportDAO systemReportDAO) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String s) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean update(SystemReportDAO systemReportDAO) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public SystemReportDAO search(String s) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public ArrayList<SystemReportDAO> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }
    public List<String> getCustomerIds() throws SQLException, ClassNotFoundException {
        ResultSet rst= CrudUtil.executeQuery("SELECT * FROM OrderDetail");
        List<String> ids = new ArrayList<>();
        while (rst.next()){
            ids.add(
                    rst.getString(7)
            );
        }
        return ids;
    }

    public ArrayList<String> getYears() throws SQLException, ClassNotFoundException {
        ArrayList<String> year = new ArrayList<>();
        ResultSet rst= CrudUtil.executeQuery("SELECT YEAR(orderDate) FROM orderDetail");
        while (rst.next()) {
            if(isYearExists(rst.getString(1),year)){

            }else {
                year.add(rst.getString(1));
            }
        }
        return year;
    }

    public boolean isYearExists(String string , ArrayList<String> year){
        for(String y : year){
            if(y.equals(string)){
                return true;
            }
        }
        return false;
    }
    public ArrayList<String> getMonth() throws SQLException, ClassNotFoundException {
        ArrayList<String>month= new ArrayList<>();
        ResultSet rst= CrudUtil.executeQuery("SELECT MONTH(orderDate) FROM orderDetail");

        while (rst.next()) {
            if(isYearExists(rst.getString(1),month)){

            }else {
                month.add(rst.getString(1));
            }
        }
        return month;
    }

    public ArrayList<String> getDates() throws SQLException, ClassNotFoundException {
        ArrayList<String>dates= new ArrayList<>();
        ResultSet rst= CrudUtil.executeQuery("SELECT DATE(orderDate) FROM orderDetail");

        while (rst.next()) {
            if(isYearExists(rst.getString(1),dates)){

            }else {
                dates.add(rst.getString(1));
            }
        }
        return dates;
    }

    public ArrayList<SystemReportDTO> getYearlyDetails(String year) throws SQLException, ClassNotFoundException {
        ArrayList<SystemReportDTO> yearlyDetails = new ArrayList<>();
        ResultSet rst= CrudUtil.executeQuery("SELECT * FROM OrderDetail WHERE YEAR(orderDate) ='"+year+"'");

        while (rst.next()) {
            SystemReportDTO s = new SystemReportDTO(
                    rst.getString(1),
                    rst.getString(2),
                    Integer.parseInt(rst.getString(3)),
                    Double.parseDouble(rst.getString(4)),
                    rst.getString(5),
                    rst.getString(6),
                    rst.getString(7),
                    Double.parseDouble(rst.getString(8))

            );
            yearlyDetails.add(s);
        }
        return yearlyDetails;
    }

    public ArrayList<SystemReportDTO> getMonthlyDetails(String month) throws SQLException, ClassNotFoundException {
        ArrayList<SystemReportDTO> MonthlyDetails= new ArrayList<>();
        ResultSet rst= CrudUtil.executeQuery("SELECT * FROM OrderDetail WHERE MONTH(orderDate) ='"+month+"'");

        while (rst.next()) {
            SystemReportDTO s = new SystemReportDTO(
                    rst.getString(1),
                    rst.getString(2),
                    Integer.parseInt(rst.getString(3)),
                    Double.parseDouble(rst.getString(4)),
                    rst.getString(5),
                    rst.getString(6),
                    rst.getString(7),
                    Double.parseDouble(rst.getString(8))
            ) {
            };
            MonthlyDetails.add(s);
        }
        return MonthlyDetails;
    }

    public ArrayList<SystemReportDTO> getDailyDetails(String date) throws SQLException, ClassNotFoundException {
        ArrayList<SystemReportDTO> DailyDetails= new ArrayList<>();
        ResultSet rst= CrudUtil.executeQuery("SELECT * FROM OrderDetail WHERE DATE(orderDate) ='"+date+"'");
        while (rst.next()) {
            SystemReportDTO s = new SystemReportDTO(
                    rst.getString(1),
                    rst.getString(2),
                    Integer.parseInt(rst.getString(3)),
                    Double.parseDouble(rst.getString(4)),
                    rst.getString(5),
                    rst.getString(6),
                    rst.getString(7),
                    Double.parseDouble(rst.getString(8))
            );
            DailyDetails.add(s);
        }
        return DailyDetails;
    }

    public ArrayList<SystemReportDTO> getCustomerIncome(String id) throws SQLException, ClassNotFoundException {
        ArrayList<SystemReportDTO> CustomerWiseIncomeDetails= new ArrayList<>();
        ResultSet rst= CrudUtil.executeQuery("SELECT * FROM OrderDetail WHERE customerId ='"+id+"'");

        while (rst.next()) {
            SystemReportDTO s = new SystemReportDTO(
                    rst.getString(1),
                    rst.getString(2),
                    Integer.parseInt(rst.getString(3)),
                    Double.parseDouble(rst.getString(4)),
                    rst.getString(5),
                    rst.getString(6),
                    rst.getString(7),
                    Double.parseDouble(rst.getString(8))
            );
            CustomerWiseIncomeDetails.add(s);
        }
        return CustomerWiseIncomeDetails;
    }

    public String getMost(String iCode) throws SQLException, ClassNotFoundException {
        ResultSet rst= CrudUtil.executeQuery("SELECT itemCode,COUNT(itemCode) FROM OrderDetail GROUP BY itemCode ORDER BY itemCode DESC LIMIT 1");
        if(rst.next()){
            return rst.getString(1);
        }
        return null;
    }

    public String getLeast(String iCode) throws SQLException, ClassNotFoundException {
        ResultSet rst= CrudUtil.executeQuery("SELECT itemCode,COUNT(itemCode) FROM OrderDetail GROUP BY itemCode ORDER BY itemCode ASC LIMIT 1");
        if(rst.next()){
            return rst.getString(1);
        }
        return null;
    }
}
