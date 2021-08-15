package com.example.flight_price_tracker_telegram.skyscanner_api.dto.browse;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Data transfer object for quotes.
 *
 */

public class QuotesDTO {

    @JsonProperty("QuoteId")
    private Integer quoteId;

    @JsonProperty("MinPrice")
    private Integer minPrice;

    @JsonProperty("Direct")
    private Boolean direct;

    @JsonProperty("OutboundLeg")
    private LegDTO outboundLeg;

    @JsonProperty("InboundLeg")
    private LegDTO inboundLeg;

    @JsonProperty("QuoteDateTime")
    private String quoteDateTime;

    public QuotesDTO() {
    }

    public Integer getQuoteId() {
        return this.quoteId;
    }

    public Integer getMinPrice() {
        return this.minPrice;
    }

    public Boolean getDirect() {
        return this.direct;
    }

    public LegDTO getOutboundLeg() {
        return this.outboundLeg;
    }

    public LegDTO getInboundLeg() {
        return this.inboundLeg;
    }

    public String getQuoteDateTime() {
        return this.quoteDateTime;
    }

    @JsonProperty("QuoteId")
    public void setQuoteId(Integer quoteId) {
        this.quoteId = quoteId;
    }

    @JsonProperty("MinPrice")
    public void setMinPrice(Integer minPrice) {
        this.minPrice = minPrice;
    }

    @JsonProperty("Direct")
    public void setDirect(Boolean direct) {
        this.direct = direct;
    }

    @JsonProperty("OutboundLeg")
    public void setOutboundLeg(LegDTO outboundLeg) {
        this.outboundLeg = outboundLeg;
    }

    @JsonProperty("InboundLeg")
    public void setInboundLeg(LegDTO inboundLeg) {
        this.inboundLeg = inboundLeg;
    }

    @JsonProperty("QuoteDateTime")
    public void setQuoteDateTime(String quoteDateTime) {
        this.quoteDateTime = quoteDateTime;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof QuotesDTO)) return false;
        final QuotesDTO other = (QuotesDTO) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$quoteId = this.getQuoteId();
        final Object other$quoteId = other.getQuoteId();
        if (this$quoteId == null ? other$quoteId != null : !this$quoteId.equals(other$quoteId)) return false;
        final Object this$minPrice = this.getMinPrice();
        final Object other$minPrice = other.getMinPrice();
        if (this$minPrice == null ? other$minPrice != null : !this$minPrice.equals(other$minPrice)) return false;
        final Object this$direct = this.getDirect();
        final Object other$direct = other.getDirect();
        if (this$direct == null ? other$direct != null : !this$direct.equals(other$direct)) return false;
        final Object this$outboundLeg = this.getOutboundLeg();
        final Object other$outboundLeg = other.getOutboundLeg();
        if (this$outboundLeg == null ? other$outboundLeg != null : !this$outboundLeg.equals(other$outboundLeg))
            return false;
        final Object this$inboundLeg = this.getInboundLeg();
        final Object other$inboundLeg = other.getInboundLeg();
        if (this$inboundLeg == null ? other$inboundLeg != null : !this$inboundLeg.equals(other$inboundLeg))
            return false;
        final Object this$quoteDateTime = this.getQuoteDateTime();
        final Object other$quoteDateTime = other.getQuoteDateTime();
        if (this$quoteDateTime == null ? other$quoteDateTime != null : !this$quoteDateTime.equals(other$quoteDateTime))
            return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof QuotesDTO;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $quoteId = this.getQuoteId();
        result = result * PRIME + ($quoteId == null ? 43 : $quoteId.hashCode());
        final Object $minPrice = this.getMinPrice();
        result = result * PRIME + ($minPrice == null ? 43 : $minPrice.hashCode());
        final Object $direct = this.getDirect();
        result = result * PRIME + ($direct == null ? 43 : $direct.hashCode());
        final Object $outboundLeg = this.getOutboundLeg();
        result = result * PRIME + ($outboundLeg == null ? 43 : $outboundLeg.hashCode());
        final Object $inboundLeg = this.getInboundLeg();
        result = result * PRIME + ($inboundLeg == null ? 43 : $inboundLeg.hashCode());
        final Object $quoteDateTime = this.getQuoteDateTime();
        result = result * PRIME + ($quoteDateTime == null ? 43 : $quoteDateTime.hashCode());
        return result;
    }

    public String toString() {
//        return "QuotesDTO(quoteId=" + this.getQuoteId()
//                + ", minPrice=" + this.getMinPrice() + ", direct=" + this.getDirect()
//                + ", outboundLeg=" + this.getOutboundLeg() + ", inboundLeg="
//                + this.getInboundLeg() + ", quoteDateTime=" + this.getQuoteDateTime() + ")";

        return "Min price: " + this.getMinPrice();
    }
}
