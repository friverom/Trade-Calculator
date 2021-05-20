
package com.frm.tradecalculatormaven;



import com.frm.tradecalculatormaven.models.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author frive
 */
public class CalcViewController implements Initializable {

    //Text Fields and variable declaration for Trade Calculator
    @FXML
    private TextField symbolTxt;
    private String symbol;
    @FXML
    private TextField riskTxt;
    private double risk;
    @FXML
    private TextField entryTxt;
    private double entry;
    @FXML
    private TextField stopTxt;
    private double stop;
    @FXML
    private TextField stopLossTxt;
    private double stopLoss;
    @FXML
    private TextField ocoStop;
    @FXML
    private TextField ocoLimit;
    @FXML
    private MenuItem saveMenu;
    @FXML
    private TextField tradeTxt;
    
    //Table View and Columns declaration
    @FXML
    private TableView<Trade> tableView;
    @FXML
    private TableColumn<Trade, String> symbolCol;
    @FXML
    private TableColumn<Trade, String> sharesCol;
    @FXML
    private TableColumn<Trade, String> entryCol;
    @FXML
    private TableColumn<Trade, String> stopCol;
    @FXML
    private TableColumn<Trade, String> tradeTypeCol;
     @FXML
    private TableColumn<Trade, String> tgt1;
    @FXML
    private TableColumn<Trade, String> tgt2;
    @FXML
    private TableColumn<Trade, String> tgt3;
    
    //This list holds all trades calculated
    ObservableList<Trade> list=FXCollections.observableArrayList();
    
    Stage stage;
    TradeSettings settings;
    
   

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        stage=new Stage();
        
        // Submit trade list to Table View Control
        tableView.setItems(list);
        tableView.setEditable(false);
        
        //Setup all Table View Columns
        //All table columns are String type so the values can be easely format. 
       symbolCol.setCellValueFactory(new PropertyValueFactory<Trade, String>("stockSymbol"));
       symbolCol.setCellFactory(TextFieldTableCell.forTableColumn());
       
       sharesCol.setCellValueFactory(new PropertyValueFactory<Trade, String>("numShares"));
       sharesCol.setCellFactory(TextFieldTableCell.forTableColumn());
       
       entryCol.setCellValueFactory(new PropertyValueFactory<Trade, String>("entryPrice"));
       entryCol.setCellFactory(TextFieldTableCell.forTableColumn());
       
       stopCol.setCellValueFactory(new PropertyValueFactory<Trade, String>("stopPrice"));
       stopCol.setCellFactory(TextFieldTableCell.forTableColumn());
       
       tradeTypeCol.setCellValueFactory(new PropertyValueFactory<Trade,String>("tradeType"));
       tradeTypeCol.setCellFactory(TextFieldTableCell.forTableColumn()); 
       
       tgt1.setCellValueFactory(new PropertyValueFactory<Trade, String>("tgt1"));
       tgt1.setCellFactory(TextFieldTableCell.forTableColumn());
       
       tgt2.setCellValueFactory(new PropertyValueFactory<Trade, String>("tgt2"));
       tgt2.setCellFactory(TextFieldTableCell.forTableColumn());
       
       tgt3.setCellValueFactory(new PropertyValueFactory<Trade, String>("tgt3"));
       tgt3.setCellFactory(TextFieldTableCell.forTableColumn());
       
