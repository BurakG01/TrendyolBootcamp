package unit.test.validator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import tech.talent.exception.ReceiverNotValidException;
import tech.talent.language.Language;
import tech.talent.model.ReceiverDto;
import tech.talent.validator.MailValidator;
import tech.talent.validator.Validator;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class MailValidatorTest {

    private Validator validator;

    private ReceiverDto invalidReceiver;
    private ReceiverDto validReceiver;
    private Language language;

    public MailValidatorTest() {

        this.validator = new MailValidator();
        this.language = mock(Language.class);
        validator.setCompanyLanguage(language);
    }

    @Before
    public void setup() {
        validReceiver = new ReceiverDto();
        validReceiver.setName("Kobe Bryant");
        validReceiver.setEmail("kobe@gmail.com");

        invalidReceiver = new ReceiverDto();
    }

    @Test(expected = ReceiverNotValidException.class)
    public void mail_validator_should_throw_receiver_not_valid_exception_when_receiver_mail_is_null() {
        given(this.language.getMailReceiverNotValidMessage()).willReturn("blabla");

        validator.isValid(invalidReceiver);
    }

    @Test
    public void mail_validator_should_return_true_when_valid_receiver() {

        boolean isValid = validator.isValid(validReceiver);
        Assert.assertEquals(true,isValid);
    }

}
