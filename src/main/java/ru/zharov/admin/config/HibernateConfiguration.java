package ru.zharov.admin.config;

import org.springframework.boot.hibernate.autoconfigure.HibernatePropertiesCustomizer;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

//@Configuration
public class HibernateConfiguration implements HibernatePropertiesCustomizer {
    @Override
    public void customize(Map<String, Object> hibernateProperties) {
        hibernateProperties.put("hibernate.dialect", "ru.zharov.admin.fts.CustomPostgreSQLDialect");
    }
}