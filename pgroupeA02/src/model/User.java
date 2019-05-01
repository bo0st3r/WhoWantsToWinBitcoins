package model;

import java.util.Set;
import java.util.Map.Entry;

import exceptions.NotARoundException;
import exceptions.StatementTooShortException;

public class User {
	private String pseudo;
	private String password;
	private String email;
	private double BESTSCORE=0;
	
	
	public User(String pseudo, String password, String email) {
		super();
		this.pseudo = pseudo;
		this.password = password;
		this.email = email;
		//this.bestScore = bestScore;
	}
	
	


	public String getPseudo() {
		return pseudo;
	}


	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public double getBESTSCORE() {
		return BESTSCORE;
	}


	public void setBESTSCORE(double bESTSCORE) {
		BESTSCORE = bESTSCORE;
	}


	@Override
	public String toString() {
		return "User [pseudo=" + pseudo + ", password=" + password + ", email=" + email + ", BESTSCORE=" + BESTSCORE
				+ "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof User) {
			User u = (User)obj;
			return (u.pseudo.equals(this.pseudo)) && (u.email.equals(this.email));
		}
		return false;
	}
	
	public User clone() {
		User user = new User(pseudo, password, email);
		return user;
	}
	
	

}
