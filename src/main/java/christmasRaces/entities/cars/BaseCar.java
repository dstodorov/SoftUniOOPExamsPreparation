package christmasRaces.entities.cars;

import static christmasRaces.common.ExceptionMessages.INVALID_HORSE_POWER;
import static christmasRaces.common.ExceptionMessages.INVALID_MODEL;

public abstract class BaseCar implements Car {

    private static final int MIN_LENGTH_OF_MODEL = 4;

    private String model;
    private int horsePower;
    private double cubicCentimeters;

    public BaseCar(String model, int horsePower, double cubicCentimeters) {
        setModel(model);
        setHorsePower(horsePower);
        this.cubicCentimeters = cubicCentimeters;
    }

    private void setModel(String model) {
        if (model == null || model.trim().isEmpty() || model.length() < 4)
            throw new IllegalArgumentException(String.format(INVALID_MODEL, model, MIN_LENGTH_OF_MODEL));

        this.model = model;
    }

    private void setHorsePower(int horsePower) {
        if (this instanceof MuscleCar && (horsePower < 400 || horsePower > 600))
            throw new IllegalArgumentException(String.format(INVALID_HORSE_POWER, horsePower));
        if (this instanceof SportsCar && (horsePower < 250 || horsePower > 450))
            throw new IllegalArgumentException(String.format(INVALID_HORSE_POWER, horsePower));

        this.horsePower = horsePower;
    }

    @Override
    public String getModel() {
        return this.model;
    }

    @Override
    public int getHorsePower() {
        return this.horsePower;
    }

    @Override
    public double getCubicCentimeters() {
        return this.cubicCentimeters;
    }

    @Override
    public double calculateRacePoints(int laps) {
        return this.cubicCentimeters / this.horsePower * laps;
    }
}
