package tech.talent.pack;

import tech.talent.exception.*;

import java.math.BigDecimal;
import java.util.Calendar;

public class MailFixedPackage extends  FixedPackage {
    private final static int MAIL_FIXED_QUOTA_LIMIT = 1000;
    private final static int MAIL_FIXED_PRICE = 10;

    public MailFixedPackage() {
        super(MAIL_FIXED_QUOTA_LIMIT, new BigDecimal(MAIL_FIXED_PRICE));
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
