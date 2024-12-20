import User.*;
import java.sql.*;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import java.sql.Connection;

public class DatabaseConnectionTest {
    
    private DatabaseConnection dbConnection;

    public DatabaseConnectionTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        System.out.println("Starting DatabaseConnection tests...");
    }
    
    @AfterClass
    public static void tearDownClass() {
        System.out.println("Finished DatabaseConnection tests.");
    }
    
    @Before
    public void setUp() throws SQLException {
        dbConnection = DatabaseConnection.getInstance();
    }
    
    @After
    public void tearDown() {
        dbConnection = null;
    }

    @Test
    public void testSingletonInstance() throws SQLException {
        // Test if getInstance() returns the same instance
        DatabaseConnection instance1 = DatabaseConnection.getInstance();
        DatabaseConnection instance2 = DatabaseConnection.getInstance();
        assertSame(instance1, instance2);
    }

    @Test
    public void testGetConnection() {
        // Test if the connection is not null
        Connection connection = dbConnection.getConnection();
        assertNotNull("Connection should not be null", connection);
    }

    @Test
    public void testConnectionIsValid() {
        // Test if the connection is valid
        try {
            Connection connection = dbConnection.getConnection();
            assertTrue("Connection should be valid", connection.isValid(2));
        } catch (Exception e) {
            fail("Connection validation failed with exception: " + e.getMessage());
        }
    }
}
