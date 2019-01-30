package Model;

import Interface.ILogicExpressionResult;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;

public class Coil extends Token implements ILogicExpressionResult  {
    public Coil(){}
    public Coil(String name){ this.name = name; }
    public void setValue(int value) {
        this.value = value;
    }

    @XmlTransient
    private int value;
    private int prevValue;

    @XmlAttribute
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    private String name;

    @Override
    public String toString(){
        return "Coil: " + name + "\r\n";
    }

    public int getValue() {
        return value;
    }
}
