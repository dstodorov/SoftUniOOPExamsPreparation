package restaurant.entities.tables;

import restaurant.entities.drinks.interfaces.Beverages;
import restaurant.entities.healthyFoods.Food;
import restaurant.entities.healthyFoods.interfaces.HealthyFood;
import restaurant.entities.tables.interfaces.Table;

import java.util.ArrayList;
import java.util.Collection;

import static restaurant.common.ExceptionMessages.INVALID_NUMBER_OF_PEOPLE;
import static restaurant.common.ExceptionMessages.INVALID_TABLE_SIZE;

public abstract class BaseTable implements Table {

    private Collection<HealthyFood> healthyFood;
    private Collection<Beverages> beverages;
    private int number;
    private int size;
    private int numberOfPeople;
    private double pricePerPerson;
    private boolean isReservedTable;
    private double allPeople;

    public BaseTable(int number, int size, double pricePerPerson) {
        this.healthyFood = new ArrayList<>();
        this.beverages = new ArrayList<>();
        this.number = number;
        setSize(size);
        this.pricePerPerson = pricePerPerson;
        this.isReservedTable = false;
    }

    public void setNumberOfPeople(int numberOfPeople) {
        if (numberOfPeople <= 0) throw new IllegalArgumentException(INVALID_NUMBER_OF_PEOPLE);
        this.numberOfPeople = numberOfPeople;
    }

    private void setSize(int size) {
        if (size < 0) throw new IllegalArgumentException(INVALID_TABLE_SIZE);

        this.size = size;
    }

    @Override
    public int getTableNumber() {
        return this.number;
    }

    @Override
    public int getSize() {
        return this.size;
    }

    @Override
    public int numberOfPeople() {
        return numberOfPeople;
    }

    @Override
    public double pricePerPerson() {
        return this.pricePerPerson;
    }

    @Override
    public boolean isReservedTable() {
        return isReservedTable;
    }

    @Override
    public double allPeople() {
        return this.allPeople;
    }

    @Override
    public void reserve(int numberOfPeople) {
        setNumberOfPeople(numberOfPeople);
        this.isReservedTable = true;
    }

    @Override
    public void orderHealthy(HealthyFood food) {
        this.healthyFood.add(food);
    }

    @Override
    public void orderBeverages(Beverages beverages) {
        this.beverages.add(beverages);
    }

    @Override
    public double bill() {
        double totalOrderPrice = this.healthyFood.stream()
                .mapToDouble(HealthyFood::getPrice)
                .sum() + this.beverages.stream()
                .mapToDouble(b -> b.getPrice() * b.getCounter())
                .sum();
        this.allPeople = this.numberOfPeople * this.pricePerPerson + totalOrderPrice;
        return this.numberOfPeople * this.pricePerPerson + totalOrderPrice;
    }

    @Override
    public void clear() {
        this.healthyFood.clear();
        this.beverages.clear();
        this.isReservedTable = false;
        this.allPeople = 0;
        this.numberOfPeople = 0;

    }

    @Override
    public String tableInformation() {

        String info = "Table - " + this.number + System.lineSeparator() +
                "Size - " + this.size + System.lineSeparator() +
                "Type - " + this.getClass().getSimpleName() + System.lineSeparator() +
                "All price - " + this.pricePerPerson;

        return info.trim();
    }
}
