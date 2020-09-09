package unit.test.pack;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import tech.talent.exception.*;
import tech.talent.language.Language;
import tech.talent.pack.SmsFlexiblePackage;

import java.math.BigDecimal;
import java.util.Calendar;

import static org.mockito.BDDMockito.given;

public class SmsFlexiblePackageTest {
    private final static double MAIL_FLEXIBLE_INCREASE_PRICE = 0.1;
    private SmsFlexiblePackage smsFlexiblePackage;
    private Language language;

    public SmsFlexiblePackageTest() {
        this.smsFlexiblePackage = new SmsFlexiblePackage();
        this.language = Mockito.mock(Language.class);

    }

    @Before
    public void setup() {
        smsFlexiblePackage.setCompanyLanguage(this.language);
        given(this.language.getSmsFlexibleLockMessage()).willReturn("blabla");
        given(this.language.getSmsFlexibleExpiredMessage()).willReturn("blabla");
    }

    @Test
    public void isExceed_should_return_true_when_count_is_greater_than_limit() {
        for (int i = 0; i < 2001; i++) {
            smsFlexiblePackage.increaseCount();
        }
        Assert.assertEquals(true, smsFlexiblePackage.isExceed());
    }

    @Test
    public void isLocked_should_return_false_when_latestPaymentDate_has_not_passed() {
        Calendar oneMonthAfter = Calendar.getInstance();
        oneMonthAfter.add(Calendar.MONTH, 1);

        boolean isLocked = smsFlexiblePackage.isLocked(oneMonthAfter);
        Assert.assertEquals(false, isLocked);
    }

    @Test(expected = SmsFlexibleLockException.class)
    public void isLock_should_throw_MailFixedLockException_when_latestPaymentDate_has_passed() {
        Calendar threeMonthAfter = Calendar.getInstance();
        threeMonthAfter.add(Calendar.MONTH, 3);
        smsFlexiblePackage.isLocked(threeMonthAfter);
    }

    @Test
    public void isExpired_should_return_false_when_endDate_has_not_passed() {
        Calendar tenDaysAfter = Calendar.getInstance();
        tenDaysAfter.add(Calendar.DAY_OF_MONTH, 10);

        boolean isExpired = smsFlexiblePackage.isExpired(tenDaysAfter);
        Assert.assertEquals(false, isExpired);
    }

    @Test(expected = SmsFlexibleExpiredException.class)
    public void isExpired_should_throw_MailFixedExpiredException_when_endDate_has_passed() {
        Calendar twoMonthsAfter = Calendar.getInstance();
        twoMonthsAfter.add(Calendar.MONTH, 2);

        smsFlexiblePackage.isExpired(twoMonthsAfter);
    }

    @Test
    public void price_should_be_increased_by_package_increase_price() {

        BigDecimal expectedPrice = new BigDecimal(30);
        expectedPrice = expectedPrice.add(new BigDecimal(MAIL_FLEXIBLE_INCREASE_PRICE));
        smsFlexiblePackage.increasePrice();

        Calendar date = Calendar.getInstance();
        date.add(Calendar.MONTH, 2);

        BigDecimal currentPrice = smsFlexiblePackage.getCurrentPrice(date);
        Assert.assertEquals(expectedPrice, currentPrice);
    }

    @Test
    public void price_should_be_zero_when_invoice_has_paid() {
        smsFlexiblePackage.pay(Calendar.getInstance(),new BigDecimal(30));
        Calendar date = Calendar.getInstance();
        date.add(Calendar.MONTH, 2);
        BigDecimal expectedPrice = new BigDecimal(0);
        BigDecimal currentPrice = smsFlexiblePackage.getCurrentPrice(date);
        Assert.assertEquals(expectedPrice, currentPrice);
    }

    @Test(expected = InvoiceIsNotReadyException.class)
    public void currentPrice_should_throw_InvoiceIsNotReadyException_when_one_month_has_not_passed_after_package_start() {
        smsFlexiblePackage.getCurrentPrice(Calendar.getInstance());
    }
}
