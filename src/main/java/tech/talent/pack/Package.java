package tech.talent.pack;

import tech.talent.exception.ExpiredException;
import tech.talent.exception.InvoiceIsNotReadyException;
import tech.talent.exception.LockedException;
import tech.talent.language.Language;

import java.math.BigDecimal;
import java.util.Calendar;

public abstract class Package {

    public final static int INITIAL_COUNT = 0;
    public Calendar startDate;
    public Calendar latestPaymentDate;
    public Calendar paymentDate;
    public Calendar endDate;
    public boolean isPaid;
    public int count;
    private int limit;
    public Language language;
    public BigDecimal price;

    protected Package(int limit, BigDecimal price) {
        this.startDate = Calendar.getInstance();
        this.endDate = Calendar.getInstance();
        this.latestPaymentDate = Calendar.getInstance();
        this.endDate.add(Calendar.MONTH, 1);
        this.latestPaymentDate.add(Calendar.MONTH, 2);
        this.limit = limit;
        this.count = INITIAL_COUNT;
        this.isPaid = false;
        this.price = price;
    }

    public void setPriceZero() {
        this.price = new BigDecimal(0);
    }

    public BigDecimal getCurrentPrice(Calendar date) throws InvoiceIsNotReadyException {
        if (endDate.after(date)) {
            throw new InvoiceIsNotReadyException(language.getInvoiceIsNotReadyMessage());
        }
        return price;
    }

    public void pay(Calendar date) {
        setPriceZero();
        this.isPaid = true;
        paymentDate = date;
    }

    public void setCompanyLanguage(Language language) {
        this.language = language;
    }

    public abstract boolean isLocked(Calendar date) throws LockedException;

    public abstract boolean isExpired(Calendar date) throws ExpiredException;

    public void increaseCount() {
        this.count++;
    }

    public boolean isExceed() {
        return count > limit;
    }


}
