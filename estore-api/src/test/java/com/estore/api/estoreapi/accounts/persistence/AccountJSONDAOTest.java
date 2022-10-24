package com.estore.api.estoreapi.accounts.persistence;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.estore.api.estoreapi.accounts.model.Account;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("Persistence-tier")
public class AccountJSONDAOTest {
	AccountJSONDAO accountJSONDAO;
    Account[] testAccounts;
    ObjectMapper mockObjectMapper;

    /**
     * Before each test, we will create and inject a Mock Object Mapper to
     * isolate the tests from the underlying file
     * @throws IOException
     */
    @BeforeEach
    public void setupAccountJSONDAO() throws IOException {
        mockObjectMapper = mock(ObjectMapper.class);
        testAccounts = new Account[3];
        testAccounts[0] = new Account(99, "Adrian", "AdrianPass");
        testAccounts[1] = new Account(100, "Louan", "LouanPass");
        testAccounts[2] = new Account(101, "Norton", "NortonPass");

        // When the object mapper is supposed to read from the file
        // the mock object mapper will return the account array above
        when(mockObjectMapper
            .readValue(new File("doesnt_matter.txt"), Account[].class))
                .thenReturn(testAccounts);
        accountJSONDAO = new AccountJSONDAO("doesnt_matter.txt", mockObjectMapper);
    }

    @Test
    public void testGetAccounts() {
        // Arrange
        Account[] accounts = accountJSONDAO.getAccounts();

        // Analyze
        assertEquals(accounts.length,testAccounts.length);
        for (int i = 0; i < testAccounts.length;++i)
            assertEquals(accounts[i],testAccounts[i]);
    }

    @Test
    public void testFindAccounts() {
        // Arrange
        Account[] accounts = accountJSONDAO.findAccounts("an");

        // Analyze
        assertEquals(accounts.length,2);
        assertEquals(accounts[0],testAccounts[0]);
        assertEquals(accounts[1],testAccounts[1]);
    }

    @Test
    public void testGetAccount() {
        // Arrange
        Account account = accountJSONDAO.getAccount(99);

        // Analzye
        assertEquals(account,testAccounts[0]);
    }

    @Test
    public void testDeleteAccount() {
        // Arrange
        boolean result = assertDoesNotThrow(() -> accountJSONDAO.deleteAccount(99),
                            "Unexpected exception thrown");

        // Analzye
        assertEquals(result,true);
        // We check the internal tree map size against the length
        // of the test accounts array - 1 (because of the delete)
        // Because accounts attribute of AccountJSONDAO is package private
        // we can access it directly
        assertEquals(accountJSONDAO.accounts.size(),testAccounts.length-1);
    }

    @Test
    public void testCreateAccount() {
        // Setup
        Account account = new Account(102, "Oscar", "OscarPass");

        // Arrange
        Account result = assertDoesNotThrow(() -> accountJSONDAO.createAccount(account),
                                "Unexpected exception thrown");

        // Analyze
        assertNotNull(result);
        Account actual = accountJSONDAO.getAccount(account.getId());
        assertEquals(actual.getId(),account.getId());
        assertEquals(actual.getUsername(),account.getUsername());
    }

    @Test
    public void testUpdateAccount() {
        // Setup
        Account account = new Account(99, "Gino", "GinoPass");

        // Arrange
        Account result = assertDoesNotThrow(() -> accountJSONDAO.updateAccount(account),
                                "Unexpected exception thrown");

        // Analyze
        assertNotNull(result);
        Account actual = accountJSONDAO.getAccount(account.getId());
        assertEquals(actual,account);
    }

    @Test
    public void testSaveException() throws IOException{
        doThrow(new IOException())
            .when(mockObjectMapper)
                .writeValue(any(File.class),any(Account[].class));

        Account account = new Account(102, "Sudhir", "SudhirPass");

        assertThrows(IOException.class,
                        () -> accountJSONDAO.createAccount(account),
                        "IOException not thrown");
    }

    @Test
    public void testGetAccountNotFound() {
        // Arrange
        Account account = accountJSONDAO.getAccount(98);

        // Analyze
        assertEquals(account,null);
    }

    @Test
    public void testDeleteAccountNotFound() {
        // Arrange
        boolean result = assertDoesNotThrow(() -> accountJSONDAO.deleteAccount(98),
                                                "Unexpected exception thrown");

        // Analyze
        assertEquals(result,false);
        assertEquals(accountJSONDAO.accounts.size(),testAccounts.length);
    }

    @Test
    public void testUpdateAccountNotFound() {
        // Setup
        Account account = new Account(98, "Munson", "DJPass");

        // Arrange
        Account result = assertDoesNotThrow(() -> accountJSONDAO.updateAccount(account),
                                                "Unexpected exception thrown");

        // Analyze
        assertNull(result);
    }

    @Test
    public void testConstructorException() throws IOException {
        // Setup
        ObjectMapper mockObjectMapper = mock(ObjectMapper.class);
        // We want to simulate with a Mock Object Mapper that an
        // exception was raised during JSON object deseerialization
        // into Java objects
        // When the Mock Object Mapper readValue method is called
        // from the AccountJSONDAO load method, an IOException is
        // raised
        doThrow(new IOException())
            .when(mockObjectMapper)
                .readValue(new File("doesnt_matter.txt"),Account[].class);

        // Arrange & Analyze
        assertThrows(IOException.class,
                        () -> new AccountJSONDAO("doesnt_matter.txt",mockObjectMapper),
                        "IOException not thrown");
    }
}
