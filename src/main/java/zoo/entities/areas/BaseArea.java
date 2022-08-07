package zoo.entities.areas;

import zoo.entities.animals.Animal;
import zoo.entities.foods.Food;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

public abstract class BaseArea implements Area {
    private String name;
    private int capacity;
    private Collection<Animal> animals;
    private Collection<Food> foods;

    public BaseArea(String name, int capacity) {
        this.name = name;
        this.capacity = capacity;
        this.animals = new ArrayList<>();
        this.foods = new ArrayList<>();
    }


    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Collection<Animal> getAnimals() {
        return this.animals;
    }

    @Override
    public Collection<Food> getFoods() {
        return this.foods;
    }

    @Override
    public int sumCalories() {
        return this.foods.stream().mapToInt(Food::getCalories).sum();
    }

    @Override
    public void addAnimal(Animal animal) {
        if (this.animals.size() == this.capacity) throw new IllegalStateException("Not enough capacity.");

        this.animals.add(animal);
    }

    @Override
    public void removeAnimal(Animal animal) {
        this.animals.remove(animal);
    }

    @Override
    public void addFood(Food food) {
        this.foods.add(food);
    }

    @Override
    public void feed() {
        this.animals.forEach(Animal::eat);
    }

    @Override
    public String getInfo() {

        StringBuilder output = new StringBuilder();


        output.append(this.name).append(" (").append(this.getClass().getSimpleName()).append("):").append(System.lineSeparator());
        output.append("Animals: ");
        output.append(this.animals.size() == 0 ? "none" : this.animals.stream().map(Animal::getName).collect(Collectors.joining(" "))).append(System.lineSeparator());
        output.append("Foods: ").append(this.foods.size()).append(System.lineSeparator());
        output.append("Calories: ").append(sumCalories());

        return output.toString().trim();
    }
}
