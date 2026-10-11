package libraryloan;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class LoanManager {
    private static final String ID_PREFIX = "KD374-";
    private final ArrayList<Loan> loans = new ArrayList<>();
    private int nextIdNumber = 1;

    public String generateNextId() {
        String id = String.format("%s%03d", ID_PREFIX, nextIdNumber);
        nextIdNumber++;
        return id;
    }

    public void addLoan(Loan loan) throws InvalidLoanException {
        if (loan == null) {
            throw new InvalidLoanException("Loan cannot be null.");
        }
        if (findLoan(loan.getLoanId()) != null) {
            throw new InvalidLoanException(
                    "A loan with ID " + loan.getLoanId() + " already exists.");
        }
        loans.add(loan);
    }

    public Loan findLoan(String loanId) {
        for (Loan loan : loans) {
            if (loan.getLoanId().equalsIgnoreCase(loanId)) {
                return loan;
            }
        }
        return null;
    }

    public void removeLoan(String loanId) throws InvalidLoanException {
        Loan loan = findLoan(loanId);
        if (loan == null) {
            throw new InvalidLoanException(
                    "No loan exists with ID " + loanId + ".");
        }
        loans.remove(loan);
    }

    public List<Loan> getLoansSortedByCustomerName() {
        ArrayList<Loan> sortedLoans = new ArrayList<>(loans);
        sortedLoans.sort(Comparator.comparing(
                Loan::getCustomerName, String.CASE_INSENSITIVE_ORDER));
        return sortedLoans;
    }

    public double calculateTotalCharges() {
        double total = 0.0;
        for (Loan loan : loans) {
            total += loan.calculateCharge();
        }
        return total;
    }

    public double calculateTotalDiscounts() {
        double total = 0.0;
        for (Loan loan : loans) {
            total += loan.calculateDiscountAmount();
        }
        return total;
    }

    public boolean isEmpty() {
        return loans.isEmpty();
    }
}
