package catHouse.entities.cat;

public class LonghairCat extends BaseCat{

    private static final int INITIAL_KG = 9;
    private static final int KG_PER_EATING = 3;

    public LonghairCat(String name, String breed, double price) {
        super(name, breed, price);
        setKilograms(INITIAL_KG);
    }

    @Override
    public void eating() {
        setKilograms(getKilograms() + KG_PER_EATING);
    }
}
