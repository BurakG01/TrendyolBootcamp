package unit.test.pack;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import tech.talent.exception.*;
import tech.talent.language.Language;
import tech.talent.pack.SmsFixedPackage;

import java.math.BigDecimal;
import java.util.Calendar;

import static org.mockito.BDDMockito.given;

public class SmsFixedPackageTest {
    private SmsFixedPackage smsFixedPackage;
    private Language language;

    public SmsFixedPackageTest() {
        this.smsFixedPackage = new SmsFixedPackage();
        this.language = Mockito.mock(Language.class);

    }

    @Before
    public void setup() {
        smsFixedPackage.setCompanyLanguage(this.language);
        given(this.language.getSmsFixedLockMessage()).willReturn("blabla");
        given(this.language.getSmsFixedExpiredMessage()).willReturn("blabla");
    }

    @Test
    public void isExceed_should_return_true_when_count_is_greater_than_limit() {
        for (int i = 0; i < 1001; i++) {
            smsFixedPackage.increaseCount();
        }
        Assert.assertEquals(true, smsFixedPackage.isExceed());
    }

    @Test
    public void isLocked_should_return_false_when_latestPaymentDate_has_not_passed() {
        Calendar oneMonthAfter = Calendar.getInstance();
        oneMonthAfter.add(Calendar.MONTH, 1);

        boolean isLocked = smsFixedPackage.isLocked(oneMonthAfter);
        Assert.assertEquals(false, isLocked);
    }

    @Test(expected = SmsFixedLockException.class)
    public void isLock_should_throw_MailFixedLockException_when_latestPaymentDate_has_passed() {
        Calendar threeMonthAfter = Calendar.getInstance();
        threeMonthAfter.add(Calendar.MONTH, 3);
        smsFixedPackage.isLocked(threeMonthAfter);
    }

    @Test
    public void isExpired_should_return_false_when_endDate_has_not_passed() {
        Calendar tenDaysAfter = Calendar.getInstance();
        tenDaysAfter.add(Calendar.DAY_OF_MONTH, 10);

        boolean isExpired = smsFixedPackage.isExpired(tenDaysAfter);
        Assert.assertEquals(false, isExpired);
    }

    @Test(expected = SmsFixedExpiredException.class)
    public void isExpired_should_throw_MailFixedExpiredException_when_endDate_has_passed() {
        Calendar twoMonthsAfter = Calendar.getInstance();
        twoMonthsAfter.add(Calendar.MONTH, 2);

        smsFixedPackage.isExpired(twoMonthsAfter);
    }

    @Test
    public void resetCount_should_reset_count() {
        for (int i = 0; i < 100; i++) {
            smsFixedPackage.increaseCount();
        }
        smsFixedPackage.resetCount();
        int count = smsFixedPackage.getCount();
        Assert.assertEquals(0, count);
    }

    @Test
    public void addFixedPrice_should_add_fixed_price_to_current_price() {
        Calendar date = Calendar.getInstance();
        date.add(Calendar.MONTH, 2);
        BigDecimal expectedPrice = new BigDecimal(40);
        smsFixedPackage.addFixedPrice();
        BigDecimal currentPrice = smsFixedPackage.getCurrentPrice(date);
        Assert.assertEquals(expectedPrice, currentPrice);
    }

    @Test
    public void price_should_be_zero_when_invoice_has_paid() {
        smsFixedPackage.pay(Calendar.getInstance());
        Calendar date = Calendar.getInstance();
        date.add(Calendar.MONTH, 2);
        BigDecimal expectedPrice = new BigDecimal(0);
        BigDecimal currentPrice = smsFixedPackage.getCurrentPrice(date);
        Assert.assertEquals(expectedPrice, currentPrice);
    }

    @Test(expected = InvoiceIsNotReadyException.class)
    public void currentPrice_should_throw_InvoiceIsNotReadyException_when_one_month_has_not_passed_after_package_start() {
        smsFixedPackage.getCurrentPrice(Calendar.getInstance());
    }
}