       //Load Trade Settings file to setup the default risk and OCO Bracket
       File file=new File("TS.bin");
        try {
            FileInputStream fis=new FileInputStream(file);
            ObjectInputStream ois=new ObjectInputStream(fis);
            settings=(TradeSettings)ois.readObject();
            ois.close();
            fis.close();
            
            riskTxt.setText(String.valueOf(settings.getRisk()));
            risk=settings.getRisk();
            
            ocoLimit.setText(String.valueOf(settings.getOcoLimit()));
            
            
           
        } catch (FileNotFoundException ex) {
            System.out.println(ex);
        } catch (IOException ex) {
            System.out.println(ex);
        } catch (ClassNotFoundException ex) {
            System.out.println(ex);
        } 
       symbolTxt.requestFocus();
    }    

    public void setStage(Stage stage){
    
    }
    

    //Setup risk
    @FXML
    private void riskHandler(KeyEvent event) {
        
        //User types in risk in the text field and press ENTER Key to
        //set the value
        if(event.getCode().equals(KeyCode.ENTER)){
            risk=Double.parseDouble(riskTxt.getText());
            entryTxt.requestFocus();
        }
    }

    //Setup Entry Price
    @FXML
    private void entryHandler(KeyEvent event) {
        //Setup value when ENTER key pressed
        if(event.getCode().equals(KeyCode.ENTER)){
            entry=Double.parseDouble(entryTxt.getText());
            stopTxt.requestFocus();
        }
    }

    //Setup stop price
    @FXML
    private void stopHandler(KeyEvent event) {
        //Setup value when ENTER key pressed
        if(event.getCode().equals(KeyCode.ENTER)){
            stop=Double.parseDouble(stopTxt.getText());
            stopLoss=entry-stop; //Calculate stop loss
            
            //Compute OCO Bracket
            ocoStop.setText(String.format("%.2f",Math.abs(stopLoss)));
            ocoLimit.setText(String.format("%.2f",Math.abs(settings.getOcoLimit()*stopLoss)));
            stopLossTxt.setText(String.format("%.2f", Math.abs(stopLoss)));
            
            //Add Trade to the list. (populate Table View)
            list.add(new Trade(symbol,entry,stop,risk));
        }
    }

    //Setup Stop.
    //If user enters the stop loss, this task computes the stop price and
    //computes the whole trade
    @FXML
    private void stopLossHandler(KeyEvent event) {
        if(event.getCode().equals(KeyCode.ENTER)){
            stopLoss=Double.parseDouble(stopLossTxt.getText());
            stop=entry-stopLoss; //Compute stop price
            ocoStop.setText(String.format("%.2f",Math.abs(stopLoss)));
            ocoLimit.setText(String.format("%.2f",Math.abs(settings.getOcoLimit()*stopLoss)));
            stopTxt.setText(String.format("%.2f",stop));
            
            //Add Trade to list.
            list.add(new Trade(symbol,entry,stop,risk));
        }
    }

    //Setup Symbol and convert it to Upper Case
    @FXML
    private void symbolHandler(KeyEvent event) {
        if(event.getCode().equals(KeyCode.ENTER)){
            symbol=symbolTxt.getText().toUpperCase();
            symbolTxt.setText(symbol);
            entryTxt.requestFocus();
        }
    }
    
    //Parse the Trade Text Field and get Trade information
    @FXML
    private void tradeHandler(KeyEvent event) {
        if(event.getCode().equals(KeyCode.ENTER)){
            String[] items=tradeTxt.getText().split("\\s+");
            getTradeData(tradeTxt.getText());
            stopLoss=entry-stop;
            symbolTxt.setText(symbol);
            entryTxt.setText(String.format("%.2f",entry));
            stopTxt.setText(String.format("%.2f", stop));
            ocoStop.setText(String.format("%.2f",Math.abs(stopLoss)));
            ocoLimit.setText(String.format("%.2f",Math.abs(2*stopLoss)));
            stopLossTxt.setText(String.format("%.2f", Math.abs(stopLoss)));
            list.add(new Trade(symbol,entry,stop,risk));
            tradeTxt.clear();
            
        }
    }
    
    // Table View Events Handlers
    //Delete row from table if DELETE Key pressed on row
    @FXML
    private void deleteRowHandler(KeyEvent event) {
        if(event.getCode().equals(KeyCode.DELETE)){
            //Get the selected Trade to delete
            Trade selectedRow=tableView.getSelectionModel().getSelectedItem();
            // Delete Trade from Table View and because the observable feature
            // will also delete the trade from the list
            tableView.getItems().remove(selectedRow);
        }
    }
    // This task select the Trade from the table and setup all Text Fields
    // and variables of the trade
    @FXML
    private void selectTradeHandler(MouseEvent event) {
        Trade selectedRow=tableView.getSelectionModel().getSelectedItem();
        symbolTxt.setText(selectedRow.getSymbol());
        entryTxt.setText(selectedRow.getEntryPrice());
        stopTxt.setText(selectedRow.getStopPrice());
        double stp=Math.abs(Double.parseDouble(entryTxt.getText())-Double.parseDouble(stopTxt.getText()));
        stopLossTxt.setText(String.format("%.2f", stp));
        ocoStop.setText(String.format("%.2f", stp));
        ocoLimit.setText(String.format("%.2f",stp*settings.getOcoLimit()));
    }

    //Task for handling Menu Item requests
 
    // Save Trade Menu Item
    //Creates and save to file a list of trade 
    @FXML
    private void saveTradesMenuHandler(ActionEvent event) throws FileNotFoundException, IOException {
        
        FileChooser filechooser=new FileChooser();
        filechooser.setTitle("Save Trades");
        filechooser.setInitialDirectory(new File(System.getProperty("user.home")));
        filechooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Trades", "*.*"),
                new FileChooser.ExtensionFilter("txt", "*.txt")
        );
        File file=filechooser.showSaveDialog(stage);
        if(file!=null){
            FileWriter fw=new FileWriter(file);
            BufferedWriter bfw=new BufferedWriter(fw);
            
            for(Trade t:list){
                String str=FileUtilities.getTradeString(t)+"\n";
                bfw.write(str);
            }
            bfw.flush();
            fw.flush();
            bfw.close();
            fw.close();
        }
        
        stage.close();
    }
    
    //Load Trades Menu Item
    //Opens trade file and get the trades.
    @FXML
    private void loadTradesHandle(ActionEvent event) throws FileNotFoundException, IOException {
        FileChooser filechooser=new FileChooser();
        filechooser.setTitle("Save Trades");
        filechooser.setInitialDirectory(new File(System.getProperty("user.home")));
        filechooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Trades", "*.*"),
                new FileChooser.ExtensionFilter("txt", "*.txt")
        );
        File file=filechooser.showOpenDialog(stage);
        if(file!=null){
            FileReader fr=new FileReader(file);
            BufferedReader bfr=new BufferedReader(fr);
            String line;
            
            //Add trade to list and table
            while(((line = bfr.readLine()) != null)){
                Trade t=FileUtilities.getTrade(line);
                list.add(t);
            }
            bfr.close();
            fr.close();
        }
    }
    
    //Exit Menu Item task
    @FXML
    private void exitHandler(ActionEvent event) {
        Platform.exit();
        System.exit(0);
    }
    
    
    
    //LoadSetting menu Item
    //Opens a window to get the default risk and OCO Bracket Limit on opening
    //the Calculator App
    @FXML
    private void settingsHandler(ActionEvent event) throws IOException {
        FXMLLoader loader=new FXMLLoader(getClass().getResource("/views/SettingsView.fxml"));
        Parent root=loader.load();
        SettingsViewController controller=loader.getController();
        Scene scene=new Scene(root);
        stage.setTitle("Settings Window");
        stage.setScene(scene);
        stage.showAndWait();
        settings=controller.getSettings();
        riskTxt.setText(String.valueOf(settings.getRisk()));
        risk=settings.getRisk();
        ocoLimit.setText(String.valueOf(stopLoss*settings.getOcoLimit()));
        
        
    }
