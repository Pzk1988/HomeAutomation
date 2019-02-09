package Communication;

import Logger.Logger;
import Model.Configuration;
import Model.InOutBase;
import Model.Input;
import de.re.easymodbus.modbusclient.ModbusClient;
import java.io.IOException;
import java.util.AbstractCollection;
import java.util.AbstractList;
import java.util.Collection;
import java.util.List;

public class RemoteIoThread implements Runnable {
    private String ipAddress;
    private int port;
    private ModbusClient modbusClient;
    private int inputSlotNumber;
    private int outputSlotNumber;
    private final int READ_DELAY = 500;
    private short[] prevInputState;
    private short[] prevOutputState;
    private List<Input> inputs;

    public RemoteIoThread(Configuration config, List<Input> inputs)
    {
        ipAddress = config.getRemoteInOutIp();
        port = config.getRemoteInOutPort();
        inputSlotNumber = config.getDigitalInputSlots();
        outputSlotNumber = config.getDigitalOutputSlots();
        prevInputState = new short[inputSlotNumber];
        prevOutputState = new short[outputSlotNumber];
        this.inputs = inputs;
    }
    public void run() {
        boolean connected = false;
        while(true){
            if(connected == false){
                connected = connect();
            }else{
                connected = transmission();
            }
        }
    }

    private boolean connect() {
        try{
            modbusClient = new ModbusClient(ipAddress, port);
            modbusClient.Connect();
            Logger.getInstance().log("Connected to wago server");
            return true;
        }catch (IOException e){
                Logger.getInstance().log("Unable to connect to wago server: " + e.getMessage());
                return false;
            }
    }

    private boolean transmission(){
        int[] result;

        try{
            result = modbusClient.ReadInputRegisters(0, inputSlotNumber);
        }
        catch(Exception e){
            Logger.getInstance().log(e.toString());
            return false;
        }

        //TODO: Write efficent compareAndSet method
        for(int i = 0; i < inputSlotNumber; i++){
            short state = (short)result[i];
            if(prevInputState[i] != state){
                for(int j = 0; j < 16; j++){
                    if((state & (1 << j)) != (prevInputState[i] & (1 << j))){
                        inputs.get((i*16) + j).setValue( state & (1 << j) );
                        Logger.getInstance().log(String.format("Slot %d, channel %d value %d", i, j, inputs.get((i*16) + j).getValue()));
                    }
                }
                prevInputState[i] = (short)result[i];
            }
        }


//        int[] outputRegister = new int[outputSlotNumber];
//        for(int i = 0; i < outputSlotNumber; i++){
//        }
//        modbusClient.WriteMultipleRegisters(0, );

        try{
            Thread.sleep(READ_DELAY);
        }catch(InterruptedException e){
            Logger.getInstance().log(e.toString());
        }

        return true;
    }

    //TODO: Write efficent compareAndSet method
    private short compareAndSet(short prevValue, short value) {
        if(prevValue != value){
            int shift  = 0;
            short res = (short) (prevValue^value);
            while(res != 0){
                if((res & 0x0001) != 0){
                    Logger.getInstance().log(String.format("Changed on %d", shift));
                }
                res = (short) ((res & 0xFFFF) >>> 1);
                shift++;
            }
        }
        return value;
    }
}