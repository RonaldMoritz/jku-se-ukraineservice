package at.jku.se.ukraineservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import at.jku.se.ukraineservice.entity.Plan;


@Repository
public interface PlanRepository extends JpaRepository<Plan, Long> {

	List<Plan>findAllByOrderByPlan();

	@Query(value = " select distinct plan from plan ", nativeQuery = true)
	List<String> findAllPlans();
	
}
