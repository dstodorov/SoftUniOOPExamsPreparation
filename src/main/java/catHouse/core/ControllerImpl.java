package catHouse.core;

import catHouse.entities.cat.Cat;
import catHouse.entities.cat.LonghairCat;
import catHouse.entities.cat.ShorthairCat;
import catHouse.entities.houses.House;
import catHouse.entities.houses.LongHouse;
import catHouse.entities.houses.ShortHouse;
import catHouse.entities.toys.Ball;
import catHouse.entities.toys.Mouse;
import catHouse.entities.toys.Toy;
import catHouse.repositories.ToyRepository;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static catHouse.common.ConstantMessages.*;
import static catHouse.common.ExceptionMessages.*;

public class ControllerImpl implements Controller {

    private ToyRepository toys;
    private Map<String, House> houses;

    public ControllerImpl() {
        toys = new ToyRepository();
        houses = new LinkedHashMap<>();
    }

    @Override
    public String addHouse(String type, String name) {

        if (!type.equals("ShortHouse") && !type.equals("LongHouse")) throw new NullPointerException(INVALID_HOUSE_TYPE);

        if (type.equals("ShortHouse")) this.houses.put(name, new ShortHouse(name));
        if (type.equals("LongHouse")) this.houses.put(name, new LongHouse(name));

        return String.format(SUCCESSFULLY_ADDED_HOUSE_TYPE, type);
    }

    @Override
    public String buyToy(String type) {

        if (!type.equals("Ball") && !type.equals("Mouse")) throw new IllegalArgumentException(INVALID_TOY_TYPE);

        if (type.equals("Ball")) this.toys.buyToy(new Ball());
        if (type.equals("Mouse")) this.toys.buyToy(new Mouse());

        return String.format(SUCCESSFULLY_ADDED_TOY_TYPE, type);
    }

    @Override
    public String toyForHouse(String houseName, String toyType) {
        Toy toy = toys.findFirst(toyType);

        if (toy == null) throw new IllegalArgumentException(String.format(NO_TOY_FOUND, toyType));

        this.houses.get(houseName).buyToy(toy);
        this.toys.removeToy(toy);

        return String.format(SUCCESSFULLY_ADDED_TOY_IN_HOUSE, toyType, houseName);
    }

    @Override
    public String addCat(String houseName, String catType, String catName, String catBreed, double price) {

        if (!catType.equals("ShorthairCat") && !catType.equals("LonghairCat"))
            throw new IllegalArgumentException(INVALID_CAT_TYPE);

        House house = this.houses.get(houseName);
        Cat cat = null;
        if (catType.equals("ShorthairCat")) {
            cat = new ShorthairCat(catName, catBreed, price);
        }
        if (catType.equals("LonghairCat")) {
            cat = new LonghairCat(catName, catBreed, price);
        }

        if (catType.equals("ShorthairCat") && !house.getClass().getSimpleName().equals("ShortHouse")) {
            return UNSUITABLE_HOUSE;
        }
        if (catType.equals("LonghairCat") && !house.getClass().getSimpleName().equals("LongHouse")) {
            return UNSUITABLE_HOUSE;
        }

        house.addCat(cat);

        return String.format(SUCCESSFULLY_ADDED_CAT_IN_HOUSE, catType, houseName);
    }

    @Override
    public String feedingCat(String houseName) {
        House house = this.houses.get(houseName);

        house.feeding();

        return String.format(FEEDING_CAT, house.getCats().size());
    }

    @Override
    public String sumOfAll(String houseName) {
        House house = this.houses.get(houseName);

        Collection<Toy> toysInHouse = house.getToys();
        Collection<Cat> catsInHouse = house.getCats();

        double totalToysAmount = toysInHouse.stream().mapToDouble(Toy::getPrice).sum();
        double totalCatsAmount = catsInHouse.stream().mapToDouble(Cat::getPrice).sum();

        return String.format(VALUE_HOUSE, houseName, totalToysAmount + totalCatsAmount);
    }

    @Override
    public String getStatistics() {

        StringBuilder output = new StringBuilder();

        this.houses.forEach((houseName, house) -> {
            output.append(houseName).append(" ").append(house.getClass().getSimpleName()).append(":").append(System.lineSeparator());
            output.append("Cats: ").append(house.getCats().size() == 0 ? "none" : house.getCats().stream().map(Cat::getName).collect(Collectors.joining(" "))).append(System.lineSeparator());
            output.append("Toys: ").append(house.getToys().size()).append(" Softness: ").append(house.getToys().stream().mapToInt(Toy::getSoftness).sum()).append(System.lineSeparator());
        });

        return output.toString().trim();
    }
}
