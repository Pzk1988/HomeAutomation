package Communication;

import Logger.Logger;
import Model.Configuration;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class RemoteIoThread implements Runnable {

    private Socket socket;
    private String ipAddress;
    private int port;

    public RemoteIoThread(Configuration config){
        ipAddress = config.getRemoteInOutIp();
        port = config.getRemoteInOutPort();
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
            socket = new Socket(ipAddress, port);
            Logger.getInstance().log("connected to wago server");
            return true;
        }catch (IOException e){
            Logger.getInstance().log("Unable to connect to wago server" + e.getMessage());
            return false;
        }
    }

    private boolean transmission(){
        if(socket.isConnected() == true){
            return true;
        }else{
            return false;
        }
    }
}