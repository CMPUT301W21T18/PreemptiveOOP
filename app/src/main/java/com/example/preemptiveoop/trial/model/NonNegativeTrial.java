package com.example.preemptiveoop.trial.model;

import com.example.preemptiveoop.uiwidget.model.MyLocation;

import java.util.Date;

/**
 * The NonNegativeTrial class is used to store and manage a non-negative trial result. The result is
 * stored as a Integer.
 */
public class NonNegativeTrial extends GenericTrial {
    Integer result;

    public NonNegativeTrial() {}
    public NonNegativeTrial(String creator, Date creationDate, MyLocation location, Integer result, boolean isIgnored) {
        super(creator, creationDate, location, result.toString(), isIgnored);
        this.result = result;
    }

    @Override
    public Number getResult() { return result; }

    @Override
    public void setResult_(Number result) {
        if (!(result instanceof Integer))
            throw new IllegalArgumentException("resultNum for NonNegativeTrial must be Integer.");

        if (result.doubleValue() < 0)
            throw new IllegalArgumentException("resultNum for NonNegativeTrial must be >= 0.");

        super.setResultStr(result.toString());
        this.result = result.intValue();
    }
}
