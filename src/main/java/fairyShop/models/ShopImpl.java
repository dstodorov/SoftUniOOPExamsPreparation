package fairyShop.models;

import java.util.Collection;
import java.util.List;

public class ShopImpl implements Shop{

    @Override
    public void craft(Present present, Helper helper) {

        Collection<Instrument> instruments = helper.getInstruments();

        for (Instrument instrument : instruments) {
            while (!instrument.isBroken() && helper.canWork() && !present.isDone()) {
                helper.work();
                present.getCrafted();
                instrument.use();
                if (present.isDone() || !helper.canWork()) break;
            }
            if (present.isDone() || !helper.canWork()) break;
        }
    }
}
