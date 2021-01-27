package xyz.andrewkboyd.mitresiphon.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import xyz.andrewkboyd.mitresiphon.dao.ResourceStatDAOImpl;
import xyz.andrewkboyd.mitresiphon.dao.interfaces.ResourceStatDAO;

@Configuration
public class DAOConfig {

    @Bean
    ResourceStatDAO getResourceStateDAO(){
        return new ResourceStatDAOImpl();
    }
}
