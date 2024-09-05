package manager;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceException;

import manager.exceptions.NonexistentEntityException;
import manager.exceptions.PreexistingEntityException;
import model.Pets;
public class PetsJpaController implements Serializable {
    public PetsJpaController(EntityManagerFactory emf){
        this.emf = emf;
    }
    
    private EntityManagerFactory emf = null;
    
    public EntityManager getEntityManager(){
        return emf.createEntityManager();
    }
    public void create(Pets pet) throws NonexistentEntityException, Exception{
          EntityManager em = null;
	        try {
                    em = getEntityManager();
                    em.getTransaction().begin();
                    em.persist(pet);
                    em.getTransaction().commit();
                    System.out.println("Pet inserido com sucesso");
               } catch(Exception ex){
                   if(pet.getName()!= null){
                       throw new PreexistingEntityException("Pet" + pet + "already exits", ex);
                   }
                   throw ex;
               } finally {
                    if (em != null){
                        em.close();
                    }
                }
    }
    public void destroy(int id) throws NonexistentEntityException
		{
	        EntityManager em = null;
	        try {
		               em = getEntityManager();
		               em.getTransaction().begin();
		               Pets pet;
		               try {
                             pet = em.getReference(Pets.class, id);
			                 pet.getName();
		                   } catch (EntityNotFoundException enfe) {
			                  throw new NonexistentEntityException("Nenhum pet encontrado!", enfe);
		            }	
		               em.remove(pet);
		               em.getTransaction().commit();
	        } finally {
		               if (em != null)
		               em.close();
	        }
  }
  public Pets select(String name) throws NonexistentEntityException
  {
    EntityManager em = null;
    Pets pet;
    try {
               em = getEntityManager();
               em.getTransaction().begin();
               try {

                         pet = (Pets) em.createQuery("SELECT p FROM Pets p WHERE p.name = :name", Pets.class).setParameter("name", name)
                         .getSingleResult();
         
                         pet.getName();
                   } catch (EntityNotFoundException enfe) {
                           throw new NonexistentEntityException("Nenhum pet encontrado!", enfe);
                   }	
               em.getTransaction().commit();
    } finally {
               if (em != null)
               em.close();
    }
    return pet;
  }

  public Pets castrate(String name, Date castrateDate) throws NonexistentEntityException
  {
    EntityManager em = null;
    Pets pet;
    try {
               em = getEntityManager();
               em.getTransaction().begin();
               try {

                pet = (Pets) em.createQuery("SELECT p FROM Pets p WHERE p.name = :name", Pets.class).setParameter("name", name)
                .getSingleResult();
         
                pet.getName();

                pet.setCastrateDate(castrateDate);
                   } catch (EntityNotFoundException enfe) {
                           throw new NonexistentEntityException("Nenhum pet encontrado!", enfe);
                   }	
               em.getTransaction().commit();
    } finally {
               if (em != null)
               em.close();
    }
    return pet;
  }
    public List<Pets> selectAll(){  
        List<Pets> list = new ArrayList<>();
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            list = em.createQuery("SELECT p FROM Pets p", Pets.class).getResultList();
            
            if(list.isEmpty()){
            System.out.println("Ainda não foi inserido nenhum pet");
        }
        }catch(PersistenceException ex){
            System.out.println("Erro: "+ex);
        }
        finally {
            if (em != null) {
                em.close();
            }
        }
        return list;
    
      }
}
