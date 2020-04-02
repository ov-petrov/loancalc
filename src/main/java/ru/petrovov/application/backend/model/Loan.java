package ru.petrovov.application.backend.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
public class Loan {
    public static final BigDecimal MIN_LOAN_SUM = BigDecimal.valueOf(100_000);
    public static final BigDecimal MAX_LOAN_SUM = BigDecimal.valueOf(5_000_000);
    public static final BigDecimal MIN_LOAN_PERIOD = BigDecimal.valueOf(12);
    public static final BigDecimal MAX_LOAN_PERIOD = BigDecimal.valueOf(60);

    public static final BigDecimal LOAN_RATE = BigDecimal.valueOf(18.7);

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private BigDecimal loanSum;
    private BigDecimal loanPeriod;
    private BigDecimal loanRate;

    public Loan() {
    }

    public BigDecimal getLoanSum() {
        return loanSum;
    }

    public void setLoanSum(BigDecimal loanSum) {
        this.loanSum = loanSum;
    }

    public BigDecimal getLoanPeriod() {
        return loanPeriod;
    }

    public void setLoanPeriod(BigDecimal loanPeriod) {
        this.loanPeriod = loanPeriod;
    }

    public BigDecimal getLoanRate() {
        return loanRate;
    }

    public void setLoanRate(BigDecimal loanRate) {
        this.loanRate = loanRate;
    }

    @Override
    public String toString() {
        return "Loan{" +
                "loanSum=" + loanSum +
                ", loanPeriod=" + loanPeriod +
                ", loanRate=" + loanRate +
                '}';
    }
}
