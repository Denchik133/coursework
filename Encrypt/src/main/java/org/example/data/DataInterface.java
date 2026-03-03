package org.example.data;

import org.example.core.asymmetric.SimulationState;

public interface DataInterface<D> {
    void saveData(D data) throws MyDataException;
    D loadData() throws MyDataException;
}
