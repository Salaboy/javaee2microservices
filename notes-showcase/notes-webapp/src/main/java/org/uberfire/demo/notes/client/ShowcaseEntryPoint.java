/*
 * Copyright 2012 JBoss Inc
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.uberfire.demo.notes.client;

import java.util.Set;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import com.google.gwt.animation.client.Animation;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import org.jboss.errai.ioc.client.api.AfterInitialization;
import org.jboss.errai.ioc.client.api.EntryPoint;
import org.jboss.errai.ioc.client.container.SyncBeanManager;
import org.jboss.errai.security.shared.api.Role;
import org.jboss.errai.security.shared.api.identity.User;
import org.uberfire.client.menu.CustomSplashHelp;
import org.uberfire.client.views.pfly.menu.MainBrand;
import org.uberfire.client.views.pfly.menu.UserMenu;
import org.uberfire.client.workbench.events.ApplicationReadyEvent;
import org.uberfire.client.workbench.widgets.menu.UtilityMenuBar;
import org.uberfire.client.workbench.widgets.menu.WorkbenchMenuBar;
import org.uberfire.demo.notes.client.event.AddNote;
import org.uberfire.demo.notes.client.resources.AppResource;
import org.uberfire.mvp.Command;
import org.uberfire.mvp.impl.DefaultPlaceRequest;
import org.uberfire.workbench.model.menu.Menus;

import static org.uberfire.workbench.model.menu.MenuFactory.*;

@EntryPoint
public class ShowcaseEntryPoint {

    @Inject
    private SyncBeanManager manager;

    @Inject
    private WorkbenchMenuBar menubar;

    @Inject
    private UserMenu userMenu;

    @Inject
    private User user;

    @Inject
    private UtilityMenuBar utilityMenu;

    @Inject
    private Event<AddNote> addNoteEvent;

    @AfterInitialization
    public void startApp() {
        hideLoadingPopup();
    }

    private void setupMenu( @Observes final ApplicationReadyEvent event ) {

        final Menus menus =
                newTopLevelMenu( "Home" )
                    .place( new DefaultPlaceRequest( "HomePerspective" ) )
                    .perspective( "HomePerspective" )
                .endMenu()
                .newTopLevelMenu( "Add Note" )
                    .respondsWith( new Command() {
                        @Override
                        public void execute() {
                            addNoteEvent.fire( new AddNote() );
                        }
                    } )
                .endMenu()
                .build();

        menubar.addMenus( menus );

        userMenu.addMenus(
                newTopLevelMenu( "My roles" ).respondsWith(
                        new Command() {
                            @Override
                            public void execute() {
                                final Set<Role> roles = user.getRoles();
                                if ( roles == null || roles.isEmpty() ) {
                                    Window.alert( "You have no roles assigned" );
                                } else {
                                    Window.alert( "Currently logged in using roles: " + roles );
                                }
                            }
                        } )
                        .endMenu()
                        .newTopLevelCustomMenu( manager.lookupBean( WorkbenchViewModeSwitcherMenuBuilder.class ).getInstance() )
                        .endMenu()
                        .build() );

        utilityMenu.addMenus(
                newTopLevelCustomMenu( userMenu ).endMenu()
                        .newTopLevelMenu( "Status" )
                        .respondsWith( new Command() {
                            @Override
                            public void execute() {
                                Window.alert( "Hello from status!" );
                            }
                        } )
                        .endMenu()
                        .newTopLevelCustomMenu( manager.lookupBean( CustomSplashHelp.class ).getInstance() )
                        .endMenu()
                        .build() );
    }

    //Fade out the "Loading application" pop-up
    private void hideLoadingPopup() {
        final Element e = RootPanel.get( "loading" ).getElement();

        new Animation() {

            @Override
            protected void onUpdate( double progress ) {
                e.getStyle().setOpacity( 1.0 - progress );
            }

            @Override
            protected void onComplete() {
                e.getStyle().setVisibility( Style.Visibility.HIDDEN );
            }
        }.run( 500 );
    }

    @Produces
    @ApplicationScoped
    public MainBrand createBrandLogo() {
        return new MainBrand() {
            @Override
            public Widget asWidget() {
                return new Image( AppResource.INSTANCE.images().ufBrandLogo() );
            }
        };
    }

}