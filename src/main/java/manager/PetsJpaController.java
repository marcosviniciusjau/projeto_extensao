package manager;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
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
                   if(pet.getCodigoMicrochip()!= null){
                       throw new PreexistingEntityException("Pet" + pet + "já existente", ex);
                   }
                   throw ex;
               } finally {
                    if (em != null){
                        em.close();
                    }
                }
    }
    public void destroy(int codigoMicrochip) throws NonexistentEntityException
		{
	        EntityManager em = null;
	        try {
		               em = getEntityManager();
		               em.getTransaction().begin();
		               Pets pet;
		               try {
                        pet = (Pets) em.createQuery("SELECT p FROM Pets p WHERE p.codigoMicrochip = :codigoMicrochip", Pets.class).setParameter("codigoMicrochip", codigoMicrochip)
                        .getSingleResult();
		                   } catch (EntityNotFoundException enfe) {
			                  throw new NonexistentEntityException("Nenhum pet encontrado!", enfe);
		            }	
		               em.remove(pet);
                       System.out.println("Pet excluído com sucesso");
		               em.getTransaction().commit();
	        } finally {
		               if (em != null)
		               em.close();
	        }
  }
  public Pets select(Integer codigo_microchip) throws NonexistentEntityException, PreexistingEntityException
  {
    EntityManager em = null;
    Pets pet;
    try {
               em = getEntityManager();
               em.getTransaction().begin();
               try {

                         pet = (Pets) em.createQuery("SELECT p FROM Pets p WHERE p.codigoMicrochip = :codigoMicrochip", Pets.class).setParameter("codigoMicrochip", codigo_microchip)
                         .getSingleResult();
         
                         pet.getCodigoMicrochip();
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

  public Pets update(Integer codigo_microchip, Date dataCastracao, Date dataAdocao, BigDecimal peso) throws NonexistentEntityException
  {
    EntityManager em = null;
    Pets pet;
    try {
               em = getEntityManager();
               em.getTransaction().begin();
               try {

                pet = (Pets) em.createQuery("SELECT p FROM Pets p WHERE p.codigoMicrochip = :codigoMicrochip", Pets.class).setParameter("codigoMicrochip", codigo_microchip)
                .getSingleResult();
         
                pet.getCodigoMicrochip();

                pet.setDataCastracao(dataCastracao);
                pet.setDataAdocao(dataAdocao);
                pet.setPeso(peso);
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
      public synchronized Pets verify(Integer codigo_microchip) throws NonexistentEntityException {
        EntityManager em = null;
        Pets pet = null; // Inicializa o objeto como null
        try {
            em = getEntityManager();
            em.getTransaction().begin();
    
            try {
                // Tenta obter o resultado
                pet = em.createQuery("SELECT p FROM Pets p WHERE p.codigoMicrochip = :codigoMicrochip", Pets.class)
                        .setParameter("codigoMicrochip", codigo_microchip)
                        .getSingleResult();
            } catch (NoResultException nre) {
                // Se não houver resultado, continua normalmente
                System.out.println("Nenhum pet encontrado com o nome: " + codigo_microchip);
            }
    
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return pet; // Retorna null se nenhum pet for encontrado
    }
    
      public Pets adopt(Integer codigo_microchip, Date dataAdocao, String cpf) throws NonexistentEntityException {
        EntityManager em = null;
        Pets pet;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            
            try {
                pet = em.createQuery("SELECT p FROM Pets p WHERE p.codigoMicrochip = :codigoMicrochip", Pets.class)
                        .setParameter("codigoMicrochip", codigo_microchip)
                        .getSingleResult();
            } catch (NoResultException nre) {
                throw new NonexistentEntityException("Pet não encontrado");
            }
            pet.setDataAdocao(dataAdocao);
            pet.setAdotanteCpf(cpf);
    
            em.getTransaction().commit(); 
    
        } catch (EntityNotFoundException enfe) {
            throw new NonexistentEntityException("Nenhum pet encontrado!", enfe);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    
        return pet;
    }
    
}
