package com.sw.av.infrastructure.config;

import com.sw.av.Constants;
import io.swagger.v3.oas.models.OpenAPI;
import jakarta.servlet.ServletContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class OpenAPIConfigurator  {
    
    @Autowired
    private ServletContext context;
    
   
    @Bean
    public OpenAPI openAPI() {
        
        
        return new OpenAPI()
                .addServersItem(new io.swagger.v3.oas.models.servers.Server().url(context.getContextPath()))
                //.servers(serverList)
                .info(new io.swagger.v3.oas.models.info.Info()
                    .title(""+Constants.SERVICE_NAME+" API")
                    .version("0.1")
                    .description("This is a G's API server based on the preferred `OpenAPI 3.0`.<br/>"
                                + "RESTful APIs will allow you to integrate your front-end application, allowing you to easily interface to interact with the schema master tables.<br/>"
                                + "Some useful links:<br/><br/>"
                                + "    - [The source API definition for the service]("+context.getContextPath()+"/v3/api-docs.yaml)\n<br/>" 
                              //+ "    - [The project documentation]("+serviceDocumentation.getUrlDocumentation()+")\n<br/>"
                            )
                    .contact(new io.swagger.v3.oas.models.info.Contact()
                                    .name("Do you need more help, send an email to xx@xx.com")
                                    .email("xx@xx.com"))
                    
                    );
        
    }
    
}
