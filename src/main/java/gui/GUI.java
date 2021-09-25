package gui;

import gui.controllers.AbstractController;
import java.io.IOException;
import javafx.application.Application;
import javafx.application.HostServices;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

public class GUI extends Application {
    
    // Static fields
    public static final int BORDER_PANE_CENTER = 0;
    public static final int BORDER_PANE_TOP = 1;
    public static final int BORDER_PANE_BOTTOM = 2;
    public static final int BORDER_PANE_LEFT = 3;
    public static final int BORDER_PANE_RIGHT = 4;
    
    
    // Logical fields
    public ConfigurableApplicationContext context;

    
    // Main methods
    public static void main(String[] args) {
        launch(args);
    }
    
    
    // Public methods
    @Override
    public void init() throws Exception {
        ApplicationContextInitializer<GenericApplicationContext> initializer = ac -> {
            ac.registerBean(Application.class, () -> GUI.this);
            ac.registerBean(Parameters.class, this::getParameters);
            ac.registerBean(HostServices.class, this::getHostServices);
        };
        context = new SpringApplicationBuilder().sources(RPGPlanner.class).initializers(initializer)
            .run(getParameters().getRaw().toArray(new String[0]));
        
    }

    @Override
    public void start(Stage stage) throws Exception {
        context.publishEvent(new StageReadyEvent(stage));
        System.out.println("yolo world !");
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainWindow.fxml"));
        loader.setControllerFactory(context::getBean);
        
        // Scene creation
        stage.setScene(new Scene(loader.load()));
        stage.setTitle("RPG Planner");
        stage.setMaximized(true);
        
        /*mainWindowController = ((MainWindowController) loader.getController());
        mainWindowController.initializeUI(null);*/
        
        // Initialisation du CharacterService
        /*serviceCharacter.preloadAllCharactersFromDatabase();
        Character character = serviceCharacter.loadCharacter(mainWindowController);
        stage.titleProperty().bind(mainWindowController.titleProperty());
        mainWindowController.updateCurrentCharacter(character);*/
        
        // Display the window
        stage.show();
    }
    
    @Override
    public void stop() {
        context.close();
        Platform.exit();
    }
    
    private static class StageReadyEvent extends ApplicationEvent {
        public StageReadyEvent(Stage stage) {
            super(stage);
        }
    }
    
    
    // FXML Loaders
    public AbstractController loadFXML(Stage stage, String fxml, boolean prototype) {
        try {
            FXMLLoader loader = new FXMLLoader(GUI.class.getResource(fxml));
            loader.setControllerFactory(context::getBean);
            stage.setScene(new Scene(loader.load()));
            return loader.getController();
        } catch (IOException ex) {
            return null;
        }
    }
    
    protected AbstractController loadFXML(BorderPane parent, String fxml, int side, boolean prototype) {
        try {
            FXMLLoader loader = new FXMLLoader(GUI.class.getResource(fxml));
            loader.setControllerFactory(context::getBean);
            switch (side) {
                case BORDER_PANE_TOP:
                    parent.setTop(loader.load());
                    break;
                case BORDER_PANE_BOTTOM:
                    parent.setBottom(loader.load());
                    break;
                case BORDER_PANE_LEFT:
                    parent.setLeft(loader.load());
                    break;
                case BORDER_PANE_RIGHT:
                    parent.setRight(loader.load());
                    break;
                default:
                    parent.setCenter(loader.load());
                    break;
            }
            return loader.getController();
        } catch (IOException ex) {
            return null;
        }
    }
    
}
