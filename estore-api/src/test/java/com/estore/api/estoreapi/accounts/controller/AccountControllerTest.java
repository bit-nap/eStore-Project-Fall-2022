package com.estore.api.estoreapi.accounts.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.estore.api.estoreapi.accounts.model.Account;
import com.estore.api.estoreapi.accounts.persistence.AccountDAO;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Tag("Controller-tier")
public class AccountControllerTest {
	private AccountController accountController;
	private AccountDAO mockAccountDAO;

	/**
	 * Before each test, create a new AccountController object and inject a mock Account DAO
	 */
	@BeforeEach
	public void setupAccountController() {
		mockAccountDAO = mock(AccountDAO.class);
		accountController = new AccountController(mockAccountDAO);
	}

    @Test
    public void testGetAccount() throws IOException {  // getAccount may throw IOException
        // Arrange
        Account account = new Account(99, "Adrian", "password0");
        // When the same id is passed in, our mock Account DAO will return the Account object
        when(mockAccountDAO.getAccount(account.getId())).thenReturn(account);

        // Invoke
        ResponseEntity<Account> response = accountController.getAccount(account.getId());

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(account,response.getBody());
    }

    @Test
    public void testGetAccountNotFound() throws Exception { // createAccount may throw IOException
        // Arrange
        int accountId = 99;
        // When the same id is passed in, our mock Account DAO will return null, simulating
        // no account found
        when(mockAccountDAO.getAccount(accountId)).thenReturn(null);

        // Invoke
        ResponseEntity<Account> response = accountController.getAccount(accountId);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    @Test
    public void testGetAccountHandleException() throws Exception { // createAccount may throw IOException
        // Arrange
        int accountId = 99;
        // When getAccount is called on the Mock Account DAO, throw an IOException
        doThrow(new IOException()).when(mockAccountDAO).getAccount(accountId);

        // Invoke
        ResponseEntity<Account> response = accountController.getAccount(accountId);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testCreateAccount() throws IOException {  // createAccount may throw IOException
        // Arrange
        Account account = new Account(99, "Gino", "password1");
        // when createAccount is called, return true simulating successful
        // creation and save
        when(mockAccountDAO.createAccount(account)).thenReturn(account);

        // Invoke
        ResponseEntity<Account> response = accountController.createAccount(account);

        // Analyze
        assertEquals(HttpStatus.CREATED,response.getStatusCode());
        assertEquals(account,response.getBody());
    }

    @Test
    public void testCreateAccountFailed() throws IOException {  // createAccount may throw IOException
        // Arrange
        Account account = new Account(99, "Oscar", "password2");
        // when createAccount is called, return false simulating failed
        // creation and save
        when(mockAccountDAO.createAccount(account)).thenReturn(null);

        // Invoke
        ResponseEntity<Account> response = accountController.createAccount(account);

        // Analyze
        assertEquals(HttpStatus.CONFLICT,response.getStatusCode());
    }

    @Test
    public void testCreateAccountHandleException() throws IOException {  // createAccount may throw IOException
        // Arrange
        Account account = new Account(99, "Norton", "password3");

        // When createAccount is called on the Mock Account DAO, throw an IOException
        doThrow(new IOException()).when(mockAccountDAO).createAccount(account);

        // Invoke
        ResponseEntity<Account> response = accountController.createAccount(account);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testUpdateAccount() throws IOException { // updateAccount may throw IOException
        // Arrange
        Account account = new Account(99, "Louan", "password4");
        // when updateAccount is called, return true simulating successful
        // update and save
        when(mockAccountDAO.updateAccount(account)).thenReturn(account);
        ResponseEntity<Account> response = accountController.updateAccount(account);
        account.setName("BetterLouan");

        // Invoke
        response = accountController.updateAccount(account);

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(account,response.getBody());
    }

    @Test
    public void testUpdateAccountFailed() throws IOException { // updateAccount may throw IOException
        // Arrange
        Account account = new Account(99, "Sudhir", "123");
        // when updateAccount is called, return true simulating successful
        // update and save
        when(mockAccountDAO.updateAccount(account)).thenReturn(null);

        // Invoke
        ResponseEntity<Account> response = accountController.updateAccount(account);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    @Test
    public void testUpdateAccountHandleException() throws IOException { // updateAccount may throw IOException
        // Arrange
        Account account = new Account(99, "Sudhir", "456");
        // When updateAccount is called on the Mock Account DAO, throw an IOException
        doThrow(new IOException()).when(mockAccountDAO).updateAccount(account);

        // Invoke
        ResponseEntity<Account> response = accountController.updateAccount(account);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testGetAccounts() throws IOException { // getAccounts may throw IOException
        // Arrange
        Account[] accounts = new Account[2];
        accounts[0] = new Account(99, "Adrian", "password");
        accounts[1] = new Account(100, "Sudhir", "betterPassword");
        // When getAccounts is called return the accounts created above
        when(mockAccountDAO.getAccounts()).thenReturn(accounts);

        // Invoke
        ResponseEntity<Account[]> response = accountController.getAccounts();

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(accounts,response.getBody());
    }

    @Test
    public void testGetAccountsHandleException() throws IOException { // getAccounts may throw IOException
        // Arrange
        // When getAccounts is called on the Mock Account DAO, throw an IOException
        doThrow(new IOException()).when(mockAccountDAO).getAccounts();

        // Invoke
        ResponseEntity<Account[]> response = accountController.getAccounts();

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testSearchAccounts() throws IOException { // findAccounts may throw IOException
        // Arrange
        String searchString = "an";
        Account[] accounts = new Account[2];
        accounts[0] = new Account(99, "Adrian", "password");
        accounts[1] = new Account(100, "Louan", "password");
        // When findAccounts is called with the search string, return the two
        /// accounts above
        when(mockAccountDAO.findAccounts(searchString)).thenReturn(accounts);

        // Invoke
        ResponseEntity<Account[]> response = accountController.findAccounts(searchString);

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(accounts,response.getBody());
    }

    @Test
    public void testSearchAccountsHandleException() throws IOException { // findAccounts may throw IOException
        // Arrange
        String searchString = "an";
        // When createAccount is called on the Mock Account DAO, throw an IOException
        doThrow(new IOException()).when(mockAccountDAO).findAccounts(searchString);

        // Invoke
        ResponseEntity<Account[]> response = accountController.findAccounts(searchString);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testDeleteAccount() throws IOException { // deleteAccount may throw IOException
        // Arrange
        int accountId = 99;
        // when deleteAccount is called return true, simulating successful deletion
        when(mockAccountDAO.deleteAccount(accountId)).thenReturn(true);

        // Invoke
        ResponseEntity<Account> response = accountController.deleteAccount(accountId);

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
    }

    @Test
    public void testDeleteAccountNotFound() throws IOException { // deleteAccount may throw IOException
        // Arrange
        int accountId = 99;
        // when deleteAccount is called return false, simulating failed deletion
        when(mockAccountDAO.deleteAccount(accountId)).thenReturn(false);

        // Invoke
        ResponseEntity<Account> response = accountController.deleteAccount(accountId);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    @Test
    public void testDeleteAccountHandleException() throws IOException { // deleteAccount may throw IOException
        // Arrange
        int accountId = 99;
        // When deleteAccount is called on the Mock Account DAO, throw an IOException
        doThrow(new IOException()).when(mockAccountDAO).deleteAccount(accountId);

        // Invoke
        ResponseEntity<Account> response = accountController.deleteAccount(accountId);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }
}
