package fairyShop.core;

import fairyShop.common.ConstantMessages;
import fairyShop.common.ExceptionMessages;
import fairyShop.models.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

public class ControllerImpl implements Controller {

    private Collection<Helper> helpers;
    private Collection<Present> presents;
    private Shop shop;


    public ControllerImpl() {
        this.helpers = new ArrayList<>();
        this.presents = new ArrayList<>();
        this.shop = new ShopImpl();
    }

    @Override
    public String addHelper(String type, String helperName) {

        if (!type.equals("Happy") && !type.equals("Sleepy"))
            throw new IllegalArgumentException(ExceptionMessages.HELPER_TYPE_DOESNT_EXIST);

        Helper helper = null;

        if (type.equals("Happy")) {
            helper = new Happy(helperName);
        }
        if (type.equals("Sleepy")) {
            helper = new Sleepy(helperName);
        }

        this.helpers.add(helper);

        return String.format(ConstantMessages.ADDED_HELPER, type, helperName);
    }

    @Override
    public String addInstrumentToHelper(String helperName, int power) {

        Helper helper = this.helpers.stream().filter(h -> h.getName().equals(helperName)).findFirst().orElse(null);

        if (helper == null)
            throw new IllegalArgumentException(ExceptionMessages.HELPER_DOESNT_EXIST);

        Instrument instrument = new InstrumentImpl(power);

        helper.getInstruments().add(instrument);

        return String.format(ConstantMessages.SUCCESSFULLY_ADDED_INSTRUMENT_TO_HELPER, power, helperName);
    }

    @Override
    public String addPresent(String presentName, int energyRequired) {

        Present present = new PresentImpl(presentName, energyRequired);

        this.presents.add(present);

        return String.format(ConstantMessages.SUCCESSFULLY_ADDED_PRESENT, presentName);
    }

    @Override
    public String craftPresent(String presentName) {

        Collection<Helper> suitableHelpers = this.helpers.stream().filter(helper -> helper.getEnergy() > 50).collect(Collectors.toList());

        if (suitableHelpers.size() == 0)
            throw new IllegalArgumentException(ExceptionMessages.NO_HELPER_READY);

        Present present = this.presents.stream().filter(p -> p.getName().equals(presentName)).findFirst().orElse(null);

        int brokenInstruments = 0;

        if (present != null) {

            for (Helper helper : suitableHelpers) {
                shop.craft(present, helper);
                brokenInstruments += helper.getInstruments().stream().filter(Instrument::isBroken).count();
            }

        }

        String status = present != null && present.isDone() ? "done" : "not done";

        return String.format(ConstantMessages.PRESENT_DONE + ConstantMessages.COUNT_BROKEN_INSTRUMENTS, presentName, status, brokenInstruments);
    }

    @Override
    public String report() {

        StringBuilder output = new StringBuilder();

        long craftedPresents = this.presents.stream().filter(Present::isDone).count();

        output.append(craftedPresents).append(" presents are done!").append(System.lineSeparator());
        output.append("Helpers info:").append(System.lineSeparator());
        this.helpers.forEach(helper -> {
            output.append("Name: ").append(helper.getName()).append(System.lineSeparator());
            output.append("Energy: ").append(helper.getEnergy()).append(System.lineSeparator());
            output.append("Instruments: ").append(helper.getInstruments().stream().filter(i -> !i.isBroken()).count()).append(" not broken left").append(System.lineSeparator());
        });

        return output.toString().trim();
    }
}
