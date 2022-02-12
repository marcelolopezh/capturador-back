package cl.codiner.capturador.back.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="data")
public class Data {
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="data_id_seq")
    @SequenceGenerator(name="data_id_seq", sequenceName="data_id_seq", allocationSize=1)
	private Long id;
	private Integer nroServicio;
	private Integer dv;
	private String nombre;
	private Long nro_medidor1;
	private String marca_medidor1;
	private Integer enteros1;
	private Integer anterior1;
	
	@Column(nullable = true)
	private Long nro_medidor2;
	@Column(nullable = true)
	private String marca_medidor2;
	@Column(nullable = true)
	private Integer enteros2;
	@Column(nullable = true)
	private Integer anterior2;
	
	@Column(nullable = true)
	private Long nro_medidor3;
	@Column(nullable = true)
	private String marca_medidor3;
	@Column(nullable = true)
	private Integer enteros3;
	@Column(nullable = true)
	private Integer anterior3;
	
	
	private Date updatedAt;
	@PrePersist
    protected void onCreate(){
        this.updatedAt = new Date();
    }
    @PreUpdate
    protected void onUpdate(){
	    this.updatedAt = new Date();
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
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Long getNro_medidor1() {
		return nro_medidor1;
	}
	public void setNro_medidor1(Long nro_medidor1) {
		this.nro_medidor1 = nro_medidor1;
	}
	public String getMarca_medidor1() {
		return marca_medidor1;
	}
	public void setMarca_medidor1(String marca_medidor1) {
		this.marca_medidor1 = marca_medidor1;
	}
	public Integer getEnteros1() {
		return enteros1;
	}
	public void setEnteros1(Integer enteros1) {
		this.enteros1 = enteros1;
	}
	public Integer getAnterior1() {
		return anterior1;
	}
	public void setAnterior1(Integer anterior1) {
		this.anterior1 = anterior1;
	}
	public Long getNro_medidor2() {
		return nro_medidor2;
	}
	public void setNro_medidor2(Long nro_medidor2) {
		this.nro_medidor2 = nro_medidor2;
	}
	public Integer getEnteros2() {
		return enteros2;
	}
	public void setEnteros2(Integer enteros2) {
		this.enteros2 = enteros2;
	}
	public Integer getAnterior2() {
		return anterior2;
	}
	public void setAnterior2(Integer anterior2) {
		this.anterior2 = anterior2;
	}
	public Date getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
	public String getMarca_medidor2() {
		return marca_medidor2;
	}
	public void setMarca_medidor2(String marca_medidor2) {
		this.marca_medidor2 = marca_medidor2;
	}
	public Long getNro_medidor3() {
		return nro_medidor3;
	}
	public void setNro_medidor3(Long nro_medidor3) {
		this.nro_medidor3 = nro_medidor3;
	}
	public String getMarca_medidor3() {
		return marca_medidor3;
	}
	public void setMarca_medidor3(String marca_medidor3) {
		this.marca_medidor3 = marca_medidor3;
	}
	public Integer getEnteros3() {
		return enteros3;
	}
	public void setEnteros3(Integer enteros3) {
		this.enteros3 = enteros3;
	}
	public Integer getAnterior3() {
		return anterior3;
	}
	public void setAnterior3(Integer anterior3) {
		this.anterior3 = anterior3;
	}
	
}
