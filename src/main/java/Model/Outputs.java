package Model;

import javax.xml.bind.annotation.*;
import java.util.AbstractList;
import java.util.ArrayList;

@XmlRootElement(name="Outputs")
public class Outputs extends AbstractList<Output> {

    @XmlElements({
            @XmlElement(name = "Output", type = Output.class, required = false)
    })
    private  final AbstractList<Output> list = new ArrayList();

    @Override
    public Output get(int index) {
        return list.get(index);
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean add(Output output){
        return list.add(output);
    }

    @Override
    public String toString(){
        String res = "";
        for(Output output: list){
            res += output.toString() + "\r\n";
        }
        return  res;
    }
}
