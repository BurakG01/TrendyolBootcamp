package tech.talent.service;

import tech.talent.exception.InvoiceIsNotReadyException;
import tech.talent.model.CompanyDto;
import tech.talent.pack.Package;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

public class PaymentService {
    private List<Package> packages;
    private CompanyDto company;

    public PaymentService(List<Package> packages, CompanyDto company) {
        this.packages = packages;
        this.company = company;
    }

    public void pay() {
        for (Package pack : packages) {
            try {
                BigDecimal invoice = pack.getCurrentPrice(Calendar.getInstance());
                pack.pay(Calendar.getInstance(), invoice);
            } catch (InvoiceIsNotReadyException exception) {
                System.out.println(exception.getMessage());
            }
        }
        if (company.isLocked()) {
            company.setLocked(false);
            company.clearLockReason();
        }

    }
}
