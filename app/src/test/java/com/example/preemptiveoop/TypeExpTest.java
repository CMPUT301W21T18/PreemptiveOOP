package com.example.preemptiveoop;

import android.location.Location;

import com.example.preemptiveoop.experiment.model.BinomialExp;
import com.example.preemptiveoop.experiment.model.CountExp;
import com.example.preemptiveoop.experiment.model.Experiment;
import com.example.preemptiveoop.experiment.model.NonNegativeExp;
import com.example.preemptiveoop.experiment.model.Trial;


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
    private Trial mockFalseTrial(){
        return new Trial("Charlie", null, null, 0);
    }
    private Trial mockTrueTrial(){
        return new Trial("Lucy", null, null, 1);
    }
    private Trial mockIllegalTrial(){
        return new Trial("Felix", null, null, -3);
    }

    @Test
    public void TestAddLegalTrials_Bino(){
        BinomialExp binomialExp = mockBinoExperiment();
        binomialExp.addTrial(mockFalseTrial());
        binomialExp.addTrial(mockTrueTrial());
        assertEquals(2,binomialExp.getTrials().size());
    }

    @Test
    public void TestIllegalTrials_Bino(){
        BinomialExp binomialExp = mockBinoExperiment();
        //binomialExp.addTrial(mockIllegalTrial());
        assertThrows(IllegalArgumentException.class, () -> {
            binomialExp.addTrial(mockIllegalTrial());
        });
    }
    @Test
    public void TestAddLegalTrials_Count(){
        CountExp countExp = mockCountExperiment();
        countExp.addTrial(mockTrueTrial());
        countExp.addTrial(mockTrueTrial());
        assertEquals(2,countExp.getTrials().size());
    }

    @Test
    public void TestIllegalTrials_Count(){
        CountExp countExp = mockCountExperiment();
        assertThrows(IllegalArgumentException.class, () -> {
            countExp.addTrial(mockIllegalTrial());
        });
    }
    @Test
    public void TestAddLegalTrials_NonN(){
        NonNegativeExp nonNegativeExp = mockNonNegExperiment();
        nonNegativeExp.addTrial(mockFalseTrial());
        nonNegativeExp.addTrial(mockTrueTrial());
        assertEquals(2,nonNegativeExp.getTrials().size());
    }

    @Test
    public void TestIllegalTrials_NonN(){
        NonNegativeExp nonNegativeExp = mockNonNegExperiment();
        assertThrows(IllegalArgumentException.class, () -> {
            nonNegativeExp.addTrial(mockIllegalTrial());
        });
    }

}
