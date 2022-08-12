package farmville;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class FarmvilleTests {
    private Farm validFarm;
    private Farm invalidFarm;
    private Farm fullFarm;
    private Animal validAnimal1;

    private static final String VALID_FARM_NAME = "Clark's farm";
    private static final String VALID_ANIMAL_TYPE = "Horse";
    private static final String INVALID_ANIMAL_TYPE = "Not existing horse";
    private static final int VALID_FARM_CAPACITY = 10;
    private static final int INVALID_FARM_CAPACITY = -1;
    private static final int EMPTY_FARM_COUNT = 0;

    @Before
    public void setup() {
        validFarm = new Farm(VALID_FARM_NAME, VALID_FARM_CAPACITY);
        validAnimal1 = new Animal(VALID_ANIMAL_TYPE, 100.00);
        fullFarm = new Farm(VALID_FARM_NAME, 0);
    }

    @Test
    public void farm_With_valid_parameters_should_be_successfully_created() {
        assertEquals(VALID_FARM_NAME, validFarm.getName());
        assertEquals(VALID_FARM_CAPACITY, validFarm.getCapacity());
        assertEquals(EMPTY_FARM_COUNT, validFarm.getCount());
    }

    @Test(expected = NullPointerException.class)
    public void farm_with_empty_name_should_throw_exception() {
        invalidFarm = new Farm("", VALID_FARM_CAPACITY);
    }

    @Test(expected = NullPointerException.class)
    public void farm_with_null_name_should_throw_exception() {
        invalidFarm = new Farm(null, VALID_FARM_CAPACITY);
    }

    @Test(expected = IllegalArgumentException.class)
    public void farm_with_negative_capacity_should_throw_exception() {
        invalidFarm = new Farm(VALID_FARM_NAME, INVALID_FARM_CAPACITY);
    }

    @Test(expected = IllegalArgumentException.class)
    public void add_animal_in_full_farm_should_throw_exception() {
        fullFarm.add(validAnimal1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void add_same_animal_in_farm_should_throw_exception() {
        validFarm.add(validAnimal1);
        validFarm.add(validAnimal1);
    }

    @Test
    public void add_animal_should_successfully_add_animal_in_the_farm() {
        assertEquals(EMPTY_FARM_COUNT, validFarm.getCount());
        validFarm.add(validAnimal1);
        assertEquals(1, validFarm.getCount());
    }

    @Test
    public void remove_animal_should_successfully_remove_animal_from_the_farm() {
        assertEquals(EMPTY_FARM_COUNT, validFarm.getCount());
        validFarm.add(validAnimal1);
        assertEquals(1, validFarm.getCount());
        assertTrue(validFarm.remove(VALID_ANIMAL_TYPE));
        assertEquals(EMPTY_FARM_COUNT, validFarm.getCount());
    }

    @Test
    public void remove_not_existing_animal_should_return_false() {
        assertEquals(EMPTY_FARM_COUNT, validFarm.getCount());
        validFarm.add(validAnimal1);
        assertEquals(1, validFarm.getCount());
        assertFalse(validFarm.remove(INVALID_ANIMAL_TYPE));
        assertEquals(1, validFarm.getCount());
    }


}
