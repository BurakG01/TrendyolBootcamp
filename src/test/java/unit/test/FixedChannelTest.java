package unit.test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import tech.talent.channel.FixedChannel;
import tech.talent.exception.ExpiredException;
import tech.talent.exception.LockedException;
import tech.talent.model.ReceiverDto;
import tech.talent.pack.FixedPackage;
import tech.talent.validator.Validator;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class FixedChannelTest {
    private FixedChannel fixedChannel;
    private FixedPackage fixedPackage;
    private Validator validator;
    private List<ReceiverDto> receivers;

    public FixedChannelTest() {
        this.receivers = new ArrayList<>();
        this.fixedPackage = mock(FixedPackage.class);
        this.validator = mock(Validator.class);
    }

    @Before
    public void setup() {
        ReceiverDto receiverDto = new ReceiverDto();
        receiverDto.setEmail("email@sdsd");
        receiverDto.setName("sdsda");
        receiverDto.setPhoneNumber("23112332323");
        receivers.add(receiverDto);
        this.fixedChannel = new FixedChannel(this.fixedPackage, this.validator, receivers);
    }


    @Test(expected = LockedException.class)
    public void fixed_channel_should_throw_lockedException_when_package_is_lock() {
        given(this.fixedPackage.isLocked()).willThrow(new LockedException("come on!") {
        });

        this.fixedChannel.send("lest go!");
    }

    @Test(expected = ExpiredException.class)
    public void fixed_channel_should_throw_expiredException_when_package_is_expired() {
        given(this.fixedPackage.isExpired()).willThrow(new ExpiredException("Hey you!") {
        });
        this.fixedChannel.send("Yeah!");
    }

}
