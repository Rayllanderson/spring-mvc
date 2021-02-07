package com.rayllanderson.exceptions;

/**
 * Deverá ser lançada quando já existir um usuário com username especificado.
 * @author Ray
 *
 */
public class UsernameExistsException extends RuntimeException{
    private static final long serialVersionUID = 1L;
    
    public UsernameExistsException(String msg) {
	super(msg);
    }

}
