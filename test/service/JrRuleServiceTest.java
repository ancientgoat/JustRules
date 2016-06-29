package service;

import conf.AppConf;
import conf.TestDataConf;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import javax.inject.Inject;

@ContextConfiguration(classes = {AppConf.class, TestDataConf.class})
public class JrRuleServiceTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Inject private JrRuleService ruleService;

    @Test
    public void testSimpleTest() {
        try {
        } catch (IllegalArgumentException expected) {
        }
    }

//    @Test
//    public void userExistsFalseWithEmptyNonUser() {
//        assertFalse("Empty user shouldn't exist initially",
//                    ruleService.userExists(""));
//    }
//
//    @Test
//    public void userExistsFalseWithNonUser() {
//        assertFalse("No users should have been registered yet",
//                    ruleService.userExists("test"));
//    }
//
//    @Test
//    public void userExistsAfterRegister() {
//        final String username = "test";
//        ruleService.register(username, "test123");
//        assertTrue("User 'test' should exist after being registered",
//                   ruleService.userExists(username));
//    }
//
//    @Test
//    public void userStillExistsAfterSeveralRegisters() {
//        ruleService.register("test", "test123");
//        ruleService.register("bob", "bob123bob");
//        ruleService.register("joe", "123joe");
//
//        //mixed order prevents being duped by stack or queue, minor
//        assertTrue("User 'bob' should exist after being registered",
//                   ruleService.userExists("bob"));
//        assertTrue("User 'joe' should exist after being registered",
//                   ruleService.userExists("joe"));
//        assertTrue("User 'test' should exist after being registered",
//                   ruleService.userExists("test"));
//    }
//
//    @Test
//    public void loginFailsWithNullUser() {
//        try {
//            ruleService.login(null, "test123");
//            fail("login should throw an exception on a null username");
//        } catch (IllegalArgumentException expected) {
//        }
//    }
//
//    @Test
//    public void loginFailsWithNullPassword() {
//        try {
//            ruleService.login("test", null);
//            fail("login should throw an exception on a null password");
//        } catch (IllegalArgumentException expected) {
//        }
//    }
//
//    @Test
//    public void loginFailsWithNonUser() {
//        assertFalse("User 'test' shouldn't exist in database, so can't login",
//                    ruleService.login("test", "test123"));
//    }
//
//    @Test
//    public void loginFailsWithWrongPassword() {
//        final String username = "test";
//        final String password = "test123";
//        final String password2 = "abcd1234";
//        ruleService.register(username, password);
//        assertFalse("User 'test' shouldn't be able to login with the wrong password",
//                    ruleService.login(username, password2));
//    }
//
//    @Test
//    public void loginSucceedsWithRegisteredCredentials() {
//        final String username = "test";
//        final String password = "test123";
//        ruleService.register(username, password);
//        assertTrue("User 'test' should be able to login with the matching password",
//                   ruleService.login(username, password));
//    }
//
//    @Test
//    public void loginSucceedsWithEmptyUser() {
//        final String username = "";
//        final String password = "test123";
//        ruleService.register(username, password);
//        assertTrue("Empty user should exist after being registered",
//                   ruleService.userExists(username));
//        assertTrue("Empty user should be able to login after being registered",
//                   ruleService.login(username, password));
//    }
//
//    @Test
//    public void loginSucceedsWithEmptyPassword() {
//        final String username = "test";
//        final String password = "";
//        ruleService.register(username, password); //no exception expected
//        assertTrue("User should exist after being registered with empty password",
//                   ruleService.userExists(username));
//        assertTrue("User should be able to login with empty password",
//                   ruleService.login(username, password));
//    }
//
//    @Test
//    public void loginSucceedsAfterSeveralRegisters() {
//        ruleService.register("test", "test123");
//        ruleService.register("bob", "bob123bob");
//        ruleService.register("joe", "123joe");
//
//        assertTrue("User 'bob' should exist after being registered",
//                   ruleService.login("bob", "bob123bob"));
//        assertTrue("User 'joe' should exist after being registered",
//                   ruleService.login("joe", "123joe"));
//        assertTrue("User 'test' should exist after being registered",
//                   ruleService.login("test", "test123"));
//    }
//
//    @Test
//    public void registerFailsWithNullUser() {
//        try {
//            ruleService.register(null, "test123");
//            fail("register should throw an exception on a null username");
//        } catch (IllegalArgumentException expected) {
//        }
//    }
//
//    @Test
//    public void registerFailsWithNullPassword() {
//        try {
//            ruleService.register("test", null);
//            fail("register should throw an exception on a null password");
//        } catch (IllegalArgumentException expected) {
//        }
//    }
//
//    @Test
//    public void registerErrorsWithDuplicateUser() {
//        ruleService.register("test", "test123");
//        try {
//            ruleService.register("test", "bob1234");
//            fail("Duplicate registers should cause an exception");
//        } catch (RuntimeException expected) {
//        }
//    }
//
//    @Test
//    public void getBalanceFailsWithNullUser() {
//        try {
//            ruleService.getBalance(null);
//            fail("getBalance should throw an exception on a null username");
//        } catch (IllegalArgumentException expected) {
//        }
//    }
//
//    @Test
//    public void getBalanceGivesNullWithNonUser() {
//        try {
//            ruleService.getBalance("test");
//            fail("Getting balance of non-user 'test' should throw an exception");
//        } catch (RuntimeException expected) {
//        }
//    }
//
//    @Test
//    public void getBalanceGivesDefaultBalanceWithNewUser() {
//        final String username = "test";
//        ruleService.register(username, "test123");
//        assertEquals("New user 'test' should have the correct inital balance",
//                     User.INITIAL_BALANCE, ruleService.getBalance(username));
//    }
//
//    @Test
//    public void getBalanceGivesDefaultBalanceForSeveralNewUsers() {
//        ruleService.register("test", "test123");
//        ruleService.register("bob", "bob123bob");
//        ruleService.register("joe", "123joe");
//
//        assertEquals("New user 'bob' should have the correct inital balance",
//                     User.INITIAL_BALANCE, ruleService.getBalance("bob"));
//        assertEquals("New user 'joe' should have the correct inital balance",
//                     User.INITIAL_BALANCE, ruleService.getBalance("joe"));
//        assertEquals("New user 'test' should have the correct inital balance",
//                     User.INITIAL_BALANCE, ruleService.getBalance("test"));
//    }
//
//    @Test
//    public void setBalanceFailsWithNullUser() {
//        try {
//            ruleService.setBalance(null, 2000L);
//            fail("Expected setBalance to raise exception on null username");
//        } catch (IllegalArgumentException expected) {
//        }
//    }
//
//    @Test
//    public void setBalanceFailsWithNegativeBalance() {
//        final String username = "test";
//        ruleService.register(username, "test123");
//        try {
//            ruleService.setBalance(username, -2000L);
//            fail("Expected setBalance to raise exception on negative balance");
//        } catch (IllegalArgumentException expected) {
//        }
//    }
//
//    @Test
//    public void setBalanceFailsWithNonUser() {
//        try {
//            ruleService.setBalance("test", 2000L);
//            fail("Expected setBalance to raise exception on non-user");
//        } catch (RuntimeException expcected) {
//        }
//    }
//
//    @Test
//    public void setBalanceFailsWithEmptyNonUser() {
//        try {
//            ruleService.setBalance("", 2000L);
//            fail("Expected setBalance to raise exception on empty non-user");
//        } catch (RuntimeException expcected) {
//        }
//    }
//
//    @Test
//    public void setBalanceChangesBalance() {
//        ruleService.register("test", "test123");
//
//        long balance = ruleService.getBalance("test");
//        long newBalance = balance + 100;
//        ruleService.setBalance("test", newBalance);
//
//        assertEquals("Expected last value given to setBalance to be given by getBalance",
//                     newBalance, ruleService.getBalance("test"));
//    }
//
//    @Test
//    public void setBalanceSucceedsWithZeroBalance() {
//        final String username = "test";
//        ruleService.register(username, "test123");
//        ruleService.setBalance(username, 0);
//
//        assertEquals("Balance should be zero after being set to zero",
//                     0, ruleService.getBalance(username));
//    }
}
