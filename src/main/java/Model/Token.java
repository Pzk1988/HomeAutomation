package Model;

import javax.xml.bind.annotation.XmlTransient;

public abstract class Token{
    public enum TokenType{
        OPERATOR, OPERAND
    }
    public enum TokenActivity{
        RISING_EDGE,
        FALLING_EDGE,
        LOW,
        HIGH
    }

    public Token(){
        activity = TokenActivity.HIGH;
    }
    public Token(TokenType type){ this.type = type; }

    public void setActivity(TokenActivity activity){
        this.activity = activity;
    }
    @XmlTransient
    public TokenType getType(){ return type; }

    public abstract int getValue();
    public abstract String getName();

    private TokenType type;
    private TokenActivity activity;
}
