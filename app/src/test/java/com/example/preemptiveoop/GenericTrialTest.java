package com.example.preemptiveoop;
import com.example.preemptiveoop.trial.model.BinomialTrial;
import com.example.preemptiveoop.trial.model.CountTrial;
import com.example.preemptiveoop.trial.model.GenericTrial;
import com.example.preemptiveoop.trial.model.MeasurementTrial;
import com.example.preemptiveoop.trial.model.NonNegativeTrial;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class GenericTrialTest {
    private GenericTrial mockTrial(){
        return new GenericTrial("Jack D",null,null,"1",false);
    }

    @Test
    public void testToBinoTrial(){
        GenericTrial trail = mockTrial();
        BinomialTrial trail2 = trail.toBinomialTrial();
        assertTrue(trail2 instanceof BinomialTrial);
    }

    @Test
    public void testToMeaTrial(){
        GenericTrial trail = mockTrial();
        MeasurementTrial trail2 = trail.toMeasurementTrial();
        assertTrue(trail2 instanceof MeasurementTrial);
    }
    @Test
    public void testToNonTrial(){
        GenericTrial trail = mockTrial();
        NonNegativeTrial trail2 = trail.toNonNegativeTrial();
        assertTrue(trail2 instanceof NonNegativeTrial);
    }
    @Test
    public void testToCountTrial(){
        GenericTrial trail = mockTrial();
        CountTrial trail2 = trail.toCountTrial();
        assertTrue(trail2 instanceof CountTrial);
    }
}

