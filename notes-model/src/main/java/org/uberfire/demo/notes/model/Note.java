package org.uberfire.demo.notes.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.jboss.errai.common.client.api.annotations.Portable;

@Entity
@Portable
public class Note implements Serializable {

    @Id
    private String id;
    private String owner;
    private String title;
    private String content;

    public String getId() {
        return id;
    }

    public String getOwner() {
        return owner;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public void setId( final String id ) {
        this.id = id;
    }

    public void setOwner( final String owner ) {
        this.owner = owner;
    }

    public void setTitle( final String title ) {
        this.title = title;
    }

    public void setContent( final String content ) {
        this.content = content;
    }
}
