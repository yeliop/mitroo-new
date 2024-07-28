package gr.aegean.mitroo.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import gr.aegean.mitroo.model.Eggrafi;
import gr.aegean.mitroo.repository.EggrafiRepository;

@TestMethodOrder(org.junit.jupiter.api.MethodOrderer.OrderAnnotation.class)
@DataJpaTest
public class DBTest implements TestInterface{

	@Autowired
	EggrafiRepository repo;
	
	/* Checking eggrafi insertion positive cases */
	@ParameterizedTest
	@Order(5)
	@CsvFileSource(resources="/positiveSingleEggrafi.csv")
	void checkPositiveSingleEggrafiWithDB(ArgumentsAccessor accessor) {
		Eggrafi eggrafi = EggrafiUtility.createPositiveEggrafi(accessor,0);
		repo.save(eggrafi);
		eggrafi = repo.findById(accessor.getString(0)).orElse(null);
		assertNotNull(eggrafi);
		EggrafiUtility.checkEggrafi(eggrafi,accessor);
	}
	
	/* Checking eggrafi deletion positive cases */
	@ParameterizedTest
	@Order(2)
	@CsvSource({
		"AT123456, Giannis, Ioannou, Andras, 01-01-2010",
		"AT123457, Nikos, Nikolaou, Andras, 25-12-2015",
		"AT123458, Maria, Apostolaki, gynaika, 10-10-2005",
	})
	void checkEggrafikDeletion(String at, String firstname, String lastname, String sex, String dob) {
		Eggrafi eggrafi = new Eggrafi.Builder(at,firstname,lastname,sex, dob).build();
		repo.save(eggrafi);
		repo.deleteById(at);
		eggrafi = repo.findById(at).orElse(null);
		assertNull(eggrafi);
	}
	
	//Checking book update on obligatory fields
	@ParameterizedTest
	@Order(4)
	@CsvSource({
		"AT123456, Giannis, Ioannou, Andras, 01-01-2010",
		"AT123457, Nikos, Nikolaou, Andras, 25-12-2015",
		"AT123458, Maria, Apostolaki, gynaika, 10-10-2005",
	})
	void checkEggrafiUpdate(String at, String firstname, String lastname, String sex, String dob) {
		
		Eggrafi eggrafi = new Eggrafi.Builder(at,firstname,lastname,sex, dob).build();
		repo.save(eggrafi);
		Random r = new Random();
		int choice = r.nextInt(4);
		switch(choice) {
			case 0: eggrafi.setFirstname("FIRSTNAME"); break;
			case 1: eggrafi.setLastname("LASTNAME"); break;
			case 2: eggrafi.setSex("SEX"); break;
			case 3: eggrafi.setDob("DOB"); break;
		}
		repo.save(eggrafi);
		eggrafi = repo.findById(at).orElse(null);
		assertNotNull(eggrafi);
		if (choice == 0) {
			assertEquals(eggrafi.getLastname(),lastname);
			assertEquals(eggrafi.getSex(),sex);
			assertEquals(eggrafi.getDob(),dob);
			assertEquals(eggrafi.getFirstname(),"FIRSTNAME");
		}
		else if (choice == 1) {
			assertEquals(eggrafi.getFirstname(),firstname);
			assertEquals(eggrafi.getSex(),sex);
			assertEquals(eggrafi.getDob(),dob);
			assertEquals(eggrafi.getLastname(),"LASTNAME");
		}
		else if (choice == 2) {
			assertEquals(eggrafi.getFirstname(),firstname);
			assertEquals(eggrafi.getLastname(),lastname);
			assertEquals(eggrafi.getDob(),dob);
			assertEquals(eggrafi.getSex(),"SEX");
		}
		else {
			assertEquals(eggrafi.getFirstname(),firstname);
			assertEquals(eggrafi.getLastname(),lastname);
			assertEquals(eggrafi.getSex(),sex);
			assertEquals(eggrafi.getDob(),"DOB");
		}
	}
	
	//Checking eggrafi retrieval (all eggrafes or some)
	@Test
	@Order(3)
	void checkEggrafiRetrieval() {
		Eggrafi eggrafi1 = new Eggrafi.Builder("AT123456", "Giannis", "Ioannou", "Andras", "10-12-2010").build();
		Eggrafi eggrafi2 = new Eggrafi.Builder("AT123457", "Nikos", "Nikolaou", "Andras", "25-03-2015").build();
		Eggrafi eggrafi3 = new Eggrafi.Builder("AT123458", "Maria", "Apostolaki", "Gynaika", "03-03-2020").build();
		repo.save(eggrafi1);
		repo.save(eggrafi2);
		repo.save(eggrafi3);
		List<Eggrafi> eggrafes = repo.findAll();
		
		//Checking if we have 3 eggrafes and these are eggrafi1, eggrafi2 & eggrafi3
		assertEquals(eggrafes.size(),3);
		int matches = 0;
		for (Eggrafi eggrafi: eggrafes) {
			if (eggrafi.equals(eggrafi1) || eggrafi.equals(eggrafi2) || eggrafi.equals(eggrafi3)) {
				matches++;
			}
		}
		assertEquals(matches,3);
		
		//Checking that with 1 lastname, we get only one eggrafi
		eggrafes = repo.findByLastname("Ioannou");
		assertEquals(eggrafes.size(),1);
		assertEquals(eggrafes.get(0),eggrafi1);
		eggrafes = repo.findByLastname("Nikolaou");
		assertEquals(eggrafes.size(),1);
		assertEquals(eggrafes.get(0),eggrafi2);
		
		//Checking that with one publisher, we get two books
		eggrafes = repo.findBySex("Andras");
		assertEquals(eggrafes.size(),2);
		matches = 0;
		for (Eggrafi eggrafi: eggrafes) {
			if (eggrafi.equals(eggrafi1) || eggrafi.equals(eggrafi2)) {
				matches++;
			}
		}
		assertEquals(matches,2);
	}
}
