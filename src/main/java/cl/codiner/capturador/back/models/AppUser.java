package cl.codiner.capturador.back.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="appUsers")
public class AppUser {
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="appusers_id_seq")
    @SequenceGenerator(name="appusers_id_seq", sequenceName="appusers_id_seq", allocationSize=1)
	private Long id;
	
	private String username;
	private String password;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
