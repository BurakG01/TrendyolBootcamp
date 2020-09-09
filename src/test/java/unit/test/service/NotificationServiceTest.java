package unit.test.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import tech.talent.channel.Channel;
import tech.talent.exception.CompanyLockedException;
import tech.talent.exception.MailFixedLockException;
import tech.talent.language.Language;
import tech.talent.model.CompanyDto;
import tech.talent.service.NotificationService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.doThrow;

public class NotificationServiceTest {
    private CompanyDto company;
    private List<Channel> channels;
    private Channel channel;
    private Language language;
    private NotificationService notificationService;

    public NotificationServiceTest() {
        this.channel = Mockito.mock(Channel.class);
        this.language = Mockito.mock(Language.class);
        this.channels = new ArrayList<>();
    }

    @Before
    public void setup() {
        this.channels.add(channel);
        this.company = new CompanyDto(13, "companyName", channels, language);
        this.notificationService = new NotificationService(company);
    }

    @Test(expected = CompanyLockedException.class)
    public void NotificationService_should_throw_CompanyLockedException_when_company_is_lock() {
        this.company.setLocked(true);
        this.notificationService.send("blabla");
    }

    @Test
    public void company_should_be_locked_when_catch_locked_exception() {
        String message = "Welcome to TechTalent";
        doThrow(new MailFixedLockException("sasd")).when(channel).send(message, language);
        notificationService.send(message);
        Assert.assertEquals(true, company.isLocked());
    }


}
