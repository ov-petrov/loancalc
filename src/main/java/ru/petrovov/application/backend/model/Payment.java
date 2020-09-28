package ru.petrovov.application.backend.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

@Embeddable
public class Payment {
    @NotNull
    @Column(nullable = false)
    private Integer paymentIndex;

    @Temporal(TemporalType.DATE)
    @NotNull
    @Column(nullable = false)
    private LocalDate paymentDate;

    @NotNull
    @Column(nullable = false)
    private BigDecimal bodyPayment;

    @NotNull
    @Column(nullable = false)
    private BigDecimal percentsPayment;

    @NotNull
    @Column(nullable = false)
    private BigDecimal loanReminder;

    public Payment() {
    }

    public Payment(Integer paymentIndex) {
        this.paymentIndex = paymentIndex;
    }

    public void setPaymentIndex(Integer paymentIndex) {
        this.paymentIndex = paymentIndex;
    }

    public Integer getPaymentIndex() {
        return paymentIndex;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    public BigDecimal getBodyPayment() {
        return bodyPayment;
    }

    public void setBodyPayment(BigDecimal bodyPayment) {
        this.bodyPayment = bodyPayment;
    }

    public BigDecimal getPercentsPayment() {
        return percentsPayment;
    }

    public void setPercentsPayment(BigDecimal percentsPayment) {
        this.percentsPayment = percentsPayment;
    }

    public BigDecimal getLoanReminder() {
        return loanReminder;
    }

    public void setLoanReminder(BigDecimal loanReminder) {
        this.loanReminder = loanReminder;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "paymentIndex=" + paymentIndex +
                ", paymentDate=" + paymentDate +
                ", bodyPayment=" + bodyPayment +
                ", percentsPayment=" + percentsPayment +
                ", loanReminder=" + loanReminder +
                '}';
    }
}
