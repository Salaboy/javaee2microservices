package org.uberfire.demo.notes;

import java.util.Collection;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.uberfire.cloud.Cloud;
import org.uberfire.commons.services.cdi.Startup;
import org.uberfire.commons.services.cdi.StartupType;
import org.uberfire.demo.notes.model.Note;

@ApplicationScoped
@Startup(StartupType.BOOTSTRAP)
@Cloud("rest")
public class NotesRestAPI {

    private MailService mailService;

    private PersistenceService persistenceService;

    public NotesRestAPI() {

    }

    @Inject
    public NotesRestAPI( final MailService mailService,
                         final PersistenceService persistenceService ) {
        this.mailService = mailService;
        this.persistenceService = persistenceService;
    }

    public Collection<Note> getNotes( final String userId ) {
        return persistenceService.getNotes( userId );
    }

    public Note getNote( final String id ) {
        return persistenceService.getNote( id );
    }

    public void sendNote( final String noteId,
                          final String email ) {
        mailService.sendMail( persistenceService.getNote( noteId ),
                              email );
    }
}
