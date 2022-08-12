package glacialExpedition.core;

import glacialExpedition.models.explorers.AnimalExplorer;
import glacialExpedition.models.explorers.Explorer;
import glacialExpedition.models.explorers.GlacierExplorer;
import glacialExpedition.models.explorers.NaturalExplorer;
import glacialExpedition.models.mission.Mission;
import glacialExpedition.models.mission.MissionImpl;
import glacialExpedition.models.states.State;
import glacialExpedition.models.states.StateImpl;
import glacialExpedition.models.suitcases.Carton;
import glacialExpedition.models.suitcases.Suitcase;
import glacialExpedition.repositories.ExplorerRepository;
import glacialExpedition.repositories.StateRepository;

import java.util.*;
import java.util.stream.Collectors;

import static glacialExpedition.common.ConstantMessages.*;
import static glacialExpedition.common.ExceptionMessages.*;

public class ControllerImpl implements Controller {

    private ExplorerRepository explorers;
    private StateRepository states;

    private int exploredStates;

    public ControllerImpl() {
        this.explorers = new ExplorerRepository();
        this.states = new StateRepository();
        this.exploredStates = 0;
    }

    @Override
    public String addExplorer(String type, String explorerName) {
        if (!type.equals("GlacierExplorer") && !type.equals("AnimalExplorer") && !type.equals("NaturalExplorer"))
            throw new IllegalArgumentException(EXPLORER_INVALID_TYPE);

        if (type.equals("GlacierExplorer")) {
            this.explorers.add(new GlacierExplorer(explorerName));
        }
        if (type.equals("AnimalExplorer")) {
            this.explorers.add(new AnimalExplorer(explorerName));
        }
        if (type.equals("NaturalExplorer")) {
            this.explorers.add(new NaturalExplorer(explorerName));
        }

        return String.format(EXPLORER_ADDED, type, explorerName);
    }

    @Override
    public String addState(String stateName, String... exhibits) {
        State state = new StateImpl(stateName);
        state.getExhibits().addAll(Arrays.stream(exhibits).collect(Collectors.toList()));

        this.states.add(state);

        return String.format(STATE_ADDED, stateName);
    }

    @Override
    public String retireExplorer(String explorerName) {
        Explorer explorer = this.explorers.byName(explorerName);

        if (explorer == null) throw new IllegalArgumentException(String.format(EXPLORER_DOES_NOT_EXIST, explorerName));

        this.explorers.remove(explorer);

        return String.format(EXPLORER_RETIRED, explorerName);
    }

    @Override
    public String exploreState(String stateName) {
        Collection<Explorer> suitableExplorers = this.explorers.getCollection().stream().filter(explorer -> explorer.getEnergy() > 50).collect(Collectors.toList());

        if (suitableExplorers.size() == 0) throw new IllegalArgumentException(STATE_EXPLORERS_DOES_NOT_EXISTS);

        State state = this.states.byName(stateName);

        Mission mission = new MissionImpl();

        mission.explore(state, suitableExplorers);

        if (state.getExhibits().isEmpty()) this.exploredStates++;

        long retiredExplorers = suitableExplorers.stream().mapToDouble(Explorer::getEnergy).filter(e -> e == 0).count();

        return String.format(STATE_EXPLORER, stateName, retiredExplorers);
    }

    @Override
    public String finalResult() {

        StringBuilder results = new StringBuilder();
        results.append(String.format(FINAL_STATE_EXPLORED, this.exploredStates)).append(System.lineSeparator());
        results.append(FINAL_EXPLORER_INFO).append(System.lineSeparator());
        this.explorers.getCollection().forEach(explorer -> {
            results.append(String.format(FINAL_EXPLORER_NAME, explorer.getName())).append(System.lineSeparator());
            results.append(String.format(FINAL_EXPLORER_ENERGY, explorer.getEnergy())).append(System.lineSeparator());
            results.append(String.format(FINAL_EXPLORER_SUITCASE_EXHIBITS,
                    explorer.getSuitcase().getExhibits().size() == 0 ? "None" : String.join(FINAL_EXPLORER_SUITCASE_EXHIBITS_DELIMITER, explorer.getSuitcase().getExhibits())));
            results.append(System.lineSeparator());
        });
        return results.toString().trim();
    }
}
