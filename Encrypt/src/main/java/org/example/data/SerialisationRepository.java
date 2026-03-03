package org.example.data;

import org.example.core.asymmetric.SimulationState;

import java.io.*;

public class SerialisationRepository<D> implements DataInterface<D> {
    private String fileName;

    public SerialisationRepository(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void saveData(D data) throws MyDataException {
        try {
            File file = new File(fileName);
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
            oos.writeObject(data);
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new MyDataException("Some error with file");
        }
    }

    @Override
    public D loadData() throws MyDataException{
        try {
            File file = new File(fileName);
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
            D data = (D) ois.readObject();
            return data;
        } catch (FileNotFoundException e) {
            throw new MyDataException("File not found");
        } catch (ClassNotFoundException e) {
            throw new MyDataException("Class not found");
        } catch (IOException e) {
            e.printStackTrace();
            throw new MyDataException("Some error with file");
        }
    }
}
