package gui;

import javafx.application.Application;
import javafx.application.HostServices;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

public class IHM extends Application {
    
    public ConfigurableApplicationContext context;

    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void init() throws Exception {
        ApplicationContextInitializer<GenericApplicationContext> initializer = ac -> {
            ac.registerBean(Application.class, () -> IHM.this);
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
        
        /*FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainWindow.fxml"));
        loader.setControllerFactory(context::getBean);
        
        rootNode = loader.load();
        mainWindowController = ((MainWindowController) loader.getController());
        mainWindowController.initializeUI(null);
        
        // Scene creation
        stage.setScene(new Scene(rootNode));
        stage.setTitle("Last Epoch Builder");
        stage.setMaximized(true);
        stage.show();
        
        // Initialisation du CharacterService
        serviceCharacter.preloadAllCharactersFromDatabase();
        Character character = serviceCharacter.loadCharacter(mainWindowController);
        stage.titleProperty().bind(mainWindowController.titleProperty());
        mainWindowController.updateCurrentCharacter(character);*/
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
    
}
