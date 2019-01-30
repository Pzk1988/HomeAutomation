package Model;

import Interface.ILogicExpressionResult;

public class Output extends InOutBase implements ILogicExpressionResult {
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
