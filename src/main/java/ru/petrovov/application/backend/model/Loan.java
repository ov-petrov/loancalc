package ru.petrovov.application.backend.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
public class Loan {

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
