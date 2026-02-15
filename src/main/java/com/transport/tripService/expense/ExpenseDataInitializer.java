package com.transport.tripService.expense;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ExpenseDataInitializer implements CommandLineRunner {

    private final ExpenseCategoryRepository categoryRepository;

    public ExpenseDataInitializer(ExpenseCategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void run(String... args) {

        if (categoryRepository.count() == 0) {

            List<ExpenseCategory> defaultCategories = List.of(
                    new ExpenseCategory("FUEL", "Diesel or fuel expense"),
                    new ExpenseCategory("TOLL", "Toll charges"),
                    new ExpenseCategory("DRIVER_FOOD", "Driver food expenses"),
                    new ExpenseCategory("DRIVER_ALLOWANCE", "Driver allowance"),
                    new ExpenseCategory("MAINTENANCE", "Vehicle maintenance"),
                    new ExpenseCategory("TYRE_REPAIR", "Tyre repair or puncture"),
                    new ExpenseCategory("PARKING", "Parking charges"),
                    new ExpenseCategory("LOADING", "Loading charges"),
                    new ExpenseCategory("UNLOADING", "Unloading charges"),
                    new ExpenseCategory("OTHER", "Miscellaneous expense")
            );

            categoryRepository.saveAll(defaultCategories);

            System.out.println("✅ Default expense categories seeded.");
        }
    }
}