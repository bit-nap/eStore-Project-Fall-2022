package com.estore.api.estoreapi.accounts.controller;

import com.estore.api.estoreapi.accounts.model.Account;
import com.estore.api.estoreapi.accounts.persistence.AccountDAO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("accounts")
public class AccountController {
	private static final Logger LOG = Logger.getLogger(AccountController.class.getName());

	private final AccountDAO accountDao;

	public AccountController(AccountDAO accountDao) {
		this.accountDao = accountDao;
	}

	@PostMapping("")
	public ResponseEntity<Account> createAccount(@RequestBody Account account) {
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

	@PutMapping("")
	public ResponseEntity<Account> updateAccount(@RequestBody Account account) {
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
	 * Deletes a {@linkplain Account account} with the given id.
	 *
	 * @param id The id of the {@link Account account} to be deleted
	 * @return ResponseEntity HTTP status of OK if deleted<br>
	 * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
	 * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<Account> deleteAccount(@PathVariable int id) {
		LOG.info("DELETE /accounts/" + id);
		try {
			if (accountDao.deleteAccount(id)) {
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
	 * Responds to the GET request for a {@linkplain Account account} with the given id.
	 *
	 * @param id the id used to locate a {@link Account account}
	 * @return ResponseEntity with {@link Account account} object and HTTP status of OK if found<br>
	 * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
	 * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
	 */
	@GetMapping("/{id}")
	public ResponseEntity<Account> getAccount (@PathVariable int id) {
		LOG.info("GET /accounts/" + id);
		try {
			// Try to get the account based on the id entered by the user
			Account account = accountDao.getAccount(id);
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

	/**
	 * Responds to the GET request for all {@linkplain Account accounts} whose account username contains the given text.
	 *
	 * @param text A String which contains the text used to query {@link Account account} to find all accounts with the text.
	 * @return ResponseEntity with array of {@link Account account} objects (may be empty) and HTTP status of OK<br>
	 * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
	 */
	@GetMapping("/")
	public ResponseEntity<Account[]> findAccounts (@RequestParam String text) {
		LOG.info("GET /accounts/?text=" + text);
		try {
			Account[] foundAccounts = accountDao.findAccounts(text);
			/*
			 * If accountDao.findAccounts() fails, an IOException is thrown. Assume function
			 * passed successfully and return the Account array even if it is empty. Which
			 * returns a null list
			 */
			return new ResponseEntity<>(foundAccounts, HttpStatus.OK);
		} catch (IOException e) {
			LOG.log(Level.SEVERE, e.getLocalizedMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
