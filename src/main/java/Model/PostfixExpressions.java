package Model;

import Interface.ILogicExpressionResult;

import java.util.AbstractList;
import java.util.ArrayList;

public class PostfixExpressions extends AbstractList<PostfixExpression> {
    private  final ArrayList<PostfixExpression> list = new ArrayList();

    @Override
    public PostfixExpression get(int index) {
        return list.get(index);
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean add(PostfixExpression postfixExpression){
        return list.add(postfixExpression);
    }

    @Override
    public String toString(){
        String res = "";
        for(PostfixExpression postfixExp : list){
            res += postfixExp.toString();
        }
        return  res;
    }
}
