package libraryloan;

import java.util.Locale;

public abstract class Loan implements Chargeable {
    private static final double FIRST_TIME_DISCOUNT_RATE = 0.07;

    private final String loanId;
    private final String customerName;
    private final int daysLate;
    private final boolean firstTimeBorrower;

    protected Loan(String loanId, String customerName, int daysLate,
                   boolean firstTimeBorrower) throws InvalidLoanException {
        if (loanId == null || !loanId.matches("KD374-\\d{3}")) {
            throw new InvalidLoanException(
                    "Loan ID must follow the format KD374-001.");
        }
        if (customerName == null || customerName.trim().isEmpty()) {
            throw new InvalidLoanException("Customer name cannot be blank.");
        }
        if (daysLate < 0) {
            throw new InvalidLoanException("Days late cannot be negative.");
        }

        this.loanId = loanId;
        this.customerName = customerName.trim();
        this.daysLate = daysLate;
        this.firstTimeBorrower = firstTimeBorrower;
    }

    public final String getLoanId() {
        return loanId;
    }

    public final String getCustomerName() {
        return customerName;
    }

    public final int getDaysLate() {
        return daysLate;
    }

    public final boolean isFirstTimeBorrower() {
        return firstTimeBorrower;
    }

    protected abstract double getDailyRate();

    public final double calculateGrossCharge() {
        return daysLate * getDailyRate();
    }

    public final double calculateDiscountAmount() {
        if (!firstTimeBorrower) {
            return 0.0;
        }
        return calculateGrossCharge() * FIRST_TIME_DISCOUNT_RATE;
    }

    protected final double calculateFinalCharge() {
        return calculateGrossCharge() - calculateDiscountAmount();
    }

    protected abstract String getLoanType();

    @Override
    public String toString() {
        return String.format(Locale.US,
                "%s | ID: %s | Customer: %s | Days late: %d | Gross: UGX %.2f"
                        + " | Discount: UGX %.2f | Final charge: UGX %.2f",
                getLoanType(), loanId, customerName, daysLate,
                calculateGrossCharge(), calculateDiscountAmount(),
                calculateCharge());
    }
}
