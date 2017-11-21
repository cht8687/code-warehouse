package threewks.framework.usermanager.model;

import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.threewks.thundr.gae.objectify.Refs;
import com.threewks.thundr.user.Roles;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.joda.time.DateTime;
import org.joda.time.Duration;

import java.util.Collections;
import java.util.Set;

@Entity
public class UserInviteLink {

    public static String hash(String code) {
        return DigestUtils.sha256Hex(code);
    }

    @Id
    private String hashedCode;
    @Index
    private String email;
    private Set<String> roles;
    private Ref<AppUser> issuedBy;
    private DateTime created;
    private DateTime expires;
    private boolean redeemed;

    private UserInviteLink() {
    }

    public UserInviteLink(String code, AppUser issuer, String email, Roles roles) {
        this.hashedCode = hash(code);
        this.email = email;
        this.roles = roles == null ? Collections.<String>emptySet() : roles.getRoles();
        this.issuedBy = Ref.create(issuer);
        this.redeemed = false;
        this.created = DateTime.now();
        this.expires = created.plus(Duration.standardDays(2));
    }

    /**
     * Return a SHA256 hex hash of the code. We don't store the original since this is basically a
     * password.
     *
     * @return the hashed code
     */
    public String getHashedCode() {
        return hashedCode;
    }

    public String getEmail() {
        return email;
    }

    public Roles getRoles() {
        return new Roles(roles);
    }

    public AppUser getIssuedBy() {
        return Refs.unref(issuedBy);
    }

    public DateTime getCreated() {
        return created;
    }

    public boolean isRedeemed() {
        return redeemed;
    }

    public void setRedeemed(boolean redeemed) {
        this.redeemed = redeemed;
    }

    public boolean hasExpired() {
        return expires.isBeforeNow();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        UserInviteLink that = (UserInviteLink) o;

        return new EqualsBuilder()
            .append(hashedCode, that.hashedCode)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(hashedCode)
            .toHashCode();
    }
}
