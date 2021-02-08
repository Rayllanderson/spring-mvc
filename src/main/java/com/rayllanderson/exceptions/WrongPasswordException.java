package com.rayllanderson.exceptions;

/**
 * Deverá ser lançada quando já existir um usuário com username especificado.
 * @author Ray
 *
 */
public class WrongPasswordException extends RuntimeException{
    private static final long serialVersionUID = 1L;
    
    public WrongPasswordException(String msg) {
	super(msg);
    }

}
