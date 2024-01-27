package me.mertunctuncer.peorm.api.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class RangeTest {

    @Test
    public void contains_ValueWithinRange() {
        Range range = new Range(1, 10);
        assertTrue(range.contains(5));
    }

    @Test
    public void contains_ValueEqualToStartOrEnd() {
        Range range = new Range(1, 10);
        assertTrue(range.contains(1));
        assertTrue(range.contains(10));
    }

    @Test
    public void contains_ValueOutsideRange() {
        Range range = new Range(1, 10);
        assertFalse(range.contains(15));
    }

    @Test
    public void contains_ValueEqualToStartOrEndAndEmptyRange() {
        Range range = new Range(5, 5);
        assertTrue(range.contains(5));
    }
}