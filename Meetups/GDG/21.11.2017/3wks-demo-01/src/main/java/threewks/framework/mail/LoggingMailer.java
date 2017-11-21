package threewks.framework.mail;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.threewks.thundr.logger.Logger;
import com.threewks.thundr.mail.Attachment;
import com.threewks.thundr.mail.BaseMailer;
import com.threewks.thundr.request.InMemoryResponse;
import com.threewks.thundr.request.RequestContainer;
import com.threewks.thundr.view.ViewResolverRegistry;
import org.apache.commons.lang3.StringUtils;
import threewks.framework.shared.util.HtmlFormattingUtil;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class LoggingMailer extends BaseMailer {

    public LoggingMailer(ViewResolverRegistry viewResolverRegistry, RequestContainer requestContainer) {
        super(viewResolverRegistry, requestContainer);
        Logger.info("%s: All emails will be logged and not sent", getClass().getSimpleName());
    }

    @Override
    protected void sendInternal(Map.Entry<String, String> from, Map.Entry<String, String> replyTo, Map<String, String> to, Map<String, String> cc, Map<String, String> bcc, String subject, Object body, List<Attachment> attachments) {
        String bodyString = getBodyAsString(body);

        StringBuilder sb = new StringBuilder();
        addProperty(sb, "from", toEmail(from));
        addProperty(sb, "replyTo", toEmail(replyTo));
        addProperty(sb, "to", toEmails(to));
        addProperty(sb, "cc", toEmails(cc));
        addProperty(sb, "bcc", toEmails(bcc));
        addProperty(sb, "subject", subject);
        addProperty(sb, "attachments", attachments == null || attachments.isEmpty() ? null : String.valueOf(attachments.size()));
        sb.append(StringUtils.defaultString(bodyString, "")).append("\n\n");

        Logger.info("%s email: \n\n%s", getClass().getSimpleName(), sb.toString());
    }

    private String toEmails(Map<String, String> emailMap) {
        if (emailMap == null) {
            return null;
        }

        Collection<String> emails = Collections2.transform(emailMap.entrySet(), new Function<Map.Entry<String, String>, String>() {
            @Nullable
            @Override
            public String apply(@Nullable Map.Entry<String, String> emailEntry) {
                return toEmail(emailEntry);
            }
        });

        return StringUtils.join(emails, ", ");
    }

    private String toEmail(Map.Entry<String, String> emailDefinition) {
        if (emailDefinition == null) {
            return null;
        }

        return StringUtils.isBlank(emailDefinition.getValue()) ?
            emailDefinition.getKey() :
            String.format("\"%s\" <%s>", emailDefinition.getValue(), emailDefinition.getKey());
    }

    private void addProperty(StringBuilder sb, String key, String value) {
        if (StringUtils.isNotBlank(value)) {
            sb
                .append("   ")
                .append(StringUtils.rightPad(key, 20))
                .append(": ")
                .append(value)
                .append("\n");
        }

    }

    String getBodyAsString(Object body) {
        InMemoryResponse bodyResponse = render(body);
        return HtmlFormattingUtil.toPlainText(bodyResponse.getBodyAsString());
    }
}
