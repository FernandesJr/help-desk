package com.gilfer.helpdesk.config;

import com.gilfer.helpdesk.services.DBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("test") //É executada quando no application.properties estiver setado test no profile
public class TestConfig {

    @Autowired
    private DBService dbService;

    @Bean
    public void instanciarDB(){
        this.dbService.instanciaDB();
    }
}
