package com.estore.api.estoreapi.accounts.persistence;

import com.estore.api.estoreapi.accounts.model.Account;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;

/**
 * Implements the functionality for JSON file-based persistence for Accounts.<p>
 * {@literal @}Component Spring annotation instantiates a single instance of this
 * class and injects the instance into other classes as needed
 *
 * @author Group 3C, The Code Monkeys
 */
@Component
public class AccountJSONDAO implements AccountDAO {
	/** A local cache of Account objects, to avoid reading from file each time. */
	Map<String, Account> accounts;

	/** TODO: Add description of the purpose of Logger, once it's actually used. */
	private static final Logger LOG = Logger.getLogger(AccountJSONDAO.class.getName());

	/** The next id to assign to a new account. */
	private static int nextId;

	/** Name of the file to read and write to. */
	private final String filename;
	/** Provides conversion between Java Account and JSON Account objects. */
	private final ObjectMapper objectMapper;

	/**
	 * Creates a Data Access Object for JSON-based Accounts.
	 *
	 * @param filename     Filename to read from and write to
	 * @param objectMapper Provides JSON Object to/from Java Object serialization and deserialization
	 * @throws IOException when file cannot be accessed or read from
	 */
	public AccountJSONDAO (@Value("${accounts.file}") String filename, ObjectMapper objectMapper) throws IOException {
		this.filename = filename;
		this.objectMapper = objectMapper;
		load();  // load the accounts from the file
	}

	/**
	 * Generates the next id for a new {@linkplain Account account}.
	 *
	 * @return The next account id
	 */
	private synchronized static int nextId () {
		int id = nextId;
		++nextId;
		return id;
	}

	/**
	 * Generates an array of {@linkplain Account accounts} from the tree map.
	 *
	 * @return The array of {@link Account accounts}, may be empty
	 */
	private Account[] getAccountsArray () {
		ArrayList<Account> accountArrayList = new ArrayList<>(accounts.values());
		Account[] accountArray = new Account[accountArrayList.size()];
		accountArrayList.toArray(accountArray);
		return accountArray;
	}

	/**
	 * Saves the {@linkplain Account accounts} from the map into the file as an array of JSON objects.
	 *
	 * @return true if the {@link Account accounts} were written successfully
	 * @throws IOException when file cannot be accessed or written to
	 */
	private boolean save () throws IOException {
		Account[] accountArray = getAccountsArray();

		// Serializes the Java Objects to JSON objects into the file,
		// writeValue will throw an IOException if there is an issue with or reading from the file
		objectMapper.writeValue(new File(filename), accountArray);
		return true;
	}

	/**
	 * Loads {@linkplain Account accounts} from the JSON file into the map.<br>
	 * Also sets this object's nextId to one more than the greatest id found in the file.
	 *
	 * @return true if the file was read successfully
	 * @throws IOException when file cannot be accessed or read from
	 */
	private boolean load () throws IOException {
		accounts = new TreeMap<>();
		nextId = 0;

		// Deserializes the JSON objects from the file into an array of accounts,
		// readValue will throw an IOException if there's an issue with or reading from the file
		Account[] accountArray = objectMapper.readValue(new File(filename), Account[].class);

		// Add each account to the tree map and keep track of the greatest id
		for (Account account : accountArray) {
			accounts.put(account.getUsername(), account);
			if (account.getId() > nextId) {
				nextId = account.getId();
			}
		}
		// Make the next id one greater than the maximum from the file
		++nextId;
		return true;
	}

	/**
	 * * {@inheritDoc}
	 */
	@Override
	public Account createAccount (Account account) throws IOException {
		synchronized (accounts) {
			// We create a new account object because the id field is immutable, and we need to assign the next unique id
			Account newAccount = new Account(nextId(), account.getUsername(), account.getPassword());
			accounts.put(newAccount.getUsername(), newAccount);
			save(); // may throw an IOException
			return newAccount;
		}
	}

	/**
	 * * {@inheritDoc}
	 */
	@Override
	public Account updateAccount (Account account) throws IOException {
		synchronized (accounts) {
			if (!accounts.containsKey(account.getUsername())) {
				return null;  // account does not exist
			}

			accounts.put(account.getUsername(), account);
			save(); // may throw an IOException
			return account;
		}
	}

	/**
	 * * {@inheritDoc}
	 */
	@Override
	public boolean deleteAccount (String username) throws IOException {
		synchronized (accounts) {
			if (accounts.containsKey(username)) {
				accounts.remove(username);
				return save();
			} else {
				return false;
			}
		}
	}

	/**
	 * * {@inheritDoc}
	 */
	@Override
	public Account getAccount (String username) {
		synchronized (accounts) {
			if (accounts.containsKey(username)) {
				return accounts.get(username);
			} else {
				return null;
			}
		}
	}

	/**
	 * * {@inheritDoc}
	 */
	@Override
	public Account[] getAccounts () {
		synchronized (accounts) {
			return getAccountsArray();
		}
	}
}
