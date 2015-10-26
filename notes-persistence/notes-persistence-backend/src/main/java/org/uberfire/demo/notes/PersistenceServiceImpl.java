package org.uberfire.demo.notes;

import java.util.Collection;
import java.util.UUID;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.TypedQuery;

import org.uberfire.cloud.Cloud;
import org.uberfire.demo.notes.model.Note;

@ApplicationScoped
@Cloud("persistence")
public class PersistenceServiceImpl implements PersistenceService {

    @PersistenceUnit(unitName = "org.uberfire.demo.notes")
    private EntityManagerFactory emf;
    private EntityManager em;

    @PostConstruct
    public void init() {
        this.em = emf.createEntityManager();
    }

    @Override
    public Collection<Note> getNotes( final String owner ) {
        final TypedQuery<Note> query = em.createQuery( "SELECT n FROM Note n WHERE n.owner=:arg1", Note.class );
        query.setParameter( "arg1", owner );

        System.err.println( "List Notes!" );

        return query.getResultList();
    }

    @Override
    public void saveNote( final Note note ) {
        if ( note.getId() == null ) {
            note.setId( UUID.randomUUID().toString() );
        }
        em.getTransaction().begin();
        em.persist( note );
        em.flush();
        em.getTransaction().commit();
        System.err.println( "Note Saved" );
    }

    @Override
    public void deleteNote( final String noteId ) {
        em.getTransaction().begin();
        em.remove( getNote( noteId ) );
        em.flush();
        em.getTransaction().commit();

        System.err.println( "Delete Note!" );
    }

    @Override
    public Note getNote( final String noteId ) {
        return em.find( Note.class, noteId );
    }
}
