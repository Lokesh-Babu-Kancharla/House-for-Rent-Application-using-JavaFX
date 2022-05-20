package com.example.da3_javafx;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;


public class MainController implements Initializable {


    @FXML
    private TextField tfId;
    @FXML
    private TextField tfHouseNo;
    @FXML
    private TextField tfAddress;
    @FXML
    private TextField tfRooms;
    @FXML
    private TextField tfRent;
    @FXML
    private TableView<Records> tvRecords;
    @FXML
    private TableColumn<Records, Integer> colId;
    @FXML
    private TableColumn<Records, String> colHouseNo;
    @FXML
    private TableColumn<Records, String> colAddress;
    @FXML
    private TableColumn<Records, Integer> colRooms;
    @FXML
    private TableColumn<Records, Integer> colRent;
    @FXML
    private Button btnInsert;
    @FXML
    private Button btnUpdate;
    @FXML
    private Button btnDelete;

    @FXML
    private void handleButtonAction(ActionEvent event) {

        if(event.getSource() == btnInsert){
            insertRecord();
        }else if (event.getSource() == btnUpdate){
            updateRecord();
        }else if(event.getSource() == btnDelete){
            deleteButton();
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        showRecords();
    }

    public Connection getConnection(){
        String dbName="houseforrent"; // Database Name
        String userName="root";
        String password="";
        Connection conn;
        try{
            conn = DriverManager.getConnection("jdbc:mysql://localhost/"+dbName,userName,password);
            return conn;
        }catch(Exception ex){
            System.out.println("Error: " + ex.getMessage());
            return null;
        }
    }

    public ObservableList<Records> getRecordsList(){
        ObservableList<Records> recordList = FXCollections.observableArrayList();
        Connection conn = getConnection();
        String query = "SELECT * FROM records";
        Statement st;
        ResultSet rs;

        try{
            st = conn.createStatement();
            rs = st.executeQuery(query);
            Records records;
            while(rs.next()){
                records = new Records(rs.getInt("id"), rs.getString("houseno"), rs.getString("address"), rs.getInt("rooms"),rs.getInt("rent"));
                recordList.add(records);
            }

        }catch(Exception ex){
            ex.printStackTrace();
        }
        return recordList;
    }

    public void showRecords(){
        ObservableList<Records> list = getRecordsList();

        colId.setCellValueFactory(new PropertyValueFactory<Records, Integer>("id"));
        colHouseNo.setCellValueFactory(new PropertyValueFactory<Records, String>("HouseNo"));
        colAddress.setCellValueFactory(new PropertyValueFactory<Records, String>("address"));
        colRooms.setCellValueFactory(new PropertyValueFactory<Records, Integer>("rooms"));
        colRent.setCellValueFactory(new PropertyValueFactory<Records, Integer>("rent"));

        tvRecords.setItems(list);
    }
    private void insertRecord(){
        String query = "INSERT INTO records VALUES (" + tfId.getText() + ",'" + tfHouseNo.getText() + "','" + tfAddress.getText() + "',"
                + tfRooms.getText() + "," + tfRent.getText() + ")";
        executeQuery(query);
        showRecords();
    }
    private void updateRecord(){
        String query = "UPDATE  records SET houseno  = '" + tfHouseNo.getText() + "', address = '" + tfAddress.getText() + "', rooms = " +
                tfRooms.getText() + ", rent = " + tfRent.getText() + " WHERE id = " + tfId.getText() + "";
        executeQuery(query);
        showRecords();
    }
    private void deleteButton(){
        String query = "DELETE FROM records WHERE id =" + tfId.getText() + "";
        executeQuery(query);
        showRecords();
    }

    private void executeQuery(String query) {
        Connection conn = getConnection();
        Statement st;
        try{
            st = conn.createStatement();
            st.executeUpdate(query);
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }


}
