package Model;

import javax.xml.bind.annotation.XmlTransient;

public abstract class Token {
    public enum TokenType{
        OPERATOR, OPERAND
    }

    public Token(){}
    public Token(TokenType type){ this.type = type; }
    public void setType(TokenType type){ this.type = type; }

    @XmlTransient
    public TokenType getType(){ return type; }
    private TokenType type;

    public abstract int getValue();
}
