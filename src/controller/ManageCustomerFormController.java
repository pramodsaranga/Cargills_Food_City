package controller;

import bo.BoFactory;
import bo.custom.CustomerBO;
import dto.CustomerDTO;
import entity.Customer;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import view.tm.CustomerTm;

import java.sql.SQLException;
import java.util.ArrayList;

public class ManageCustomerFormController {
    /* Dependence  injection */
    private final CustomerBO customerBO = (CustomerBO) BoFactory.getBOFactory().getBO(BoFactory.BoTypes.CUSTOMER);

    public AnchorPane CustomerContext;
    public TextField txtCustomerTitle;
    public TextField txtAddress;
    public TextField txtProvince;
    public TextField txtCustomerId;
    public TextField txtCustomerName;
    public TextField txtCity;
    public TextField txtPostalCode;
    public Button btnAdd;
    public TableView<CustomerTm> tblCustomer;
    public TableColumn colCustomerId;
    public TableColumn colCustomerTitle;
    public TableColumn colCustomerName;
    public TableColumn colAddress;
    public TableColumn colCity;
    public TableColumn colProvince;
    public TableColumn colPostalCode;
    public Button btnModify;
    public Button btnRemove;

    public void initialize() throws SQLException, ClassNotFoundException {
        lastId();
        loadAllCustomers();
        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        colCustomerTitle.setCellValueFactory(new PropertyValueFactory<>("customerTitle"));
        colCustomerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("Address"));
        colCity.setCellValueFactory(new PropertyValueFactory<>("city"));
        colProvince.setCellValueFactory(new PropertyValueFactory<>("province"));
        colPostalCode.setCellValueFactory(new PropertyValueFactory<>("postalCode"));

        tblCustomer.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                setData(newValue);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
    }

    public void setData(CustomerTm customerTm) throws SQLException, ClassNotFoundException {
        String Id=tblCustomer.getSelectionModel().getSelectedItem().getCustomerId();
        Customer c=customerBO.getCustomer(Id);
        if (c==null) {
            new Alert(Alert.AlertType.WARNING, "Empty Result Set").show();
        } else {
            setDataToField(c);
        }
    }

    public void setDataToField(Customer c){
        txtCustomerId.setText(c.getCustomerId());
        txtCustomerTitle.setText(c.getCustomerTitle());
        txtCustomerName.setText(c.getCustomerName());
        txtAddress.setText(c.getAddress());
        txtCity.setText(c.getCity());
        txtProvince.setText(c.getProvince());
        txtPostalCode.setText(c.getPostalCode());
    }

   private void loadAllCustomers() {
        tblCustomer.getItems().clear();
        /*Get all customers*/
        try {
            ArrayList<CustomerDTO> allCustomers = customerBO.getAllCustomer();
            for (CustomerDTO customer : allCustomers) {
                tblCustomer.getItems().add(new CustomerTm(customer.getCustomerId(), customer.getCustomerTitle(), customer.getCustomerName()
                        ,customer.getAddress(),customer.getCity(),customer.getProvince(),customer.getPostalCode()));
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    boolean existCustomer(String id) throws SQLException, ClassNotFoundException {
        return customerBO.ifCustomerExist(id);
    }

    public void AddCustomerOnAction(ActionEvent actionEvent) {
        String id=txtCustomerId.getText();
        String title=txtCustomerTitle.getText();
        String name=txtCustomerName.getText();
        String address=txtAddress.getText();
        String city=txtCity.getText();
        String province=txtProvince.getText();
        String postalCode=txtPostalCode.getText();
        try {
            if (existCustomer(id)) {
                new Alert(Alert.AlertType.ERROR, id + " already exists").show();
            }
            CustomerDTO customerDTO = new CustomerDTO(id,title, name, address,city,province,postalCode);
            tblCustomer.getItems().add(new CustomerTm(id,title, name, address,city,province,postalCode));
            if(customerBO.addCustomer(customerDTO)){
                new Alert(Alert.AlertType.CONFIRMATION, "Saved..").show();
                loadAllCustomers();
                lastId();
                txtCustomerTitle.clear();
                txtCustomerName.clear();
                txtAddress.clear();
                txtCity.clear();
                txtProvince.clear();
                txtPostalCode.clear();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to save the customer " + e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void ModifyOnAction(ActionEvent actionEvent) {
        String id=txtCustomerId.getText();
        String title=txtCustomerTitle.getText();
        String name=txtCustomerName.getText();
        String address=txtAddress.getText();
        String city=txtCity.getText();
        String province=txtProvince.getText();
        String postalCode=txtPostalCode.getText();
        try {
            if (!existCustomer(id)) {
                new Alert(Alert.AlertType.ERROR, "There is no such customer associated with the id " + id).show();
            }

            CustomerDTO customerDTO = new CustomerDTO(id,title, name, address,city,province,postalCode);
            if(customerBO.updateCustomer(customerDTO)){
                new Alert(Alert.AlertType.CONFIRMATION, "Updated..").show();
                loadAllCustomers();
                lastId();
                txtCustomerTitle.clear();
                txtCustomerName.clear();
                txtAddress.clear();
                txtCity.clear();
                txtProvince.clear();
                txtPostalCode.clear();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to update the customer " + id + e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void RemoveOnAction(ActionEvent actionEvent) {
        String id = tblCustomer.getSelectionModel().getSelectedItem().getCustomerId();
        try {
            if (!existCustomer(id)) {
                new Alert(Alert.AlertType.ERROR, "There is no such customer associated with the id " + id).show();
            }
            if(customerBO.deleteCustomer(id)){
                new Alert(Alert.AlertType.CONFIRMATION, "Deleted..").show();
                loadAllCustomers();
                lastId();
                txtCustomerTitle.clear();
                txtCustomerName.clear();
                txtAddress.clear();
                txtCity.clear();
                txtProvince.clear();
                txtPostalCode.clear();
                tblCustomer.getItems().remove(tblCustomer.getSelectionModel().getSelectedItem());
                tblCustomer.getSelectionModel().clearSelection();

            }

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to delete the customer " + id).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void lastId() throws SQLException, ClassNotFoundException {
        String Id = customerBO.generateNewID();
        String finalId = "C-001";

        if (Id != null) {

            String[] splitString = Id.split("-");
            int id = Integer.parseInt(splitString[1]);
            id++;

            if (id < 10) {
                finalId = "C-00" + id;
            } else if (id < 100) {
                finalId = "C-0" + id;
            } else {
                finalId = "C-" + id;
            }
            txtCustomerId.setText(finalId);
        } else {
            txtCustomerId.setText(finalId);
        }
    }
}
