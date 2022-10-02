package controller;

import bo.BoFactory;
import bo.custom.SystemReportBO;
import com.jfoenix.controls.JFXComboBox;
import dto.SystemReportDTO;
import entity.SystemReport;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SystemReportFormController {
    public AnchorPane IncomeReportContext;
    public JFXComboBox<String> cmbSelectDate;
    public TextField txtDailyIncome;
    public TextField txtMonthIncome;
    public JFXComboBox<String> cmbMonth;
    public JFXComboBox<String> cmbYear;
    public TextField txtYearIncome;
    public JFXComboBox<String> cmbCustomerIds;
    public TextField txtCustomerIncome;
    public TableView<SystemReportDTO> tblAnnualIncome;
    public TableColumn colOrderId;
    public TableColumn colQty;
    public TableColumn colCustomerId;
    public TableColumn colTotal;
    public JFXComboBox<String> cmbMovableItem;
    public TextField txtMovable;
    /* Dependence  injection */
    private final SystemReportBO systemReportBO = (SystemReportBO) BoFactory.getBOFactory().getBO(BoFactory.BoTypes.SYSTEM_REPORT);


    public  void initialize() throws SQLException, ClassNotFoundException {
        loadCustomerIds();
        loadYears();
        loadMonths();
        loadDates();

        cmbYear.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            ArrayList<SystemReportDTO> yearlyDetails = new ArrayList<>();
            try {

                yearlyDetails =systemReportBO.getYearlyDetails(newValue);

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            calculateIncome(yearlyDetails);

            colOrderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
            colCustomerId.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
            colQty.setCellValueFactory(new PropertyValueFactory<>("orderQty"));
            colCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
            colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));

            tblAnnualIncome.setItems(FXCollections.observableArrayList(yearlyDetails));
        });

        cmbMonth.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            ArrayList<SystemReportDTO> MonthlyDetails = new ArrayList<>();
            try {

                MonthlyDetails =systemReportBO.getMonthlyDetails(newValue);

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            calculateMonthIncome(MonthlyDetails);

            colOrderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
            colCustomerId.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
            colQty.setCellValueFactory(new PropertyValueFactory<>("orderQty"));
            colCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
            colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));

            tblAnnualIncome.setItems(FXCollections.observableArrayList(MonthlyDetails));
        });

        cmbSelectDate.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            ArrayList<SystemReportDTO> DailyDetails = new ArrayList<>();
            try {

                DailyDetails = systemReportBO.getDailyDetails(newValue);

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            calculateDailyIncome(DailyDetails);

            colOrderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
            colCustomerId.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
            colQty.setCellValueFactory(new PropertyValueFactory<>("orderQty"));
            colCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
            colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));

            tblAnnualIncome.setItems(FXCollections.observableArrayList(DailyDetails));
        });

        cmbCustomerIds.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            ArrayList<SystemReportDTO> CustomerIncomeDetails = new ArrayList<>();
            try {

                CustomerIncomeDetails = systemReportBO.getCustomerIncome(newValue);

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            calculateCustomerWiseIncome(CustomerIncomeDetails);

            colOrderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
            colCustomerId.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
            colQty.setCellValueFactory(new PropertyValueFactory<>("orderQty"));
            colCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
            colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));

            tblAnnualIncome.setItems(FXCollections.observableArrayList(CustomerIncomeDetails));
        });

        ArrayList<String> MovaleList = new ArrayList<>();
        MovaleList.add("MostMovableItem");
        MovaleList.add("LeastMovableItem");

        for (String Movable : MovaleList) {
            cmbMovableItem.getItems().add(Movable);
        }



        cmbMovableItem.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            ArrayList<SystemReport> CustomerIncomeDetails = new ArrayList<>();


            try {
                if(newValue=="MostMovableItem"){
                    String a=systemReportBO.getMost(newValue);
                    txtMovable.setText(a);
                }else{
                    String a=systemReportBO.getLeast(newValue);
                    txtMovable.setText(a);
                }


            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
    }

    private void loadCustomerIds() throws SQLException, ClassNotFoundException {
        List<String> customerIds = systemReportBO
                .getCustomerIds();
        cmbCustomerIds.getItems().addAll(customerIds);

    }

    private void loadYears() throws SQLException, ClassNotFoundException {
        List<String> years =systemReportBO
                .getYears();
        cmbYear.getItems().addAll(years);

    }

    private void loadMonths() throws SQLException, ClassNotFoundException {
        List<String> months = systemReportBO
                .getMonth();
        cmbMonth.getItems().addAll(months);

    }
    private void loadDates() throws SQLException, ClassNotFoundException {
        List<String> dates = systemReportBO
                .getDates();
        cmbSelectDate.getItems().addAll(dates);
    }

    private void calculateIncome(ArrayList<SystemReportDTO> temp){
        double tPrice=0.0;
        for(SystemReportDTO r : temp){
            tPrice+= r.getTotal();
        }
        txtYearIncome.setText(String.valueOf(tPrice)+" /=");
    }

    private void calculateMonthIncome(ArrayList<SystemReportDTO> temp){
        double tPrice=0.0;
        for(SystemReportDTO r : temp){
            tPrice+= r.getTotal();
        }
        txtMonthIncome.setText(String.valueOf(tPrice)+" /=");
    }

    private void calculateDailyIncome(ArrayList<SystemReportDTO> temp){
        double tPrice=0.0;
        for(SystemReportDTO r : temp){
            tPrice+= r.getTotal();
        }
        txtDailyIncome.setText(String.valueOf(tPrice)+" /=");
    }


    private void calculateCustomerWiseIncome(ArrayList<SystemReportDTO> temp){
        double tPrice=0.0;
        for(SystemReportDTO r : temp){
            tPrice+= r.getTotal();
        }
        txtCustomerIncome.setText(String.valueOf(tPrice)+" /=");
    }

    public void PrintOnAction(ActionEvent actionEvent) {

    }
}
