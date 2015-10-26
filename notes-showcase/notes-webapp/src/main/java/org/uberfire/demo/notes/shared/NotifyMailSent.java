package org.uberfire.demo.notes.shared;

import org.jboss.errai.common.client.api.annotations.Portable;

@Portable
public class NotifyMailSent {

    private String owner;

    public NotifyMailSent() {

    }

    public NotifyMailSent( final String owner ) {
        this.owner = owner;
    }

    public String getOwner() {
        return owner;
    }
}
