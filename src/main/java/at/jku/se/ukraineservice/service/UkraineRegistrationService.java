package at.jku.se.ukraineservice.service;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

import at.jku.se.ukraineservice.dto.RefugeeDto;
import at.jku.se.ukraineservice.entity.Plan;
import at.jku.se.ukraineservice.entity.Refugee;
import at.jku.se.ukraineservice.entity.Region;
import at.jku.se.ukraineservice.repository.PlanRepository;
import at.jku.se.ukraineservice.repository.RefugeeRepository;
import at.jku.se.ukraineservice.repository.RegionRepository;

@Service
public class UkraineRegistrationService {

	@Autowired
	RefugeeRepository refugeeRepository;	
	
	@Autowired
	RegionRepository regionRepository;
	
	@Autowired
	PlanRepository planRepository;
	

	
	public List<Refugee>findAllRefugees(){
		return refugeeRepository.findAll();
	}

	public Page<Refugee> findRefugeeByNamePatternAndRegion(String name, String region, int page, int size) {
		return refugeeRepository.findByNamePatternAndRegion("%"+name+"%","%"+region+"%",PageRequest.of(page, size));
	}
	
	public List<Region>findAllRegionsOrderByRegion(){
		return regionRepository.findAllByOrderByRegion();
	}
	public List<Plan>findAllPlansOrderByPlan(){
		return planRepository.findAllByOrderByPlan();
	}
	
	public static Refugee RefugeeInstance(RefugeeDto dto) {
		Refugee refugee = new Refugee();
				
		refugee.setId(dto.getId());
		refugee.setFirstName(dto.getFirstName());
		refugee.setLastName(dto.getLastName());
		
		if (dto.getBirthDate()!=null)
			refugee.setBirthDate(new java.sql.Date(dto.getBirthDate().getTime()));
				
		refugee.setGender(dto.getGender());
		refugee.setPassportNo(dto.getPassportNo());

		// TODO: default timestamp
		if (!StringUtils.isEmpty(dto.getRegistrationDate()))
			refugee.setRegistrationDate(new Timestamp(dto.getRegistrationDate().getTime()));
		//else refugee.setRegistrationDate(new Timestamp(Now()));

		if (!StringUtils.isEmpty(dto.getArrivalDate()))
			refugee.setArrivalDate(new Timestamp(dto.getArrivalDate().getTime()));
		
		if (!StringUtils.isEmpty(dto.getPlannedDepartureDate()))
			refugee.setPlannedDepartureDate(new Timestamp(dto.getPlannedDepartureDate().getTime()));
		
		if (!StringUtils.isEmpty(dto.getDepartureDate()))
			refugee.setDepartureDate(new Timestamp(dto.getDepartureDate().getTime()));

		return refugee;
	}
	
	public static RefugeeDto RefugeeDtoInstance(Refugee dto) {
		RefugeeDto refugee = new RefugeeDto();
		
		refugee.setFirstName(dto.getFirstName());
		refugee.setLastName(dto.getLastName());
		
		if (dto.getBirthDate()!=null)
			refugee.setBirthDate(new java.sql.Date(dto.getBirthDate().getTime()));
				
		refugee.setGender(dto.getGender());
		refugee.setPassportNo(dto.getPassportNo());

		// TODO: default timestamp
		if (!StringUtils.isEmpty(dto.getRegistrationDate()))
			refugee.setRegistrationDate(new Timestamp(dto.getRegistrationDate().getTime()));
		//else refugee.setRegistrationDate(new Timestamp(Now()));

		if (!StringUtils.isEmpty(dto.getArrivalDate()))
			refugee.setArrivalDate(new Timestamp(dto.getArrivalDate().getTime()));
		
		if (!StringUtils.isEmpty(dto.getPlannedDepartureDate()))
			refugee.setPlannedDepartureDate(new Timestamp(dto.getPlannedDepartureDate().getTime()));
		
		if (!StringUtils.isEmpty(dto.getDepartureDate()))
			refugee.setDepartureDate(new Timestamp(dto.getDepartureDate().getTime()));

		return refugee;
	}
	
	  @Transactional(propagation=Propagation.REQUIRED)
	    public Refugee registerNewRefugee(final RefugeeDto dto)  {
		   Refugee newRefugee = RefugeeInstance(dto);
		   Region region = regionRepository.findById(dto.getRegionId()).orElse(null);
		   Plan plan = planRepository.findById(dto.getPlanId()).orElse(null);

		   newRefugee.setRegion(region);
		   newRefugee.setPlan(plan);
	       return refugeeRepository.save(newRefugee);
	    }
	
	  @Transactional(propagation=Propagation.REQUIRED)
	  public Refugee updateRefugee(final RefugeeDto dto)  {
		  if (dto.getId()==null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		  Refugee refugee=refugeeRepository.findById(dto.getId()).orElse(null);
		  if (refugee==null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		  
		  Refugee afterUpdate = RefugeeInstance(dto);
		 
		  //afterUpdate.setPlannedDepartureDate(new Timestamp(dto.getPlannedDepartureDate().getTime()));
		  //afterUpdate.setPassportNo(dto.getPassportNo());
		  
		  if (!StringUtils.isEmpty(dto.getDepartureDate()))
			  afterUpdate.setDepartureDate(new Timestamp(dto.getDepartureDate().getTime()));

		  Plan plan = planRepository.findById(dto.getPlanId()).orElse(null);
		  afterUpdate.setPlan(plan);
		  
		  Region region = regionRepository.findById(dto.getRegionId()).orElse(null);
		  afterUpdate.setRegion(region);
		  
		  refugee = afterUpdate;
		  
		  refugeeRepository.save(refugee);
		  return refugee;
	  }
	  
	  
	  public Refugee findRefugeeById(Long id) {
		  return refugeeRepository.findById(id).orElse(null);
	  }

	  @Transactional(propagation=Propagation.REQUIRED)
	  public void deleteRefugee(Long id) {
		  if(refugeeRepository.findById(id).orElse(null)==null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		  else refugeeRepository.deleteById(id);
	  }

	  public List<String> findAllRegions() {
			return regionRepository.findAllRegions();
		}
	  
	  public List<String> findAllPlans() {
			return planRepository.findAllPlans();
		}	  

	
}
