package com.rayllanderson.entities.enums;

public enum Gender {
    
    F ("Feminino"), 
    M ("Masculino");
    
    private String name; 
    
    private Gender (String name) {
	this.name = name;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }
}
