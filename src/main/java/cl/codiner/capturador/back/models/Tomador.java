package cl.codiner.capturador.back.models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;


@Entity
@Table(name="tomadores")
public class Tomador {
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="tomador_id_seq")
    @SequenceGenerator(name="tomador_id_seq", sequenceName="tomador_id_seq", allocationSize=1)
	private Long id;
	@NotNull
	private String nombre;
	@NotNull
	private String apellido;

	@NotNull
	@Column(length=512)
	private String token;
	
	@NotNull
	private boolean isAuthorized;
	@NotNull
    private String password; 
	

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "tomador")
	@JsonIgnore
	private List<Medicion> mediciones;
	
	private String letra;
	// GETTERS Y SETTERS
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public boolean isAuthorized() {
		return isAuthorized;
	}
	public void setAuthorized(boolean isAuthorized) {
		this.isAuthorized = isAuthorized;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getLetra() {
		return letra;
	}
	public void setLetra(String letra) {
		this.letra = letra;
	}
	
	public List<Medicion> getMediciones() {
		return mediciones;
	}
	public void setMediciones(List<Medicion> mediciones) {
		this.mediciones = mediciones;
	}
	
	
}
