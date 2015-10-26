package org.uberfire.demo.notes;

import java.util.Collection;

import org.uberfire.demo.notes.model.Note;

public interface PersistenceService {

    Collection<Note> getNotes( final String owner );

    void saveNote( final Note note );

    void deleteNote( final String noteId );

    Note getNote( final String noteId );

}
