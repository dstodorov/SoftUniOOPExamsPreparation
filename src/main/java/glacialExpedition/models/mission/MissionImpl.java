package glacialExpedition.models.mission;

import glacialExpedition.models.explorers.Explorer;
import glacialExpedition.models.states.State;

import java.util.ArrayList;
import java.util.Collection;

public class MissionImpl implements Mission {

    @Override
    public void explore(State state, Collection<Explorer> explorers) {



        for (Explorer explorer : explorers) {
            Collection<String> collectedExhibits = new ArrayList<>();
            for (String exhibit : state.getExhibits()) {
                if (!explorer.canSearch()) break;

                explorer.search();
                explorer.getSuitcase().getExhibits().add(exhibit);
                collectedExhibits.add(exhibit);
            }
            if (collectedExhibits.size() > 0) state.getExhibits().removeAll(collectedExhibits);
            if (state.getExhibits().isEmpty()) break;
        }


    }
}
