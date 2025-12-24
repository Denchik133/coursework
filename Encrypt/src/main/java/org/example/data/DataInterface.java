package org.example.data;

import org.example.core.asymmetric.SimulationState;

public interface DataInterface {
    void saveData(SimulationState state);
    SimulationState loadData();
}
