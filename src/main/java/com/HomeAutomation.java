package com;
import Model.Configuration;
import Model.Input;
import Model.Inputs;
import Model.TempInput;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;

public class HomeAutomation {

    private Inputs inputs;
    private Configuration config;

    public void run(){
        config = deserialize(Configuration.class,"Configuration.xml");
        inputs = deserialize(Inputs.class, "InputList.xml");
        for (Input input: inputs) {
            System.out.println(input.toString());
        }
        //intializeInputList();
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
