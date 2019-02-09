import Model.Input;
import Model.Inputs;
import Model.TempInput;
import org.junit.Test;

import java.util.List;

public class InputsTest {

    @Test
    public void startIndexTest() throws Exception{
        Inputs inputs = new Inputs();

        for(int chassi = 0 ; chassi < 2; chassi++){
            for( int slot = 0; slot < 5; slot++){
                for(int input = 0; input < 16; input++){
                    Input in = new Input(String.format("in_%d", ((chassi + 1) * 10000 ) + ((slot + 1) * 100) + (input + 1)));
                    in.setNumber(((chassi + 1) * 10000 ) + ((slot + 1) * 100) + (input + 1));
                    inputs.add(in);
                }
            }
        }
        inputs.add(new TempInput("room1", "43-43-32-43"));
        inputs.add(new TempInput("room2", "43-43-32-43"));
        inputs.add(new TempInput("room3", "43-43-32-43"));


        assert( inputs.getList(1).size() == 80);
        assert( inputs.getList(2).size() == 80);
        assert( inputs.getList(3).size() == 0);
        assert( inputs.getList(4).size() == 0);
        assert( inputs.getList(5).size() == 0);
    }
}
