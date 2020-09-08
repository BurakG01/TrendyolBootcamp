package tech.talent.pack;

import tech.talent.exception.SmsExpiredException;
import tech.talent.exception.SmsLockException;

import java.math.BigDecimal;
import java.util.Calendar;

public class SmsFixedPackage extends FixedPackage {
    private final static int SMS_FIXED_QUOTA_LIMIT = 1000;
    private final static int SMS_FIXED_PRICE = 20;

    public SmsFixedPackage() {
        super(SMS_FIXED_QUOTA_LIMIT, new BigDecimal(SMS_FIXED_PRICE));

    }

    @Override
    public boolean isLocked() throws SmsLockException {
        if ((Calendar.getInstance().after(latestPaymentDate) && !isPaid) ||
                (isPaid && paymentDate.after(latestPaymentDate))) {
            throw new SmsLockException();
        }
        return false;
    }

    @Override
    public boolean isExpired() throws SmsExpiredException {
        if (Calendar.getInstance().after(endDate)) {
            throw new SmsExpiredException();
        }
        return false;
    }


}
