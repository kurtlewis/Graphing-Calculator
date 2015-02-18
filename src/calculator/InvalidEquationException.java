/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package calculator;

/**
 *
 * @author Kurt Lewis
 * This is an exception for cases when an entered equation is invalid
 */
public class InvalidEquationException extends Exception {
    private String message;
    private boolean message_entered;
    
    public InvalidEquationException() {
        message = "The entered equation was invalid.";
        message_entered = false;
    }
    
    public InvalidEquationException(String message) {
        this.message = message;
        message_entered = true;
    }
    
    public boolean isMessageEntered() {
        return message_entered;
    }
    
    @Override
    public String toString() {
        return message;
    }
}
