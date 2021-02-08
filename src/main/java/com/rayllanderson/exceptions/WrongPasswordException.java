package com.rayllanderson.exceptions;

/**
 * Deverá ser lançada quando a senha digitada não corresponde a senha atual cadastrada
 * @author Ray
 *
 */
public class WrongPasswordException extends RuntimeException{
    private static final long serialVersionUID = 1L;
    
    public WrongPasswordException(String msg) {
	super(msg);
    }

}
