package tech.talent.pack;

import tech.talent.exception.*;

import java.math.BigDecimal;
import java.util.Calendar;

public class SmsFlexiblePackage extends FlexiblePackage {
    private final static int SMS_FLEXIBLE_QUOTA_LIMIT = 2000;
    private final static int SMS_FLEXIBLE_INITIAL_PRICE = 30;
    private final static double SMS_FLEXIBLE_INCREASE_PRICE = 0.1;

    public SmsFlexiblePackage() {
        super(SMS_FLEXIBLE_QUOTA_LIMIT, new BigDecimal(SMS_FLEXIBLE_INITIAL_PRICE));
    }

    @Override
    public void increasePrice() {
        this.price = price.add(new BigDecimal(SMS_FLEXIBLE_INCREASE_PRICE));
    }

    @Override
    public boolean isLocked(Calendar date) throws LockedException {
        if ((date.after(latestPaymentDate) && !isPaid)) {
            throw new SmsFlexibleLockException(language.getSmsFlexibleLockMessage());
        }
        return false;
    }

    @Override
    public boolean isExpired(Calendar date) throws ExpiredException {
        if (date.after(endDate)) {
            throw new SmsFlexibleExpiredException(language.getSmsFlexibleExpiredMessage());
        }
        return false;
    }
}
