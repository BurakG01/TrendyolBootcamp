package unit.test.channel;

import org.junit.Before;
import org.junit.Test;
import tech.talent.channel.FlexibleChannel;
import tech.talent.exception.ExpiredException;
import tech.talent.exception.LockedException;
import tech.talent.language.Language;
import tech.talent.model.ReceiverDto;
import tech.talent.pack.FlexiblePackage;
import tech.talent.validator.Validator;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class FlexibleChannelTest {

    private FlexibleChannel flexibleChannel;
    private FlexiblePackage flexiblePackage;
    private Validator validator;
    private List<ReceiverDto> receivers;
    private Language language;

    public FlexibleChannelTest() {
        this.receivers = new ArrayList<>();
        this.language = mock(Language.class);
        this.flexiblePackage = mock(FlexiblePackage.class);
        this.validator = mock(Validator.class);
    }

    @Before
    public void setup() {
        ReceiverDto receiverDto = new ReceiverDto();
        receiverDto.setEmail("email@sdsd");
        receiverDto.setName("sdsda");
        receiverDto.setPhoneNumber("23112332323");
        receivers.add(receiverDto);
        this.flexibleChannel = new FlexibleChannel(this.flexiblePackage, this.validator, receivers);
    }


    @Test(expected = LockedException.class)
    public void flexible_channel_should_throw_lockedException_when_package_is_lock() {
        given(this.flexiblePackage.isLocked(Calendar.getInstance())).willThrow(new LockedException("come on!") {
        });

        this.flexibleChannel.send("lest go!", language);
    }

    @Test(expected = ExpiredException.class)
    public void flexible_channel_should_throw_expiredException_when_package_is_expired() {
        given(this.flexiblePackage.isExpired(Calendar.getInstance())).willThrow(new ExpiredException("Hey you!") {
        });
        this.flexibleChannel.send("Yeah!", language);
    }
}
