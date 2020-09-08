package tech.talent.model;

import tech.talent.channel.Channel;
import tech.talent.language.Language;

import java.util.List;

public class CompanyDto {

    private int companyId;
    private String companyName;
    private List<Channel> channels;
    private Language language;
    private boolean isLocked;
    private String lockReason;


    public CompanyDto(int companyId, String companyName, List<Channel> channels, Language language) {
        this.companyId = companyId;
        this.companyName = companyName;
        this.channels = channels;
        this.language = language;
        this.isLocked = false;
    }

    public boolean isLocked() {
        return isLocked;
    }

    public void setLocked(boolean locked) {
        isLocked = locked;
    }

    public String getLockReason() {
        return lockReason;
    }

    public void setLockReason(String lockReason) {
        this.lockReason = lockReason;
    }

    public int getCompanyId() {
        return companyId;
    }

    public Language getLanguage() {
        return language;
    }

    public String getCompanyName() {
        return companyName;
    }

    public List<Channel> getChannels() {
        return channels;
    }
}
