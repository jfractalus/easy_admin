package com.volia.eadmin.config;

import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import java.util.ArrayList;
import java.util.List;

import static com.volia.eadmin.config.Constant.PROPERTY_ROOT;

@Configuration
public class ApplicationConfig {

    @Bean
    public static PropertySourcesPlaceholderConfigurer properties() {
        PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer = new PropertySourcesPlaceholderConfigurer();
        YamlPropertiesFactoryBean yml = new YamlPropertiesFactoryBean();
        yml.setResources(getActiveResources());
        propertySourcesPlaceholderConfigurer.setProperties(yml.getObject());
        return propertySourcesPlaceholderConfigurer;
    }

    private static Resource[] getActiveResources(){
        List<ClassPathResource> result = new ArrayList<>();
        result.add(new ClassPathResource(PROPERTY_ROOT + "application.yml"));
        String activeProfile = System.getProperty("spring.profiles.active", "");
        ClassPathResource activeYml = new ClassPathResource(PROPERTY_ROOT + "application-" + activeProfile + ".yml");
        if (activeYml.exists()){
            result.add(activeYml);
        }
        return result.stream().toArray(ClassPathResource[]::new);
    }

}
