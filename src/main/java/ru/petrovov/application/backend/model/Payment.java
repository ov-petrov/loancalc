package ru.petrovov.application.backend.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Payment {
    private Integer paymentIndex;
    private LocalDate paymentDate;
    private BigDecimal bodyPayment;
    private BigDecimal percentsPayment;
    private BigDecimal loanReminder;

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

    public BigDecimal getWholePaymentSum() {
        return bodyPayment.add(percentsPayment);
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
