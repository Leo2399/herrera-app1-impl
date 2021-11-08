package baseline;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

class LocalDateTableCellTest {
    private String pattern = "YYYY-MM-DD";

    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
    private LocalDateTableCell test;
    private Events e = new Events("", "");

    @Test
    void testStringPattern(){
        LocalDate testDate = LocalDate.now();
        assertEquals(e.getDueDate().format(dateTimeFormatter), dateTimeFormatter.format(testDate));


    }
}