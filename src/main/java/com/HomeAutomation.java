package com;
import Model.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;

public class HomeAutomation {

    private Outputs outputs;
    private Inputs inputs;
    private Configuration config;

    public void run(){
        config = deserialize(Configuration.class,"Configuration.xml");
        inputs = deserialize(Inputs.class, "InputList.xml");
        outputs = deserialize(Outputs.class, "OutputList.xml");

        //intializeInputList();
        //intializeOutputList();

        deserializationValidation();
    }

    private void intializeInputList(){
        inputs = new Inputs();

        for(int i = 0; i < config.getDigitalInputSlots(); i++)
        {
            for(int j = 0; j < config.getChannelsPerSlot(); j++)
            {
                String name = "01" + String.format("%02d", i+1) + String.format("%02d", j+1);
                inputs.add(new Input(name));
            }
        }

        for(int i = 0; i < config.getTempInputs(); i++){
            inputs.add(new TempInput(String.format("Room " + i), String.format("28-000008d6bac" + i)));
        }
        serialize(inputs, "InputList.xml");
    }
    private void intializeOutputList(){
        outputs = new Outputs();

        for(int i = 0; i < config.getDigitalOutputSlots(); i++)
        {
            for(int j = 0; j < config.getChannelsPerSlot(); j++)
            {
                String name = "01" + String.format("%02d", i+1) + String.format("%02d", j+1);
                outputs.add(new Output(name));
            }
        }

        serialize(outputs, "OutputList.xml");
    }
    private void deserializationValidation(){
        System.out.print(config.toString());
        System.out.print(inputs.toString());
        System.out.print(outputs.toString());
    }

    public <T> void serialize(T object, String name) {
        try {
            File file = new File(name);
            JAXBContext context = JAXBContext.newInstance(object.getClass());
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(object, file);

        } catch (JAXBException e) {
            System.err.println(String.format("Exception while marshalling: %s", e.getMessage()));
        }
    }
    public <T> T deserialize(Class<T> clazz, String name) {
        try {
            File file = new File(name);
            JAXBContext context = JAXBContext.newInstance(clazz);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            T obj = (T) unmarshaller.unmarshal(file);
            return obj;

        } catch (JAXBException e) {
            System.err.println(String.format("Exception while marshalling: %s", e.getMessage()));
            return null;
        }
    }
}
