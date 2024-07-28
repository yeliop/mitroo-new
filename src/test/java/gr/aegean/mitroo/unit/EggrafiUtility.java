package gr.aegean.mitroo.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.jupiter.params.aggregator.ArgumentsAccessor;

import gr.aegean.mitroo.model.Eggrafi;

public class EggrafiUtility {
	//Creating a book from each CSV row
	public static Eggrafi createPositiveEggrafi(ArgumentsAccessor accessor, int start) {
		String at = accessor.getString(start + 0);
		String firstname = accessor.getString(start + 1);
		String lastname = accessor.getString(start + 2);
		String sex = accessor.getString(start + 3);

		String dob = accessor.getString(start + 4);
		String afm = accessor.getString(start + 5);
		String address = accessor.getString(start + 6);

		
		return new Eggrafi.Builder(at, firstname, lastname, sex,dob).
				afm(afm).address(address).build();
	}
	
	//Trying to create a book from each CVS row - The creation of book will deliberately fail
	public static void createNegativeEggrafi(ArgumentsAccessor accessor) {
		String at = accessor.getString(0);
		String firstname = accessor.getString(1);
		String lastname = accessor.getString(2);
		String sex = accessor.getString(3);
		

		String dob = accessor.getString(4);
		String afm = accessor.getString(5);
		String address = accessor.getString(6);
		
		Eggrafi eggrafi = new Eggrafi.Builder(at, firstname, lastname, sex, dob).
				afm(afm).address(address).build();
		System.out.println("Got Eggrafi: " + eggrafi);
	}
	
	//Checking if a created book from a CVS row has correct values
	public static void checkEggrafi(Eggrafi eggrafi, ArgumentsAccessor accessor) {
		assertEquals(eggrafi.getAt(),accessor.get(0));
		assertEquals(eggrafi.getFirstname(),accessor.get(1));
		
		
		assertEquals(eggrafi.getLastname(),accessor.getString(2));
		assertEquals(eggrafi.getSex(),accessor.getString(3));
		assertEquals(eggrafi.getDob(),accessor.getString(4));
		assertEquals(eggrafi.getAfm(),accessor.getString(5));
		assertEquals(eggrafi.getAddress(),accessor.getString(6));
	}
}
