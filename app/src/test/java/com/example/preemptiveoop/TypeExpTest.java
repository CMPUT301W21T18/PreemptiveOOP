package com.example.preemptiveoop;

import android.location.Location;

import com.example.preemptiveoop.experiment.model.BinomialExp;
import com.example.preemptiveoop.experiment.model.CountExp;
import com.example.preemptiveoop.experiment.model.Experiment;
import com.example.preemptiveoop.experiment.model.NonNegativeExp;
import com.example.preemptiveoop.trial.model.BinomialTrial;
import com.example.preemptiveoop.trial.model.CountTrial;
import com.example.preemptiveoop.trial.model.GenericTrial;
import com.example.preemptiveoop.trial.model.NonNegativeTrial;


import java.util.Date;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TypeExpTest {

    private BinomialExp mockBinoExperiment(){
         return new BinomialExp("TestId","TestOwner",
                 null,"This is a mock binomial Experimental",
                 null, false,8);
    }
    private CountExp mockCountExperiment(){
        return new CountExp("TestId","TestOwner",
                null,"This is a mock binomial Experimental",
                null, false,8);
    }
    private NonNegativeExp mockNonNegExperiment(){
        return new NonNegativeExp("TestId","TestOwner",
                null,"This is a mock binomial Experimental",
                null, false,8);
    }
    private GenericTrial mockFalseTrial(){
        return new GenericTrial("Charlie", null, null, "0",false);
    }
    private GenericTrial mockTrueTrial(){
        return new GenericTrial("Lucy", null, null, "1",false);
    }
    private GenericTrial mockIllegalTrial(){
        return new GenericTrial("Felix", null, null, "-3",false);
    }

    @Test
    public void TestAddLegalTrials_Bino(){
        BinomialExp binomialExp = mockBinoExperiment();
        binomialExp.addTrial(mockFalseTrial().toBinomialTrial());
        binomialExp.addTrial(mockTrueTrial().toBinomialTrial());
        assertEquals(2,binomialExp.getTrials().size());
    }

    @Test
    public void TestIllegalTrials_Bino(){
        BinomialExp binomialExp = mockBinoExperiment();
        //binomialExp.addTrial(mockIllegalTrial());
        assertThrows(IllegalArgumentException.class, () -> {
            binomialExp.addTrial(mockIllegalTrial().toBinomialTrial());
        });
    }
    @Test
    public void TestAddLegalTrials_Count(){
        CountExp countExp = mockCountExperiment();
        countExp.addTrial(mockTrueTrial().toCountTrial());
        countExp.addTrial(mockTrueTrial().toCountTrial());
        assertEquals(2,countExp.getTrials().size());
    }

    @Test
    public void TestIllegalTrials_Count(){
        CountExp countExp = mockCountExperiment();
        assertThrows(IllegalArgumentException.class, () -> {
            countExp.addTrial(mockIllegalTrial().toCountTrial());
        });
    }
    @Test
    public void TestAddLegalTrials_NonN(){
        NonNegativeExp nonNegativeExp = mockNonNegExperiment();
        nonNegativeExp.addTrial(mockFalseTrial().toNonNegativeTrial());
        nonNegativeExp.addTrial(mockTrueTrial().toNonNegativeTrial());
        assertEquals(2,nonNegativeExp.getTrials().size());
    }

    @Test
    public void TestIllegalTrials_NonN(){
        NonNegativeExp nonNegativeExp = mockNonNegExperiment();
        assertThrows(IllegalArgumentException.class, () -> {
            nonNegativeExp.addTrial(mockIllegalTrial().toNonNegativeTrial());
        });
    }

}
