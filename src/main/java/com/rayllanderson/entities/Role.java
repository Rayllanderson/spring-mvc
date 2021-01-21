package com.rayllanderson.entities;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.springframework.security.core.GrantedAuthority;

import com.rayllanderson.entities.enums.RoleType;

@Entity
public class Role implements GrantedAuthority {

    private static final long serialVersionUID = 1L;

    @Id
    private RoleType roleName;

    public RoleType getRoleName() {
	return roleName;
    }

    public void setRoleName(RoleType roleName) {
	this.roleName = roleName;
    }

    @Override
    public String getAuthority() {
	return roleName.toString();
    }
    
    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((roleName == null) ? 0 : roleName.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	Role other = (Role) obj;
	if (roleName != other.roleName)
	    return false;
	return true;
    }
    
    @Override
    public String toString() {
	return "Role [roleName=" + roleName + "]";
    }

}
