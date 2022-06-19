package at.jku.se.ukraineservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import at.jku.se.ukraineservice.entity.Region;

@Repository
public interface RegionRepository extends JpaRepository<Region, Long> {

	List<Region>findAllByOrderByRegion();
	
	@Query(value = " select distinct region from UKRAINE_REGION", nativeQuery = true)
	List<String> findAllRegions();
	
}
