package com.example.bread;

import org.junit.Test;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ValueValidatorTest {
    @Test
    public void valueValidator_CorrectValueSimple_ReturnsTrue(){
        assertTrue(ValueValidator.isValidValue("12.99"));
    }
    @Test
    public void valueValidator_CorrectValueInt_ReturnsTrue(){
        assertTrue(ValueValidator.isValidValue("100"));
    }
    @Test
    public void valueValidator_InvalidValue_ReturnsFalse(){
        assertFalse(ValueValidator.isValidValue("1.00.00"));
    }
    @Test
    public void valueValidator_InvalidValueChar_ReturnsFalse(){
        assertFalse(ValueValidator.isValidValue("a.00"));
    }
    @Test
    public void valueValidator_InvalidValueChar2_ReturnsFalse(){
        assertFalse(ValueValidator.isValidValue("1.aa"));
    }
    @Test
    public void valueValidator_InvalidValueChar3_ReturnsFalse(){
        assertFalse(ValueValidator.isValidValue("a"));
    }
    @Test
    public void valueValidator_InvalidValueNull_ReturnsFalse(){
        assertFalse(ValueValidator.isValidValue(""));
    }
}
