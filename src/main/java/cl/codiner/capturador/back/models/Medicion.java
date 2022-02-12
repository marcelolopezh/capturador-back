package cl.codiner.capturador.back.models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity(name="mediciones")
public class Medicion {
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="mediciones_id_seq")
    @SequenceGenerator(name="mediciones_id_seq", sequenceName="mediciones_id_seq", allocationSize=1)
	private Long id;
	private Integer nroServicio;
	private Integer dv;
	private Long medidor;
	private Integer lectura;
	private String motivo;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="tomador")
	private Tomador tomador;

	private String latitude;
	private String longitude;
	
	private Date fecha;
	@PreUpdate
    protected void onCreate(){
	    this.fecha = new Date();
    }
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getNroServicio() {
		return nroServicio;
	}
	public void setNroServicio(Integer nroServicio) {
		this.nroServicio = nroServicio;
	}
	public Integer getDv() {
		return dv;
	}
	public void setDv(Integer dv) {
		this.dv = dv;
	}
	public Long getMedidor() {
		return medidor;
	}
	public void setMedidor(Long medidor) {
		this.medidor = medidor;
	}
	public Integer getLectura() {
		return lectura;
	}
	public void setLectura(Integer lectura) {
		this.lectura = lectura;
	}
	public String getMotivo() {
		return motivo;
	}
	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}
	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public Tomador getTomador() {
		return tomador;
	}
	public void setTomador(Tomador tomador) {
		this.tomador = tomador;
	}
	
	
}
