package gui;

import javafx.application.Application;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@ComponentScan(basePackages = { "gui", "db", "model" })
@EnableJpaRepositories("db")
@EntityScan("db")
@EnableTransactionManagement
public class RPGPlanner {

    public static void main(String[] args) {
        Application.launch(IHM.class, args);
    }
    
}
