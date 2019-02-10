package Model;

import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlTransient;

@XmlSeeAlso({TempInput.class})
public class Input extends InOutBase
{
    public Input(){}
    public Input(String name){ super(name); }

    @XmlTransient
    public boolean getOutOfControl() {
        return outOfControl;
    }
    public void setOutOfControl(boolean outOfControl) {
        this.outOfControl = outOfControl;
    }

    private boolean outOfControl;

    @Override
    public synchronized void setValue(int value) {
        if(value == 0){
            this.value = 0;
        }else{
            this.value = 1;
        }
    }

    @Override
    public synchronized int getValue(){
        return  value;
    }

    @Override
    public String toString(){
        return "Input: " + name;
    }
}
