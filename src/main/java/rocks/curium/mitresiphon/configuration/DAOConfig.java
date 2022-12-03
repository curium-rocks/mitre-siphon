package rocks.curium.mitresiphon.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import rocks.curium.mitresiphon.dao.CVEDAOImpl;
import rocks.curium.mitresiphon.dao.ResourceStatDAOImpl;
import rocks.curium.mitresiphon.dao.interfaces.CVEDAO;
import rocks.curium.mitresiphon.dao.interfaces.ResourceStatDAO;

@Configuration
public class DAOConfig {

    @Bean
    ResourceStatDAO getResourceStateDAO(){
        return new ResourceStatDAOImpl();
    }

    @Bean
    CVEDAO getCVEDAO(){
        return new CVEDAOImpl();
    }
}
