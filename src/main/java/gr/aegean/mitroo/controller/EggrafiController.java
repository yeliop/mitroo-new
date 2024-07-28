package gr.aegean.mitroo.controller;

import java.net.InetAddress;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import gr.aegean.mitroo.model.Eggrafi;
import gr.aegean.mitroo.repository.EggrafiRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/eggrafes")
public class EggrafiController {
	private final EggrafiRepository repo;
	
	EggrafiController(EggrafiRepository repo){
		this.repo = repo;
	}
	
	@GetMapping(produces = {"application/json", "application/xml"})
	List<Eggrafi> getEggrafes(@RequestParam(required = false) String at, @RequestParam(required = false) String afm, @RequestParam(required = false) String lastname){
		boolean atNotEmpty = (at != null && !at.isBlank());
		boolean afmNotEmpty = (afm != null && !afm.isBlank());
		boolean lastnameNotEmpty = (lastname != null && !lastname.isBlank());
		
		if (!atNotEmpty && !afmNotEmpty && !lastnameNotEmpty) return repo.findAll();
		else {
			if (atNotEmpty && !afmNotEmpty && !lastnameNotEmpty) return repo.findByAt(at);
			else if (!atNotEmpty && !afmNotEmpty && lastnameNotEmpty) return repo.findByLastname(lastname);
			else if (!atNotEmpty && afmNotEmpty && !lastnameNotEmpty) return repo.findByAfm(afm);
			else if (atNotEmpty && lastnameNotEmpty) return repo.findByAtAndLastname(at,lastname);
			else if (atNotEmpty && afmNotEmpty) return repo.findByAtAndAfm(at,afm);
			else if (!atNotEmpty && afmNotEmpty && lastnameNotEmpty) return repo.findByAfmAndLastname(at,afm);
			
			else return repo.findAll();
			
		}
	}
	
	@GetMapping(value = "{id}", produces = {"application/json", "application/xml"})
	Eggrafi getEggrafi(@PathVariable String id) {
		return repo.findById(id).orElseThrow(
			() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Eggrafi with given AT does not exist!"));
	}
	
	@PostMapping(consumes = {"application/json", "application/xml"})
	ResponseEntity<?> insertEggrafi(@Valid @RequestBody Eggrafi eggrafi) {
		if (repo.findById(eggrafi.getAt()).isPresent())
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Eggrafi with given AT already exists!");
		else {
			repo.save(eggrafi);
			try {
				String url = "http://" + InetAddress.getLocalHost().getHostName() + ":8080/api/eggrafes/" + eggrafi.getAt();
				return ResponseEntity.created(new URI(url)).build();
			}
			catch(Exception e) {
				throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong while generating the response!");
			}
		}
	}
	
	@PutMapping(value = "{id}", consumes = {"application/json", "application/xml"})
	ResponseEntity<?> updateEggrafi(@PathVariable String id, @Valid @RequestBody Eggrafi eggrafi) {
		if (!eggrafi.getAt().equals(id))
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Trying to update eggrafi with wrong AT!");
		else return repo.findById(id)
	      .map(oldEggrafi -> {
	          oldEggrafi.setAt(eggrafi.getAt());
	          oldEggrafi.setFirstname(eggrafi.getFirstname());
	          oldEggrafi.setLastname(eggrafi.getLastname());
	          oldEggrafi.setSex(eggrafi.getSex());
	          oldEggrafi.setAfm(eggrafi.getAfm());
	          oldEggrafi.setDob(eggrafi.getDob());
	          oldEggrafi.setAddress(eggrafi.getAddress());

	          repo.save(oldEggrafi);
	          return ResponseEntity.noContent().build();
	        })
	      .orElseThrow(() -> 
	      	new ResponseStatusException(HttpStatus.NOT_FOUND, "Eggrafi with given AT does not exist!"));
	}
	
	@DeleteMapping("{id}")
	ResponseEntity<?> deleteEggrafi(@PathVariable String id) {
		return repo.findById(id)
			    .map(oldEggrafi -> {
			         repo.deleteById(id);
			         return ResponseEntity.noContent().build();
			    })
			    .orElseThrow(() -> 
			      	 new ResponseStatusException(HttpStatus.NOT_FOUND, "Eggrafi with given AT does not exist!"));
	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleValidationExceptions(
	  MethodArgumentNotValidException ex) {
	    Map<String, String> errors = new HashMap<>();
	    ex.getBindingResult().getAllErrors().forEach((error) -> {
	        String fieldName = ((FieldError) error).getField();
	        String errorMessage = error.getDefaultMessage();
	        System.out.println("Fieldname is: " + fieldName + " ErrorMessage:" + errorMessage);
	        errors.put(fieldName, errorMessage);
	    });
	    return errors;
	}
}
