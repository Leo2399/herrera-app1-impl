package baseline;

import javafx.beans.property.SimpleStringProperty;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class EventsTest {

    private Events events;

    @BeforeEach
    void setUp() {
        events = new Events("Title", "Description");
    }

    @Test
    void testFactoryMethods(){
        assertEquals("Title",events.getTitle());
        assertEquals("Description",events.getDescription());
        assertEquals(LocalDate.now(),events.getDueDate());
        assertEquals("Incomplete", events.getStatus());

        System.out.println("All match");
    }
    /*
    Testing to make sure data toString is giving the correct data
     */
    @Test
    void testToString() {
        String expected = "Title 2021-11-07 Description Incomplete";
        assertEquals(expected,events.toString());
        System.out.println("Expected: "+expected+"\n"
        +"Actual: "+events.toString());
    }
}