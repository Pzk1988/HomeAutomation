package Model;

import javax.xml.bind.annotation.*;

public class TempInput extends Input {

    public TempInput(){}
    public TempInput(String name, String id){
        super(name);
        this.id = id;
    }

    @Override
    public String toString(){
        return "Temperature input: " + name + ", id: " + id;
    }

    private String id;

    @XmlAttribute
    public String getId(){
        return id;
    }
    public void setId(String id){ this.id = id; }
}
