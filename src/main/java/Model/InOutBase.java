package Model;

import Interface.IOperand;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;

public class InOutBase extends Token
{
    public InOutBase(){
        super(TokenType.OPERAND);
    }
    public InOutBase( String name ) {
        super(TokenType.OPERAND);
        this.name = name;
    }

    @XmlTransient
    public int getValue() {
        return value;
    }
    public void setValue(int value) {
        this.value =  value;
    }

    @XmlTransient
    public int getPrevValue() {
        return prevValue;
    }
    public synchronized void setPrevValue(int prevValue) {
        this.prevValue = prevValue;
    }

    @XmlAttribute
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @XmlAttribute
    public int getNumber() {
        return number;
    }
    public void setNumber(int number) {
        this.number = number;
    }

    protected int value;
    private int prevValue;
    protected String name;
    protected int number;
}
