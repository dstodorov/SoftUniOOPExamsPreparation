package glacialExpedition.models.explorers;

public class NaturalExplorer extends BaseExplorer {

    private static final double INITIAL_ENERGY = 60;
    private static final double ENERGY_DECREASE_BY_SEARCH = 7;


    public NaturalExplorer(String name) {
        super(name, INITIAL_ENERGY);
    }

    @Override
    public void search() {
        setEnergy(getEnergy() - ENERGY_DECREASE_BY_SEARCH);
    }
}
