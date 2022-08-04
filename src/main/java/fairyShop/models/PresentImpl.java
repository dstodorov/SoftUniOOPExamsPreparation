package fairyShop.models;

import fairyShop.common.ExceptionMessages;

public class PresentImpl implements Present {

    private String name;
    private int energyRequired;

    public PresentImpl(String name, int energyRequired) {
        setName(name);
        setEnergyRequired(energyRequired);
    }

    private void setName(String name) {
        if (name.trim().isEmpty()) throw new NullPointerException(ExceptionMessages.PRESENT_NAME_NULL_OR_EMPTY);

        this.name = name;
    }

    private void setEnergyRequired(int energyRequired) {
        if (energyRequired < 0)
            throw new IllegalArgumentException(ExceptionMessages.PRESENT_ENERGY_LESS_THAN_ZERO);

        this.energyRequired = energyRequired;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public int getEnergyRequired() {
        return this.energyRequired;
    }

    @Override
    public boolean isDone() {
        return this.energyRequired == 0;
    }

    @Override
    public void getCrafted() {
        if (this.energyRequired >= 10) this.energyRequired -= 10;
        else this.energyRequired = 0;
    }
}
