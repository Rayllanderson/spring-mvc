package com.rayllanderson.entities;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.rayllanderson.entities.enums.RoleType;

@Entity
@Table(name = "users")
public class User implements UserDetails {

    private static final long serialVersionUID = 1L;

    @Id
    @NotEmpty(message = "username não pode ser vazio")
    private String username;

    @Column(nullable = false)
    @NotEmpty(message = "senha não pode ser vazia")
    private String password;
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="user_role",
    joinColumns = @JoinColumn(name = "user_id"),
    inverseJoinColumns = @JoinColumn(name="role_id"))
    private Set<Role> roles = new HashSet<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
	return roles;
    }

    @Override
    public String getPassword() {
	return password;
    }

    @Override
    public String getUsername() {
	return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean isAccountNonExpired() {
	return true;
    }

    @Override
    public boolean isAccountNonLocked() {
	return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
	return true;
    }

    @Override
    public boolean isEnabled() {
	return true;
    }

    public Set<Role> getRoles() {
	return roles;
    }

    public boolean roleIsActive(String roleName) {
	return roles.contains(new Role(RoleType.valueOf(roleName)));
    }
    
    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((username == null) ? 0 : username.hashCode());
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
	User other = (User) obj;
	if (username == null) {
	    if (other.username != null)
		return false;
	} else if (!username.equals(other.username))
	    return false;
	return true;
    }

    @Override
    public String toString() {
	return "User [username=" + username + ", password=" + password + ", roles=" + roles + "]";
    }
}
