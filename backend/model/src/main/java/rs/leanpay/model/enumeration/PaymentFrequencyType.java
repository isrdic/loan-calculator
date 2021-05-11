package rs.leanpay.model.enumeration;

import lombok.Getter;

@Getter
public enum PaymentFrequencyType {

    Daily("Daily 365/yr", 365),
    Weekly("Weekly 52/yr", 52),
    Biweekly("Biweekly 26/yr", 26),
    SemiMonth("Semi-Month 24/yr", 24),
    Monthly("Monthly 12/yr", 12),
    Bimonthly("Bimonthly 6/yr", 6),
    Quarterly("Quarterly 4/yr", 4),
    SemiAnnual("Semi-Annual 2/yr", 2),
    Annual("Annual 1/yr", 1);

    private final String description;
    private final int numberOfPaymentsPerYear;

    PaymentFrequencyType(String description, int numberOfPaymentsPerYear) {
        this.description = description;
        this.numberOfPaymentsPerYear = numberOfPaymentsPerYear;
    }

}
