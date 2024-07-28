package gr.aegean.mitroo.model;

//import java.util.ArrayList;
//import java.util.List;


import jakarta.persistence.Basic;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.*;

@Entity
public class Eggrafi {
	@Id
	@NotBlank(message = "AT cannot be blank!")
	@Size(min=8,max=8)	
	private String at = null;
	@Basic(optional = false)
	@NotBlank(message = "First Name cannot be blank!")
    private String firstname = null;
	@Basic(optional = false)
	@NotBlank(message = "Last Name cannot be blank!")
    private String lastname = null;
	@Basic(optional = false)
	@NotBlank(message = "Sex cannot be blank!")
    private String sex = null;
	@Basic(optional = false)
	@NotBlank(message = "Date of Birth cannot be blank!")
	@Pattern(
			regexp = "^(0[1-9]|[12][0-9]|3[01])-(0[1-9]|1[0-2])-\\d{4}$", 
			message = "Date of Birth is invalid"
		)
	private String dob = null;
	@Pattern(regexp = "\\d{9}",
			message = "AFM is invalid")
	private String afm = null;
	private String address = null;
	

	
   
    
    public Eggrafi() {}
    
    private Eggrafi(Builder builder) {
    	this.at = builder.at;
    	this.firstname = builder.firstname;
    	this.lastname = builder.lastname;
    	this.sex = builder.sex;
    	this.dob = builder.dob;
    	setAfm(builder.afm);
    	setAddress(builder.address);
    	
    }
    
    
    public static class Builder{
    	private String at = null;
        private String firstname = null;
        private String lastname = null;
        private String sex = null;
        private String dob = null;
        
        private String afm = null;
        private String address = null;
        
        private static void checkSingleValue(String value, String message) throws IllegalArgumentException{
        	if (value == null || value.trim().equals("")) throw new IllegalArgumentException(message + " cannot be null or empty");
        }
        
        public Builder(String at, String firstname, String lastname, String sex, String dob) throws IllegalArgumentException{
        	checkSingleValue(at, "AT");
        	checkSingleValue(firstname, "Firstname");
        	checkSingleValue(lastname, "Lastname");
        	checkSingleValue(sex, "Sex");
        	checkSingleValue(dob, "Date of Birth");
        	
        	this.at = at;
        	this.firstname = firstname;
        	this.lastname = lastname;
        	this.sex = sex;
        	this.dob = dob;
        	
        }
        
        public Builder afm(String value) {
        	this.afm = value;
        	return this;
        }
        
        public Builder address(String value) {
        	this.address = value;
        	return this;
        }
        
       
        
        public Eggrafi build() {
        	return new Eggrafi(this);
        }
    }
    
    public String toString(){
    	return "Eggrafi(" + at + ", " + firstname + ", " + lastname + ")";
    }

	public String getAt() {
		return at;
	}

	public void setAt(String at) throws IllegalArgumentException{
		Builder.checkSingleValue(at, "AT");
		this.at = at;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) throws IllegalArgumentException{
		Builder.checkSingleValue(firstname, "Firstname");
		this.firstname = firstname;
	}
	
	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) throws IllegalArgumentException{
		Builder.checkSingleValue(lastname, "Lastname");
		this.lastname = lastname;
	}
	
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) throws IllegalArgumentException{
		Builder.checkSingleValue(sex, "Sex");
		this.sex = sex;
	}
	
	public String getDob() {
		return dob;
	}

	public void setDob(String dob) throws IllegalArgumentException{
		Builder.checkSingleValue(dob, "Date of Birth");
		this.dob = dob;
	}
	
	

	public String getAfm() {
		return afm;
	}

	public void setAfm(String afm) {
		this.afm = afm;
	}
	
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		if (address == null || address.trim().equals("")) this.address = null;
		else this.address = address.trim();
	}


	
	public boolean equals(Object o) {
		if (o instanceof Eggrafi) {
			Eggrafi b = (Eggrafi)o;
			if (b.getAfm().equals(afm)) return true;
		}
		
		return false;
	}
}
