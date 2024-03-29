package com.estore.api.estoreapi.accounts.controller;

import com.estore.api.estoreapi.accounts.model.Account;
import com.estore.api.estoreapi.accounts.persistence.AccountDAO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The REST API controller for an Account object
 *
 * @author Group 3C, The Code Monkeys
 */
@RestController
@RequestMapping("accounts")
public class AccountController {
	/* Logger is used to log to command line the HTTP request performed, or any internal server errors encountered. */
	private static final Logger LOG = Logger.getLogger(AccountController.class.getName());

	/** The Data Access Object for Account */
	private final AccountDAO accountDao;

	/**
	 * Creates a REST API controller to respond to Account requests.
	 *
	 * @param accountDao The Account Data Access Object to perform CRUD operations
	 */
	public AccountController (AccountDAO accountDao) {
		this.accountDao = accountDao;
	}

	/**
	 * Creates an Account with the provided Account object
	 *
	 * @param account The Account to create
	 * @return ResponseEntity with created {@link Account account} object and HTTP status of CREATED<br>
	 * ResponseEntity with HTTP status of CONFLICT if {@link Account account} object already exists<br>
	 * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
	 */
	@PostMapping("")
	public ResponseEntity<Account> createAccount (@RequestBody Account account) {
		LOG.info("POST /accounts/" + account);

		try {
			Account newAccount = accountDao.createAccount(account);
			if (newAccount != null) {
				return new ResponseEntity<>(newAccount, HttpStatus.CREATED);
			} else {
				return new ResponseEntity<>(HttpStatus.CONFLICT);
			}
		} catch (IOException e) {
			LOG.log(Level.SEVERE, e.getLocalizedMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Updates the {@linkplain Account account} with the provided {@linkplain Account account} object, if it exists.
	 *
	 * @param account The {@link Account account} to update
	 * @return ResponseEntity with updated {@link Account account} object and HTTP status of OK if updated<br>
	 * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
	 * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
	 */
	@PutMapping("")
	public ResponseEntity<Account> updateAccount (@RequestBody Account account) {
		LOG.info("PUT /accounts/" + account);
		try {
			Account updatedAccount = accountDao.updateAccount(account);
			if (updatedAccount != null) {
				return new ResponseEntity<>(updatedAccount, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			LOG.log(Level.SEVERE, e.getLocalizedMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Deletes a {@linkplain Account account} with the given username.
	 *
	 * @param username The username of the {@link Account account} to be deleted
	 * @return ResponseEntity HTTP status of OK if deleted<br>
	 * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
	 * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
	 */
	@DeleteMapping("/{username}")
	public ResponseEntity<Account> deleteAccount (@PathVariable String username) {
		LOG.info("DELETE /accounts/" + username);
		try {
			if (accountDao.deleteAccount(username)) {
				return new ResponseEntity<>(HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (IOException e) {
			LOG.log(Level.SEVERE, e.getLocalizedMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Responds to the GET request for a {@linkplain Account account} with the given username.
	 *
	 * @param username the username used to locate a {@link Account account}
	 * @return ResponseEntity with {@link Account account} object and HTTP status of OK if found<br>
	 * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
	 * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
	 */
	@GetMapping("/{username}")
	public ResponseEntity<Account> getAccount (@PathVariable String username) {
		LOG.info("GET /accounts/" + username);
		try {
			// Try to get the account based on the id entered by the user
			Account account = accountDao.getAccount(username);
			if (account != null) {
				return new ResponseEntity<>(account, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (IOException e) {
			LOG.log(Level.SEVERE, e.getLocalizedMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Responds to the GET request for all {@linkplain Account accounts}.
	 *
	 * @return ResponseEntity with array of {@link Account account} objects (may be empty) and HTTP status of OK<br>
	 * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
	 */
	@GetMapping("")
	public ResponseEntity<Account[]> getAccounts () {
		LOG.info("GET /accounts/");
		try {
			// Try and get a list of all the accounts from the system
			Account[] accounts = accountDao.getAccounts();
			if (accounts != null) {
				return new ResponseEntity<>(accounts, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (IOException e) {
			LOG.log(Level.SEVERE, e.getLocalizedMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
