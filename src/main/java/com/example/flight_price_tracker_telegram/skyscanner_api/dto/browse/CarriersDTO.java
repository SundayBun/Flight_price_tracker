package com.example.flight_price_tracker_telegram.skyscanner_api.dto.browse;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Data transfer object for Carriers.
 *
 */

public class CarriersDTO {

    @JsonProperty("CarrierId")
    private Integer carrierId;

    @JsonProperty("Name")
    private String name;

    public CarriersDTO() {
    }

    public Integer getCarrierId() {
        return this.carrierId;
    }

    public String getName() {
        return this.name;
    }

    @JsonProperty("CarrierId")
    public void setCarrierId(Integer carrierId) {
        this.carrierId = carrierId;
    }

    @JsonProperty("Name")
    public void setName(String name) {
        this.name = name;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof CarriersDTO)) return false;
        final CarriersDTO other = (CarriersDTO) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$carrierId = this.getCarrierId();
        final Object other$carrierId = other.getCarrierId();
        if (this$carrierId == null ? other$carrierId != null : !this$carrierId.equals(other$carrierId)) return false;
        final Object this$name = this.getName();
        final Object other$name = other.getName();
        if (this$name == null ? other$name != null : !this$name.equals(other$name)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof CarriersDTO;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $carrierId = this.getCarrierId();
        result = result * PRIME + ($carrierId == null ? 43 : $carrierId.hashCode());
        final Object $name = this.getName();
        result = result * PRIME + ($name == null ? 43 : $name.hashCode());
        return result;
    }

    public String toString() {
//        return "CarriersDTO(carrierId=" + this.getCarrierId()
//                + ", name=" + this.getName() + ")";

        return " "+this.getName();
    }
}
