package Model;
import javax.xml.bind.annotation.*;
import java.util.AbstractList;
import java.util.ArrayList;

@XmlRootElement(name="Inputs")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="Input")
public class Inputs extends AbstractList<Input> {

    @XmlElements ({
            @XmlElement(name = "TempInput", type = TempInput.class, required = false),
            @XmlElement(name = "DigitalInput", type = Input.class, required = false)
    })
    private  final ArrayList<Input> list = new ArrayList();

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
}
