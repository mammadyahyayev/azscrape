package az.my.datareport.config;

import az.my.datareport.utils.Assert;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Author {
    private final String fullName;
    private Map<String, String> socialLinks;

    public Author(String fullName) {
        this.fullName = fullName;
        socialLinks = new HashMap<>();
    }

    //TODO: if required, change social String to enum
    public void addSocialLink(String social, String link) {
        if (socialLinks.containsKey(social)) {
            return;
        }

        socialLinks.put(social, link);
    }

    public void removeSocialLink(String social) {
        Assert.required(social, "Social account is required field");
        this.socialLinks.remove(social);
    }

    public String getFullName() {
        return fullName;
    }

    public Map<String, String> getSocialLinks() {
        return Collections.unmodifiableMap(this.socialLinks);
    }
}
