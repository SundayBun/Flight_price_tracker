package com.example.flight_price_tracker_telegram.skyscanner_api.dto.localisation;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Data transfer object for Currency.
 */

//@JsonProperty("Code") — это аннотация из Jackson Project, которая говорит, какое поле будет присваиваться этой переменной.
// То есть поле в JSON, равное Code, будет присваиваться переменной code.

public class CurrencyDTO {

    @JsonProperty("Code")
    private String code;

    @JsonProperty("Symbol")
    private String symbol;

    @JsonProperty("ThousandsSeparator")
    private String thousandsSeparator;

    @JsonProperty("DecimalSeparator")
    private String decimalSeparator;

    @JsonProperty("SymbolOnLeft")
    private boolean symbolOnLeft;

    @JsonProperty("SpaceBetweenAmountAndSymbol")
    private boolean spaceBetweenAmountAndSymbol;

    @JsonProperty("RoundingCoefficient")
    private int roundingCoefficient;

    @JsonProperty("DecimalDigits")
    private int decimalDigits;

    public CurrencyDTO() {
    }

    public String getCode() {
        return this.code;
    }

    public String getSymbol() {
        return this.symbol;
    }

    public String getThousandsSeparator() {
        return this.thousandsSeparator;
    }

    public String getDecimalSeparator() {
        return this.decimalSeparator;
    }

    public boolean isSymbolOnLeft() {
        return this.symbolOnLeft;
    }

    public boolean isSpaceBetweenAmountAndSymbol() {
        return this.spaceBetweenAmountAndSymbol;
    }

    public int getRoundingCoefficient() {
        return this.roundingCoefficient;
    }

    public int getDecimalDigits() {
        return this.decimalDigits;
    }

    @JsonProperty("Code")
    public void setCode(String code) {
        this.code = code;
    }

    @JsonProperty("Symbol")
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    @JsonProperty("ThousandsSeparator")
    public void setThousandsSeparator(String thousandsSeparator) {
        this.thousandsSeparator = thousandsSeparator;
    }

    @JsonProperty("DecimalSeparator")
    public void setDecimalSeparator(String decimalSeparator) {
        this.decimalSeparator = decimalSeparator;
    }

    @JsonProperty("SymbolOnLeft")
    public void setSymbolOnLeft(boolean symbolOnLeft) {
        this.symbolOnLeft = symbolOnLeft;
    }

    @JsonProperty("SpaceBetweenAmountAndSymbol")
    public void setSpaceBetweenAmountAndSymbol(boolean spaceBetweenAmountAndSymbol) {
        this.spaceBetweenAmountAndSymbol = spaceBetweenAmountAndSymbol;
    }

    @JsonProperty("RoundingCoefficient")
    public void setRoundingCoefficient(int roundingCoefficient) {
        this.roundingCoefficient = roundingCoefficient;
    }

    @JsonProperty("DecimalDigits")
    public void setDecimalDigits(int decimalDigits) {
        this.decimalDigits = decimalDigits;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof CurrencyDTO)) return false;
        final CurrencyDTO other = (CurrencyDTO) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$code = this.getCode();
        final Object other$code = other.getCode();
        if (this$code == null ? other$code != null : !this$code.equals(other$code)) return false;
        final Object this$symbol = this.getSymbol();
        final Object other$symbol = other.getSymbol();
        if (this$symbol == null ? other$symbol != null : !this$symbol.equals(other$symbol)) return false;
        final Object this$thousandsSeparator = this.getThousandsSeparator();
        final Object other$thousandsSeparator = other.getThousandsSeparator();
        if (this$thousandsSeparator == null ? other$thousandsSeparator != null : !this$thousandsSeparator.equals(other$thousandsSeparator))
            return false;
        final Object this$decimalSeparator = this.getDecimalSeparator();
        final Object other$decimalSeparator = other.getDecimalSeparator();
        if (this$decimalSeparator == null ? other$decimalSeparator != null : !this$decimalSeparator.equals(other$decimalSeparator))
            return false;
        if (this.isSymbolOnLeft() != other.isSymbolOnLeft()) return false;
        if (this.isSpaceBetweenAmountAndSymbol() != other.isSpaceBetweenAmountAndSymbol()) return false;
        if (this.getRoundingCoefficient() != other.getRoundingCoefficient()) return false;
        if (this.getDecimalDigits() != other.getDecimalDigits()) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof CurrencyDTO;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $code = this.getCode();
        result = result * PRIME + ($code == null ? 43 : $code.hashCode());
        final Object $symbol = this.getSymbol();
        result = result * PRIME + ($symbol == null ? 43 : $symbol.hashCode());
        final Object $thousandsSeparator = this.getThousandsSeparator();
        result = result * PRIME + ($thousandsSeparator == null ? 43 : $thousandsSeparator.hashCode());
        final Object $decimalSeparator = this.getDecimalSeparator();
        result = result * PRIME + ($decimalSeparator == null ? 43 : $decimalSeparator.hashCode());
        result = result * PRIME + (this.isSymbolOnLeft() ? 79 : 97);
        result = result * PRIME + (this.isSpaceBetweenAmountAndSymbol() ? 79 : 97);
        result = result * PRIME + this.getRoundingCoefficient();
        result = result * PRIME + this.getDecimalDigits();
        return result;
    }

    public String toString() {
//        return "CurrencyDTO(code=" + this.getCode() + ", symbol=" + this.getSymbol()
//                + ", thousandsSeparator=" + this.getThousandsSeparator()
//                + ", decimalSeparator=" + this.getDecimalSeparator() + ", symbolOnLeft=" + this.isSymbolOnLeft()
//                + ", spaceBetweenAmountAndSymbol=" + this.isSpaceBetweenAmountAndSymbol()
//                + ", roundingCoefficient=" + this.getRoundingCoefficient()
//                + ", decimalDigits=" + this.getDecimalDigits() + ")";

        return " " +this.getSymbol()+" ("+this.getCode()+")";
    }
}
