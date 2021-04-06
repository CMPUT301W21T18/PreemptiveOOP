package com.example.preemptiveoop;

import com.example.preemptiveoop.experiment.model.BinomialExp;
import com.example.preemptiveoop.experiment.model.CountExp;
import com.example.preemptiveoop.experiment.model.Experiment;
import com.example.preemptiveoop.experiment.model.GenericExperiment;
import com.example.preemptiveoop.experiment.model.MeasurementExp;
import com.example.preemptiveoop.experiment.model.NonNegativeExp;
import com.example.preemptiveoop.trial.model.GenericTrial;

import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ExperimentTest {
    private Experiment mockExperiment(){
        Experiment experiment = new Experiment("TESTid", "Binomial", "Test Owner", null, "This is a mock Experiment",
                null, false, 8);
        return experiment;
    }
    private GenericTrial mockTrial(){
        return new GenericTrial("Jack D",null,null,"1",false);
    }
    @Ignore
    public void testWriteToDatabase(){
        Experiment experiment = new Experiment(null, "Binomial", "Test Owner", null, "This is a mock Experiment",
                null, false, 8);
        experiment.writeToDatabase();
        assertFalse(experiment.getDatabaseId() == null);
    }
    @Test
    public void testAddTrial(){
        Experiment experiment = mockExperiment();
        GenericTrial trial = mockTrial();
        experiment.addTrial(trial);
        assertEquals(1, experiment.getTrials().size());
        assertTrue(experiment.getTrials().contains(trial));
    }
    @Test
    public void testAddExperimenter(){
        Experiment experiment = mockExperiment();
        experiment.addExperimenter("Charles Dickens");
        assertEquals(1, experiment.getExperimenters().size());
        assertTrue(experiment.getExperimenters().contains("Charles Dickens"));
    }

    @Test
    public void testSetStatus(){
        Experiment experiment = mockExperiment();
        experiment.setStatus("Binomial");
        assertEquals(experiment.getStatus(), "Binomial");
    }

    @Test
    public void testGenericExp(){
        Experiment experiment = mockExperiment();
        GenericExperiment experiment1 = new GenericExperiment(experiment);
        assertTrue(experiment1 instanceof GenericExperiment);
    }

    @Test
    public void testToBinoExp(){
        Experiment experiment = mockExperiment();
        GenericExperiment experiment1 = new GenericExperiment(experiment);
        BinomialExp BinoExperiment = experiment1.toBinomialExp();
        assertTrue(BinoExperiment instanceof BinomialExp);
    }

    @Test
    public void testToMeasureExp(){
        Experiment experiment = mockExperiment();
        GenericExperiment experiment1 = new GenericExperiment(experiment);
        MeasurementExp MeasExperiment = experiment1.toMeasurementExp();
        assertTrue(MeasExperiment instanceof MeasurementExp);
    }

    @Test
    public void testToCountExp(){
        Experiment experiment = mockExperiment();
        GenericExperiment experiment1 = new GenericExperiment(experiment);
        CountExp countExperiment = experiment1.toCountExp();
        assertTrue(countExperiment instanceof CountExp);
    }

    @Test
    public void testToNNExp(){
        Experiment experiment = mockExperiment();
        GenericExperiment experiment1 = new GenericExperiment(experiment);
        NonNegativeExp nnExperiment = experiment1.toNonNegativeExp();
        assertTrue(nnExperiment instanceof NonNegativeExp);
    }

    @Test
    public void testToCorrExp_Bino(){
        Experiment experiment = mockExperiment();
        GenericExperiment experiment1 = new GenericExperiment(experiment);
        experiment1.setStatus("Binomal");
        Experiment experiment2 = experiment1.toCorrespondingExp();
        assertEquals(experiment2.getStatus(),"Binomal");
    }

    @Test
    public void testToCorrExp_Count(){
        Experiment experiment = mockExperiment();
        GenericExperiment experiment1 = new GenericExperiment(experiment);
        experiment1.setStatus("Count");
        Experiment experiment2 = experiment1.toCorrespondingExp();
        assertEquals(experiment2.getStatus(),"Count");
    }
    @Test
    public void testToCorrExp_Mea(){
        Experiment experiment = mockExperiment();
        GenericExperiment experiment1 = new GenericExperiment(experiment);
        experiment1.setStatus("Measurement");
        Experiment experiment2 = experiment1.toCorrespondingExp();
        assertEquals(experiment2.getStatus(),"Measurement");
    }
    @Test
    public void testToCorrExp_NonN(){
        Experiment experiment = mockExperiment();
        GenericExperiment experiment1 = new GenericExperiment(experiment);
        experiment1.setStatus("NonNegative");
        Experiment experiment2 = experiment1.toCorrespondingExp();
        assertEquals(experiment2.getStatus(),"NonNegative");
    }

}

