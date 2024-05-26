package INFSUS.project.PRO.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class RefactorStringTest {

    @Test
    public void testRefactor() {
        String input1 = "hello";
        String output1 = RefactorString.refactor(input1);
        assertEquals("Hello", output1);

        String input2 = "WORLD";
        String output2 = RefactorString.refactor(input2);
        assertEquals("World", output2);

        String input3 = "gOoD mOrNiNg";
        String output3 = RefactorString.refactor(input3);
        assertEquals("Good morning", output3);
    }
}
