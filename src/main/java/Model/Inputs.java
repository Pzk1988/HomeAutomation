package Model;
import javax.xml.bind.annotation.*;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;
import Logger.Logger;

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

    public List<Input> getList(int chassi) {
        int start = getStartIndex(chassi);
        int end = getEndIndex(chassi);
        return list.subList(start, end);
    }

    private int getStartIndex(int chassi) {
        for(int i = 0; i < list.size(); i++){
            if(((chassi * 10000) < list.get(i).getNumber()) ||  (list.get(i).getClass() != Input.class)){
                return i;
            }
        }
        return list.size();
    }

    private int getEndIndex(int chassi) {
        for(int i = 0; i < list.size(); i ++){
            if((list.get(i).getNumber() > ((chassi * 10000) + 9999)) || (list.get(i).getClass() != Input.class)){
                return i;
            }
        }
        return list.size();
    }

    public void updatePrevValue() {
        for(Input in : list){
            if(in.getPrevValue() != in.getValue()){
                in.setPrevValue(in.getValue());
            }
        }
    }
}
