package com.rayllanderson.entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.rayllanderson.entities.enums.RoleType;
import com.rayllanderson.exceptions.UsernameExistsException;
import com.rayllanderson.repositories.UserRepository;

@Entity
@Table(name = "users")
public class User implements UserDetails {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotEmpty(message = "username não pode ser vazio")
    private String username;

    @Column(nullable = false)
    @NotEmpty(message = "senha não pode ser vazia")
    private String password;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    List<People> peoples = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    public User(String username) {
	this.username = username;
    }

    public User(Long id) {
	this.id = id;
    }

    public User(Long id, String username, String password) {
	this.id = id;
	this.username = username;
	this.password = password;
    }

    public User() {
    }

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

    /**
     * Verifica se o username atual já está em uso por outro usuário
     * 
     * @param repository
     * @throws UsernameExistsException There's already an User using the username
     */
    public void checkIfUsernameExists(UserRepository repository) throws UsernameExistsException {
	if (repository.existsByUsername(username)) {
	    throw new UsernameExistsException("There's already an User using the username" + username);
	}
    }

    /**
     * Nome não pode ser vazio, nulo nem possuir espaços em branco
     * 
     * @return true caso seja um nome válido, não vazio e não nulo
     */
    public boolean usernameIsValid() {
	return !(username == null || this.username.isBlank() || username.isEmpty());
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((id == null) ? 0 : id.hashCode());
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
	if (id == null) {
	    if (other.id != null)
		return false;
	} else if (!id.equals(other.id))
	    return false;
	return true;
    }

    @Override
    public String toString() {
	return "User [username=" + username + ", password=" + password + ", roles=" + roles + "]";
    }
}
