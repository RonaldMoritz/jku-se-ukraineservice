package at.jku.se.ukraineservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import at.jku.se.ukraineservice.entity.Refugee;


@Repository
public interface RefugeeRepository extends JpaRepository<Refugee, Long> {

	  @Query(value = " select * from ukraine_refugee r inner join ukraine_region rg on r.region=rg.id where (lower(r.first_name) like lower(?1) or lower(r.last_name) like lower(?1)) and rg.region like ?2 order by r.id",
	            nativeQuery = true)
	  Page<Refugee> findByNamePatternAndRegion(String name, String region, Pageable pageable);
}
