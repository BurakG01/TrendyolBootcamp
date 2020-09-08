package tech.talent.pack;

import tech.talent.exception.ExpiredException;
import tech.talent.exception.LockedException;
import tech.talent.exception.SmsExpiredException;
import tech.talent.exception.SmsLockException;

import java.math.BigDecimal;
import java.util.Calendar;

public class SmsFlexiblePackage extends  FlexiblePackage {
    private final static int SMS_FLEXIBLE_QUOTA_LIMIT = 2000;
    private final static int SMS_FLEXIBLE_INITIAL_PRICE = 30;
    private final static double SMS_FLEXIBLE_INCREASE_PRICE = 0.1;

    protected SmsFlexiblePackage() {
        super(SMS_FLEXIBLE_QUOTA_LIMIT, new BigDecimal(SMS_FLEXIBLE_INITIAL_PRICE));
    }

    @Override
    public void increasePrice() {
        this.price = price.add(new BigDecimal(SMS_FLEXIBLE_INCREASE_PRICE));
    }

    @Override
    public boolean isLocked() throws LockedException {
        if ((Calendar.getInstance().after(latestPaymentDate) && !isPaid) ||
                (isPaid && paymentDate.after(latestPaymentDate))) {
            throw new SmsLockException();
        }
        return false;
    }

    @Override
    public boolean isExpired() throws ExpiredException {
        if (Calendar.getInstance().after(endDate)) {
            throw new SmsExpiredException();
        }
        return false;
    }
}
