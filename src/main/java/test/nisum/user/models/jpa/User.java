package test.nisum.user.models.jpa;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table
public class User {

	@Id
	private String userId;
	@JsonProperty(access= JsonProperty.Access.WRITE_ONLY)
	@Column(name="user_name")
	private String name;
	@JsonProperty(access= JsonProperty.Access.WRITE_ONLY)
	private String email;
	@Column(name="user_password")
	@JsonProperty(access= JsonProperty.Access.WRITE_ONLY)
	private String password;
	@Column(name="create_to")
	private Date create;
	@Column(name="modified_to")
	private Date modified;
	private Date lastLogin;
	private boolean isActive;
	@JsonProperty(access= JsonProperty.Access.WRITE_ONLY)
	@OneToMany(mappedBy = "user", cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
	private List<Phone> phones;
	@JsonProperty(access= JsonProperty.Access.READ_ONLY)
	private String token;

	public User(String userId, String name, String email, String password, List<Phone> phones) {
		this.userId = userId;
		this.name = name;
		this.email = email;
		this.password = password;
		this.phones = phones;
	}
	
	
	public User() {
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String mail) {
		this.email = mail;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public List<Phone> getPhones() {
		return phones;
	}
	public void setPhones(List<Phone> phones) {
		this.phones = phones;
	}


	public String getUserId() {
		return userId;
	}


	public void setUserId(String userId) {
		this.userId = userId;
	}


	public Date getCreate() {
		return create;
	}


	public void setCreate(Date create) {
		this.create = create;
	}


	public Date getModified() {
		return modified;
	}


	public void setModified(Date modified) {
		this.modified = modified;
	}


	public Date getLastLogin() {
		return lastLogin;
	}


	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}


	public boolean isActive() {
		return isActive;
	}


	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}


	public String getToken() {
		return token;
	}


	public void setToken(String token) {
		this.token = token;
	}


	@Override
	public String toString() {
		return "User [name=" + name + ", email=" + email + ", create=" + create + ", modified=" + modified
				+ ", lastLogin=" + lastLogin + ", isActive=" + isActive + ", phones=" + phones + "]";
	}
	
	
	
	
}
