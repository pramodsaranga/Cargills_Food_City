package controller;

import bo.BoFactory;
import bo.custom.ItemBO;
import dto.ItemDTO;
import entity.Item;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import view.tm.ItemTm;
import java.sql.SQLException;
import java.util.ArrayList;

public class ManageItemFormController {
    public AnchorPane ItemContext;
    public TextField txtItemCode;
    public TextField txtDescription;
    public TextField txtPackSize;
    public TextField txtUnitPrice;
    public TextField txtQtyOnHand;
    public Button btnAddItem;
    public TableView<ItemTm> tblItems;
    public TableColumn colItemCode;
    public TableColumn colDescription;
    public TableColumn colPackSize;
    public TableColumn colUnitPrice;
    public TableColumn colQtyOnHand;
    public Button btnModifyItem;
    public Button btnRemove;
    /* Dependence  injection */
    private final ItemBO itemBO = (ItemBO) BoFactory.getBOFactory().getBO(BoFactory.BoTypes.ITEM);

    public void initialize() throws SQLException, ClassNotFoundException {
        lastId();
        loadAllItems();
        colItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colPackSize.setCellValueFactory(new PropertyValueFactory<>("packSize"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colQtyOnHand.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));

        tblItems.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                setData(newValue);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
    }

    public void setData(ItemTm itemTm) throws SQLException, ClassNotFoundException {
        String Id=tblItems.getSelectionModel().getSelectedItem().getItemCode();

        Item i=itemBO.getItem(Id);
        if (i==null) {
            new Alert(Alert.AlertType.WARNING, "Empty Result Set").show();
        } else {
            setDataToField(i);
        }
    }

    public void setDataToField(Item i){
        txtItemCode.setText(i.getItemCode());
        txtDescription.setText(i.getDescription());
        txtPackSize.setText(i.getPackSize());
        txtUnitPrice.setText(String.valueOf(i.getUnitPrice()));
        txtQtyOnHand.setText(String.valueOf(i.getQtyOnHand()));
    }

    public void AddItemOnAction(ActionEvent actionEvent) {
        String itemCode=txtItemCode.getText();
        String description=txtDescription.getText();
        String packSize=txtPackSize.getText();
        Double unitPrice=Double.parseDouble(txtUnitPrice.getText());
        int qtyOnHand=Integer.parseInt(txtQtyOnHand.getText());

        try {
            if (existCustomer(itemCode)) {
                new Alert(Alert.AlertType.ERROR, itemCode + " already exists").show();
            }
            ItemDTO itemDTO= new ItemDTO(itemCode,description,packSize,unitPrice,qtyOnHand);
            tblItems.getItems().add(new ItemTm(itemCode,description,packSize,unitPrice,qtyOnHand));
            if(itemBO.addItem(itemDTO)){
                new Alert(Alert.AlertType.CONFIRMATION, "Saved..").show();
                loadAllItems();
                lastId();
                txtDescription.clear();
                txtPackSize.clear();
                txtQtyOnHand.clear();
                txtUnitPrice.clear();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to save the Item" + e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void ModifyItemOnAction(ActionEvent actionEvent) {
        String id=txtItemCode.getText();
        String description=txtDescription.getText();
        String packSize=txtPackSize.getText();
        Double unitPrice=Double.parseDouble(txtUnitPrice.getText());
        int qty=Integer.parseInt(txtQtyOnHand.getText());

        try {
            if (!existCustomer(id)) {
                new Alert(Alert.AlertType.ERROR, "There is no such item associated with the id " + id).show();
            }
            ItemDTO itemDTO = new ItemDTO(id,description,packSize,unitPrice,qty);
            if(itemBO.updateItem(itemDTO)){
                new Alert(Alert.AlertType.CONFIRMATION, "Updated..").show();
                loadAllItems();
                lastId();
                txtDescription.clear();
                txtPackSize.clear();
                txtQtyOnHand.clear();
                txtUnitPrice.clear();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to update the Item " + id + e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void RemoveItemOnAction(ActionEvent actionEvent) {
        String id = tblItems.getSelectionModel().getSelectedItem().getItemCode();
        try {
            if (!existCustomer(id)) {
                new Alert(Alert.AlertType.ERROR, "There is no such customer associated with the id " + id).show();
            }
            if(itemBO.deleteItem(id)){
                new Alert(Alert.AlertType.CONFIRMATION, "Deleted..").show();
                loadAllItems();
                lastId();
                txtDescription.clear();
                txtPackSize.clear();
                txtQtyOnHand.clear();
                txtUnitPrice.clear();
                tblItems.getItems().remove(tblItems.getSelectionModel().getSelectedItem());
                tblItems.getSelectionModel().clearSelection();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to delete the customer " + id).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    boolean existCustomer(String id) throws SQLException, ClassNotFoundException {
        return itemBO.ifItemExist(id);
    }

    public void lastId() throws SQLException, ClassNotFoundException {
        String Id = itemBO.generateNewID();;
        String finalId = "I-001";

        if (Id != null) {

            String[] splitString = Id.split("-");
            int id = Integer.parseInt(splitString[1]);
            id++;

            if (id < 10) {
                finalId = "I-00" + id;
            } else if (id < 100) {
                finalId = "I-0" + id;
            } else {
                finalId = "I-" + id;
            }
            txtItemCode.setText(finalId);
        } else {
            txtItemCode.setText(finalId);
        }
    }

    private void loadAllItems() {
        tblItems.getItems().clear();
        /*Get all item*/
        try {
            ArrayList<ItemDTO> allItem=itemBO.getAllItems();
            for (ItemDTO itemDTO: allItem) {
                tblItems.getItems().add(new ItemTm(itemDTO.getItemCode(),itemDTO.getDescription(),
                        itemDTO.getPackSize(),itemDTO.getUnitPrice(),itemDTO.getQtyOnHand()));
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

}
