package restaurant.core;

import restaurant.common.enums.BeveragesType;
import restaurant.common.enums.HealthyFoodType;
import restaurant.common.enums.TableType;
import restaurant.core.interfaces.Controller;
import restaurant.entities.drinks.Fresh;
import restaurant.entities.drinks.Smoothie;
import restaurant.entities.healthyFoods.Food;
import restaurant.entities.healthyFoods.Salad;
import restaurant.entities.healthyFoods.VeganBiscuits;
import restaurant.entities.healthyFoods.interfaces.HealthyFood;
import restaurant.entities.drinks.interfaces.Beverages;
import restaurant.entities.tables.InGarden;
import restaurant.entities.tables.Indoors;
import restaurant.entities.tables.interfaces.Table;
import restaurant.repositories.BeverageRepositoryImpl;
import restaurant.repositories.HealthFoodRepositoryImpl;
import restaurant.repositories.TableRepositoryImpl;
import restaurant.repositories.interfaces.*;

import static restaurant.common.ExceptionMessages.*;
import static restaurant.common.OutputMessages.*;

public class ControllerImpl implements Controller {

    HealthFoodRepository<HealthyFood> healthFoodRepository;
    BeverageRepository<Beverages> beverageRepository;
    TableRepository<Table> tableRepository;

    private double totalMoney;


    public ControllerImpl(HealthFoodRepository<HealthyFood> healthFoodRepository, BeverageRepository<Beverages> beverageRepository, TableRepository<Table> tableRepository) {
        this.healthFoodRepository = healthFoodRepository;
        this.beverageRepository = beverageRepository;
        this.tableRepository = tableRepository;
        totalMoney = 0;
    }

    @Override
    public String addHealthyFood(String type, double price, String name) {
        HealthyFood food = null;

        if (type.equals(HealthyFoodType.Salad.name())) food = new Salad(name, price);
        if (type.equals(HealthyFoodType.VeganBiscuits.name())) food = new VeganBiscuits(name, price);

        if (this.healthFoodRepository.foodByName(name) != null)
            throw new IllegalArgumentException(String.format(FOOD_EXIST, name));

        this.healthFoodRepository.add(food);

        return String.format(FOOD_ADDED, name);
    }

    @Override
    public String addBeverage(String type, int counter, String brand, String name) {

        Beverages beverages = null;
        if (type.equals(BeveragesType.Fresh.name())) beverages = new Fresh(name, counter, brand);
        if (type.equals(BeveragesType.Smoothie.name())) beverages = new Smoothie(name, counter, brand);

        if (this.beverageRepository.beverageByName(name, brand) != null)
            throw new IllegalArgumentException(String.format(BEVERAGE_EXIST, name));

        this.beverageRepository.add(beverages);

        return String.format(BEVERAGE_ADDED, type, brand);
    }

    @Override
    public String addTable(String type, int tableNumber, int capacity) {

        Table table = null;

        if (type.equals(TableType.Indoors.name())) {
            table = new Indoors(tableNumber, capacity);
        }

        if (type.equals(TableType.InGarden.name())) {
            table = new InGarden(tableNumber, capacity);
        }

        if (this.tableRepository.byNumber(tableNumber) != null)
            throw new IllegalArgumentException(String.format(TABLE_IS_ALREADY_ADDED, tableNumber));

        this.tableRepository.add(table);

        return String.format(TABLE_ADDED, tableNumber);
    }

    @Override
    public String reserve(int numberOfPeople) {

        Table suitableTable = this.tableRepository.getAllEntities().stream().filter(table -> !table.isReservedTable() && table.getSize() >= numberOfPeople).findFirst().orElse(null);

        if (suitableTable == null) return String.format(RESERVATION_NOT_POSSIBLE, numberOfPeople);

        suitableTable.reserve(numberOfPeople);

        return String.format(TABLE_RESERVED, suitableTable.getTableNumber(), numberOfPeople);
    }

    @Override
    public String orderHealthyFood(int tableNumber, String healthyFoodName) {

        Table table = this.tableRepository.byNumber(tableNumber);

        if (table == null) return String.format(WRONG_TABLE_NUMBER, tableNumber);

        HealthyFood food = this.healthFoodRepository.foodByName(healthyFoodName);

        if (food == null) return String.format(NONE_EXISTENT_FOOD, healthyFoodName);

        table.orderHealthy(food);

        return String.format(FOOD_ORDER_SUCCESSFUL, healthyFoodName, tableNumber);
    }

    @Override
    public String orderBeverage(int tableNumber, String name, String brand) {
        Table table = this.tableRepository.byNumber(tableNumber);

        if (table == null) return String.format(WRONG_TABLE_NUMBER, tableNumber);

        Beverages beverages = this.beverageRepository.beverageByName(name, brand);

        if (beverages == null) return String.format(NON_EXISTENT_DRINK, name, brand);

        table.orderBeverages(beverages);

        return String.format(BEVERAGE_ORDER_SUCCESSFUL, name, tableNumber);
    }

    @Override
    public String closedBill(int tableNumber) {
        Table table = this.tableRepository.byNumber(tableNumber);

        double bill = table.bill();
        this.totalMoney += bill;

        table.clear();

        return String.format(BILL, tableNumber, bill);
    }


    @Override
    public String totalMoney() {
        return String.format(TOTAL_MONEY, this.totalMoney);
    }
}
