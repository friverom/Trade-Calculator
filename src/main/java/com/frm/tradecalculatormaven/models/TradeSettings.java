
package com.frm.tradecalculatormaven.models;

import java.io.Serializable;

/**
 * This class creates a file to save the default risk and the OCO bracket limit
 * 
 * @author frive
 */
public class TradeSettings implements Serializable{
    
    private double risk, ocoLimit;

    public TradeSettings(double risk, double ocoLimit) {
        this.risk = risk;
        this.ocoLimit = ocoLimit;
    }

    public double getRisk() {
        return risk;
    }

    public double getOcoLimit() {
        return ocoLimit;
    }

    public void setRisk(double risk) {
        this.risk = risk;
    }

    public void setOcoLimit(double ocoLimit) {
        this.ocoLimit = ocoLimit;
    }
    
    
    
}
