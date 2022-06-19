package at.jku.se.ukraineservice.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import at.jku.se.ukraineservice.dto.RefugeeDto;
import at.jku.se.ukraineservice.entity.Plan;
import at.jku.se.ukraineservice.entity.Refugee;
import at.jku.se.ukraineservice.entity.Region;
import at.jku.se.ukraineservice.service.UkraineRegistrationService;

@Controller
public class RegistrationController {
	
	@Autowired
	UkraineRegistrationService service;
	
	private final int PAGE_SIZE=1000;
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	
	@GetMapping("/")
	public String getHomepage(Model model) {
		return "index";
	}
	
	@GetMapping("/refugees")
	public ModelAndView showRefugees(Model model, @RequestParam("keyword") Optional<String> keyword, @RequestParam("region") Optional<String>region , @RequestParam("page") Optional<Integer> page, SessionStatus sessionStatus) {
		log.info("refugees was called");
		if (!sessionStatus.isComplete())
			sessionStatus.setComplete();
		int currentPage = page.orElse(1);
		List<String>regions=service.findAllRegions();
		model.addAttribute("regionList", regions);
        
		Page<Refugee>refugees=service.findRefugeeByNamePatternAndRegion(keyword.orElse(""),region.orElse(""), currentPage-1, PAGE_SIZE);
		
		int totalPages = refugees.getTotalPages();
		model.addAttribute("totalPages", totalPages);
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                .boxed()
                .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
		return new ModelAndView("refugees","refugees",refugees);
	}
	
	@GetMapping("/registration")
	public ModelAndView showRegistration(Model model) {
		List<Region>regions=service.findAllRegionsOrderByRegion();
		model.addAttribute("regions",regions);
		List<Plan>plans=service.findAllPlansOrderByPlan();
		model.addAttribute("plans",plans);
		RefugeeDto dto = new RefugeeDto();
		return new ModelAndView("registration","refugee",dto); //model attribute "refugee" in template referenced via th:object="${refugee}"
	}
	
	@PostMapping("/registration")
	public ModelAndView addRegistration(Model model, @ModelAttribute("refugee") @Valid RefugeeDto dto, BindingResult result, RedirectAttributes redirectAttributes) {
		log.info("addrefugee was called with model refugee Dto: {}",dto);
		if (result.hasErrors()) {
			List<Region>regions=service.findAllRegionsOrderByRegion();
			model.addAttribute("regions",regions);
			List<Plan>plans=service.findAllPlansOrderByPlan();
			model.addAttribute("plans",plans);			
			return new ModelAndView("registration","refugee",dto);
		}
		
		Refugee refugee=service.registerNewRefugee(dto);
		model.addAttribute("refugeedetailsSuccess", "refugee");
		redirectAttributes.addAttribute("refugeeId", refugee.getId());
		return new ModelAndView(new RedirectView("/refugees",true));
	}
	
	@GetMapping("/refugee")
	public ModelAndView showRefugee(Model model,@RequestParam("refugeeId") Long refugeeId, @RequestParam("selected")Optional<String>selected, @RequestParam("search") Optional<String>search, SessionStatus sessionStatus) {
		if (!sessionStatus.isComplete())
			sessionStatus.setComplete();
		List<Region>regions=service.findAllRegionsOrderByRegion();
		model.addAttribute("regions",regions);
		List<Plan>plans=service.findAllPlansOrderByPlan();
		model.addAttribute("plans",plans);		
		Refugee refugee = service.findRefugeeById(refugeeId);
		RefugeeDto dto = service.RefugeeDtoInstance(refugee);
		
		return new ModelAndView("refugee","refugee",dto);
	}
	
	@PostMapping("/refugee/{id}")
	public ModelAndView updateRefugee(@PathVariable(name="id")Long id, Model model, @ModelAttribute("refugee") @Valid RefugeeDto dto, BindingResult result, RedirectAttributes redirectAttributes) {
		log.info("refugee was called with refugee Dto: {}",dto);
		
		if (result.hasErrors()) {
			log.info("Has error");
			List<Region>regions=service.findAllRegionsOrderByRegion();
			model.addAttribute("regions",regions);
			List<Plan>plans=service.findAllPlansOrderByPlan();
			model.addAttribute("plans",plans);			
			return new ModelAndView(new RedirectView("/refugees",true));
		}
		log.info("No error");
		Refugee refugee = service.updateRefugee(dto);
		//model.addAttribute("refugeedetailsSuccess", "refugee");
		redirectAttributes.addAttribute("refugeeId", refugee.getId());
		return new ModelAndView(new RedirectView("/refugees",true));
	}
	
	@PostMapping("/refugee/{id}/delete")
	public ModelAndView deleteRefugee(@PathVariable(name="id")Long id, Model model, RedirectAttributes redirectAttributes) {
		log.info("refugee delete was called with refugeeId: {}",id); // @RequestParam("id") Long id,
		service.deleteRefugee(id);
		model.addAttribute("refugeedeleteSuccess", "Successfully deleted refugee with Id "+id);
		return new ModelAndView(new RedirectView("/refugees",true));
	}	

}
