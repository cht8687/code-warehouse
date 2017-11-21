package threewks.framework.usermanager.model;

import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Load;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.joda.time.DateTime;
import org.joda.time.Duration;

@Entity
public class MagicLink {

    public static String hash(String code) {
        return DigestUtils.sha256Hex(code);
    }

    @Id
    private String hashedCode;
    @Load
    private Ref<AppUser> user;
    private DateTime created;
    private DateTime expires;

    // Default constructor required for Objectify
    private MagicLink() {
    }

    public MagicLink(String code, AppUser user, Integer magicLinkExpiryInMinutes) {
        this.hashedCode = hash(code);
        this.user = Ref.create(user);
        this.created = DateTime.now();
        this.expires = created.plus(Duration.standardMinutes(magicLinkExpiryInMinutes));
    }

    /**
     * Return a SHA256 hex hash of the code. We don't store the original since this is basically a password.
     *
     * @return the hashed code
     */
    public String getHashedCode() {
        return hashedCode;
    }

    public AppUser getUser() {
        return user.get();
    }

    public boolean hasExpired() {
        return expires.isBeforeNow();
    }

    public DateTime getExpires() {
        return expires;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        MagicLink magicLink = (MagicLink) o;

        return new EqualsBuilder()
            .append(hashedCode, magicLink.hashedCode)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(hashedCode)
            .toHashCode();
    }
}
