package ru.petrovov.application.backend;

import ru.petrovov.application.backend.model.Loan;
import ru.petrovov.application.backend.model.Payment;

import java.util.List;

public interface LoanService {

    List<Payment> calculateLoan(Loan loan);
}
