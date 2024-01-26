package com.stockapp.main.DTOs;

import java.util.Arrays;
import java.util.Objects;

public class Aggregates {

	private boolean adjusted;
    private String next_url;
    private long queryCount;
    private String request_id;
    
    private AggregatesResult[] results;
    
    private long resultsCount;
    private String status;
    private String ticker;

    @Override
    public String toString() {
        return "Aggregates{" + "adjusted=" + adjusted + ", next_url=" + next_url + ", queryCount=" + queryCount + ", request_id=" + request_id + ", results=" + results + ", resultsCount=" + resultsCount + ", status=" + status + ", ticker=" + ticker + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + (this.adjusted ? 1 : 0);
        hash = 47 * hash + Objects.hashCode(this.next_url);
        hash = 47 * hash + (int) (this.queryCount ^ (this.queryCount >>> 32));
        hash = 47 * hash + Objects.hashCode(this.request_id);
        hash = 47 * hash + Arrays.deepHashCode(this.results);
        hash = 47 * hash + (int) (this.resultsCount ^ (this.resultsCount >>> 32));
        hash = 47 * hash + Objects.hashCode(this.status);
        hash = 47 * hash + Objects.hashCode(this.ticker);
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
        final Aggregates other = (Aggregates) obj;
        if (this.adjusted != other.adjusted) {
            return false;
        }
        if (this.queryCount != other.queryCount) {
            return false;
        }
        if (this.resultsCount != other.resultsCount) {
            return false;
        }
        if (!Objects.equals(this.next_url, other.next_url)) {
            return false;
        }
        if (!Objects.equals(this.request_id, other.request_id)) {
            return false;
        }
        if (!Objects.equals(this.status, other.status)) {
            return false;
        }
        if (!Objects.equals(this.ticker, other.ticker)) {
            return false;
        }
        if (!Arrays.deepEquals(this.results, other.results)) {
            return false;
        }
        return true;
    }

    public boolean isAdjusted() {
        return adjusted;
    }

    public void setAdjusted(boolean adjusted) {
        this.adjusted = adjusted;
    }

    public String getNext_url() {
        return next_url;
    }

    public void setNext_url(String next_url) {
        this.next_url = next_url;
    }

    public long getQueryCount() {
        return queryCount;
    }

    public void setQueryCount(long queryCount) {
        this.queryCount = queryCount;
    }

    public String getRequest_id() {
        return request_id;
    }

    public void setRequest_id(String request_id) {
        this.request_id = request_id;
    }

    public AggregatesResult[] getResults() {
        return results;
    }

    public void setResults(AggregatesResult[] results) {
        this.results = results;
    }

    public long getResultsCount() {
        return resultsCount;
    }

    public void setResultsCount(long resultsCount) {
        this.resultsCount = resultsCount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }
}
