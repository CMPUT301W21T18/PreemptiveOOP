package com.example.preemptiveoop.trial.model;

import com.example.preemptiveoop.uiwidget.model.MyLocation;

import java.util.Date;

/**
 * The BinomialTrial class is used to store and manage a binomial trial result. The result is stored
 * as a Integer.
 */
public class BinomialTrial extends GenericTrial {
    Integer result;

    public BinomialTrial() {}
    public BinomialTrial(String creator, Date creationDate, MyLocation location, Integer result, boolean isIgnored) {
        super(creator, creationDate, location, result.toString(), isIgnored);
        this.result = result;
    }

    @Override
    public Number getResult() { return result; }

    @Override
    public void setResult_(Number result) {
        if (!(result instanceof Integer))
            throw new IllegalArgumentException("resultNum for BinomialTrial must be Integer.");

        if (result.intValue() != 0 && result.intValue() != 1)
            throw new IllegalArgumentException("resultNum for BinomialTrial must be 1 or 0.");

        super.setResultStr(result.toString());
        this.result = result.intValue();
    }
}
