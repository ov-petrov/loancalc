package ru.petrovov.application.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.BigDecimalField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import ru.petrovov.application.MainView;
import ru.petrovov.application.backend.LoanService;
import ru.petrovov.application.backend.model.Loan;
import ru.petrovov.application.backend.model.Payment;
import ru.petrovov.application.utils.HibernateUtil;

import javax.inject.Inject;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static ru.petrovov.application.backend.model.Loan.LOAN_RATE;

@Route(value = "calcloan", layout = MainView.class)
@RouteAlias(value = "", layout = MainView.class)
@PageTitle("Расчитать кредит")
@CssImport("styles/views/calulateloan/calulateloan-view.css")
public class CalulateLoanView extends Div {

    @Inject
    LoanService loanService;

    @Inject
    HibernateUtil hibernateUtil;

    private BigDecimalField loanSum = new BigDecimalField();
    private BigDecimalField loanPeriod = new BigDecimalField();
    private BigDecimalField loanRate = new BigDecimalField();

    private Button cleanFields = new Button("Очистить");
    private Button calculateLoan = new Button("Расчитать");
    private Button saveCalculations = new Button("Сохранить расчет");
    private Grid<Payment> paymentsGrid = new Grid<>();

    private Loan loan;

    public CalulateLoanView() {
        setId("calulateloan-view");
        VerticalLayout wrapper = createWrapper();

        createTitle(wrapper);
        createFormLayout(wrapper);
        createButtonLayout(wrapper);
        createGrid(wrapper);

        Binder<Loan> binder = createBinder();

        loanRate.setReadOnly(true);
        loanRate.setPrefixComponent(new Icon(VaadinIcon.BOOK_PERCENT));
        loanRate.setValue(LOAN_RATE);
        cleanFields.addClickListener(event -> clearFields());
        calculateLoan.addClickListener(e -> {
            loan = new Loan();
            binder.writeBeanIfValid(loan);
            List<Payment> payments = loanService.calculateLoanPayments(loan);
            paymentsGrid.setItems(payments);
        });
        saveCalculations.addClickListener(e -> hibernateUtil.saveEntity(loan));

        add(wrapper);
    }

    private Binder<Loan> createBinder() {
        Binder<Loan> binder = new Binder<>();
        binder.forField(loanSum)
                .asRequired("Заполните сумму кредита")
                .withValidator((value, context) -> {
                    if (value.compareTo(Loan.MIN_LOAN_SUM) < 0)
                        return ValidationResult.error(String.format("Сумма кредита должна быть не менее %s рублей",
                                Loan.MIN_LOAN_SUM.toPlainString()));
                    if (value.compareTo(Loan.MAX_LOAN_SUM) > 0)
                        return ValidationResult.error(String.format("Сумма кредита должна быть не более %s рублей",
                                Loan.MAX_LOAN_SUM.toPlainString()));
                    return ValidationResult.ok();
                })
                .bind(Loan::getLoanSum, Loan::setLoanSum);
        binder.forField(loanPeriod)
                .asRequired("Заполните срок кредита")
                .withValidator((value, context) -> {
                    if (value.compareTo(Loan.MIN_LOAN_PERIOD) < 0)
                        return ValidationResult.error(String.format("Срок кредита должен быть не менее %s месяцев",
                                Loan.MIN_LOAN_PERIOD.toPlainString()));
                    if (value.compareTo(Loan.MAX_LOAN_PERIOD) > 0)
                        return ValidationResult.error(String.format("Срок кредита должен быть не более %s месяцев",
                                Loan.MAX_LOAN_PERIOD.toPlainString()));
                    return ValidationResult.ok();
                })
                .bind(Loan::getLoanPeriod, Loan::setLoanPeriod);
        binder.forField(loanRate)
                .bind(Loan::getLoanRate, Loan::setLoanRate);

        return binder;
    }

    private void clearFields() {
        loanSum.clear();
        loanPeriod.clear();
    }

    private void createTitle(VerticalLayout wrapper) {
        H1 h1 = new H1("Расчитать кредит");
        wrapper.add(h1);
    }

    private VerticalLayout createWrapper() {
        VerticalLayout wrapper = new VerticalLayout();
        wrapper.setId("wrapper");
        wrapper.setSpacing(false);
        return wrapper;
    }

    private void createFormLayout(VerticalLayout wrapper) {
        FormLayout formLayout = new FormLayout();
        addFormItem(wrapper, formLayout, loanSum, "Сумма кредита (в рублях)");
        addFormItem(wrapper, formLayout, loanPeriod, "Срок кредита (в месяцах)");
        addFormItem(wrapper, formLayout, loanRate, "Процентная ставка (в год)");
    }

    private void createButtonLayout(VerticalLayout wrapper) {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.addClassName("button-layout");
        buttonLayout.setWidthFull();
        buttonLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.END);
        cleanFields.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        calculateLoan.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        saveCalculations.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
        buttonLayout.add(cleanFields, calculateLoan, saveCalculations);
        wrapper.add(buttonLayout);
    }

    private void createGrid(VerticalLayout wrapper) {
        paymentsGrid.addColumn(Payment::getPaymentIndex).setHeader("Номер платежа").setSortable(false);
        paymentsGrid.addColumn(v -> v.getPaymentDate().format(DateTimeFormatter.ofPattern("MMM yyyy")))
                .setHeader("Месяц/Год платежа").setSortable(false);
        paymentsGrid.addColumn(Payment::getBodyPayment).setHeader("Платеж по основному долгу").setSortable(false);
        paymentsGrid.addColumn(Payment::getPercentsPayment).setHeader("Платеж по процентам").setSortable(false);
        paymentsGrid.addColumn(Payment::getLoanReminder).setHeader("Остаток основного долга").setSortable(false);
        paymentsGrid.addColumn(Payment::getWholePaymentSum).setHeader("Общая сумма платежа").setSortable(false);

        wrapper.add(paymentsGrid);
    }

    private FormLayout.FormItem addFormItem(VerticalLayout wrapper,
                                            FormLayout formLayout, Component field, String fieldName) {
        FormLayout.FormItem formItem = formLayout.addFormItem(field, fieldName);
        wrapper.add(formLayout);
        field.getElement().getClassList().add("full-width");
        return formItem;
    }

}
