package org.uberfire.demo.notes;

import org.uberfire.demo.notes.model.Note;

public interface MailService {

    void sendMail( final Note note,
                   final String... destinations );

}
