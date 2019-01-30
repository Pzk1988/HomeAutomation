package Model;

import Interface.ILogicExpressionResult;

public class Coil implements ILogicExpressionResult {
    public void setValue(int value) {
        this.value = value;
    }

    private int value;
    private int prevValue;
}
