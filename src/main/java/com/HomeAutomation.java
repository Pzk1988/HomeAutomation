package com;
import Communication.RemoteIoThread;
import Exceptions.InfixPostfixParsException;
import Exceptions.UnknowResultException;
import Interface.ILogicExpResult;
import Interface.IOperand;
import Model.*;
import Utilites.ShuntingYard;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.AbstractCollection;
import java.util.ArrayList;

public class HomeAutomation {

    private final String CONFIG_PATH = "configuration/";
    private Outputs outputs;
    private Inputs inputs;
    private Configuration config;
    private Coils coils;
    private LogicExpEvaluator logixExpEvaluator;

    private AbstractCollection<ILogicExpResult> logicExpressionResult;
    private AbstractCollection<Token> operandsList;

    private RemoteIoThread remoteIoThread;
    private Thread thread;

    public void run(){
        // Deserialize
        config = deserialize(Configuration.class,CONFIG_PATH + "Configuration.xml");
        inputs = deserialize(Inputs.class, CONFIG_PATH + "InputList.xml");
        outputs = deserialize(Outputs.class, CONFIG_PATH + "OutputList.xml");
        coils = deserialize(Coils.class, CONFIG_PATH + "Coils.xml");
        InfixExpressions infixExpressions = deserialize(InfixExpressions.class, CONFIG_PATH + "InfixExpressions.xml");

        // Create logic exp result list
        logicExpressionResult = new ArrayList<ILogicExpResult>();
        logicExpressionResult.addAll(outputs);
        logicExpressionResult.addAll(coils);

        // Create operand list
        operandsList = new ArrayList<Token>();
        operandsList.addAll(inputs);
        operandsList.addAll(outputs);
        operandsList.addAll(coils);

        // Pars postfix
        logixExpEvaluator = new LogicExpEvaluator();
        logixExpEvaluator.setInputs(operandsList);
        logixExpEvaluator.setLogicExpResults(logicExpressionResult);
        logixExpEvaluator.parsInfixToPostfix(infixExpressions);

        // Threads
        remoteIoThread = new RemoteIoThread(config);
        thread = new Thread(remoteIoThread);
        thread.run();

        // Clean
        infixExpressions = null;

        runtime();
    }

    private void runtime(){
        while( true ){
            logixExpEvaluator.evaluate();
        }
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
