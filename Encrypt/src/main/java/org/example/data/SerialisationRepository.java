package org.example.data;

import org.example.core.asymmetric.SimulationState;

import java.io.*;

public class SerialisationRepository implements DataInterface {
    private String fileName;

    @Override
    public void saveData(SimulationState state) throws MyDataException {
        try {
            File file = new File(fileName);
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
            oos.writeObject(state);
        } catch (IOException ex) {
            throw new MyDataException("Some error with file");
        }
    }

    @Override
    public SimulationState loadData() throws MyDataException{
        try {
            File file = new File(fileName);
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
            SimulationState state = (SimulationState) ois.readObject();
            return state;
        } catch (FileNotFoundException e) {
            throw new MyDataException("File not found");
        } catch (ClassNotFoundException e) {
            throw new MyDataException("Class not found");
        } catch (IOException e) {
            throw new MyDataException("Some error with file");
        }
    }
}
