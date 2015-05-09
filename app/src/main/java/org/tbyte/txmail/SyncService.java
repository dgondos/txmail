package org.tbyte.txmail;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsMessage;

import java.util.Date;
import java.util.Properties;

import korex.mail.BodyPart;
import korex.mail.MessagingException;
import korex.mail.PasswordAuthentication;
import korex.mail.Session;
import korex.mail.Transport;
import korex.mail.internet.InternetAddress;
import korex.mail.internet.MimeBodyPart;
import korex.mail.internet.MimeMessage;
import korex.mail.internet.MimeMultipart;

public class SyncService extends BroadcastReceiver {
    @Override
    public void onReceive(Context c, Intent intent) {
        if (!TxConfig.get(c).getBoolean("sync_enabled", false)) {
            return;
        }

        byte[] pdu = (byte[]) ((Object[]) intent.getExtras().get("pdus"))[0];

        SmsMessage sms = SmsMessage.createFromPdu(pdu);
        String sms_orig_address = sms.getDisplayOriginatingAddress();
        String sms_body = sms.getDisplayMessageBody();

        SMTPSender sender = new SMTPSender(TxConfig.get(c).getString("smtp_host", ""),
                                           TxConfig.get(c).getString("smtp_port", ""),
                                           TxConfig.get(c).getString("smtp_user", ""),
                                           TxConfig.get(c).getString("smtp_pass", ""),
                                           TxConfig.get(c).getBoolean("smtp_ssl", false));
        try {
            sender.send("TxMail", "dgondos@localhost", "TxMail: New SMS from " + sms_orig_address, "SMS body:\n" + sms_body);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}

class SMTPSender extends korex.mail.Authenticator {
    private String _user;
    private String _pass;
    private Properties _javamail_properties;

    private boolean isAuthUsed() {
        return !_user.isEmpty();
    }

    public SMTPSender(String host, String port, String user, String pass, boolean ssl) {
        _user = user;
        _pass = pass;

        _javamail_properties = new Properties();

        _javamail_properties.put("mail.smtp.host", host);
        _javamail_properties.put("mail.smtp.auth", isAuthUsed());
        _javamail_properties.put("mail.smtp.port", port);
        if (ssl) {
            _javamail_properties.put("mail.smtp.socketFactory.port", port);
            _javamail_properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            _javamail_properties.put("mail.smtp.socketFactory.fallback", "false");
        }
    }

    public void send(String from, String to, String subject, String body) throws MessagingException {
        Session javamail_session = Session.getInstance(_javamail_properties, this);
        MimeMessage mime_msg = new MimeMessage(javamail_session);

        mime_msg.setFrom(new InternetAddress(from));
        mime_msg.setSubject(subject);
        mime_msg.setSentDate(new Date());

        BodyPart bodyPart = new MimeBodyPart();
        bodyPart.setText(body);
        MimeMultipart content = new MimeMultipart();
        content.addBodyPart(bodyPart);
        mime_msg.setContent(content);

        InternetAddress[] to_addresses = { new InternetAddress(to) };
        mime_msg.setRecipients(MimeMessage.RecipientType.TO, to_addresses);

        Transport.send(mime_msg);
    }

    @Override
    public PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(_user, _pass);
    }
}