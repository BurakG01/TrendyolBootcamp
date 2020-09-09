package unit.test.pack;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import tech.talent.exception.InvoiceIsNotReadyException;
import tech.talent.exception.MailFixedExpiredException;
import tech.talent.exception.MailFixedLockException;
import tech.talent.language.Language;
import tech.talent.pack.MailFixedPackage;

import java.math.BigDecimal;
import java.util.Calendar;

import static org.mockito.BDDMockito.given;

public class MailFixedPackageTest {
    private MailFixedPackage mailFixedPackage;
    private Language language;

    public MailFixedPackageTest() {
        this.mailFixedPackage = new MailFixedPackage();
        this.language = Mockito.mock(Language.class);

    }

    @Before
    public void setup() {
        mailFixedPackage.setCompanyLanguage(this.language);
        given(this.language.getMailFixedLockMessage()).willReturn("blabla");
        given(this.language.getMailFixedExpiredMessage()).willReturn("blabla");
    }

    @Test
    public void isExceed_should_return_true_when_count_is_greater_than_limit() {
        for (int i = 0; i < 1001; i++) {
            mailFixedPackage.increaseCount();
        }
        Assert.assertEquals(true, mailFixedPackage.isExceed());
    }

    @Test
    public void isLocked_should_return_false_when_latestPaymentDate_has_not_passed() {
        Calendar oneMonthAfter = Calendar.getInstance();
        oneMonthAfter.add(Calendar.MONTH, 1);

        boolean isLocked = mailFixedPackage.isLocked(oneMonthAfter);
        Assert.assertEquals(false, isLocked);
    }

    @Test(expected = MailFixedLockException.class)
    public void isLock_should_throw_MailFixedLockException_when_latestPaymentDate_has_passed() {
        Calendar threeMonthAfter = Calendar.getInstance();
        threeMonthAfter.add(Calendar.MONTH, 3);
        mailFixedPackage.isLocked(threeMonthAfter);
    }

    @Test
    public void isExpired_should_return_false_when_endDate_has_not_passed() {
        Calendar tenDaysAfter = Calendar.getInstance();
        tenDaysAfter.add(Calendar.DAY_OF_MONTH, 10);

        boolean isExpired = mailFixedPackage.isExpired(tenDaysAfter);
        Assert.assertEquals(false, isExpired);
    }

    @Test(expected = MailFixedExpiredException.class)
    public void isExpired_should_throw_MailFixedExpiredException_when_endDate_has_passed() {
        Calendar twoMonthsAfter = Calendar.getInstance();
        twoMonthsAfter.add(Calendar.MONTH, 2);

        mailFixedPackage.isExpired(twoMonthsAfter);
    }

    @Test
    public void resetCount_should_reset_count() {
        for (int i = 0; i < 100; i++) {
            mailFixedPackage.increaseCount();
        }
        mailFixedPackage.resetCount();
        int count = mailFixedPackage.getCount();
        Assert.assertEquals(0, count);
    }

    @Test
    public void addFixedPrice_should_add_fixed_price_to_current_price() {
        Calendar date = Calendar.getInstance();
        date.add(Calendar.MONTH, 2);
        BigDecimal expectedPrice = new BigDecimal(20);
        mailFixedPackage.addFixedPrice();
        BigDecimal currentPrice = mailFixedPackage.getCurrentPrice(date);
        Assert.assertEquals(expectedPrice, currentPrice);
    }

    @Test
    public void price_should_be_zero_when_invoice_has_paid() {
        mailFixedPackage.pay(Calendar.getInstance());
        Calendar date = Calendar.getInstance();
        date.add(Calendar.MONTH, 2);
        BigDecimal expectedPrice = new BigDecimal(0);
        BigDecimal currentPrice = mailFixedPackage.getCurrentPrice(date);
        Assert.assertEquals(expectedPrice, currentPrice);
    }

    @Test(expected = InvoiceIsNotReadyException.class)
    public void currentPrice_should_throw_InvoiceIsNotReadyException_when_one_month_has_not_passed_after_package_start() {
        mailFixedPackage.getCurrentPrice(Calendar.getInstance());
    }


}
