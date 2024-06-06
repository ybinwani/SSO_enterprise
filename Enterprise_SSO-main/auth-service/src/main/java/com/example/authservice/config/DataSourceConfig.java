//package com.example.authservice.config;
//
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//import org.springframework.orm.jpa.JpaTransactionManager;
//import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
//import org.springframework.transaction.PlatformTransactionManager;
//
//import javax.sql.DataSource;
//import org.springframework.boot.jdbc.DataSourceBuilder;
//import org.springframework.core.env.Environment;
//import org.springframework.beans.factory.annotation.Autowired;
//
//@Configuration
//@EnableJpaRepositories(
//    basePackages = {
//        "com.example.authservice.repository",
//        "com.example.employeeservice.repository",
//        "com.example.salaryservice.repository"
//    },
//    entityManagerFactoryRef = "entityManagerFactory",
//    transactionManagerRef = "transactionManager"
//)
//public class DataSourceConfig {
//
//    @Autowired
//    private Environment env;
//
//    @Bean(name = "dataSource")
//    public DataSource dataSource() {
//        return DataSourceBuilder.create()
//                .url(env.getProperty("spring.datasource.url"))
//                .username(env.getProperty("spring.datasource.username"))
//                .password(env.getProperty("spring.datasource.password"))
//                .driverClassName(env.getProperty("spring.datasource.driver-class-name"))
//                .build();
//    }
//
//    @Bean(name = "entityManagerFactory")
//    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
//            EntityManagerFactoryBuilder builder,
//            @Qualifier("dataSource") DataSource dataSource) {
//        return builder
//                .dataSource(dataSource)
//                .packages("com.example.authservice.model", "com.example.employeeservice.model", "com.example.salaryservice.model")
//                .persistenceUnit("default")
//                .build();
//    }
//
//    @Bean(name = "transactionManager")
//    public PlatformTransactionManager transactionManager(
//            @Qualifier("entityManagerFactory") LocalContainerEntityManagerFactoryBean entityManagerFactory) {
//        return new JpaTransactionManager(entityManagerFactory.getObject());
//    }
//}
