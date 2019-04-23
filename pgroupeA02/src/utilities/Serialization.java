package utilities;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.JsonParser;

import model.Deck;
import model.User;
import model.UserManagement;
import view.RegistrationConnectionGridPane;

public class Serialization {
	public static String datas = "data/";

	/*
	 * Writes a deck into a JSON file using JSON format. The JSON file name's
	 * depends of the "dest" param passed while calling the method.
	 * 
	 * @param Deck deck It's a set of questions
	 * 
	 * @param String dest It's the name of the .json file
	 */
	public static void deckToJson(Deck deck, String dest) {
		// Gets the text in JSON format for the deck
		String json = new Gson().toJson(deck);

		try (BufferedWriter bw = new BufferedWriter(new FileWriter(datas + dest + ".json"))) {
			bw.write(json);
			bw.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	/*
	 * Reads a deck from a JSON file using JSON format. The JSON file name's depends
	 * of the "dest" param passed while calling the method.
	 * 
	 * @param String dest It's the name of the JSON file.
	 * 
	 * @return The Deck that was recovered from the JSON file.
	 */
	public static Deck jsonToDeck(String dest) {
		Deck result = null;
		try (BufferedReader br = new BufferedReader(new FileReader(datas + dest + ".json"))) {
			String fromFile = "";

			String tmp = br.readLine();
			while (tmp != null) {
				fromFile = fromFile + tmp;
				tmp = br.readLine();
			}

			br.close();
			result = new Gson().fromJson(fromFile, Deck.class);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return result;
	}
	
	/**
	 * Writes a user into a JSON file using JSON format. The JSON file name's
	 * depends of the "dest" param passed while calling the method.
	 * @param string 
	 * @param users 
	 * @param user it's a user created by {@link RegistrationConnectionGridPane}
	 * @param dest  It's the name of the .json file
	 */
	public static void UserToJson(List<User> users, String dest) {
		// Gets the text in JSON format for the deck
		String json = new Gson().toJson(users);

		try (BufferedWriter bw = new BufferedWriter(new FileWriter(datas + dest + ".json", true))) {
			bw.write(json);
			bw.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	public static User jsonToUser(String dest) {
		User result = null;
		try (BufferedReader br = new BufferedReader(new FileReader(datas + dest + ".json"))) {
			String fromFile = "";

			String tmp = br.readLine();
			while (tmp != null) {
				fromFile = fromFile + tmp;
				tmp = br.readLine();
			}

			br.close();
			result = new Gson().fromJson(fromFile, User.class);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return result;
	}

}
