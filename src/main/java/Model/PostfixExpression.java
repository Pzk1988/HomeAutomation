package Model;

import Interface.ILogicExpressionResult;

import java.util.ArrayList;

public class PostfixExpression {
    public PostfixExpression(String out, String postfixExpression){
        this.out = out;
        this.postfixExpression = postfixExpression;
    }

    private String out;
    private String postfixExpression;

    @Override
    public String toString(){
        return "Postfix: " + out + "=" + postfixExpression + "\r\n";
    }

    public void convert(ArrayList<Token> tokens, ArrayList<ILogicExpressionResult> logicExpressionResult) {

    }
}
