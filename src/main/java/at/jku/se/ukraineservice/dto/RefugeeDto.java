package at.jku.se.ukraineservice.dto;

import java.util.Date;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import at.jku.se.ukraineservice.entity.Plan;
import at.jku.se.ukraineservice.entity.Region;


public class RefugeeDto {
	private Long id;
	
	@NotNull
	@Size(min = 1, message = "first name needs to have at least one character")	
    private String firstName;
	
	@NotNull	
	@Size(min = 1, message = "last name needs to have at least one character")	
	private String lastName;
	
	@NotNull(message = "date of birth is required")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date birthDate;
   
	@NotNull(message = "a gender is required")
	private String gender;
    
	@NotNull(message = "District of user must be provided")
	private String passportNo;
    

	// TODO: Datetime  "yyyy-MM-dd'T'HH:mm"
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private Date registrationDate;
	
	@NotNull(message="date of arrival is required")
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	private Date arrivalDate;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date plannedDepartureDate;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date departureDate; // update in case of departure
     
    private Region region;
    @NotNull(message="home region is required")
	private Long regionId;
	
	
    private Plan plan;
    
    @NotNull(message="plan after arrival is required")
	private Long planId;

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

	public Date getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}

	public Date getArrivalDate() {
		return arrivalDate;
	}

	public void setArrivalDate(Date arrivalDate) {
		this.arrivalDate = arrivalDate;
	}

	public Date getPlannedDepartureDate() {
		return plannedDepartureDate;
	}

	public void setPlannedDepartureDate(Date plannedDepartureDate) {
		this.plannedDepartureDate = plannedDepartureDate;
	}

	public Date getDepartureDate() {
		return departureDate;
	}

	public void setDepartureDate(Date departureDate) {
		this.departureDate = departureDate;
	}

	public Region getRegion() {
		return region;
	}

	public void setRegion(Region region) {
		this.region = region;
	}

	public Long getRegionId() {
		return regionId;
	}

	public void setRegionId(Long regionId) {
		this.regionId = regionId;
	}

	public Plan getPlan() {
		return plan;
	}

	public void setPlan(Plan plan) {
		this.plan = plan;
	}

	public Long getPlanId() {
		return planId;
	}

	public void setPlanId(Long planId) {
		this.planId = planId;
	}

}
