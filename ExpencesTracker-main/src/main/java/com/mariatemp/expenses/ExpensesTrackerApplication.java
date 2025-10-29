package com.mariatemp.expenses;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = "com.mariatemp.expenses.model")
@EnableJpaRepositories(basePackages = "com.mariatemp.expenses.repository")
public class ExpensesTrackerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExpensesTrackerApplication.class, args);
    }
}
