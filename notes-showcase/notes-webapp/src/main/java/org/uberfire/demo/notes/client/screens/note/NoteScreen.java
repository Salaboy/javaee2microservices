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

package org.uberfire.demo.notes.client.screens.note;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import org.gwtbootstrap3.client.ui.TextArea;
import org.gwtbootstrap3.client.ui.TextBox;
import org.jboss.errai.common.client.api.Caller;
import org.jboss.errai.common.client.api.RemoteCallback;
import org.uberfire.demo.notes.client.screens.notes.NoteListScreen;
import org.uberfire.demo.notes.model.Note;
import org.uberfire.demo.notes.shared.NotesUIServices;

@Dependent
public class NoteScreen extends Composite {

    private NoteListScreen parent;

    interface ViewBinder
            extends
            UiBinder<Widget, NoteScreen> {

    }

    private static ViewBinder uiBinder = GWT.create( ViewBinder.class );

    @Inject
    private Caller<NotesUIServices> service;

    private Note note;

    @UiField
    TextBox title;

    @UiField
    TextBox email;

    @UiField
    TextArea tcontent;

    public NoteScreen() {
        initWidget( uiBinder.createAndBindUi( this ) );
    }

    public void setParent( final NoteListScreen parent ) {
        this.parent = parent;
    }

    public void newNote() {

    }

    @UiHandler("save")
    public void clickSave( final ClickEvent event ) {
        note.setTitle( title.getText() );
        note.setContent( tcontent.getText() );
        service.call( new RemoteCallback<Note>() {
            @Override
            public void callback( final Note note ) {
                NoteScreen.this.note = note;
            }
        } ).saveNote( note );
    }

    @UiHandler("delete")
    public void clickDelete( final ClickEvent event ) {
        if ( note != null && note.getId() != null ) {
            service.call( new RemoteCallback<Void>() {
                @Override
                public void callback( final Void aVoid ) {
                    parent.remove( NoteScreen.this );
                }
            } ).deleteNote( note );
        } else {
            parent.remove( NoteScreen.this );
        }
    }

    @UiHandler("send")
    public void clickSend( final ClickEvent event ) {
        if ( note != null ) {
            note.setTitle( title.getText() );
            note.setContent( tcontent.getText() );
            service.call().mailNote( note, email.getText() );
        }
    }

    public void setNote( final Note note ) {
        this.note = note;
        title.setText( note.getTitle() );
        tcontent.setText( note.getContent() );
    }

    public Note getNote() {
        return note;
    }
}
