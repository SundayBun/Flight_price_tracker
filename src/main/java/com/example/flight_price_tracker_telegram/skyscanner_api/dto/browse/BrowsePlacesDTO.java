package com.example.flight_price_tracker_telegram.skyscanner_api.dto.browse;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Data transfer object for FlightPrices.
 */

public class BrowsePlacesDTO {

    @JsonProperty("PlaceId")
    private Integer placeId;

    @JsonProperty("IataCode")
    private String iataCode;

    @JsonProperty("Name")
    private String name;

    @JsonProperty("Type")
    private String type;

    @JsonProperty("SkyscannerCode")
    private String skyscannerCode;

    @JsonProperty("CityName")
    private String cityName;

    @JsonProperty("CityId")
    private String cityId;

    @JsonProperty("CountryName")
    private String countryName;

    public BrowsePlacesDTO() {
    }

    public Integer getPlaceId() {
        return this.placeId;
    }

    public String getIataCode() {
        return this.iataCode;
    }

    public String getName() {
        return this.name;
    }

    public String getType() {
        return this.type;
    }

    public String getSkyscannerCode() {
        return this.skyscannerCode;
    }

    public String getCityName() {
        return this.cityName;
    }

    public String getCityId() {
        return this.cityId;
    }

    public String getCountryName() {
        return this.countryName;
    }

    @JsonProperty("PlaceId")
    public void setPlaceId(Integer placeId) {
        this.placeId = placeId;
    }

    @JsonProperty("IataCode")
    public void setIataCode(String iataCode) {
        this.iataCode = iataCode;
    }

    @JsonProperty("Name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("Type")
    public void setType(String type) {
        this.type = type;
    }

    @JsonProperty("SkyscannerCode")
    public void setSkyscannerCode(String skyscannerCode) {
        this.skyscannerCode = skyscannerCode;
    }

    @JsonProperty("CityName")
    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    @JsonProperty("CityId")
    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    @JsonProperty("CountryName")
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof BrowsePlacesDTO)) return false;
        final BrowsePlacesDTO other = (BrowsePlacesDTO) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$placeId = this.getPlaceId();
        final Object other$placeId = other.getPlaceId();
        if (this$placeId == null ? other$placeId != null : !this$placeId.equals(other$placeId)) return false;
        final Object this$iataCode = this.getIataCode();
        final Object other$iataCode = other.getIataCode();
        if (this$iataCode == null ? other$iataCode != null : !this$iataCode.equals(other$iataCode)) return false;
        final Object this$name = this.getName();
        final Object other$name = other.getName();
        if (this$name == null ? other$name != null : !this$name.equals(other$name)) return false;
        final Object this$type = this.getType();
        final Object other$type = other.getType();
        if (this$type == null ? other$type != null : !this$type.equals(other$type)) return false;
        final Object this$skyscannerCode = this.getSkyscannerCode();
        final Object other$skyscannerCode = other.getSkyscannerCode();
        if (this$skyscannerCode == null ? other$skyscannerCode != null : !this$skyscannerCode.equals(other$skyscannerCode))
            return false;
        final Object this$cityName = this.getCityName();
        final Object other$cityName = other.getCityName();
        if (this$cityName == null ? other$cityName != null : !this$cityName.equals(other$cityName)) return false;
        final Object this$cityId = this.getCityId();
        final Object other$cityId = other.getCityId();
        if (this$cityId == null ? other$cityId != null : !this$cityId.equals(other$cityId)) return false;
        final Object this$countryName = this.getCountryName();
        final Object other$countryName = other.getCountryName();
        if (this$countryName == null ? other$countryName != null : !this$countryName.equals(other$countryName))
            return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof BrowsePlacesDTO;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $placeId = this.getPlaceId();
        result = result * PRIME + ($placeId == null ? 43 : $placeId.hashCode());
        final Object $iataCode = this.getIataCode();
        result = result * PRIME + ($iataCode == null ? 43 : $iataCode.hashCode());
        final Object $name = this.getName();
        result = result * PRIME + ($name == null ? 43 : $name.hashCode());
        final Object $type = this.getType();
        result = result * PRIME + ($type == null ? 43 : $type.hashCode());
        final Object $skyscannerCode = this.getSkyscannerCode();
        result = result * PRIME + ($skyscannerCode == null ? 43 : $skyscannerCode.hashCode());
        final Object $cityName = this.getCityName();
        result = result * PRIME + ($cityName == null ? 43 : $cityName.hashCode());
        final Object $cityId = this.getCityId();
        result = result * PRIME + ($cityId == null ? 43 : $cityId.hashCode());
        final Object $countryName = this.getCountryName();
        result = result * PRIME + ($countryName == null ? 43 : $countryName.hashCode());
        return result;
    }

    public String toString() {
        return "\n" + this.getName();
    }
}
