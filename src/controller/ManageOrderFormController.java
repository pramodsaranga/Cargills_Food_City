package controller;

import bo.BoFactory;
import bo.custom.OrderBO;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import dto.CustomerDTO;
import dto.ItemDTO;
import dto.OrderDTO;
import dto.OrderDetailDTO;
import entity.Customer;
import entity.Item;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import view.tm.OrderDetailTm;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class ManageOrderFormController {
    public AnchorPane MakePaymentContext;
    public Label lblOrderId;
    public Label lblDate;
    public Label lblTime;
    public JFXComboBox <String>cmbCustomerId;
    public JFXTextField txtCustomerTitle;
    public JFXTextField txtCustomerName;
    public JFXTextField txtCity;
    public JFXTextField txtProvince;
    public JFXTextField txtPostalCode;
    public JFXComboBox <String>cmbItemCode;
    public JFXTextField txtDescription;
    public JFXTextField txtPackSize;
    public JFXTextField txtUnitPrice;
    public JFXTextField txtQtyOnHand;
    public JFXTextField txtQty;
    public JFXButton btnSave;
    public TableView  <OrderDetailTm>tblCart;
    public TableColumn colOrderId;
    public TableColumn colItemCode;
    public TableColumn colOrderQty;
    public TableColumn colDiscount;
    public TableColumn colTotal;
    public Label lblTotal;
    public JFXTextField txtAddress;
    /* Dependence  injection */
    private final OrderBO orderBO = (OrderBO) BoFactory.getBOFactory().getBO(BoFactory.BoTypes.ORDER);

    public void initialize() throws SQLException, ClassNotFoundException {
        loadAllCustomerIds();
        loadAllItemCodes();
        lastId();
        loadDateAndTime();

        colOrderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        colItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colOrderQty.setCellValueFactory(new PropertyValueFactory<>("OrderQty"));
        colDiscount.setCellValueFactory(new PropertyValueFactory<>("discount"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));


        cmbCustomerId.getSelectionModel().selectedItemProperty().
                addListener((observable, oldValue, newValue) -> {
                    try {
                        setCustomerData( newValue);
                    } catch (SQLException | ClassNotFoundException throwables) {
                        throwables.printStackTrace();
                    }
                });

        cmbItemCode.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                setItemData( newValue);
            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
            }
        });

        tblCart.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, selectedOrderDetail) -> {
            if (selectedOrderDetail != null) {
                cmbItemCode.setDisable(true);
                cmbItemCode.setValue(selectedOrderDetail.getItemCode());
                btnSave.setText("Update");
                txtQtyOnHand.setText(Integer.parseInt(txtQtyOnHand.getText()) + selectedOrderDetail.getOrderQty() + "");
                txtQty.setText(selectedOrderDetail.getOrderQty() + "");
            } else {
                btnSave.setText("Add");
                cmbItemCode.setDisable(false);
                cmbItemCode.getSelectionModel().clearSelection();
                txtQty.clear();
            }
        });
    }

    private void setItemData(String itemCode) throws SQLException, ClassNotFoundException {

        Item i=orderBO.getItem(itemCode);
        if (i == null) {
            new Alert(Alert.AlertType.WARNING, "Empty Result Set");
        } else {
            txtDescription.setText(i.getDescription());
            txtPackSize.setText(i.getPackSize());
            txtUnitPrice.setText(String.valueOf(i.getUnitPrice()));
            txtQtyOnHand.setText(String.valueOf(i.getQtyOnHand()));
        }
    }

    private void setCustomerData(String customerId) throws SQLException, ClassNotFoundException {
        Customer c=orderBO.getCustomer(customerId);
        if (c == null) {
            new Alert(Alert.AlertType.WARNING, "Empty Result Set");
        } else {
            txtCustomerTitle.setText(c.getCustomerTitle());
            txtCustomerName.setText(c.getCustomerName());
            txtAddress.setText(c.getAddress());
            txtCity.setText(c.getCity());
            txtProvince.setText(c.getProvince());
            txtPostalCode.setText(c.getPostalCode());
        }
    }

    public void AddToCartOnAction(ActionEvent actionEvent) {
        String id=lblOrderId.getText();
        String itemCode=cmbItemCode.getSelectionModel().getSelectedItem();
        int  qtyOnHand=Integer.parseInt(txtQtyOnHand.getText());
        Double discount=50.00/100;
        String orderDate=lblDate.getText();
        String orderTime=lblTime.getText();
        String customerId=cmbCustomerId.getSelectionModel().getSelectedItem();
        Double unitPrice=Double.parseDouble(txtUnitPrice.getText());
        int orderQty=Integer.parseInt(txtQty.getText());
        Double total=(unitPrice*orderQty)-unitPrice*discount;

        if (qtyOnHand<orderQty) {
            new Alert(Alert.AlertType.ERROR, "Invalid qty").show();
        }else {

            boolean exists = tblCart.getItems().stream().anyMatch(detail -> detail.getItemCode().equals(itemCode));

            if (exists) {
                OrderDetailTm orderDetailTm = tblCart.getItems().stream().filter(detail -> detail.getItemCode().equals(itemCode)).findFirst().get();

                if (btnSave.getText().equalsIgnoreCase("Update")) {
                    orderDetailTm.setOrderQty(qtyOnHand);
                    orderDetailTm.setTotal(total);
                    tblCart.getSelectionModel().clearSelection();
                } else {
                    orderDetailTm.setOrderQty(orderDetailTm.getOrderQty()+ orderQty);
                    orderDetailTm.setTotal(total);
                }
                tblCart.refresh();
            } else {
                tblCart.getItems().add(new OrderDetailTm(id,itemCode,orderQty,discount,orderDate,orderTime,customerId,total));
            }
            calculateTotal();
            cmbItemCode.getSelectionModel().clearSelection();
            txtDescription.clear();
            txtPackSize.clear();
            txtQtyOnHand.clear();
            txtUnitPrice.clear();
            txtQty.clear();
        }
    }

    private void calculateTotal() {
        Double total = 0.00;
        for (OrderDetailTm detail : tblCart.getItems()) {
            total = detail.getTotal();
        }
        lblTotal.setText("Total: " + total);
    }

    public void PlaceOrderOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        boolean b = saveOrder(lblOrderId.getText(),lblDate.getText(),lblTime.getText(), cmbCustomerId.getValue(),
                tblCart.getItems().stream().map(tm -> new OrderDetailDTO(lblOrderId.getText(), tm.getItemCode(), tm.getOrderQty(), tm.getDiscount(),tm.getOrderDate(),tm.getOrderTime(),tm.getCustomerId(),tm.getTotal())).collect(Collectors.toList()));
        if (b) {
            new Alert(Alert.AlertType.INFORMATION, "Order has been placed successfully").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Order has not been placed successfully").show();
        }

        lastId();
        cmbCustomerId.getSelectionModel().clearSelection();
        txtCustomerTitle.clear();
        txtCustomerName.clear();
        txtAddress.clear();
        txtCity.clear();
        txtProvince.clear();
        txtPostalCode.clear();
        cmbItemCode.getSelectionModel().clearSelection();
        tblCart.getItems().clear();
        txtQty.clear();
        txtDescription.clear();
        txtPackSize.clear();
        txtQtyOnHand.clear();
        txtUnitPrice.clear();

        calculateTotal();
    }

    public boolean saveOrder(String orderId, String orderDate, String orderTime, String customerId, List<OrderDetailDTO> orderDetail) {
        try {
            OrderDTO orderDTO = new OrderDTO(orderId,orderDate,orderTime,customerId,orderDetail);
            return orderBO.purchaseOrder(orderDTO);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void loadAllCustomerIds() {
        try {
            ArrayList<CustomerDTO> all = orderBO.getAllCustomers();
            for (CustomerDTO customerDTO : all) {
                cmbCustomerId.getItems().add(customerDTO.getCustomerId());
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to load customer ids").show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void loadAllItemCodes() {
        try {
            ArrayList<ItemDTO> all = orderBO.getAllItems();
            for (ItemDTO dto : all) {
                cmbItemCode.getItems().add(dto.getItemCode());
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void lastId() throws SQLException, ClassNotFoundException {
        String Id = orderBO.generateNewOrderId();;
        String finalId = "O-001";

        if (Id != null) {

            String[] splitString = Id.split("-");
            int id = Integer.parseInt(splitString[1]);
            id++;

            if (id < 10) {
                finalId = "O-00" + id;
            } else if (id < 100) {
                finalId = "O-0" + id;
            } else {
                finalId = "O-" + id;
            }
            lblOrderId.setText(finalId);
        } else {
            lblOrderId.setText(finalId);
        }
    }

    private void loadDateAndTime() {
        // load Date
        Date date = new Date();
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        lblDate.setText(f.format(date));

        // load Time
        Timeline time = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalTime currentTime = LocalTime.now();
            lblTime.setText(
                    currentTime.getHour() + " : " + currentTime.getMinute() +
                            " : " + currentTime.getSecond()
            );
        }),
                new KeyFrame(Duration.seconds(1))
        );
        time.setCycleCount(Animation.INDEFINITE);
        time.play();
    }
}
