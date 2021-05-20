
package com.frm.tradecalculatormaven.models;

/**
 * This Class holds all the trade information and provides getters for all
 * variables
 * @author frive
 */
public class TradeUnit {
    
    private String symbol,type;
    private int shares;
    private double entry,stop,risk,target1,target2,target3;
    
    /**
     * Class Constructor
     * @param symbol
     * @param entry
     * @param stop
     * @param risk 
     */
    public TradeUnit(String symbol, double entry, double stop, double risk) {
        this.symbol = symbol;
        this.entry = entry;
        this.stop = stop;
        this.risk = risk;
        
        this.calcTrade();
    }

    public String getSymbol() {
        return symbol;
    }

    public int getShares() {
        return shares;
    }

    public double getEntry() {
        return entry;
    }

    public double getStop() {
        return stop;
    }

    public double getTarget1() {
        return target1;
    }

    public double getTarget2() {
        return target2;
    }

    public double getTarget3() {
        return target3;
    }
    
    public String getType(){
        return type;
    }
    
    public double getRisk(){
        return risk;
    }
   
    // This method calculates all the trade share size, targets and stop loss.
    private void calcTrade(){
        
        double rgn=Math.abs(entry-stop);
        shares=(int)(risk/(rgn));
        if((entry-stop)>0){
            type="LONG";
            target1=entry+rgn;
            target2=entry+2*rgn;
            target3=entry+3*rgn;
        }else{
            type="SHORT";
            target1=entry-rgn;
            target2=entry-2*rgn;
            target3=entry-3*rgn;
        }
    }
    
    
    
    
}
