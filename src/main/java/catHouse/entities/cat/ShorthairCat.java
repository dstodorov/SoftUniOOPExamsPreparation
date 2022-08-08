package catHouse.entities.cat;

public class ShorthairCat extends BaseCat {

    private static final int INITIAL_KG = 7;
    private static final int KG_PER_EATING = 1;

    public ShorthairCat(String name, String breed, double price) {
        super(name, breed, price);
        setKilograms(INITIAL_KG);
    }

    @Override
    public void eating() {
        setKilograms(getKilograms() + KG_PER_EATING);
    }
}
