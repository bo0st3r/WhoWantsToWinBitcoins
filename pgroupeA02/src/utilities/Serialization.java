package utilities;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.google.gson.Gson;

import model.Deck;
import model.Earning;
import model.UserManagerSingleton;
import view.RegistrationConnectionGP;

public class Serialization {
	public static final String DATA_DIRECTORY = "data/";

	// Deck
	/**
	 * Writes a Deck object into a JSON file using JSON format. The JSON file name's
	 * depends of the "dest" param passed while calling the method.
	 * 
	 * @param Deck   deck It's a set of questions
	 * 
	 * @param String dest It's the name of the .json file
	 */
	public static void deckToJson(Deck deck, String dest) {
		// Gets the text in JSON format for the deck
		String json = new Gson().toJson(deck);

		try (BufferedWriter bw = new BufferedWriter(
				new FileWriter(DATA_DIRECTORY + removeJSONExtension(dest) + ".json"))) {
			bw.write(json);
			bw.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * Recovers a Deck object from a JSON file using JSON format. The JSON file
	 * name's depends on the "source" param passed while calling the method.
	 * 
	 * @param String source It's the name of the JSON file.
	 * 
	 * @return The Deck that was recovered from the JSON file.
	 */
	public static Deck jsonToDeck(String source) {
		Deck deck = null;
		try (BufferedReader br = new BufferedReader(
				new FileReader(DATA_DIRECTORY + removeJSONExtension(source) + ".json"))) {
			String fromFile = "";

			String tmp = br.readLine();
			while (tmp != null) {
				fromFile = fromFile + tmp;
				tmp = br.readLine();
			}

			br.close();
			deck = new Gson().fromJson(fromFile, Deck.class);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return deck;
	}

	// UserManager
	/**
	 * Writes an UserManager object into a JSON file using JSON format. The JSON
	 * file name's depends of the "dest" param passed while calling the method.
	 * 
	 * @param dest     the name of the .json file.
	 * @param instance the list of users created by
	 *                 {@link RegistrationConnectionGP}.
	 */
	public static void userManagerSingletonToJson(UserManagerSingleton instance, String dest) {
		try (ObjectOutputStream oos = new ObjectOutputStream(
				new FileOutputStream(DATA_DIRECTORY + removeJSONExtension(dest) + ".json"))) {

			oos.writeObject(instance);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Recovers an UserManagerSingleton object from a JSON file using JSON format.
	 * The JSON file name's depends on the "source" param passed while calling the
	 * method.
	 * 
	 * @param String source It's the name of the JSON file.
	 * 
	 * @return The UserManagerSingleton object that was recovered from the JSON
	 *         file.
	 */
	public static UserManagerSingleton jsonToUserManagerSingleton(String source) {
		UserManagerSingleton userManagerSingleton = null;

		try (ObjectInputStream ois = new ObjectInputStream(
				new FileInputStream(DATA_DIRECTORY + removeJSONExtension(source) + ".json"))) {

			if (UserManagerSingleton.isNullInstance()) {
				try {
					userManagerSingleton = (UserManagerSingleton) ois.readObject();
				} catch (ClassNotFoundException | IOException e) {
					e.printStackTrace();
				}
			} else {
				userManagerSingleton = UserManagerSingleton.getInstance();
			}

		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		return userManagerSingleton;
	}

	// Earnings
	/**
	 * Writes an Earnings object into a JSON file using JSON format. The JSON file
	 * name's depends of the "dest" param passed while calling the method.
	 * 
	 * @param dest    the name of the .json file.
	 * @param earning the Earning object {@link RegistrationConnectionGP}.
	 */
	public static void earningToJson(Earning earning, String dest) {
		// Gets the text in JSON format for the deck
		String json = new Gson().toJson(earning);

		try (BufferedWriter bw = new BufferedWriter(
				new FileWriter(DATA_DIRECTORY + removeJSONExtension(dest) + ".json"))) {
			bw.write(json);
			bw.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * Recovers an UserManager from a JSON file using JSON format. The JSON file
	 * name's depends on the "source" param passed while calling the method.
	 * 
	 * @param String source It's the name of the JSON file.
	 * 
	 * @return The Deck that was recovered from the JSON file.
	 */
	public static Earning jsonToEarning(String source) {
		Earning earning = null;

		try (BufferedReader br = new BufferedReader(
				new FileReader(DATA_DIRECTORY + removeJSONExtension(source) + ".json"))) {

			String fromFile = "";

			String tmp = br.readLine();
			while (tmp != null) {
				fromFile = fromFile + tmp;
				tmp = br.readLine();
			}

			br.close();
			earning = new Gson().fromJson(fromFile, Earning.class);
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		return earning;
	}

	/**
	 * If the given param contains ".json" (case insensitive), returns the param
	 * without it.
	 * 
	 * @param str String, param to verify.
	 * @return String The verified param.
	 */
	public static String removeJSONExtension(String str) {
		String lowered = str.toLowerCase();
		return lowered.replaceAll(".json", "");
	}
}
