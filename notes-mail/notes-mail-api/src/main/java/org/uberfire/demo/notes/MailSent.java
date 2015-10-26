package org.uberfire.demo.notes;

import org.uberfire.cloud.Cloud;

@Cloud
public class MailSent {

    private final String owner;
    private final String noteId;

    public MailSent( final String noteId,
                     final String owner ) {
        this.noteId = noteId;
        this.owner = owner;
    }

    public String getOwner() {
        return owner;
    }

    public String getNoteId() {
        return noteId;
    }
}
