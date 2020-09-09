package tech.talent.pack;

import tech.talent.exception.*;

import java.math.BigDecimal;
import java.util.Calendar;

public class MailFlexiblePackage extends FlexiblePackage {
    private final static int MAIL_FLEXIBLE_QUOTA_LIMIT = 2000;
    private final static double MAIL_FLEXIBLE_INITIAL_PRICE = 7.5;
    private final static double MAIL_FLEXIBLE_INCREASE_PRICE = 0.03;

    public MailFlexiblePackage() {
        super(MAIL_FLEXIBLE_QUOTA_LIMIT, new BigDecimal(MAIL_FLEXIBLE_INITIAL_PRICE));
    }

    @Override
    public void increasePrice() {
        this.price = price.add(new BigDecimal(MAIL_FLEXIBLE_INCREASE_PRICE));
    }

    @Override
    public boolean isLocked(Calendar date) throws LockedException {
        if ((date.after(latestPaymentDate) && !isPaid) ||
                (isPaid && paymentDate.after(latestPaymentDate))) {
            throw new MailFlexibleLockException(language.getMailFlexibleLockMessage());
        }
        return false;
    }

    @Override
    public boolean isExpired(Calendar date) throws ExpiredException {
        if (date.after(endDate)) {
            throw new MailFlexibleExpiredException(language.getMailFlexibleExpiredMessage());
        }
        return false;
    }
}
