package org.uberfire.demo.notes.backend;

import java.util.Collection;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.jboss.errai.bus.server.annotations.Service;
import org.uberfire.demo.notes.MailSent;
import org.uberfire.demo.notes.MailService;
import org.uberfire.demo.notes.PersistenceService;
import org.uberfire.demo.notes.model.Note;
import org.uberfire.demo.notes.shared.NotesUIServices;
import org.uberfire.demo.notes.shared.NotifyMailSent;

@ApplicationScoped
@Service
public class NotesUIServicesImpl implements NotesUIServices {

    @Inject
    private PersistenceService persistenceService;

    @Inject
    private MailService mailService;

    @Inject
    @RequestScoped
    UserPreferences userPreferences;

    @Inject
    private Event<NotifyMailSent> notifyMailSent;

    @Override
    public Collection<Note> listNotes() {
        return persistenceService.getNotes( userPreferences.getUserId() );
    }

    @Override
    public Note saveNote( final Note note ) {
        persistenceService.saveNote( note );
        return note;
    }

    @Override
    public void deleteNote( final Note note ) {
        persistenceService.deleteNote( note.getId() );
    }

    @Override
    public void mailNote( final Note note,
                          final String email ) {
        mailService.sendMail( note, email );
    }

    void onMailSent( @Observes final MailSent mailSent ) {
        notifyMailSent.fire( new NotifyMailSent( mailSent.getOwner() ) );
    }
}
