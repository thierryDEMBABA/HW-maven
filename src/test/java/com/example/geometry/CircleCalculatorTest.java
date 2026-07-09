package com.example.geometry;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CircleCalculatorTest {

    private static final double DELTA = 1e-9;

    @Test
    public void testPerimetreRayonUnitaire() {
        assertEquals(2 * Math.PI, CircleCalculator.perimetre(1.0), DELTA);
    }

    @Test
    public void testPerimetreRayonQuelconque() {
        assertEquals(2 * Math.PI * 5.5, CircleCalculator.perimetre(5.5), DELTA);
    }

    @Test
    public void testPerimetreRayonZero() {
        assertEquals(0.0, CircleCalculator.perimetre(0.0), DELTA);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPerimetreRayonNegatifEchoue() {
        CircleCalculator.perimetre(-1.0);
    }

    @Test
    public void testPerimetreDepuisDiametre() {
        assertEquals(Math.PI * 10.0, CircleCalculator.perimetreDepuisDiametre(10.0), DELTA);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPerimetreDepuisDiametreNegatifEchoue() {
        CircleCalculator.perimetreDepuisDiametre(-2.0);
    }
}
