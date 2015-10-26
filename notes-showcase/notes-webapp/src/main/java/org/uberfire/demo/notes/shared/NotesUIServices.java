package org.uberfire.demo.notes.shared;

import java.util.Collection;

import org.jboss.errai.bus.server.annotations.Remote;
import org.uberfire.demo.notes.model.Note;

@Remote
public interface NotesUIServices {

    Collection<Note> listNotes();

    Note saveNote( final Note note );

    void deleteNote( final Note note );

    void mailNote( final Note note,
                   final String email );

}
