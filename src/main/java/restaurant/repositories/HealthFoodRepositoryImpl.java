package restaurant.repositories;

import restaurant.entities.healthyFoods.interfaces.HealthyFood;
import restaurant.repositories.interfaces.HealthFoodRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class HealthFoodRepositoryImpl implements HealthFoodRepository<HealthyFood> {

    private Collection<HealthyFood> entities;

    public HealthFoodRepositoryImpl() {
        this.entities = new ArrayList<>();
    }

    @Override
    public Collection<HealthyFood> getAllEntities() {
        return Collections.unmodifiableCollection(this.entities);
    }

    @Override
    public void add(HealthyFood healthyFood) {
        this.entities.add(healthyFood);
    }

    @Override
    public HealthyFood foodByName(String name) {
        return this.entities.stream().filter(food -> food.getName().equals(name)).findFirst().orElse(null);
    }
}
