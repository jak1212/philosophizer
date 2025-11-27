package org.philosophizer.data;

public class Philosophy {
    public String quote;
    public String saidBy;

    public Philosophy(String quote, String saidBy){
        this.quote = quote;
        this.saidBy = saidBy;
    }

    public Philosophy(){}

    public String getQuote() {
        return quote;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }

    public String getSaidBy() {
        return saidBy;
    }

    public void setSaidBy(String saidBy) {
        this.saidBy = saidBy;
    }
}
