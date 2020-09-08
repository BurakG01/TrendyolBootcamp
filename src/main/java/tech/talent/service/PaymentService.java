package tech.talent.service;

import tech.talent.exception.InvoiceIsNotReadyException;
import tech.talent.pack.Package;

import java.math.BigDecimal;
import java.util.List;

public class PaymentService {
    List<Package> packages;

    public PaymentService(List<Package> packages) {
        this.packages = packages;
    }

    public void pay(BigDecimal invoicePrice) {
        for (Package pack : packages) {
            try {
                BigDecimal invoice = pack.getCurrentPrice();
                if (invoicePrice == invoice) {
                    pack.pay();
                }
            } catch (InvoiceIsNotReadyException exception) {
                System.out.println(exception.getMessage());

            }
        }
    }
}
