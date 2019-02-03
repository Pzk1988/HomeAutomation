package Exceptions;

public class InfixPostfixParsException extends Exception {
    public InfixPostfixParsException( String exp){
        System.out.println("Error in exp: " + exp);
    }
}
