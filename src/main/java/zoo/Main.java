package zoo;

import zoo.core.Engine;
import zoo.core.EngineImpl;
import zoo.entities.areas.Area;
import zoo.entities.areas.LandArea;

public class Main {

    public static void main(String[] args) {
        Engine engine = new EngineImpl();
        engine.run();
    }
}
