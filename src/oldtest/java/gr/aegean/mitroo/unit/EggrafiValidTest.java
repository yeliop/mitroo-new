package gr.aegean.mitroo.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import gr.aegean.mitroo.model.Eggrafi;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

public class EggrafiValidTest implements TestInterface{
	private static Validator validator = null;
	
	@BeforeAll
	static void constructValidator() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}
	
	private static List<String> getMessages(Set<ConstraintViolation<Eggrafi>> viols){
		List<String> messages = new ArrayList<String>();
		for (ConstraintViolation<Eggrafi> viol: viols) {
			messages.add(viol.getMessage());
		}
		return messages;
	}
	
	@ParameterizedTest
	@NullAndEmptySource
	@ValueSource(strings = { " ", "\t", "\n", " \t \n", "at452", "458963" })
	void checkInvalidAt(String at) {
		Eggrafi eggrafi = new Eggrafi();
		eggrafi.setAt(at);
		assertEquals(at,eggrafi.getAt());
		
		Set<ConstraintViolation<Eggrafi>> viols = validator.validate(eggrafi);
		assertNotEquals(viols.size(), 0);
		
		List<String> messages = getMessages(viols);
		assertTrue(messages.contains("AT is invalid or cannot be blank!"));
	}

	
	@ParameterizedTest
	@ValueSource(strings = { "AT457896", "MN562314", "KP789632"})
	void checkValidAt(String at) {
		Eggrafi eggrafi = new Eggrafi();
		eggrafi.setAt(at);
		assertEquals(at,eggrafi.getAt());
		
		Set<ConstraintViolation<Eggrafi>> viols = validator.validate(eggrafi);
		assertNotEquals(viols.size(), 0);
		
		List<String> messages = getMessages(viols);
		assertFalse(messages.contains("AT is invalid!"));
	}
	
	@ParameterizedTest
	@NullAndEmptySource
	@ValueSource(strings = { " ", "\t", "\n", " \t \n" })
	void checkInvalidFirstname(String firstname) {
		Eggrafi eggrafi = new Eggrafi();
		eggrafi.setFirstname(firstname);
		assertEquals(firstname,eggrafi.getFirstname());
		
		Set<ConstraintViolation<Eggrafi>> viols = validator.validate(eggrafi);
		assertNotEquals(viols.size(), 0);
		
		List<String> messages = getMessages(viols);
		assertTrue(messages.contains("Firstname cannot be blank!"));
	}

	
	@ParameterizedTest
	@ValueSource(strings = { "Giannis", "Nikos", "Maria"})
	void checkValidFirstname(String firstname) {
		Eggrafi eggrafi = new Eggrafi();
		eggrafi.setFirstname(firstname);
		assertEquals(firstname,eggrafi.getFirstname());
		
		Set<ConstraintViolation<Eggrafi>> viols = validator.validate(eggrafi);
		assertNotEquals(viols.size(), 0);
		
		List<String> messages = getMessages(viols);
		assertFalse(messages.contains("Firstname is invalid!"));
	}
	
	
	@ParameterizedTest
	@NullAndEmptySource
	@ValueSource(strings = { " ", "\t", "\n", " \t \n" })
	void checkInvalidLastname(String lastname) {
		Eggrafi eggrafi = new Eggrafi();
		eggrafi.setLastname(lastname);
		assertEquals(lastname,eggrafi.getLastname());
		
		Set<ConstraintViolation<Eggrafi>> viols = validator.validate(eggrafi);
		assertNotEquals(viols.size(), 0);
		
		List<String> messages = getMessages(viols);
		assertTrue(messages.contains("lastname cannot be blank!"));
	}

	
	@ParameterizedTest
	@ValueSource(strings = { "Ioannou", "Georgiou", "Apostolaki"})
	void checkValidLastname(String lastname) {
		Eggrafi eggrafi = new Eggrafi();
		eggrafi.setLastname(lastname);
		assertEquals(lastname,eggrafi.getLastname());
		
		Set<ConstraintViolation<Eggrafi>> viols = validator.validate(eggrafi);
		assertNotEquals(viols.size(), 0);
		
		List<String> messages = getMessages(viols);
		assertFalse(messages.contains("lastname is invalid!"));
	}
	
	@ParameterizedTest
	@NullAndEmptySource
	@ValueSource(strings = { " ", "\t", "\n", " \t \n"})
	void checkInvalidSex(String sex) {
		Eggrafi eggrafi = new Eggrafi();
		eggrafi.setSex(sex);
		assertEquals(sex,eggrafi.getSex());
		
		Set<ConstraintViolation<Eggrafi>> viols = validator.validate(eggrafi);
		assertNotEquals(viols.size(), 0);
		
		List<String> messages = getMessages(viols);
		assertTrue(messages.contains("sex cannot be blank!"));
	}

	
	@ParameterizedTest
	@ValueSource(strings = { "Andras", "Andras", "Gynaika"})
	void checkValidSex(String sex) {
		Eggrafi eggrafi = new Eggrafi();
		eggrafi.setSex(sex);
		assertEquals(sex,eggrafi.getSex());
		
		Set<ConstraintViolation<Eggrafi>> viols = validator.validate(eggrafi);
		assertNotEquals(viols.size(), 0);
		
		List<String> messages = getMessages(viols);
		assertFalse(messages.contains("sex is invalid!"));
	}
	
	@ParameterizedTest
	@NullAndEmptySource
	@ValueSource(strings = { " ", "\t", "\n", " \t \n", "5-8-24", "05-6-202"  })
	void checkInvalidDob(String dob) {
		Eggrafi eggrafi = new Eggrafi();
		eggrafi.setDob(dob);
		assertEquals(dob,eggrafi.getDob());
		
		Set<ConstraintViolation<Eggrafi>> viols = validator.validate(eggrafi);
		assertNotEquals(viols.size(), 0);
		
		List<String> messages = getMessages(viols);
		assertTrue(messages.contains("DoB is invalid or cannot be blank!"));
	}

	
	@ParameterizedTest
	@ValueSource(strings = { "12-12-2020", "05-06-2019", "30-10-2010"})
	void checkValidDob(String dob) {
		Eggrafi eggrafi = new Eggrafi();
		eggrafi.setDob(dob);
		assertEquals(dob,eggrafi.getDob());
		
		Set<ConstraintViolation<Eggrafi>> viols = validator.validate(eggrafi);
		assertNotEquals(viols.size(), 0);
		
		List<String> messages = getMessages(viols);
		assertFalse(messages.contains("DoB is invalid!"));
	}
	
	@ParameterizedTest
	@NullAndEmptySource
	@ValueSource(strings = { " ", "\t", "\n", " \t \n", "45896", "fgd568923" })
	void checkInvalidAfm(String afm) {
		Eggrafi eggrafi = new Eggrafi();
		eggrafi.setAfm(afm);
		assertEquals(afm,eggrafi.getAfm());
		
		Set<ConstraintViolation<Eggrafi>> viols = validator.validate(eggrafi);
		assertNotEquals(viols.size(), 0);
		
		List<String> messages = getMessages(viols);
		assertTrue(messages.contains("AFM is invalid!"));
	}

	
	@ParameterizedTest
	@ValueSource(strings = { "457896321", "1234567789", "987654321"})
	void checkValidAfm(String afm) {
		Eggrafi eggrafi = new Eggrafi();
		eggrafi.setAfm(afm);
		assertEquals(afm,eggrafi.getAfm());
		
		Set<ConstraintViolation<Eggrafi>> viols = validator.validate(eggrafi);
		assertNotEquals(viols.size(), 0);
		
		List<String> messages = getMessages(viols);
		assertFalse(messages.contains("AFM is invalid!"));
	}
	
	//@ParameterizedTest
	//@NullAndEmptySource
	//@ValueSource(strings = { " ", "\t", "\n", " \t \n" })
	//void checkInvalidAddress(String address) {
	//	Eggrafi eggrafi = new Eggrafi();
	//	eggrafi.setAddress(address);
	//	assertEquals(address,eggrafi.getAddress());
		
	//	Set<ConstraintViolation<Eggrafi>> viols = validator.validate(eggrafi);
	//	assertNotEquals(viols.size(), 0);
		
	//	List<String> messages = getMessages(viols);
	//	assertTrue(messages.contains("address cannot be blank!"));
	//}

	
	//@ParameterizedTest
	//@ValueSource(strings = { "odos 24, poli 13456", "odos 345, poli 15678", "odos 3454, poli, 56789"})
	//void checkValidAddress(String address) {
	//	Eggrafi eggrafi = new Eggrafi();
	//	eggrafi.setAddress(address);
	//	assertEquals(address,eggrafi.getAddress());
		
	//	Set<ConstraintViolation<Eggrafi>> viols = validator.validate(eggrafi);
	//	assertNotEquals(viols.size(), 0);
		
	//	List<String> messages = getMessages(viols);
	//	assertFalse(messages.contains("address is invalid!"));
	//}
	
	

}
