
package com.frm.tradecalculatormaven.models;

/**
 * This class has methods to handle all trades to and from Trade File.
 * @author frive
 */
public class FileUtilities {
    
    //Takes a "Trade" Object and converts all variables to a String so it can be
    //save as CSV file.
    public static String getTradeString(Trade t){
        String str=t.getStockSymbol()+",";
        str+=t.getRiskAmount()+",";
        str+=t.getNumShares()+",";
        str+=t.getEntryPrice()+",";
        str+=t.getStopPrice()+",";
        str+=t.getTradeType();
        
        return str;
    }
    
    //Takes a String with CSV values and converts them to a "Trade" Object
    public static Trade getTrade(String str){
        String[] items=str.split(",");
        
        String symbol=items[0];
        double entry=Double.parseDouble(items[3]);
        double stop=Double.parseDouble(items[4]);
        double risk=Double.parseDouble(items[1]);
        
        Trade t=new Trade(symbol,entry,stop,risk);
        return t;
        
    }
}
