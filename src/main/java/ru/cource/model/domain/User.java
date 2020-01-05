package ru.cource.model.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * POJO domain object representing an User.
 * 
 * @author AlexanderM-O
 *
 */
@Entity
@Table(name = "Usr")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int Id;

	@NotEmpty(message = "Name should contain at least 1 character")
	private String name;

	@Email(message = "incorrect email")
	private String email;

	@NotEmpty(message = "empty password")
	@Size(min = 4, message = "password should have 4 or more characters")
	private String password;
	
	private String avatarFileName;

	@Transient
	private String confPassword;

	@ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
	@CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
	@Enumerated(EnumType.STRING)
	@Column(name = "role")
	Set<Role> roles = new HashSet<Role>();

	public User() {
	};

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	// used for view
	public void setRole(String role) {
		if (role.equals("admin")) {
			roles.add(Role.ADMIN);
		}
		roles.add(Role.USER);
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfPassword() {
		return confPassword;
	}

	public void setConfPassword(String confPassword) {
		this.confPassword = confPassword;
	}

	@Override
	public boolean equals(Object obj) {
		// type cast to goal
		if (this == obj)
			return true;
		if (obj == null || this.getClass() != obj.getClass())
			return false;
		// the same class and not null =>could cast
		User user = (User) obj;

		return (this.email.equals(user.email) && this.name.equals(user.name) && this.roles.equals(user.roles));
	}

	@Override
	public int hashCode() {
		// TODO: WRITE HASH CODE LOGIC HERE
		return 1;
	}

}
