/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package californication.view;

import californication.socket.Client;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
//import javafx.scene.control.Label;
import californication.socket.Server;
import java.awt.event.KeyEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.effect.Effect;
import javafx.scene.effect.MotionBlur;
import javax.swing.JOptionPane;


/**
 * FXML Controller class
 *
 * @author Roman
 */
public class RunCalifornicationController implements Initializable {
    
    public Server server;
    public Client client;
    
    public String sendtext;
    
    @FXML
    private Button serverClick;
    @FXML
    private Button ClientClick;
    @FXML
    private TextField ipTextEdit;
    @FXML
    private TextField userTextEdit;
    @FXML
    private TextArea printTextEdit;
    @FXML
    private Button goMass;
    
//    printTextEdit
    
    /**
     * Initializes the controller class.
     */
    @FXML
    private void serverStart() {
        
        Thread myThready = new Thread(new Runnable()
		{
                          
                    public void run() //Этот метод будет выполняться в побочном потоке
			{
                                    //System.out.println("Server Sterting ...");
                                    server = new Server();                               
			}
                }); 
        
        //System.out.println(serverClick.getText());
        if(new String("Start Server").equals(serverClick.getText())){
             
             //serverClick.setText("Stop Server ...");
             myThready.start();
             serverClick.setDisable(true);   
        }else{ 
            serverClick.setText("Start Server");
            myThready.stop();
            //myThready.suspend();
        }
           
    }
    
    
    
    @FXML
    private void clientStart() throws InterruptedException {
        String ipText;
        String userText;
        
        
                
        ipText = ipTextEdit.getText();
        userText = userTextEdit.getText();
        
        if((!ipText.equals("")) && (!userText.equals(""))){
            
        ClientClick.setDisable(true);
        ipTextEdit.setEditable(true);
        userTextEdit.setEditable(true);
        
        client = new Client(ipText, userText);
        client.getIPuser();
        
        
        Thread myThready = new Thread(new Runnable()
            {

                public void run() //Этот метод будет выполняться в побочном потоке
                    {
                                System.out.println("Client Sterting ...");
                                client.ClientStarting();                               
                    }
            }); 
        myThready.start();
        }else{
            
            JOptionPane.showMessageDialog(null, "Введите IP или ник", "InfoBox: " + " ERROR", JOptionPane.INFORMATION_MESSAGE);
            
            
            
        }
        
        
    }
    
    @FXML
    private void SendMassage(){
       sendtext = printTextEdit.getText();
       if(!sendtext.equals("")){       
            client.printStr(sendtext);
            printTextEdit.setText("");
       }
    }
    
   
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
    }    
    
}
