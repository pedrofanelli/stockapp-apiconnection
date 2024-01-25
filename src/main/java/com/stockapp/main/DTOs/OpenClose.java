package com.stockapp.main.DTOs;

import java.util.Objects;

public class OpenClose {

	private String status;
    private String from;
    private String symbol;
    private float open;
    private float high;
    private float low;
    private float close;
    private float volume;
    private float afterHours;
    private float preMarket;

    @Override
    public String toString() {
        return "OpenClose{" + "status=" + status + ", from=" + from + ", symbol=" + symbol + ", open=" + open + ", high=" + high 
                + ", low=" + low + ", close=" + close + ", volume=" + volume + ", afterHours=" + afterHours + ", preMarket=" + preMarket + '}';
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 13 * hash + Objects.hashCode(this.status);
        hash = 13 * hash + Objects.hashCode(this.from);
        hash = 13 * hash + Objects.hashCode(this.symbol);
        hash = 13 * hash + Float.floatToIntBits(this.open);
        hash = 13 * hash + Float.floatToIntBits(this.high);
        hash = 13 * hash + Float.floatToIntBits(this.low);
        hash = 13 * hash + Float.floatToIntBits(this.close);
        hash = 13 * hash + Float.floatToIntBits(this.volume);
        hash = 13 * hash + Float.floatToIntBits(this.afterHours);
        hash = 13 * hash + Float.floatToIntBits(this.preMarket);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final OpenClose other = (OpenClose) obj;
        if (Float.floatToIntBits(this.open) != Float.floatToIntBits(other.open)) {
            return false;
        }
        if (Float.floatToIntBits(this.high) != Float.floatToIntBits(other.high)) {
            return false;
        }
        if (Float.floatToIntBits(this.low) != Float.floatToIntBits(other.low)) {
            return false;
        }
        if (Float.floatToIntBits(this.close) != Float.floatToIntBits(other.close)) {
            return false;
        }
        if (Float.floatToIntBits(this.volume) != Float.floatToIntBits(other.volume)) {
            return false;
        }
        if (Float.floatToIntBits(this.afterHours) != Float.floatToIntBits(other.afterHours)) {
            return false;
        }
        if (Float.floatToIntBits(this.preMarket) != Float.floatToIntBits(other.preMarket)) {
            return false;
        }
        if (!Objects.equals(this.status, other.status)) {
            return false;
        }
        if (!Objects.equals(this.from, other.from)) {
            return false;
        }
        if (!Objects.equals(this.symbol, other.symbol)) {
            return false;
        }
        return true;
    }
    
    

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public float getOpen() {
        return open;
    }

    public void setOpen(float open) {
        this.open = open;
    }

    public float getHigh() {
        return high;
    }

    public void setHigh(float high) {
        this.high = high;
    }

    public float getLow() {
        return low;
    }

    public void setLow(float low) {
        this.low = low;
    }

    public float getClose() {
        return close;
    }

    public void setClose(float close) {
        this.close = close;
    }

    public float getVolume() {
        return volume;
    }

    public void setVolume(float volume) {
        this.volume = volume;
    }

    public float getAfterHours() {
        return afterHours;
    }

    public void setAfterHours(float afterHours) {
        this.afterHours = afterHours;
    }

    public float getPreMarket() {
        return preMarket;
    }

    public void setPreMarket(float preMarket) {
        this.preMarket = preMarket;
    }
	
}
