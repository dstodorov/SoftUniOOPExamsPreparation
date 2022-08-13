package restaurant.repositories;

import restaurant.entities.drinks.interfaces.Beverages;
import restaurant.repositories.interfaces.BeverageRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class BeverageRepositoryImpl implements BeverageRepository<Beverages> {

    private Collection<Beverages> entities;

    public BeverageRepositoryImpl() {
        this.entities = new ArrayList<>();
    }

    @Override
    public Collection<Beverages> getAllEntities() {
        return Collections.unmodifiableCollection(this.entities);
    }

    @Override
    public void add(Beverages beverages) {
        this.entities.add(beverages);
    }

    @Override
    public Beverages beverageByName(String drinkName, String drinkBrand) {
        return this.entities.stream().filter(drink -> drink.getName().equals(drinkName) && drink.getBrand().equals(drinkBrand)).findFirst().orElse(null);
    }
}
