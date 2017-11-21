package threewks.framework.usermanager.model;

import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.threewks.thundr.gae.objectify.Refs;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.joda.time.DateTime;

@Entity
public class LoginIdentifier {
    @Id
    private String loginIdentifier;
    private DateTime created;
    private Ref<AppUser> user;

    // For Objectify
    private LoginIdentifier() {
    }

    public LoginIdentifier(AppUser user) {
        this.loginIdentifier = user.getEmail();
        this.created = DateTime.now();
        this.user = Ref.create(user);
    }

    public String getLoginIdentifier() {
        return loginIdentifier;
    }

    public DateTime getCreated() {
        return created;
    }

    public AppUser getUser() {
        return Refs.unref(user);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        LoginIdentifier that = (LoginIdentifier) o;

        return new EqualsBuilder()
            .append(loginIdentifier, that.loginIdentifier)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(loginIdentifier)
            .toHashCode();
    }
}
