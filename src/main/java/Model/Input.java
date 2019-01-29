package Model;

import javax.xml.bind.annotation.*;

@XmlSeeAlso(TempInput.class)
public class Input
{
    public Input(){}
    public Input(String name){
        this.name = name;
        this.state = 0;
        this.prevState = 0;
        this.outOfControl = true;
    }

    @Override
    public String toString(){
        return "Digital input: " + name;
    }

    @XmlTransient
    public int getState() {
        return state;
    }
    public void setState(int state) {
        this.state = state;
    }

    @XmlTransient
    public int getPrevState() {
        return prevState;
    }
    public void setPrevState(int prevState) {
        this.prevState = prevState;
    }

    @XmlTransient
    public void setOutOfControl(boolean outOfControl) {
        this.outOfControl = outOfControl;
    }
    public boolean getOutOfControl(){
        return outOfControl;
    }

    @XmlAttribute(name = "name")
    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return name;
    }

    protected int state;
    protected boolean outOfControl;
    protected int prevState;
    protected String name;
}
