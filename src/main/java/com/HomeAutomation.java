package com;
import Interface.ILogicExpressionResult;
import Model.*;
import Utilites.ShuntingYard;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.ArrayList;

public class HomeAutomation {

    private final String CONFIG_PATH = "configuration/";
    private Outputs outputs;
    private Inputs inputs;
    private Configuration config;
    private Coils coils;
    private InfixExpressions infixExpressions;
    private PostfixExpressions postfixExpressions;

    private ArrayList<ILogicExpressionResult> logicExpressionResult;
    private ArrayList<Token> tokens;

    public void run(){
        config = deserialize(Configuration.class,CONFIG_PATH + "Configuration.xml");
        inputs = deserialize(Inputs.class, CONFIG_PATH + "InputList.xml");
        outputs = deserialize(Outputs.class, CONFIG_PATH + "OutputList.xml");
        coils = deserialize(Coils.class, CONFIG_PATH + "Coils.xml");

        infixExpressions = deserialize(InfixExpressions.class, CONFIG_PATH + "InfixExpressions.xml");
        logicExpressionResult = new ArrayList<ILogicExpressionResult>();
        logicExpressionResult.addAll(outputs);
        logicExpressionResult.addAll(coils);

        tokens = new ArrayList<Token>();
        tokens.addAll(inputs);
        tokens.addAll(outputs);
        tokens.addAll(coils);

        postfixExpressions = new PostfixExpressions();
        for(InfixExpression infixExpression : infixExpressions){

            String postfixExpression = ShuntingYard.convertToPostfix(infixExpression.getExpression());
            String out = infixExpression.getOut();
            PostfixExpression postFixExp = new PostfixExpression(out, postfixExpression);
            postFixExp.convert(tokens, logicExpressionResult);

            postfixExpressions.add(postFixExp);
        }

        //intializeInputList();
        //intializeOutputList();
        //initializeCoils();
        //initializeInfixExpressions();

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
        serialize(inputs, CONFIG_PATH + "InputList.xml");
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

        serialize(outputs, CONFIG_PATH + "OutputList.xml");
    }
    private void initializeCoils(){
        coils = new Coils();
        for(int i = 0; i < 20; i++){
            coils.add(new Coil(String.format("Coil_" + i)));
        }
        serialize(coils, CONFIG_PATH + "Coils.xml");
    }
    private void initializeInfixExpressions(){
        infixExpressions = new InfixExpressions();

        infixExpressions.add(new InfixExpression("out_010101", "in_010101 & in_010102"));
        infixExpressions.add(new InfixExpression("Coil_1", "+Coil_0 | out_010514"));
        infixExpressions.add(new InfixExpression("Coil_8", "( out_010101 & in_010102 ) & ( -Coil_1 )"));
        infixExpressions.add(new InfixExpression("out_010102", "( !out_010101 & in_010102 ) & ( Room 9 > 20.3 )"));

        serialize(infixExpressions, CONFIG_PATH + "InfixExpressions.xml");
    }
    private void deserializationValidation(){
        System.out.print(config.toString());
        System.out.print(inputs.toString());
        System.out.print(outputs.toString());
        System.out.print(coils.toString());
        System.out.println(infixExpressions.toString());
        System.out.println(postfixExpressions.toString());
    }

    public <T> void serialize(T object, String name) {
        try {
            File file = new File(name);
            JAXBContext context = JAXBContext.newInstance(object.getClass());
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
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
