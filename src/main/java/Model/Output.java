package Model;

import Interface.ILogicExpResult;

public class Output extends InOutBase implements ILogicExpResult {
    public Output(){}

    public Output(String name){
        super(name);
    }

    @Override
    public String toString(){
        return "Output: " + name;
    }
}
