package restaurant.entities.healthyFoods;

public class VeganBiscuits extends Food {
    private static final double INITIAL_PORTION_SIZE = 205.00;

    public VeganBiscuits(String name, double price) {
        super(name, INITIAL_PORTION_SIZE, price);
    }
}
