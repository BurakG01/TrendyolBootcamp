package unit.test.pack;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import tech.talent.exception.*;
import tech.talent.language.Language;
import tech.talent.pack.MailFlexiblePackage;

import java.math.BigDecimal;
import java.util.Calendar;

import static org.mockito.BDDMockito.given;

public class MailFlexiblePackageTest {
    private final static double MAIL_FLEXIBLE_INCREASE_PRICE = 0.03;
    private MailFlexiblePackage mailFlexiblePackage;
    private Language language;

    public MailFlexiblePackageTest() {
        this.mailFlexiblePackage = new MailFlexiblePackage();
        this.language = Mockito.mock(Language.class);

    }

    @Before
    public void setup() {
        mailFlexiblePackage.setCompanyLanguage(this.language);
        given(this.language.getMailFlexibleLockMessage()).willReturn("blabla");
        given(this.language.getMailFlexibleExpiredMessage()).willReturn("blabla");
    }

    @Test
    public void isExceed_should_return_true_when_count_is_greater_than_limit() {
        for (int i = 0; i < 2001; i++) {
            mailFlexiblePackage.increaseCount();
        }
        Assert.assertEquals(true, mailFlexiblePackage.isExceed());
    }

    @Test
    public void isLocked_should_return_false_when_latestPaymentDate_has_not_passed() {
        Calendar oneMonthAfter = Calendar.getInstance();
        oneMonthAfter.add(Calendar.MONTH, 1);

        boolean isLocked = mailFlexiblePackage.isLocked(oneMonthAfter);
        Assert.assertEquals(false, isLocked);
    }

    @Test(expected = MailFlexibleLockException.class)
    public void isLock_should_throw_MailFixedLockException_when_latestPaymentDate_has_passed() {
        Calendar threeMonthAfter = Calendar.getInstance();
        threeMonthAfter.add(Calendar.MONTH, 3);
        mailFlexiblePackage.isLocked(threeMonthAfter);
    }

    @Test
    public void isExpired_should_return_false_when_endDate_has_not_passed() {
        Calendar tenDaysAfter = Calendar.getInstance();
        tenDaysAfter.add(Calendar.DAY_OF_MONTH, 10);

        boolean isExpired = mailFlexiblePackage.isExpired(tenDaysAfter);
        Assert.assertEquals(false, isExpired);
    }

    @Test(expected = MailFlexibleExpiredException.class)
    public void isExpired_should_throw_MailFixedExpiredException_when_endDate_has_passed() {
        Calendar twoMonthsAfter = Calendar.getInstance();
        twoMonthsAfter.add(Calendar.MONTH, 2);

        mailFlexiblePackage.isExpired(twoMonthsAfter);
    }

    @Test
    public void price_should_be_increased_by_package_increase_price() {

        BigDecimal expectedPrice = new BigDecimal(7.5);
        expectedPrice = expectedPrice.add(new BigDecimal(MAIL_FLEXIBLE_INCREASE_PRICE));
        mailFlexiblePackage.increasePrice();

        Calendar date = Calendar.getInstance();
        date.add(Calendar.MONTH, 2);

        BigDecimal currentPrice = mailFlexiblePackage.getCurrentPrice(date);
        Assert.assertEquals(expectedPrice, currentPrice);
    }

    @Test
    public void price_should_be_zero_when_invoice_has_paid() {
        mailFlexiblePackage.pay(Calendar.getInstance());
        Calendar date = Calendar.getInstance();
        date.add(Calendar.MONTH, 2);
        BigDecimal expectedPrice = new BigDecimal(0);
        BigDecimal currentPrice = mailFlexiblePackage.getCurrentPrice(date);
        Assert.assertEquals(expectedPrice, currentPrice);
    }

    @Test(expected = InvoiceIsNotReadyException.class)
    public void currentPrice_should_throw_InvoiceIsNotReadyException_when_one_month_has_not_passed_after_package_start() {
        mailFlexiblePackage.getCurrentPrice(Calendar.getInstance());
    }
}
