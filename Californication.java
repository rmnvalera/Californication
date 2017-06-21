/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package californication;

import californication.SetWallpaper.VerWindows;
import californication.socket.Server;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Roman
 */
public class Californication extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("view/RunCalifornication.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setTitle("Californication");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        VerWindows ver = new VerWindows();
        launch(args);
        ver.delateWallpaper();
        Server server = new Server("close");
    }
    
}
