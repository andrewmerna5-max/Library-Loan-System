package libraryloan;

public class LaptopLoan extends Loan {
    private static final double DAILY_RATE = 5000.0;

    public LaptopLoan(String loanId, String customerName, int daysLate,
                      boolean firstTimeBorrower) throws InvalidLoanException {
        super(loanId, customerName, daysLate, firstTimeBorrower);
    }

    @Override
    protected double getDailyRate() {
        return DAILY_RATE;
    }

    @Override
    protected String getLoanType() {
        return "Laptop loan";
    }

    @Override
    public double calculateCharge() {
        return calculateFinalCharge();
    }
}
