package ru.petrovov.application.backend;

import ru.petrovov.application.backend.model.Loan;
import ru.petrovov.application.backend.model.Payment;

import javax.enterprise.context.ApplicationScoped;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static java.math.RoundingMode.HALF_UP;

@ApplicationScoped
public class LoanServiceImpl implements LoanService {

    public List<Payment> calculateLoan(Loan loan) {
        BigDecimal yearRate = loan.getLoanRate().divide(BigDecimal.valueOf(100), 2, HALF_UP);
        BigDecimal monthlyRate = loan.getLoanRate().divide(BigDecimal.valueOf(1200), 8, HALF_UP);
        int months = loan.getLoanPeriod().intValue();

        BigDecimal leftPartOfFormula = monthlyRate.divide(
                monthlyRate.add(BigDecimal.ONE).pow(months).subtract(BigDecimal.ONE), 2, HALF_UP);
        BigDecimal payment = loan.getLoanSum().multiply(monthlyRate.add(leftPartOfFormula)).setScale(2, HALF_UP);
        List<Payment> payments = new ArrayList<>(months);

        BigDecimal loanReminder = loan.getLoanSum();
        LocalDate paymentDate = LocalDate.now();

        for (int i = 1; i < months + 1; i++) {
            paymentDate = paymentDate.plus(1, ChronoUnit.MONTHS);
            Payment pmnt = new Payment(i);
            BigDecimal percents = loanReminder.multiply(yearRate).divide(BigDecimal.valueOf(12), 2, HALF_UP);
            BigDecimal body = payment.subtract(percents).setScale(2, HALF_UP);
            loanReminder = loanReminder.subtract(payment).setScale(2, HALF_UP);
            pmnt.setPercentsPayment(percents);
            pmnt.setBodyPayment(body);
            pmnt.setPaymentDate(paymentDate);
            pmnt.setLoanReminder(loanReminder);
            payments.add(pmnt);
        }

        return payments;
    }
}
