package christmasRaces.core;

import christmasRaces.core.interfaces.Controller;
import christmasRaces.entities.cars.Car;
import christmasRaces.entities.cars.MuscleCar;
import christmasRaces.entities.cars.SportsCar;
import christmasRaces.entities.drivers.Driver;
import christmasRaces.entities.drivers.DriverImpl;
import christmasRaces.entities.races.Race;
import christmasRaces.entities.races.RaceImpl;
import christmasRaces.repositories.interfaces.Repository;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static christmasRaces.common.ExceptionMessages.*;
import static christmasRaces.common.OutputMessages.*;

public class ControllerImpl implements Controller {

    private Repository<Driver> driverRepository;
    private Repository<Car> carRepository;
    private Repository<Race> raceRepository;


    public ControllerImpl(Repository<Driver> driverRepository, Repository<Car> carRepository, Repository<Race> raceRepository) {
        this.driverRepository = driverRepository;
        this.carRepository = carRepository;
        this.raceRepository = raceRepository;
    }

    @Override
    public String createDriver(String driver) {

        Driver searchedDriver = this.driverRepository.getByName(driver);

        if (searchedDriver != null) throw new IllegalArgumentException(String.format(DRIVER_EXISTS, driver));

        this.driverRepository.add(new DriverImpl(driver));

        return String.format(DRIVER_CREATED, driver);
    }

    @Override
    public String createCar(String type, String model, int horsePower) {

        Car searchedCar = this.carRepository.getByName(model);

        if (searchedCar != null) throw new IllegalArgumentException(String.format(CAR_EXISTS, model));

        Car car = null;

        if (type.equals("Muscle")) car = new MuscleCar(model, horsePower);
        if (type.equals("Sports")) car = new SportsCar(model, horsePower);

        if (car != null) this.carRepository.add(car);

        return String.format(CAR_CREATED, type + "Car", model);
    }

    @Override
    public String addCarToDriver(String driverName, String carModel) {
        Car car = this.carRepository.getByName(carModel);
        Driver driver = this.driverRepository.getByName(driverName);

        if (car == null) throw new IllegalArgumentException(String.format(CAR_NOT_FOUND, carModel));
        if (driver == null) throw new IllegalArgumentException(String.format(DRIVER_NOT_FOUND, driverName));

        driver.addCar(car);

        return String.format(CAR_ADDED, driverName, carModel);
    }

    @Override
    public String addDriverToRace(String raceName, String driverName) {

        Race race = this.raceRepository.getByName(raceName);
        Driver driver = this.driverRepository.getByName(driverName);

        if (race == null) throw new IllegalArgumentException(String.format(RACE_NOT_FOUND, raceName));
        if (driver == null) throw new IllegalArgumentException(String.format(DRIVER_NOT_FOUND, driverName));

        race.addDriver(driver);

        return String.format(DRIVER_ADDED, driverName, raceName);
    }

    @Override
    public String startRace(String raceName) {

        Race race = this.raceRepository.getByName(raceName);

        if (race == null) throw new IllegalArgumentException(String.format(RACE_NOT_FOUND, raceName));

        List<Driver> drivers = race.getDrivers()
                .stream()
                .sorted(Comparator.comparing(driver -> driver.getCar().calculateRacePoints(race.getLaps())))
                .collect(Collectors.toList());

        Collections.reverse(drivers);

        if (drivers.size() < 3) throw new IllegalArgumentException(String.format(RACE_INVALID, raceName, 3));

        return String.format(DRIVER_FIRST_POSITION + System.lineSeparator() +
                        DRIVER_SECOND_POSITION + System.lineSeparator() +
                        DRIVER_THIRD_POSITION,
                drivers.get(0).getName(), raceName,
                drivers.get(1).getName(), raceName,
                drivers.get(2).getName(), raceName);
    }

    @Override
    public String createRace(String name, int laps) {

        Race race = this.raceRepository.getByName(name);

        if (race != null) throw new IllegalArgumentException(String.format(RACE_EXISTS, name));

        this.raceRepository.add(new RaceImpl(name, laps));

        return String.format(RACE_CREATED, name);
    }
}
