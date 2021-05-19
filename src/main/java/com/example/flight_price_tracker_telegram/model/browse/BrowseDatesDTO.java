package com.example.flight_price_tracker_telegram.model.browse;

import com.example.flight_price_tracker_telegram.model.localisation.CurrencyDTO;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Data transfer object for FlightPrices and Dates.
 *
 */
public class BrowseDatesDTO {

    @JsonProperty("Quotes")
    private List<QuotesDTO> quotes;

    @JsonProperty("Places")
    private List<BrowsePlacesDTO> places;

    @JsonProperty("Carriers")
    private List<CarriersDTO> carriers;

    @JsonProperty("Currencies")
    private List<CurrencyDTO> currencies;

    @JsonProperty("Dates")
    private List<DatesDTO> dates;

    public BrowseDatesDTO() {
    }

    public List<QuotesDTO> getQuotes() {
        return this.quotes;
    }

    public List<BrowsePlacesDTO> getPlaces() {
        return this.places;
    }

    public List<CarriersDTO> getCarriers() {
        return this.carriers;
    }

    public List<CurrencyDTO> getCurrencies() {
        return this.currencies;
    }

    public List<DatesDTO> getDates() {
        return this.dates;
    }

    @JsonProperty("Quotes")
    public void setQuotes(List<QuotesDTO> quotes) {
        this.quotes = quotes;
    }

    @JsonProperty("Places")
    public void setPlaces(List<BrowsePlacesDTO> places) {
        this.places = places;
    }

    @JsonProperty("Carriers")
    public void setCarriers(List<CarriersDTO> carriers) {
        this.carriers = carriers;
    }

    @JsonProperty("Currencies")
    public void setCurrencies(List<CurrencyDTO> currencies) {
        this.currencies = currencies;
    }

    @JsonProperty("Dates")
    public void setDates(List<DatesDTO> dates) {
        this.dates = dates;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof BrowseDatesDTO)) return false;
        final BrowseDatesDTO other = (BrowseDatesDTO) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$quotes = this.getQuotes();
        final Object other$quotes = other.getQuotes();
        if (this$quotes == null ? other$quotes != null : !this$quotes.equals(other$quotes)) return false;
        final Object this$places = this.getPlaces();
        final Object other$places = other.getPlaces();
        if (this$places == null ? other$places != null : !this$places.equals(other$places)) return false;
        final Object this$carriers = this.getCarriers();
        final Object other$carriers = other.getCarriers();
        if (this$carriers == null ? other$carriers != null : !this$carriers.equals(other$carriers)) return false;
        final Object this$currencies = this.getCurrencies();
        final Object other$currencies = other.getCurrencies();
        if (this$currencies == null ? other$currencies != null : !this$currencies.equals(other$currencies))
            return false;
        final Object this$dates = this.getDates();
        final Object other$dates = other.getDates();
        if (this$dates == null ? other$dates != null : !this$dates.equals(other$dates)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof BrowseDatesDTO;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $quotes = this.getQuotes();
        result = result * PRIME + ($quotes == null ? 43 : $quotes.hashCode());
        final Object $places = this.getPlaces();
        result = result * PRIME + ($places == null ? 43 : $places.hashCode());
        final Object $carriers = this.getCarriers();
        result = result * PRIME + ($carriers == null ? 43 : $carriers.hashCode());
        final Object $currencies = this.getCurrencies();
        result = result * PRIME + ($currencies == null ? 43 : $currencies.hashCode());
        final Object $dates = this.getDates();
        result = result * PRIME + ($dates == null ? 43 : $dates.hashCode());
        return result;
    }

    public String toString() {
//        return "BrowseDatesDTO(quotes=" + this.getQuotes() + ", places="
//        + this.getPlaces() + ", carriers=" + this.getCarriers() +
//         ", currencies=" + this.getCurrencies() + ", dates=" + this.getDates() + ")";

        return this.getPlaces()+"\n"+this.getDates()+"\n"+this.getCurrencies()+"\n"+getCarriers();


    }
}
