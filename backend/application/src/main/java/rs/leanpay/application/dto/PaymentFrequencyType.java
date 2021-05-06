package rs.leanpay.application.dto;

import lombok.Getter;

@Getter
public enum PaymentFrequencyType {

    Daily("Daily 365/yr"),
    Weekly("Weekly 52/yr"),
    Biweekly("Biweekly 26/yr"),
    SemiMonth("Semi-Month 24/yr"),
    Monthly("Monthly 12/yr"),
    Bimonthly("Bimonthly 6/yr"),
    Quarterly("Quarterly 4/yr"),
    SemiAnnual("Semi-Annual 2/yr"),
    Annual("Annual 1/yr");

    private final String description;

    PaymentFrequencyType(String description) {
        this.description = description;
    }
}