//    private void symbolColEditHandler(CellEditEvent<Trade, String> event) {
//        Trade t=(Trade)event.getTableView().getItems().get(event.getTablePosition().getRow());
//        t.setSymbol(event.getNewValue());
//        
//    }
//
//    private void shareEditHandler(CellEditEvent<Trade, Integer> event) {
//        Trade t=(Trade)event.getTableView().getItems().get(event.getTablePosition().getRow());
//        t.setShares(event.getNewValue());
//    }
//
//    private void entryEditHandler(CellEditEvent<Trade, Double> event) {
//        Trade t=(Trade)event.getTableView().getItems().get(event.getTablePosition().getRow());
//        t.setEntry(event.getNewValue());
//        
//    }
//
//    private void stopEditHandler(CellEditEvent<Trade, Double> event) {
//        Trade t=(Trade)event.getTableView().getItems().get(event.getTablePosition().getRow());
//        t.setStop(event.getNewValue());
//        
//    }

    
    
    //Actualize variables of trade
    private void getTradeData(String trade){
        
        String[] items=trade.split("\\s+");
        symbol=getSymbol(items);
        entry=getEntry(items);
        stop=getStop(items);
    
    }
    
    //Get the symbol name and convert it to upper case
    private String getSymbol(String[] items){
        String txt="";
        for(String s:items){
            if(s.length()>0){
                txt=s.toUpperCase();
                break;
            }
        }
        return txt;
    }
    
    //Get Entry Price
    private Double getEntry(String[] items){
        double num=0;
        for(String s:items){
            if(isNumeric(s)){
                num=Double.parseDouble(s);
                break;
            }
        }
        return num;
    }
    
    //Get Stop Price
    private Double getStop(String[] items){
        double num=0;
        for(String s:items){
            if(isNumeric(s)){
                num=Double.parseDouble(s);
            }
        }
        return num;
    }
    
    //Check if string is numeric value
    private boolean isNumeric(String item){
        try{
        Double num=Double.parseDouble(item);
        return true;
        } catch(NumberFormatException e){
            return false;
        }
    }
}
