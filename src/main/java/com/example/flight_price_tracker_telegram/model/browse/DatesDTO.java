package com.example.flight_price_tracker_telegram.model.browse;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Data transfer object for Dates.
 *
 */

public class DatesDTO {

    @JsonProperty("InboundDates")
    private List<OutInboundDatesDTO> inboundDates;

    @JsonProperty("OutboundDates")
    private List<OutInboundDatesDTO> outboundDates;


    public DatesDTO() {
    }

    protected boolean canEqual(final Object other) {
        return other instanceof DatesDTO;
    }

    public List<OutInboundDatesDTO> getInboundDates() {
        return this.inboundDates;
    }

    public List<OutInboundDatesDTO> getOutboundDates() {
        return this.outboundDates;
    }

    @JsonProperty("InboundDates")
    public void setInboundDates(List<OutInboundDatesDTO> inboundDates) {
        this.inboundDates = inboundDates;
    }

    @JsonProperty("OutboundDates")
    public void setOutboundDates(List<OutInboundDatesDTO> outboundDates) {
        this.outboundDates = outboundDates;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof DatesDTO)) return false;
        final DatesDTO other = (DatesDTO) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$inboundDates = this.getInboundDates();
        final Object other$inboundDates = other.getInboundDates();
        if (this$inboundDates == null ? other$inboundDates != null : !this$inboundDates.equals(other$inboundDates))
            return false;
        final Object this$outboundDates = this.getOutboundDates();
        final Object other$outboundDates = other.getOutboundDates();
        if (this$outboundDates == null ? other$outboundDates != null : !this$outboundDates.equals(other$outboundDates))
            return false;
        return true;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $inboundDates = this.getInboundDates();
        result = result * PRIME + ($inboundDates == null ? 43 : $inboundDates.hashCode());
        final Object $outboundDates = this.getOutboundDates();
        result = result * PRIME + ($outboundDates == null ? 43 : $outboundDates.hashCode());
        return result;
    }

    public String toString() {
        return "Outbound date: " + this.getOutboundDates()
                + "\n" + "Inbound date: " + this.getInboundDates()  ;
    }

}
