package Model;
import javax.xml.bind.annotation.*;
import java.util.AbstractList;
import java.util.ArrayList;

@XmlRootElement(name="Inputs")
public class Inputs extends AbstractList<Input> {

    @XmlElements ({
            @XmlElement(name = "TempInput", type = TempInput.class, required = false),
            @XmlElement(name = "DigitalInput", type = Input.class, required = false)
    })
    private  final AbstractList<Input> list = new ArrayList();

    @Override
    public Input get(int index) {
        return list.get(index);
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean add(Input input){
        return list.add(input);
    }

    @Override
    public String toString(){
        String res = "";
        for(Input input : list){
            res += input.toString() + "\r\n";
        }
        return  res;
    }
}
