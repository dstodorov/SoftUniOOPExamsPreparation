package christmasRaces.entities.races;

import christmasRaces.entities.drivers.Driver;

import java.util.ArrayList;
import java.util.Collection;

import static christmasRaces.common.ExceptionMessages.*;

public class RaceImpl implements Race {

    private static final int MIN_LENGTH_OF_NAME = 5;

    private String name;
    private int laps;
    private Collection<Driver> drivers;

    public RaceImpl(String name, int laps) {
        setName(name);
        setLaps(laps);
        this.drivers = new ArrayList<>();
    }

    private void setLaps(int laps) {
        if (laps < 0) throw new IllegalArgumentException(String.format(INVALID_NUMBER_OF_LAPS, 1));

        this.laps = laps;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty() || name.length() < 5)
            throw new IllegalArgumentException(String.format(INVALID_NAME, name, MIN_LENGTH_OF_NAME));

        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public int getLaps() {
        return this.laps;
    }

    @Override
    public Collection<Driver> getDrivers() {
        return this.drivers;
    }

    @Override
    public void addDriver(Driver driver) {
        if (driver == null) throw new IllegalArgumentException(DRIVER_INVALID);
        if (!driver.getCanParticipate())
            throw new IllegalArgumentException(String.format(DRIVER_NOT_PARTICIPATE, driver.getName()));
        if (this.drivers.contains(driver))
            throw new IllegalArgumentException(String.format(DRIVER_ALREADY_ADDED, driver.getName(), this.name));

        this.drivers.add(driver);
    }
}
