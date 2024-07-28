package gr.aegean.mitroo.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;


import gr.aegean.mitroo.model.Eggrafi;

public class EggrafiTest implements TestInterface{
	
	//Checking multiple positive cases for eggrafi creation through its Builder interface
	@ParameterizedTest
	@CsvFileSource(resources="/positiveSingleEggrafi.csv")
	void checkPositiveSingleEggrafi(ArgumentsAccessor accessor) {
		Eggrafi eggrafi = EggrafiUtility.createPositiveEggrafi(accessor,0);
		EggrafiUtility.checkEggrafi(eggrafi,accessor);
	}
	
	//Checking multiple negative cases where obligatory book fields have null or empty or white space values
	@ParameterizedTest
	@CsvFileSource(resources="/negativeSingleEggrafi.csv")
	void checkNegativeSingleEggrafi(ArgumentsAccessor accessor) {
		Exception e = assertThrows(IllegalArgumentException.class, ()-> EggrafiUtility.createNegativeEggrafi(accessor));
		assertEquals(accessor.getString(6), e.getMessage());
	}
	
	//Checking that all fields of a book are null when its empty constructor is used
	@Test
	void checkEmptyConstructor() {
		Eggrafi eggrafi = new Eggrafi();
		assertNull(eggrafi.getAt());
		assertNull(eggrafi.getFirstname());
		assertNull(eggrafi.getLastname());
		assertNull(eggrafi.getSex());
		assertNull(eggrafi.getDob());
		assertNull(eggrafi.getAfm());
		assertNull(eggrafi.getAddress());
		
	}
	
	//Checking multiple negative cases for wrong AT values and whether the respective exception is thrown
	@ParameterizedTest
	@NullAndEmptySource
	@ValueSource(strings = { " ", "   ", "\t", "\n" })
	void nullEmptyAndBlankStringsforAt(String at) {
		Eggrafi eggrafi = new Eggrafi();
		Exception e = assertThrows(IllegalArgumentException.class, ()-> eggrafi.setAt(at));
		assertEquals("AT cannot be null or empty", e.getMessage());
	}
	
	//Checking multiple positive cases for proper AT values being set
	@ParameterizedTest
	@ValueSource(strings = { "AT558877", "AT258963", "AB587869", "KT231456" })
	void checkProperStringsforAt(String at) {
		Eggrafi eggrafi = new Eggrafi();
		eggrafi.setAt(at);
		assertEquals(at,eggrafi.getAt());
	}
	
	//Checking multiple negative cases for wrong firstname values and whether the respective exception is thrown
	@ParameterizedTest
	@NullAndEmptySource
	@ValueSource(strings = { " ", "   ", "\t", "\n" })
	void nullEmptyAndBlankStringsforFirstname(String firstname) {
		Eggrafi eggrafi = new Eggrafi();
		Exception e = assertThrows(IllegalArgumentException.class, ()-> eggrafi.setFirstname(firstname));
		assertEquals("Firstname cannot be null or empty", e.getMessage());
	}
	
	//Checking multiple positive cases for proper firstname values being set
	@ParameterizedTest
	@ValueSource(strings = { "Giannis", "Nikos", "Maria", "Petros" })
	void checkProperStringsforFirstname(String firstname) {
		Eggrafi eggrafi = new Eggrafi();
		eggrafi.setFirstname(firstname);
		assertEquals(firstname,eggrafi.getFirstname());
	}
	
	
	//Checking multiple negative cases for wrong lastname values and whether the respective exception is thrown
		@ParameterizedTest
		@NullAndEmptySource
		@ValueSource(strings = { " ", "   ", "\t", "\n" })
		void nullEmptyAndBlankStringsforLastname(String lastname) {
			Eggrafi eggrafi = new Eggrafi();
			Exception e = assertThrows(IllegalArgumentException.class, ()-> eggrafi.setLastname(lastname));
			assertEquals("Lastname cannot be null or empty", e.getMessage());
		}
		
		//Checking multiple positive cases for proper lastname values being set
		@ParameterizedTest
		@ValueSource(strings = { "Papadopoulos", "Ioannou", "Apostolaki", "Georgiou" })
		void checkProperStringsforLastname(String lastname) {
			Eggrafi eggrafi = new Eggrafi();
			eggrafi.setLastname(lastname);
			assertEquals(lastname,eggrafi.getLastname());
		}
	
		//Checking multiple negative cases for wrong sex values and whether the respective exception is thrown
				@ParameterizedTest
				@NullAndEmptySource
				@ValueSource(strings = { " ", "   ", "\t", "\n" })
				void nullEmptyAndBlankStringsforSex(String sex) {
					Eggrafi eggrafi = new Eggrafi();
					Exception e = assertThrows(IllegalArgumentException.class, ()-> eggrafi.setSex(sex));
					assertEquals("Sex cannot be null or empty", e.getMessage());
				}
				
				//Checking multiple positive cases for proper sex values being set
				@ParameterizedTest
				@ValueSource(strings = { "Andras", "Andras", "Gynaika", "Andras" })
				void checkProperStringsforSex(String sex) {
					Eggrafi eggrafi = new Eggrafi();
					eggrafi.setSex(sex);
					assertEquals(sex,eggrafi.getSex());
				}
	
				//Checking multiple negative cases for wrong DoB values and whether the respective exception is thrown
				@ParameterizedTest
				@NullAndEmptySource
				@ValueSource(strings = { " ", "   ", "\t", "\n" })
				void nullEmptyAndBlankStringsforDob(String dob) {
					Eggrafi eggrafi = new Eggrafi();
					Exception e = assertThrows(IllegalArgumentException.class, ()-> eggrafi.setDob(dob));
					assertEquals("DoB cannot be null or empty", e.getMessage());
				}
				
				//Checking multiple positive cases for proper DoB values being set
				@ParameterizedTest
				@ValueSource(strings = { "01-03-2023", "11-05-2024", "23-11-2020", "18-09-2023" })
				void checkProperStringsforDob(String dob) {
					Eggrafi eggrafi = new Eggrafi();
					eggrafi.setDob(dob);
					assertEquals(dob,eggrafi.getDob());
				}
	
					
				
				
				//Checking negative values for AFM
				@ParameterizedTest
				@NullAndEmptySource
				@ValueSource(strings = { " ", "   ", "\t", "\n" })
				void checkNegativeValsforAfm(String afm) {
					Eggrafi eggrafi = new Eggrafi();
					eggrafi.setAfm(afm);
					assertNull(eggrafi.getAfm());
				}
					
				//Checking positive values for AFM
				@ParameterizedTest
				@ValueSource(strings = { "589632568", "123456789", "987654321", "456789123" })
				void checkPositiveValsforAfm(String afm) {
					Eggrafi eggrafi = new Eggrafi();
					eggrafi.setAfm(afm);
					assertEquals(eggrafi.getAfm(),afm);
				}
	
}
