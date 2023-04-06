package com.p.my.prog.helpers.service;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.p.my.prog.helpers.service.StringReplacer;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StringReplacerTest {

    @Autowired
    private StringReplacer stringReplacer;

    @Test
    public void testReplaceTopic_SingleOccurrence() {
        String inputString = "What is the definition of this #topic#?";
        String expected = "What is the definition of this Java programming language?";
        assertEquals(expected, stringReplacer.replaceTopic(inputString, "Java programming language"));
    }

    @Test
    public void testReplaceTopic_MultipleOccurrences() {
        String inputString = "This #topic# is about #topic#.";
        String expected = "This Java programming language is about Java programming language.";
        assertEquals(expected, stringReplacer.replaceTopic(inputString, "Java programming language"));
    }

    @Test
    public void testReplaceTopic_NoMatch() {
        String inputString = "No match here.";
        String expected = "No match here.";
        assertEquals(expected, stringReplacer.replaceTopic(inputString, "Java programming language"));
    }

    @Test
    public void testReplaceTopic_EmptyString() {
        String inputString = "";
        String expected = "";
        assertEquals(expected, stringReplacer.replaceTopic(inputString, "Java programming language"));
    }

    @Test
    public void testReplaceTopic_NullInput() {
        String inputString = null;
        String expected = null;
        assertEquals(expected, stringReplacer.replaceTopic(inputString, "Java programming language"));
    }
}
