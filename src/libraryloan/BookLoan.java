package libraryloan;

public class BookLoan extends Loan {
    private static final double DAILY_RATE = 500.0;

    public BookLoan(String loanId, String customerName, int daysLate,
                    boolean firstTimeBorrower) throws InvalidLoanException {
        super(loanId, customerName, daysLate, firstTimeBorrower);
    }

    @Override
    protected double getDailyRate() {
        return DAILY_RATE;
    }

    @Override
    protected String getLoanType() {
        return "Book loan";
    }

    @Override
    public double calculateCharge() {
        return calculateFinalCharge();
    }
}
