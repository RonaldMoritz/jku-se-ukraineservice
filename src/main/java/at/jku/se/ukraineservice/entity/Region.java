package at.jku.se.ukraineservice.entity;


import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "ukraine_region")
public class Region implements Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = -5858543005086620548L;
	
	@Id
	private Long id;
	private String region;
	
	@JsonIgnore
	@OneToMany(mappedBy = "region")
	private List<Refugee>refugees;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public List<Refugee> getRefugees() {
		return refugees;
	}

	public void setRefugees(List<Refugee> refugees) {
		this.refugees = refugees;
	}		
}

