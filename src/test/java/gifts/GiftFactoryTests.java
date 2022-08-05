package gifts;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.text.DecimalFormatSymbols;
import java.util.Collection;
import java.util.List;

public class GiftFactoryTests {
    private static final int EMPTY_FACTORY_ELEMENTS = 0;
    private static final String EXISTING_GIFT_TYPE = "Special Gift";
    private static final String UNIQUE_GIFT_TYPE = "My Gift";
    private static final String REMOVED_GIFT_TYPE = "For Remove";
    private static final String CORRECT_GIFT_NAME = "Cool gift 5";
    private static final double EXISTING_GIFT_MAGIC = 2.4;
    private static final double UNIQUE_GIFT_MAGIC = 6.2;
    private static final double REMOVED_GIFT_MAGIC = 1.0;

    private GiftFactory giftFactory;
    private GiftFactory emptyGiftFactory;
    private GiftFactory giftFactoryForSearch;

    private Gift gift1;
    private Gift gift2;
    private Gift gift3;
    private Gift gift4;
    private Gift gift5;
    private Gift gift6;


    @Before
    public void setup() {
        this.giftFactory = new GiftFactory();
        this.emptyGiftFactory = new GiftFactory();
        this.giftFactoryForSearch = new GiftFactory();

        this.gift1 = new Gift("Cool gift 1", 123.123);
        this.gift2 = new Gift("Cool gift 2", 25.2);
        this.gift3 = new Gift("Cool gift 3", 25.3);
        this.gift4 = new Gift("Cool gift 4", 101.1);
        this.gift5 = new Gift("Cool gift 5", 101.2);
        this.gift6 = new Gift("Cool gift 6", 102.3);
    }

    @Test
    public void testFactoryIsSuccessfullyCreated() {
        Assert.assertEquals(EMPTY_FACTORY_ELEMENTS, this.emptyGiftFactory.getCount());
    }

    @Test
    public void testSuccessfullyAddedGift() {
        Gift gift = new Gift(UNIQUE_GIFT_TYPE, UNIQUE_GIFT_MAGIC);
        String status = giftFactory.createGift(gift);
        Assert.assertEquals(1, giftFactory.getCount());

        Collection<Gift> presents = giftFactory.getPresents();
        Assert.assertTrue(presents.contains(gift));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testExistingGiftMustReturnException() {
        giftFactory.createGift(new Gift(EXISTING_GIFT_TYPE, EXISTING_GIFT_MAGIC));
        giftFactory.createGift(new Gift(EXISTING_GIFT_TYPE, EXISTING_GIFT_MAGIC));
    }

    @Test(expected = NullPointerException.class)
    public void testRemovingEmptyGiftReturnException() {
        giftFactory.removeGift(" ");
    }

    @Test(expected = NullPointerException.class)
    public void testRemovingNullGiftReturnException() {
        giftFactory.removeGift(null);
    }

    @Test
    public void testSuccessfullyRemovedGift() {
        Gift giftToRemove = new Gift(REMOVED_GIFT_TYPE, REMOVED_GIFT_MAGIC);
        giftFactory.createGift(giftToRemove);

        Assert.assertTrue(giftFactory.removeGift(REMOVED_GIFT_TYPE));
    }

    @Test
    public void testSuccessfullyFoundGiftWithLeastMagic() {

        this.giftFactoryForSearch.createGift(gift1);
        this.giftFactoryForSearch.createGift(gift2);
        this.giftFactoryForSearch.createGift(gift3);

        Gift giftForSearch = giftFactoryForSearch.getPresentWithLeastMagic();

        Assert.assertEquals(gift2, giftForSearch);
    }

    @Test
    public void testSuccessfullyGetCorrectGift() {
        this.giftFactoryForSearch.createGift(gift4);
        this.giftFactoryForSearch.createGift(gift5);
        this.giftFactoryForSearch.createGift(gift6);

        Gift gift = this.giftFactoryForSearch.getPresent(CORRECT_GIFT_NAME);

        Assert.assertEquals(gift5, gift);
    }


}
