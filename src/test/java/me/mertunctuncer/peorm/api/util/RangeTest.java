package me.mertunctuncer.peorm.api.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class RangeTest {

    @Test
    public void value_should_be_contained_within_range() {
        Range range = new Range(1, 10);
        assertTrue(range.contains(5));
    }

    @Test
    public void end_values_should_be_contained() {
        Range range = new Range(1, 10);
        assertTrue(range.contains(1));
        assertTrue(range.contains(10));
    }

    @Test
    public void value_outside_should_not_be_contained() {
        Range range = new Range(1, 10);
        assertFalse(range.contains(15));
    }

    @Test
    public void when_the_ends_are_the_same_the_value_should_be_contained() {
        Range range = new Range(5, 5);
        assertTrue(range.contains(5));
    }
}