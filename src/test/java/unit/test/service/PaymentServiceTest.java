package unit.test.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import tech.talent.channel.Channel;
import tech.talent.language.Language;
import tech.talent.model.CompanyDto;
import tech.talent.pack.Package;
import tech.talent.service.PaymentService;

import java.util.ArrayList;
import java.util.List;

public class PaymentServiceTest {
    private CompanyDto company;
    private List<Package> packages;
    private List<Channel> channels;
    private Channel channel;
    private Package pack;
    private Language language;
    private PaymentService paymentService;

    public PaymentServiceTest() {
        this.pack = Mockito.mock(Package.class);
        this.packages = new ArrayList<>();
        this.channels = new ArrayList<>();
        this.language = Mockito.mock(Language.class);
        this.channel = Mockito.mock(Channel.class);

    }

    @Before
    public void setup() {
        this.channels.add(channel);
        this.packages.add(pack);
        this.packages.add(pack);
        this.company = new CompanyDto(13, "companyName", channels, language);
        this.paymentService=new PaymentService(packages,company);
    }
    @Test
    public void locked_company_should_be_unlock_when_all_pack_invoices_have_paid(){
        this.company.setLocked(true);
        paymentService.pay();
        Assert.assertEquals(false,company.isLocked());
    }

}
