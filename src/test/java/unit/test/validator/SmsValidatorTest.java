package unit.test.validator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import tech.talent.exception.ReceiverNotValidException;
import tech.talent.language.Language;
import tech.talent.model.ReceiverDto;
import tech.talent.validator.SmsValidator;
import tech.talent.validator.Validator;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class SmsValidatorTest {
    private Validator validator;

    private ReceiverDto invalidReceiver;
    private ReceiverDto validReceiver;
    private Language language;

    public SmsValidatorTest() {

        this.validator = new SmsValidator();
        this.language = mock(Language.class);
        validator.setCompanyLanguage(language);
    }

    @Before
    public void setup() {
        validReceiver = new ReceiverDto();
        validReceiver.setName("Kobe Bryant");
        validReceiver.setPhoneNumber("2212332123");

        invalidReceiver = new ReceiverDto();
    }

    @Test(expected = ReceiverNotValidException.class)
    public void sms_validator_should_throw_receiver_not_valid_exception_when_receiver_mail_is_null() {
        given(this.language.getMailReceiverNotValidMessage()).willReturn("blabla");

        validator.isValid(invalidReceiver);
    }

    @Test
    public void sms_validator_should_return_true_when_valid_receiver() {

        boolean isValid = validator.isValid(validReceiver);
        Assert.assertEquals(true,isValid);
    }
}
