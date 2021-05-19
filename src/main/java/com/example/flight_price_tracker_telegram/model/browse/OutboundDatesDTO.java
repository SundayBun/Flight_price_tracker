package com.example.flight_price_tracker_telegram.model.browse;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Data transfer object for Outbound Dates.
 *
 */
public class OutboundDatesDTO {

    @JsonProperty("PartialDate")
    private String partialDate;

    @JsonProperty("QuoteDateTime")
    private String quoteDateTime;

    @JsonProperty("Price")
    private String price;

    @JsonProperty("QuoteIds")
    private List<Integer> quoteIds;

    public OutboundDatesDTO() {
    }

    public String getPartialDate() {
        return this.partialDate;
    }

    public String getQuoteDateTime() {
        return this.quoteDateTime;
    }

    public String getPrice() {
        return this.price;
    }

    public List<Integer> getQuoteIds() {
        return this.quoteIds;
    }

    @JsonProperty("PartialDate")
    public void setPartialDate(String partialDate) {
        this.partialDate = partialDate;
    }

    @JsonProperty("QuoteDateTime")
    public void setQuoteDateTime(String quoteDateTime) {
        this.quoteDateTime = quoteDateTime;
    }

    @JsonProperty("Price")
    public void setPrice(String price) {
        this.price = price;
    }

    @JsonProperty("QuoteIds")
    public void setQuoteIds(List<Integer> quoteIds) {
        this.quoteIds = quoteIds;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof OutboundDatesDTO)) return false;
        final OutboundDatesDTO other = (OutboundDatesDTO) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$partialDate = this.getPartialDate();
        final Object other$partialDate = other.getPartialDate();
        if (this$partialDate == null ? other$partialDate != null : !this$partialDate.equals(other$partialDate))
            return false;
        final Object this$quoteDateTime = this.getQuoteDateTime();
        final Object other$quoteDateTime = other.getQuoteDateTime();
        if (this$quoteDateTime == null ? other$quoteDateTime != null : !this$quoteDateTime.equals(other$quoteDateTime))
            return false;
        final Object this$price = this.getPrice();
        final Object other$price = other.getPrice();
        if (this$price == null ? other$price != null : !this$price.equals(other$price)) return false;
        final Object this$quoteIds = this.getQuoteIds();
        final Object other$quoteIds = other.getQuoteIds();
        if (this$quoteIds == null ? other$quoteIds != null : !this$quoteIds.equals(other$quoteIds)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof OutboundDatesDTO;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $partialDate = this.getPartialDate();
        result = result * PRIME + ($partialDate == null ? 43 : $partialDate.hashCode());
        final Object $quoteDateTime = this.getQuoteDateTime();
        result = result * PRIME + ($quoteDateTime == null ? 43 : $quoteDateTime.hashCode());
        final Object $price = this.getPrice();
        result = result * PRIME + ($price == null ? 43 : $price.hashCode());
        final Object $quoteIds = this.getQuoteIds();
        result = result * PRIME + ($quoteIds == null ? 43 : $quoteIds.hashCode());
        return result;
    }

    public String toString() {
        return "Outbound Dates: "+ this.getPartialDate()+"\n Price: "+ this.getPrice();
    }
}
