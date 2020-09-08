package tech.talent.pack;

import tech.talent.exception.*;

import java.math.BigDecimal;
import java.util.Calendar;

public class MailFlexiblePackage extends FlexiblePackage {
    private final static int MAIL_FLEXIBLE_QUOTA_LIMIT = 2000;
    private final static double MAIL_FLEXIBLE_INITIAL_PRICE = 7.5;
    private final static double MAIL_FLEXIBLE_INCREASE_PRICE = 0.03;

    protected MailFlexiblePackage() {
        super(MAIL_FLEXIBLE_QUOTA_LIMIT, new BigDecimal(MAIL_FLEXIBLE_INITIAL_PRICE));
    }

    @Override
    public void increasePrice() {
        this.price = price.add(new BigDecimal(MAIL_FLEXIBLE_INCREASE_PRICE));
    }

    @Override
    public boolean isLocked() throws LockedException {
        if ((Calendar.getInstance().after(latestPaymentDate) && !isPaid) ||
                (isPaid && paymentDate.after(latestPaymentDate))) {
            throw new MailLockException();
        }
        return false;
    }

    @Override
    public boolean isExpired() throws ExpiredException {
        if (Calendar.getInstance().after(endDate)) {
            throw new MailExpiredException();
        }
        return false;
    }
}
