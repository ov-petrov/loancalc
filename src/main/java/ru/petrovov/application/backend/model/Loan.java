package ru.petrovov.application.backend.model;

import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
public class Loan implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private BigDecimal loanSum;
    private BigDecimal loanPeriod;
    private BigDecimal loanRate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column
    @Generated(GenerationTime.ALWAYS)
    private Date lastModified;

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
