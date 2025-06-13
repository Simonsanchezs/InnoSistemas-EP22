package com.example.InnoSistemas;

import org.junit.jupiter.api.Test;

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest; 
import org.springframework.test.context.ActiveProfiles; 


@DataJpaTest 
@ActiveProfiles("test") 
class InnoSistemasApplicationTests {

    
    

    @Test
    void contextLoads() {
        
        
        System.out.println("Contexto de Spring cargado exitosamente con H2!");
        
        
        
    }

    
}