package com.example.preemptiveoop;

import com.example.preemptiveoop.experiment.model.Experiment;
import com.example.preemptiveoop.experiment.model.Trial;

import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ExperimentTest {
    private Experiment mockExperiment(){
        Experiment experiment = new Experiment("TESTid", "Binomial", "Test Owner", null, "This is a mock Experiment",
                null, false, 8);
        return experiment;
    }
    private Trial mockTrial(){
        return new Trial("Jack D",null,null,1);
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
        Trial trial = mockTrial();
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
}
