package garage;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class GarageTests {

    private static final String MODEL = "Ferrari";
    private static final int MAX_SPEED = 100;
    private static final double PRICE = 200.00;
    Garage garage;
    Car car;

    @Before
    public void setup() {
        garage = new Garage();
        car = new Car(MODEL, MAX_SPEED, PRICE);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testGetCarsShouldReturnUnmodifiableList() {
        List<Car> cars = garage.getCars();
        cars.remove(0);
    }

    @Test
    public void testGetCountShouldReturnCorrectNumberOfCars() {
        assertEquals(0, garage.getCount());
        garage.addCar(car);
        assertEquals(1, garage.getCount());
    }

    @Test
    public void testFindAllCarsWithMaxSpeedAboveShouldReturnEmptyList() {
        garage.addCar(car);
        List<Car> cars = garage.findAllCarsWithMaxSpeedAbove(200);
        assertEquals(0, cars.size());
    }

    @Test
    public void testFindAllCarsWithMaxSpeedAboveShouldReturnCarsWithMoreThanMaxSpeedParameter() {
        garage.addCar(car);
        List<Car> cars = garage.findAllCarsWithMaxSpeedAbove(50);
        assertEquals(1, cars.size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddNullForCarShouldReturnException() {
        garage.addCar(null);
    }

    @Test
    public void getTheMostExpensiveCarShouldReturnMostExpensiveCar() {
        Car ford = new Car("Ford", 50, 2000.50);
        Car moskvich = new Car("Moskvich", 50, 2001.50);
        Car trabant = new Car("Trabant", 50, 859.10);

        garage.addCar(ford);
        garage.addCar(moskvich);
        garage.addCar(trabant);

        Car car = garage.getTheMostExpensiveCar();

        assertEquals(moskvich, car);

    }

    @Test
    public void findAllCarsByBrandShouldReturnAllCarsFromSpecificBrand() {

        Car ford = new Car("Ford", 50, 2000.50);
        Car moskvich = new Car("Moskvich", 50, 2001.50);
        Car trabant = new Car("Trabant", 50, 859.10);
        Car moskvich2 = new Car("Moskvich", 50, 520.99);

        garage.addCar(ford);
        garage.addCar(moskvich);
        garage.addCar(trabant);
        garage.addCar(moskvich2);

        List<Car> cars = garage.findAllCarsByBrand("Moskvich");

        assertEquals(2, cars.size());
        assertEquals(moskvich, cars.get(0));
        assertEquals(moskvich2, cars.get(1));
    }
}