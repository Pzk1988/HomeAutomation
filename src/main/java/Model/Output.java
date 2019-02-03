package Model;

import Interface.ILogicExpResult;

public class Output extends InOutBase implements ILogicExpResult {
    public Output(){}

    public Output(String name){
        super(name);
    }
    public void setValue( int value){ this.value = value; }

    @Override
    public String toString(){
        return "Output: " + name;
    }
}
