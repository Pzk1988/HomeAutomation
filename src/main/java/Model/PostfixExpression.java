package Model;

import Interface.ILogicExpResult;

import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Stack;

public class PostfixExpression {
    public PostfixExpression(ILogicExpResult out, AbstractCollection<Token> postfixExpression){
        this.out = out;
        this.postfixExpression = postfixExpression;
    }

    @Override
    public String toString(){
        return "Postfix: " + out + "=" + postfixExpression + "\r\n";
    }
    public void Evaluate(){
        Stack<Integer> stack = new Stack();
        for(Token token : postfixExpression){
            if(token.getType() == Token.TokenType.OPERAND){
                stack.push(token.getValue());
            }else{
                int operand1 = stack.pop();
                int operand2 = stack.pop();

                switch(OperatoryType.valueOf(token.getValue()))
                {
                    case AND:
                        stack.push( operand1 & operand2);
                        break;
                    case OR:
                        stack.push( operand1 | operand2);
                        break;
                    case GRT:
                        stack.push( (operand1 > operand2) ? 1 : 0);
                        break;
                    case GRT_EQ:
                        stack.push( (operand1 >= operand2) ? 1 : 0);
                        break;
                    case LESS:
                        stack.push( (operand1 < operand2) ? 1 : 0);
                        break;
                    case LESS_EQ:
                        stack.push( (operand1 <= operand2) ? 1 : 0);
                        break;
                        default:
                }
            }
        }
        assert( stack.size() == 1);
        out.setValue(stack.pop());
    }

    private ILogicExpResult out;
    private AbstractCollection<Token> postfixExpression;
}
