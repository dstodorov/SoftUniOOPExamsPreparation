package christmasRaces.entities.drivers;

import christmasRaces.entities.cars.Car;

import static christmasRaces.common.ExceptionMessages.CAR_INVALID;
import static christmasRaces.common.ExceptionMessages.INVALID_NAME;

public class DriverImpl implements Driver {

    private static final int MIN_LENGTH_OF_NAME = 5;

    private String name;
    private Car car;
    private int numberOfWins;

    private boolean canParticipate;

    public DriverImpl(String name) {
        setName(name);
        this.canParticipate = false;
        this.numberOfWins = 0;
    }

    private void setName(String name) {

        if (name == null || name.trim().isEmpty() || name.length() < 5)
            throw new IllegalArgumentException(String.format(INVALID_NAME, name, MIN_LENGTH_OF_NAME));

        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Car getCar() {
        return this.car;
    }

    @Override
    public int getNumberOfWins() {
        return this.numberOfWins;
    }

    @Override
    public void addCar(Car car) {
        if (car == null) throw new IllegalArgumentException(CAR_INVALID);

        this.car = car;
        this.canParticipate = true;
    }

    @Override
    public void winRace() {
        this.numberOfWins++;
    }

    @Override
    public boolean getCanParticipate() {
        return this.canParticipate;
    }
}
