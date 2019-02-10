import Model.*;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Collection;

public class PostfixExpEvaluatorTest {
    @Test
    public void testSingleParamHigh(){

        // Set out
        Coil out = new Coil("out_1");

        // Set in list
        AbstractCollection<Token> expInputs = new ArrayList<Token>();
        Input in = new Input("in_1");
        in.setValue(1);
        in.setActivity(Token.TokenActivity.HIGH);
        in.setPrevValue(0);
        expInputs.add(in);

        // Calculate
        PostfixExpression exp = new PostfixExpression(out, expInputs);
        exp.Evaluate();

        // Assert
        assert (out.getValue() == 1);
    }

    @Test
    public void testSingleParamLow(){

        // Set out
        Coil out = new Coil("out_1");

        // Set in list
        AbstractCollection<Token> expInputs = new ArrayList<Token>();
        Input in = new Input("in_1");
        in.setValue(1);
        in.setActivity(Token.TokenActivity.LOW);
        in.setPrevValue(0);
        expInputs.add(in);

        // Calculate
        PostfixExpression exp = new PostfixExpression(out, expInputs);
        exp.Evaluate();

        // Assert
        assert (out.getValue() == 0);
    }

    @Test
    public void testSingleParamFalling(){

        // Set out
        Coil out = new Coil("out_1");

        // Set in list
        AbstractCollection<Token> expInputs = new ArrayList<Token>();
        Input in = new Input("in_1");
        in.setValue(0);
        in.setActivity(Token.TokenActivity.FALLING_EDGE);
        in.setPrevValue(1);
        expInputs.add(in);

        // Calculate
        PostfixExpression exp = new PostfixExpression(out, expInputs);
        exp.Evaluate();

        // Assert
        exp.Evaluate();
        assert (out.getValue() == 1);
        in.setPrevValue(in.getValue());

        exp.Evaluate();
        assert (out.getValue() == 0);
        in.setPrevValue(in.getValue());
    }

    @Test
    public void testSingleParamRising(){

        // Set out
        Coil out = new Coil("out_1");

        // Set in list
        AbstractCollection<Token> expInputs = new ArrayList<Token>();
        Input in = new Input("in_1");
        in.setValue(1);
        in.setActivity(Token.TokenActivity.RISING_EDGE);
        in.setPrevValue(0);
        expInputs.add(in);

        Inputs inputs = new Inputs();
        inputs.add(in);

        // Calculate
        PostfixExpression exp = new PostfixExpression(out, expInputs);

        // Assert
        exp.Evaluate();
        assert (out.getValue() == 1);
        inputs.updatePrevValue();

        exp.Evaluate();
        assert (out.getValue() == 0);
        inputs.updatePrevValue();
    }

    @Test
    public void testDoubleParamRising(){

        // Set out
        Coil out = new Coil("out_1");

        // Set in list
        AbstractCollection<Token> expInputs = new ArrayList<Token>();
        Input in1 = new Input("in_1");
        in1.setValue(1);
        in1.setActivity(Token.TokenActivity.RISING_EDGE);
        in1.setPrevValue(0);
        expInputs.add(in1);

        Input in2 = new Input("in_2");
        in2.setValue(1);
        in2.setActivity(Token.TokenActivity.HIGH);
        in2.setPrevValue(0);
        expInputs.add(in2);

        expInputs.add( new Operator(OperatoryType.AND));

        // Inputs list
        Inputs inputs = new Inputs();
        inputs.add(in1);
        inputs.add(in2);

        // Calculate
        PostfixExpression exp = new PostfixExpression(out, expInputs);

        // Assert
        exp.Evaluate();
        assert (out.getValue() == 1);
        inputs.updatePrevValue();

        exp.Evaluate();
        assert (out.getValue() == 0);
        inputs.updatePrevValue();

        exp.Evaluate();
        assert (out.getValue() == 0);
        inputs.updatePrevValue();
    }
}
