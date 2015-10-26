package org.uberfire.demo.notes.backend;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.uberfire.rpc.SessionInfo;

@RequestScoped
public class UserPreferences {

    @Inject
    private SessionInfo user;

    public String getUserId() {
        return user.getIdentity().getIdentifier();
    }
}
