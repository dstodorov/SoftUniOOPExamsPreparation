package cats;

import org.junit.Assert;
import org.junit.Test;

public class HouseTests {

    private static final String VALID_STATISTICS_MESSAGE = "The cat Mitko, Gosho, Dobi is in the house Test!";

    @Test
    public void test_CreateHouse_WithValidParams_ShouldBeSuccessful() {
        House house = new House("Cats House", 10);
        Assert.assertEquals(0, house.getCount());
    }

    @Test(expected = NullPointerException.class)
    public void test_CreateHouse_WithEmptyName_ShouldReturnException() {
        House house = new House("", 10);
    }

    @Test(expected = NullPointerException.class)
    public void test_CreateHouse_WithNullName_ShouldReturnException() {
        House house = new House(null, 10);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_CreateHouse_WithNegativeCapacity_ShouldReturnException() {
        House house = new House("Test", -5);
    }

    @Test
    public void test_GetCapacity_ShouldReturn_ValidCapacity() {
        House house = new House("Cats House", 10);
        Assert.assertEquals(10, house.getCapacity());
    }

    @Test
    public void test_GetCount_ShouldReturn_ValidCountOfCats() {
        House house = new House("Cats House", 10);
        Assert.assertEquals(0, house.getCount());
        house.addCat(new Cat("Mitko"));
        Assert.assertEquals(1, house.getCount());
    }

    @Test
    public void test_GetName_ShouldReturn_ValidHouseName() {
        House house = new House("Cats House", 10);
        Assert.assertEquals("Cats House", house.getName());
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_AddCat_InFullHouse_ShouldReturnException() {
        House house = new House("Test", 1);
        house.addCat(new Cat("Mitko"));
        house.addCat(new Cat("Mitko"));
    }

    @Test
    public void test_removeCat_ShouldSuccessfully_RemoveCat() {
        House house = new House("Test", 1);
        house.addCat(new Cat("Mitko"));
        Assert.assertEquals(1, house.getCount());
        house.removeCat("Mitko");
        Assert.assertEquals(0, house.getCount());
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_removeNotExistingCat_ShouldReturnException() {
        House house = new House("Test", 1);
        house.addCat(new Cat("Mitko"));
        house.removeCat("Gosho");
    }

    @Test
    public void test_catForSale_onValidCat_ShouldReturnCat() {
        House house = new House("Test", 1);
        Cat cat = new Cat("Mitko");
        house.addCat(cat);

        Cat catForSale = house.catForSale("Mitko");
        Assert.assertEquals(cat, catForSale);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_catForSale_onVNullCat_ShouldReturnCat() {
        House house = new House("Test", 1);
        Cat cat = new Cat("Mitko");
        house.addCat(cat);


        Assert.assertTrue(cat.isHungry());
        Cat catForSale = house.catForSale("Gosho");
        Assert.assertEquals(cat, catForSale);
    }

    @Test
    public void test_statistics_ShouldReturn_ValidResult() {
        House house = new House("Test", 10);
        house.addCat(new Cat("Mitko"));
        house.addCat(new Cat("Gosho"));
        house.addCat(new Cat("Dobi"));

        String statistics = house.statistics();

        Assert.assertEquals(VALID_STATISTICS_MESSAGE, statistics);
    }

    @Test
    public void test_AddedCat_isHungry_ShouldReturnTrue() {
        House house = new House("Test", 10);
        house.addCat(new Cat("Mitko"));
    }
}
