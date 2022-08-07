package zoo.core;

import zoo.entities.animals.Animal;
import zoo.entities.animals.AquaticAnimal;
import zoo.entities.animals.TerrestrialAnimal;
import zoo.entities.areas.Area;
import zoo.entities.areas.LandArea;
import zoo.entities.areas.WaterArea;
import zoo.entities.foods.Food;
import zoo.entities.foods.Meat;
import zoo.entities.foods.Vegetable;
import zoo.repositories.FoodRepository;
import zoo.repositories.FoodRepositoryImpl;

import java.util.ArrayList;
import java.util.Collection;

import static zoo.common.ConstantMessages.KILOGRAMS_AREA;

public class ControllerImpl implements Controller {

    private final FoodRepository foodRepository;
    private final Collection<Area> areas;

    public ControllerImpl() {
        foodRepository = new FoodRepositoryImpl();
        areas = new ArrayList<>();
    }

    @Override
    public String addArea(String areaType, String areaName) {
        if (!areaType.equals("WaterArea") && !areaType.equals("LandArea")) {
            throw new NullPointerException("Invalid area type.");
        }
        Area area;
        if (areaType.equals("WaterArea")) {
            area = new WaterArea(areaName);
        } else {
            area = new LandArea(areaName);
        }
        this.areas.add(area);

        return String.format("Successfully added %s.", areaType);
    }

    @Override
    public String buyFood(String foodType) {
        if (!foodType.equals("Vegetable") && !foodType.equals("Meat")) {
            throw new IllegalArgumentException("Invalid food type.");
        }

        Food food;

        if (foodType.equals("Vegetable")) {
            food = new Vegetable();
        } else {
            food = new Meat();
        }

        foodRepository.add(food);

        return String.format("Successfully added %s.", foodType);
    }

    @Override
    public String foodForArea(String areaName, String foodType) {

        Food food = foodRepository.findByType(foodType);
        if (food == null) throw new IllegalArgumentException(String.format("There isn't a food of type %s.", foodType));

        foodRepository.remove(food);

        Area area = areas.stream().filter(a -> a.getName().equals(areaName)).findFirst().orElse(null);

        if (area == null) {
            return null;
        }

        area.addFood(food);
        return String.format("Successfully added %s to %s.", foodType, areaName);
    }

    @Override
    public String addAnimal(String areaName, String animalType, String animalName, String kind, double price) {
        if (!animalType.equals("AquaticAnimal") && !animalType.equals("TerrestrialAnimal")) {
            throw new IllegalArgumentException("Invalid animal type.");
        }

        Area area = areas.stream().filter(a -> a.getName().equals(areaName)).findFirst().orElse(null);

        if (area == null) return null;

        if (!(area instanceof LandArea) && !(area instanceof WaterArea)) {
            return "The external living environment is not suitable.";
        }

        if (area instanceof LandArea) {
            if (area.getAnimals().size() == LandArea.CAPACITY) {
                return "Not enough capacity.";
            }
        }
        if (area instanceof WaterArea) {
            if (area.getAnimals().size() == WaterArea.CAPACITY) {
                return "Not enough capacity.";
            }
        }

        Animal animal = getAnimal(animalType, animalName, kind, price);

        area.addAnimal(animal);
        return String.format("Successfully added %s to %s.", animalType, areaName);
    }

    private Animal getAnimal(String animalType, String animalName, String kind, double price) {
        if (animalType.equals("TerrestrialAnimal")) return new TerrestrialAnimal(animalName, kind, price);
        if (animalType.equals("AquaticAnimal")) return new AquaticAnimal(animalName, kind, price);
        else return null;
    }

    @Override
    public String feedAnimal(String areaName) {
        Area area = areas.stream().filter(a -> a.getName().equals(areaName)).findFirst().orElse(null);

        if (area == null) return null;

        area.getAnimals().forEach(Animal::eat);

        return String.format("Animals fed: %d", area.getAnimals().size());
    }

    @Override
    public String calculateKg(String areaName) {
        Area area = areas.stream().filter(a -> a.getName().equals(areaName)).findFirst().orElse(null);

        if (area == null) return null;

        double totalKilogramsOfAllAnimals = area.getAnimals().stream().mapToDouble(Animal::getKg).sum();

        return String.format(KILOGRAMS_AREA, areaName, totalKilogramsOfAllAnimals);
    }


    @Override
    public String getStatistics() {
        StringBuilder statistics = new StringBuilder();
        areas.forEach(area -> statistics.append(area.getInfo()).append(System.lineSeparator()));
        return statistics.toString().trim();
    }

}
