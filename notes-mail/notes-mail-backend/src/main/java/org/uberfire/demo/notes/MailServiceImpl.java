package org.uberfire.demo.notes;

import java.util.Properties;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.uberfire.cloud.Cloud;
import org.uberfire.demo.notes.model.Note;

@ApplicationScoped
@Cloud("mail")
public class MailServiceImpl implements MailService {

    @Inject
    private Event<MailSent> mailSentEvent;

    @Override
    public void sendMail( final Note note,
                          final String... destinations ) {
        if ( destinations == null || destinations.length == 0 ) {
            return;
        }

        final Properties props = new Properties();
        props.put( "mail.smtp.host", "localhost" );
        props.put( "mail.smtp.port", "1025" );

        final Session session = Session.getInstance( props );

        for ( final String destination : destinations ) {
            try {

                Message message = new MimeMessage( session );
                message.setFrom( new InternetAddress( "info@notesapp.com" ) );
                message.setRecipients( Message.RecipientType.TO, InternetAddress.parse( destination ) );
                message.setSubject( "[Notes] " + note.getTitle() );
                message.setText( note.getContent() );

                Transport.send( message );
            } catch ( MessagingException e ) {
                throw new RuntimeException( e );
            }
        }

        mailSentEvent.fire( new MailSent( note.getId(), note.getOwner() ) );
        System.err.println( "Email Mail Sent!" );
    }
}
