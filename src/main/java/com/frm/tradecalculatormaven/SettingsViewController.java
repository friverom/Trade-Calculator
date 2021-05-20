/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.frm.tradecalculatormaven;

import com.frm.tradecalculatormaven.models.TradeSettings;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author frive
 */
public class SettingsViewController implements Initializable {
    
    TradeSettings settings;
    @FXML
    private TextField riskTxt;
    @FXML
    private TextField ocoTxt;
    @FXML
    private Button saveBtn;
    @FXML
    private Button exitBtn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       settings=new TradeSettings(100,2);
       riskTxt.requestFocus();
    }    

    public TradeSettings getSettings(){
        return settings;
    }
    @FXML
    private void saveBtnHandler(ActionEvent event) throws FileNotFoundException, IOException {
        
        settings.setRisk(Double.parseDouble(riskTxt.getText()));
        settings.setOcoLimit(Double.parseDouble(ocoTxt.getText()));
        
        File file=new File("TS.bin");
        FileOutputStream fos=new FileOutputStream(file);
        ObjectOutputStream oos=new ObjectOutputStream(fos);
        oos.writeObject(settings);
        oos.flush();
        fos.flush();
        oos.close();
        fos.close();
        
        Stage stage=(Stage)saveBtn.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void exitBtnHandler(ActionEvent event) {
        Stage stage=(Stage)exitBtn.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void riskEventHandler(KeyEvent event) {
        if(event.getCode().equals(KeyCode.ENTER)){
         settings.setRisk(Double.parseDouble(riskTxt.getText()));
         ocoTxt.requestFocus();
        }
    }

    @FXML
    private void ocoLimitEventHandler(KeyEvent event) throws FileNotFoundException, IOException {
        if(event.getCode().equals(KeyCode.ENTER)){
        settings.setOcoLimit(Double.parseDouble(ocoTxt.getText()));
    
        File file=new File("TS.bin");
        FileOutputStream fos=new FileOutputStream(file);
        ObjectOutputStream oos=new ObjectOutputStream(fos);
        oos.writeObject(settings);
        oos.flush();
        fos.flush();
        oos.close();
        fos.close();
        
        Node source=(Node)event.getSource();
        Stage stage=(Stage)source.getScene().getWindow();
        
        stage.close();
       }
    }
    
    
}
