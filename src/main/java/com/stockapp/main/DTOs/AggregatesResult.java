package com.stockapp.main.DTOs;

public class AggregatesResult {

	private float c;
    private float h;
    private float l;
    private long n;
    private float o;
    private long t;
    private float v;
    private float vw;

    @Override
    public String toString() {
        return "AggregatesResult{" + "c=" + c + ", h=" + h + ", l=" + l + ", n=" + n + ", o=" + o + ", t=" + t + ", v=" + v + ", vw=" + vw + '}';
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 71 * hash + Float.floatToIntBits(this.c);
        hash = 71 * hash + Float.floatToIntBits(this.h);
        hash = 71 * hash + Float.floatToIntBits(this.l);
        hash = 71 * hash + (int) (this.n ^ (this.n >>> 32));
        hash = 71 * hash + Float.floatToIntBits(this.o);
        hash = 71 * hash + (int) (this.t ^ (this.t >>> 32));
        hash = 71 * hash + Float.floatToIntBits(this.v);
        hash = 71 * hash + Float.floatToIntBits(this.vw);
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
        final AggregatesResult other = (AggregatesResult) obj;
        if (Float.floatToIntBits(this.c) != Float.floatToIntBits(other.c)) {
            return false;
        }
        if (Float.floatToIntBits(this.h) != Float.floatToIntBits(other.h)) {
            return false;
        }
        if (Float.floatToIntBits(this.l) != Float.floatToIntBits(other.l)) {
            return false;
        }
        if (this.n != other.n) {
            return false;
        }
        if (Float.floatToIntBits(this.o) != Float.floatToIntBits(other.o)) {
            return false;
        }
        if (this.t != other.t) {
            return false;
        }
        if (Float.floatToIntBits(this.v) != Float.floatToIntBits(other.v)) {
            return false;
        }
        if (Float.floatToIntBits(this.vw) != Float.floatToIntBits(other.vw)) {
            return false;
        }
        return true;
    }

    
    
    public float getC() {
        return c;
    }

    public void setC(float c) {
        this.c = c;
    }

    public float getH() {
        return h;
    }

    public void setH(float h) {
        this.h = h;
    }

    public float getL() {
        return l;
    }

    public void setL(float l) {
        this.l = l;
    }

    public long getN() {
        return n;
    }

    public void setN(long n) {
        this.n = n;
    }

    public float getO() {
        return o;
    }

    public void setO(float o) {
        this.o = o;
    }

    public long getT() {
        return t;
    }

    public void setT(long t) {
        this.t = t;
    }

    public float getV() {
        return v;
    }

    public void setV(float v) {
        this.v = v;
    }

    public float getVw() {
        return vw;
    }

    public void setVw(float vw) {
        this.vw = vw;
    }
}
