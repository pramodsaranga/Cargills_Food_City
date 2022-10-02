package bo.custom.impl;

import bo.custom.SystemReportBO;
import dao.DAOFactory;
import dao.custom.SystemReportDAO;
import dto.SystemReportDTO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SystemReportBOImpl implements SystemReportBO {
    private final SystemReportDAO systemReportDAO = (SystemReportDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.SYSTEMREPORTS);
    @Override
    public String getMost(String iCode) throws SQLException, ClassNotFoundException {
        return systemReportDAO.getMost(iCode);
    }

    @Override
    public String getLeast(String iCode) throws SQLException, ClassNotFoundException {
        return systemReportDAO.getLeast(iCode);
    }

    @Override
    public List<String> getCustomerIds() throws SQLException, ClassNotFoundException {
        List<String> all = systemReportDAO.getCustomerIds();
        return all;
    }

    @Override
    public ArrayList<String> getYears() throws SQLException, ClassNotFoundException {
        ArrayList<String> all = systemReportDAO.getYears();
        return all;
    }

    @Override
    public boolean isYearExists(String string, ArrayList<String> year) {
        for(String y : year){
            if(y.equals(string)){
                return true;
            }
        }
        return false;
    }

    @Override
    public ArrayList<String> getMonth() throws SQLException, ClassNotFoundException {
        ArrayList<String> all = systemReportDAO.getMonth();
        return all;
    }

    @Override
    public ArrayList<String> getDates() throws SQLException, ClassNotFoundException {
        ArrayList<String> all = systemReportDAO.getDates();
        return all;
    }

    @Override
    public ArrayList<SystemReportDTO> getYearlyDetails(String year) throws SQLException, ClassNotFoundException {
        ArrayList<SystemReportDTO> allYears = new ArrayList<>();
        ArrayList<SystemReportDTO> all = systemReportDAO.getYearlyDetails(year);
        for (SystemReportDTO i : all) {
            allYears.add(new SystemReportDTO(i.getOrderId(),i.getItemCode(),i.getOrderQty(),i.getDiscount(),i.getOrderDate(),i.getOrderTime(),i.getCustomerId(),i.getTotal()));
        }
        return allYears;
    }

    @Override
    public ArrayList<SystemReportDTO> getMonthlyDetails(String month) throws SQLException, ClassNotFoundException {
        ArrayList<SystemReportDTO> allMonths = new ArrayList<>();
        ArrayList<SystemReportDTO> all = systemReportDAO.getMonthlyDetails(month);
        for (SystemReportDTO i : all) {
            allMonths.add(new SystemReportDTO(i.getOrderId(),i.getItemCode(),i.getOrderQty(),i.getDiscount(),i.getOrderDate(),i.getOrderTime(),i.getCustomerId(),i.getTotal()));
        }
        return allMonths;
    }

    @Override
    public ArrayList<SystemReportDTO> getDailyDetails(String Day) throws SQLException, ClassNotFoundException {
        ArrayList<SystemReportDTO> allDaily = new ArrayList<>();
        ArrayList<SystemReportDTO> all = systemReportDAO.getDailyDetails(Day);
        for (SystemReportDTO i : all) {
            allDaily.add(new SystemReportDTO(i.getOrderId(),i.getItemCode(),i.getOrderQty(),i.getDiscount(),i.getOrderDate(),i.getOrderTime(),i.getCustomerId(),i.getTotal()));
        }
        return allDaily;
    }

    @Override
    public ArrayList<SystemReportDTO> getCustomerIncome(String id) throws SQLException, ClassNotFoundException {
        ArrayList<SystemReportDTO> allCus = new ArrayList<>();
        ArrayList<SystemReportDTO> all = systemReportDAO.getCustomerIncome(id);
        for (SystemReportDTO i : all) {
            allCus.add(new SystemReportDTO(i.getOrderId(),i.getItemCode(),i.getOrderQty(),i.getDiscount(),i.getOrderDate(),i.getOrderTime(),i.getCustomerId(),i.getTotal()));
        }
        return allCus;
    }
}
