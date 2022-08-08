package catHouse.entities.houses;

import catHouse.entities.cat.Cat;
import catHouse.entities.toys.Toy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

import static catHouse.common.ConstantMessages.NOT_ENOUGH_CAPACITY_FOR_CAT;
import static catHouse.common.ExceptionMessages.HOUSE_NAME_CANNOT_BE_NULL_OR_EMPTY;

public abstract class BaseHouse implements House {

    private String name;
    private int capacity;
    private Collection<Toy> toys;
    private Collection<Cat> cats;

    public BaseHouse(String name, int capacity) {
        setName(name);
        this.capacity = capacity;
        toys = new ArrayList<>();
        cats = new ArrayList<>();
    }

    @Override
    public int sumSoftness() {
        return toys.stream().mapToInt(Toy::getSoftness).sum();
    }

    @Override
    public void addCat(Cat cat) {
        if (this.cats.size() >= capacity) throw new IllegalArgumentException(NOT_ENOUGH_CAPACITY_FOR_CAT);

        this.cats.add(cat);
    }

    @Override
    public void removeCat(Cat cat) {
        this.cats.remove(cat);
    }

    @Override
    public void buyToy(Toy toy) {
        this.toys.add(toy);
    }

    @Override
    public void feeding() {
        this.cats.forEach(Cat::eating);
    }

    @Override
    public String getStatistics() {

        String info = this.name + " " + this.getClass().getSimpleName() + ":" + System.lineSeparator() +
                "Cats: " + (this.cats.size() == 0 ? "none" : this.cats.stream().map(Cat::getName).collect(Collectors.joining(","))) + System.lineSeparator() +
                "Toys: " + this.toys.size() + " " + "Softness: " + sumSoftness();

        return info.trim();
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) throw new NullPointerException(HOUSE_NAME_CANNOT_BE_NULL_OR_EMPTY);

        this.name = name;
    }

    @Override
    public Collection<Cat> getCats() {
        return Collections.unmodifiableCollection(this.cats);
    }

    @Override
    public Collection<Toy> getToys() {
        return Collections.unmodifiableCollection(this.toys);
    }
}
