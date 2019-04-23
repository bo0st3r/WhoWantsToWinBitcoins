package model;

import java.util.ArrayList;
import java.util.List;

import utilities.Serialization;
import view.RegistrationConnectionGridPane;

public class UserManagement {
	
	private static List<User> users;
	
	public UserManagement() {
		users = new ArrayList<>();
	}
	

	public void recup () {
	
		users.add(Serialization.jsonToUser("UserJson"));
	}


	@Override
	public String toString() {
		return "UserManagement [users=" + users + "]";
	}
	
	public static void addUser(User user) {
//		if (users.contains(user)) 
//			System.out.println("DEJA PRIS");
//		else 
			users.add(user);
		
		Serialization.UserToJson(users, "UserJson");
				
	}


	public static List<User> getUsers() {
		return users;
	}
	
	
	
	 

}
