package fairyShop.models;

public class Sleepy extends BaseHelper {
    private static final int INITIAL_ENERGY = 50;

    public Sleepy(String name) {
        super(name, INITIAL_ENERGY);
    }

    @Override
    public void work() {
        if (super.getEnergy() > 15) super.setEnergy(super.getEnergy() - 15);
        else super.setEnergy(0);
    }
}
