package tech.talent.pack;

import tech.talent.exception.SmsFixedExpiredException;
import tech.talent.exception.SmsFixedLockException;

import java.math.BigDecimal;
import java.util.Calendar;

public class SmsFixedPackage extends FixedPackage {
    private final static int SMS_FIXED_QUOTA_LIMIT = 1000;
    private final static int SMS_FIXED_PRICE = 20;

    public SmsFixedPackage() {
        super(SMS_FIXED_QUOTA_LIMIT, new BigDecimal(SMS_FIXED_PRICE));

    }

    @Override
    public boolean isLocked(Calendar date) throws SmsFixedLockException {
        if ((date.after(latestPaymentDate) && !isPaid)) {
            throw new SmsFixedLockException(language.getSmsFixedLockMessage());
        }
        return false;
    }

    @Override
    public boolean isExpired(Calendar date) throws SmsFixedExpiredException {
        if (date.after(endDate)) {
            throw new SmsFixedExpiredException(language.getSmsFixedExpiredMessage());
        }
        return false;
    }


}
