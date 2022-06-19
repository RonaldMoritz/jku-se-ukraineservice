package at.jku.se.ukraineservice.entity;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Entity
@Table(name = "ukraine_refugee")
public class Refugee implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8959947400036150195L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "ukraine_refugee_id_seq")
   	@SequenceGenerator(name="ukraine_refugee_id_seq", sequenceName = "ukraine_refugee_id_seq")
    private Long id;
    private String firstName;
    private String lastName;
    private Date birthDate;
    private String gender;
    private String passportNo;
    
    private Timestamp registrationDate;
	private Timestamp arrivalDate;
    private Timestamp plannedDepartureDate;
    private Timestamp departureDate; // update in case of departure
     
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="region")
    private Region region;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="plan")
    private Plan plan;
    
   /* @ManyToMany(fetch = FetchType.EAGER)
	    @JoinTable(
	      name = "patient_illness", 
	      joinColumns = @JoinColumn(name = "patient_no"), 
	      inverseJoinColumns = @JoinColumn(name = "illness_no"))
	    private List<Illness> illnesses;
*/

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
    
    
	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public Date getBirthDate() {
		return birthDate;
	}


	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}


	public String getGender() {
		return gender;
	}


	public void setGender(String gender) {
		this.gender = gender;
	}


	public String getPassportNo() {
		return passportNo;
	}


	public void setPassportNo(String passportNo) {
		this.passportNo = passportNo;
	}


	public Timestamp getRegistrationDate() {
		return registrationDate;
	}


	public void setRegistrationDate(Timestamp registrationDate) {
		this.registrationDate = registrationDate;
	}


	public Timestamp getArrivalDate() {
		return arrivalDate;
	}


	public void setArrivalDate(Timestamp arrivalDate) {
		this.arrivalDate = arrivalDate;
	}


	public Timestamp getPlannedDepartureDate() {
		return plannedDepartureDate;
	}


	public void setPlannedDepartureDate(Timestamp plannedDepartureDate) {
		this.plannedDepartureDate = plannedDepartureDate;
	}


	public Timestamp getDepartureDate() {
		return departureDate;
	}


	public void setDepartureDate(Timestamp departureDate) {
		this.departureDate = departureDate;
	}


	public Region getRegion() {
		return region;
	}


	public void setRegion(Region region) {
		this.region = region;
	}


	public Plan getPlan() {
		return plan;
	}


	public void setPlan(Plan plan) {
		this.plan = plan;
	}	
	
	@Override
	public String toString() {
		return "Refugee [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", birthDate=" + birthDate
				+ ", gender=" + gender + ", region" + region + ", arrivalDate "+ arrivalDate + ", plan "+ plan+"]";
	}
    
}
