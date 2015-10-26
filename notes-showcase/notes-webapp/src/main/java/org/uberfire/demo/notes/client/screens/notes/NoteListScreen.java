/*
 * Copyright 2012 JBoss Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.uberfire.demo.notes.client.screens.notes;

import java.util.Collection;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
import org.jboss.errai.common.client.api.Caller;
import org.jboss.errai.common.client.api.RemoteCallback;
import org.jboss.errai.ioc.client.api.AfterInitialization;
import org.jboss.errai.ioc.client.container.SyncBeanManager;
import org.jboss.errai.security.shared.api.identity.User;
import org.uberfire.client.annotations.WorkbenchPartTitle;
import org.uberfire.client.annotations.WorkbenchScreen;
import org.uberfire.demo.notes.client.event.AddNote;
import org.uberfire.demo.notes.client.screens.note.NoteScreen;
import org.uberfire.demo.notes.model.Note;
import org.uberfire.demo.notes.shared.NotesUIServices;
import org.uberfire.demo.notes.shared.NotifyMailSent;
import org.uberfire.workbench.events.NotificationEvent;

@ApplicationScoped
@WorkbenchScreen(identifier = "NotesScreen")
public class NoteListScreen extends Composite {

    interface ViewBinder
            extends
            UiBinder<Widget, NoteListScreen> {

    }

    private static ViewBinder uiBinder = GWT.create( ViewBinder.class );

    @Inject
    private SyncBeanManager beanManager;

    @Inject
    private User user;

    @UiField
    FlowPanel content;

    @Inject
    private Caller<NotesUIServices> service;

    @Inject
    private Event<NotificationEvent> notificationEvent;

    @WorkbenchPartTitle
    public String getName() {
        return "NotesScreen";
    }

    @PostConstruct
    public void init() {
        initWidget( uiBinder.createAndBindUi( this ) );
    }

    @AfterInitialization
    public void load() {
        service.call( new RemoteCallback<Collection<Note>>() {
            @Override
            public void callback( final Collection<Note> notes ) {
                for ( Note note : notes ) {
                    newNote( note );
                }
            }
        } ).listNotes();
    }

    public void addNewNoteListen( @Observes final AddNote addNote ) {
        final Note note = new Note();
        note.setOwner( user.getIdentifier() );
        newNote( note );
    }

    public void newNote( final Note note ) {
        final NoteScreen newNote = beanManager.lookupBean( NoteScreen.class ).newInstance();
        newNote.setNote( note );
        newNote.setParent( this );
        content.add( newNote );
    }

    public void remove( final NoteScreen noteScreen ) {
        content.remove( noteScreen );
        beanManager.destroyBean( noteScreen );
    }

    void onMailSent( @Observes final NotifyMailSent mailSent ) {
        if ( mailSent.getOwner().equals( user.getIdentifier() ) ) {
            notificationEvent.fire( new NotificationEvent( "Mail sent!", NotificationEvent.NotificationType.SUCCESS ) );
        }
    }

}
