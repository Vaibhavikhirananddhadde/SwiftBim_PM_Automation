package utilities;

import java.util.Date;

import com.github.javafaker.Faker;

public class FakeDataGenerator {

	static Faker faker = new Faker();

    public static String getFullName() {
        return faker.name().fullName();
    }

    public static String getEmail() {
        return faker.internet().emailAddress();
    }

    public static String getPhoneNumber() {
        return faker.phoneNumber().cellPhone();
    }

    public static String getCompanyName() {
        return faker.company().name();
    }

    public static String getCity() {
        return faker.address().city();
    }
    
    public static Date getDOB() {
    	return faker.date().birthday();
    }

   
	
}
