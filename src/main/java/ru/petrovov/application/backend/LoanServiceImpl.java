package ru.petrovov.application.backend;

import ru.petrovov.application.backend.model.Loan;
import ru.petrovov.application.backend.model.Payment;

import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static java.math.RoundingMode.HALF_UP;

@SessionScoped
public class LoanServiceImpl implements LoanService, Serializable {

    private static final Integer SCALE = 2;
    private static final RoundingMode ROUNDING_MODE = HALF_UP;

    private List<Payment> calculateSchedule(Loan loan, BigDecimal monthlyPayment) {
        BigDecimal monthlyRate = loan.getLoanRate().divide(BigDecimal.valueOf(1200), SCALE, ROUNDING_MODE);
        LocalDate paymentDate = LocalDate.now();
        int periods = loan.getLoanPeriod().intValue();

        BigDecimal loanReminder = loan.getLoanSum().setScale(SCALE, ROUNDING_MODE);
        List<Payment> payments = new ArrayList<>(periods);

        for (int i = 0; i < periods; i++) {
            paymentDate = paymentDate.plus(1, ChronoUnit.MONTHS);
            Payment pmnt = new Payment(i);
            BigDecimal percents = loanReminder.multiply(monthlyRate).setScale(SCALE, ROUNDING_MODE);
            BigDecimal body = monthlyPayment.subtract(percents).setScale(SCALE, ROUNDING_MODE);
            pmnt.setPercentsPayment(percents);
            pmnt.setBodyPayment(body);
            pmnt.setPaymentDate(paymentDate);
            pmnt.setLoanReminder(loanReminder);
            payments.add(pmnt);
            loanReminder = loanReminder.subtract(monthlyPayment);
        }

        return payments;
    }

    public List<Payment> calculateLoanPayments(Loan loan) {
        BigDecimal monthlyRate = loan.getLoanRate().divide(BigDecimal.valueOf(1200), SCALE, ROUNDING_MODE);
        BigDecimal loanSum = loan.getLoanSum();
        int periods = loan.getLoanPeriod().intValue();

        BigDecimal onePlusRateAndPow = BigDecimal.ONE.add(monthlyRate).pow(periods).setScale(SCALE, ROUNDING_MODE);
        BigDecimal loanK = (monthlyRate.multiply(onePlusRateAndPow))
                .divide(onePlusRateAndPow.subtract(BigDecimal.ONE), SCALE, ROUNDING_MODE);

        BigDecimal monthlyPayment = loanSum.multiply(loanK);

        return calculateSchedule(loan, monthlyPayment);
    }
}
