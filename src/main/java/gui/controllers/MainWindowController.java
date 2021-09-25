package gui.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class MainWindowController extends AbstractController {

    // GUI methods
    @FXML
    private void closeApplication(ActionEvent event) {
        Platform.exit();
    }
    
}
