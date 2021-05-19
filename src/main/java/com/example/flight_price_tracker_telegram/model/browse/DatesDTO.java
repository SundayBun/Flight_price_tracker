package com.example.flight_price_tracker_telegram.model.browse;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Data transfer object for Dates.
 *
 */

public class DatesDTO {
    @JsonProperty("OutboundDates")
    private List<OutboundDatesDTO> outboundDates;

    @JsonProperty("InboundDates")
    private List<InboundDatesDTO> inboundDates;

    public DatesDTO() {
    }

    public List<OutboundDatesDTO> getOutboundDates() {
        return this.outboundDates;
    }

    public List<InboundDatesDTO> getInboundDates() {
        return this.inboundDates;
    }

    @JsonProperty("OutboundDates")
    public void setOutboundDates(List<OutboundDatesDTO> outboundDates) {
        this.outboundDates = outboundDates;
    }

    @JsonProperty("InboundDates")
    public void setInboundDates(List<InboundDatesDTO> inboundDates) {
        this.inboundDates = inboundDates;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof DatesDTO)) return false;
        final DatesDTO other = (DatesDTO) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$outboundDates = this.getOutboundDates();
        final Object other$outboundDates = other.getOutboundDates();
        if (this$outboundDates == null ? other$outboundDates != null : !this$outboundDates.equals(other$outboundDates))
            return false;
        final Object this$inboundDates = this.getInboundDates();
        final Object other$inboundDates = other.getInboundDates();
        if (this$inboundDates == null ? other$inboundDates != null : !this$inboundDates.equals(other$inboundDates))
            return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof DatesDTO;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $outboundDates = this.getOutboundDates();
        result = result * PRIME + ($outboundDates == null ? 43 : $outboundDates.hashCode());
        final Object $inboundDates = this.getInboundDates();
        result = result * PRIME + ($inboundDates == null ? 43 : $inboundDates.hashCode());
        return result;
    }

    public String toString() {
        return "" + this.getOutboundDates() + "\n " + this.getInboundDates() ;
    }
}
