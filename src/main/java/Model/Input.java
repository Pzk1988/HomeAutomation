package Model;

import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlTransient;

@XmlSeeAlso({TempInput.class})
public class Input extends InOutBase
{
    public Input(){
        outOfControl = true;
    }
    public Input(String name){
        super(name);
        outOfControl = true;
    }

    @XmlTransient
    public boolean getOutOfControl() {
        return outOfControl;
    }
    public void setOutOfControl(boolean outOfControl) {
        this.outOfControl = outOfControl;
    }

    private boolean outOfControl;

    @Override
    public void setValue(int value) {
        if(value == 0){
            this.value = 0;
        }else{
            this.value = 1;
        }
    }

    @Override
    public int getValue(){
        return  value;
    }

    @Override
    public String toString(){
        return "Input: " + name;
    }
}
