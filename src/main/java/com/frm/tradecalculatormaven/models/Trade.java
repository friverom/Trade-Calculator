
package com.frm.tradecalculatormaven.models;

import javafx.beans.property.SimpleStringProperty;

/**
 * This class is an extention of the TradeUnit class but converts all variables
 * to SimpleStringProperty so they can be observe by the table View Object
 * @author frive
 */
public class Trade extends TradeUnit{
    
    SimpleStringProperty stockSymbol, numShares, entryPrice, stopPrice, riskAmount, tgt1, tgt2, tgt3, tradeType;
    
    public Trade(String symbol, double entry, double stop, double risk) {
        super(symbol, entry, stop, risk);
        stockSymbol=new SimpleStringProperty(super.getSymbol());
        numShares=new SimpleStringProperty(String.format("%d",super.getShares()));
        entryPrice=new SimpleStringProperty(String.format("%.2f",super.getEntry()));
        stopPrice=new SimpleStringProperty(String.format("%.2f",super.getStop()));
        tgt1=new SimpleStringProperty(String.format("%.2f",super.getTarget1()));
        tgt2=new SimpleStringProperty(String.format("%.2f",super.getTarget2()));
        tgt3=new SimpleStringProperty(String.format("%.2f",super.getTarget3()));
        tradeType=new SimpleStringProperty(super.getType());
        riskAmount=new SimpleStringProperty(String.format("%.2f",super.getRisk()));
        
    }

    public String getStockSymbol() {
        return stockSymbol.get();
    }

    public String getNumShares() {
        return numShares.get();
    }

    public String getEntryPrice() {
        return entryPrice.get();
    }

    public String getStopPrice() {
        return stopPrice.get();
    }

    public String getRiskAmount() {
        return riskAmount.get();
    }

    public String getTgt1() {
        return tgt1.get();
    }

    public String getTgt2() {
        return tgt2.get();
    }

    public String getTgt3() {
        return tgt3.get();
    }

    public String getTradeType() {
        return tradeType.get();
    }
    
   
    
    
    
    
}
