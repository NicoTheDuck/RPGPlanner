package gui.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class MainWindowController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    

    @FXML
    private void closeApplication(ActionEvent event) {
        Platform.exit();
    }
    
}
