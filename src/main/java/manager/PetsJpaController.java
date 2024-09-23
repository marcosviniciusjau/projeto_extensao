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

    public synchronized void create(Pets pet) throws NonexistentEntityException, Exception{
          EntityManager em = null;
	        try {
                    em = getEntityManager();
                    em.getTransaction().begin();
                    em.persist(pet);
                    em.getTransaction().commit();
                    System.out.println("Pet inserido com sucesso");
               } catch(Exception ex){
                   if(pet.getCodigoMicrochip()!= null){
                       throw new PreexistingEntityException("Pet" + pet + "ja existente", ex);
                   }
                   throw ex;
               } finally {
                    if (em != null){
                        em.close();
                    }
                }
    }
    
    public synchronized void destroy(int codigoMicrochip) throws NonexistentEntityException
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
                       System.out.println("Pet removido com sucesso");
		               em.getTransaction().commit();
	        } finally {
		               if (em != null)
		               em.close();
	        }
   }
    
    public synchronized Pets select(Integer codigoMicrochip) throws NonexistentEntityException, PreexistingEntityException
    {
        EntityManager em = null;
        Pets pet;
        try {
                em = getEntityManager();
                em.getTransaction().begin();
                try {
                            pet = (Pets) em.createQuery("SELECT p FROM Pets p WHERE p.codigoMicrochip = :codigoMicrochip", Pets.class).setParameter("codigoMicrochip", codigoMicrochip)
                            .getSingleResult();
            
                            pet.getCodigoMicrochip();
                    } catch (NoResultException enfe) {
                            throw new NonexistentEntityException("Nenhum pet encontrado!");
                    }	
                em.getTransaction().commit();
        } finally {
                if (em != null)
                em.close();
        }
        return pet;
    }

    public synchronized Pets updateAll(Integer codigoMicrochip, Date dataCastracao, Date dataAdocao, BigDecimal peso) throws NonexistentEntityException, PreexistingEntityException
    {
        EntityManager em = null;
        Pets pet;
        try {
                em = getEntityManager();
                em.getTransaction().begin();
                try {
                    pet = (Pets) em.createQuery("SELECT p FROM Pets p WHERE p.codigoMicrochip = :codigoMicrochip", Pets.class).setParameter("codigoMicrochip", codigoMicrochip)
                    .getSingleResult();
            
                    pet.getCodigoMicrochip();

                    pet.setDataCastracao(dataCastracao);
                    if(pet.getAdotanteCpf() == null){
                        throw new PreexistingEntityException("Pet nao adotado");
                    }
                    pet.setDataAdocao(dataAdocao);
                    pet.setPeso(peso);
                    } catch (EntityNotFoundException enfe) {
                            throw new NonexistentEntityException("Nenhum pet encontrado!", enfe);
                    }	
                em.getTransaction().commit();
                System.out.println("Pet atualizado com sucesso");
        } finally {
                if (em != null)
                em.close();
        }
        return pet;
    }

    public synchronized Pets updateDataAdocao(Integer codigoMicrochip, Date dataAdocao) throws NonexistentEntityException, PreexistingEntityException {
        EntityManager em = null;
        Pets pet;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            
            try {
                pet = em.createQuery("SELECT p FROM Pets p WHERE p.codigoMicrochip = :codigoMicrochip", Pets.class)
                        .setParameter("codigoMicrochip", codigoMicrochip)
                        .getSingleResult();
            } catch (NoResultException nre) {
                throw new NonexistentEntityException("Pet nao encontrado");
            }
            pet.getCodigoMicrochip();
            if(pet.getAdotanteCpf() == null){
                throw new PreexistingEntityException("Pet nao adotado");
            }
            pet.setDataAdocao(dataAdocao);

            em.getTransaction().commit(); 

            System.out.println("Data de adocao atualizada com sucesso");
        } catch (EntityNotFoundException enfe) {
            throw new NonexistentEntityException("Nenhum pet encontrado!", enfe);
        } finally {
            if (em != null) {
                em.close();
            }
        }

        return pet;
    }

    public synchronized Pets updateDataVacinas(Integer codigoMicrochip, Date dataVacinas) throws NonexistentEntityException, PreexistingEntityException {
        EntityManager em = null;
        Pets pet;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            
            try {
                pet = em.createQuery("SELECT p FROM Pets p WHERE p.codigoMicrochip = :codigoMicrochip", Pets.class)
                        .setParameter("codigoMicrochip", codigoMicrochip)
                        .getSingleResult();
            } catch (NoResultException nre) {
                throw new NonexistentEntityException("Pet nao encontrado");
            }
            pet.getCodigoMicrochip();
            if(pet.getAdotanteCpf() == null){
                throw new PreexistingEntityException("Pet nao adotado");
            }
            pet.setDataVacinas(dataVacinas);

            em.getTransaction().commit(); 

            System.out.println("Data de adocao atualizada com sucesso");
        } catch (EntityNotFoundException enfe) {
            throw new NonexistentEntityException("Nenhum pet encontrado!", enfe);
        } finally {
            if (em != null) {
                em.close();
            }
        }

        return pet;
    }

    public synchronized Pets updateDataCastracao(Integer codigoMicrochip, Date dataCastracao) throws NonexistentEntityException {
        EntityManager em = null;
        Pets pet;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            
            try {
                pet = em.createQuery("SELECT p FROM Pets p WHERE p.codigoMicrochip = :codigoMicrochip", Pets.class)
                        .setParameter("codigoMicrochip", codigoMicrochip)
                        .getSingleResult();
            } catch (NoResultException nre) {
                throw new NonexistentEntityException("Pet nao encontrado");
            }
            pet.getCodigoMicrochip();
            pet.setDataCastracao(dataCastracao);

            em.getTransaction().commit(); 
            
            System.out.println("Data de castraçao atualizada com sucesso");

        } catch (EntityNotFoundException enfe) {
            throw new NonexistentEntityException("Nenhum pet encontrado!", enfe);
        } finally {
            if (em != null) {
                em.close();
            }
        }

        return pet;
    }

    public synchronized Pets updateDatas(Integer codigoMicrochip, Date dataCastracao, Date dataAdocao, Date dataVacinas) throws NonexistentEntityException, PreexistingEntityException {
        EntityManager em = null;
        Pets pet;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            
            try {
                pet = em.createQuery("SELECT p FROM Pets p WHERE p.codigoMicrochip = :codigoMicrochip", Pets.class)
                        .setParameter("codigoMicrochip", codigoMicrochip)
                        .getSingleResult();
            } catch (NoResultException nre) {
                throw new NonexistentEntityException("Pet nao encontrado");
            }
            pet.getCodigoMicrochip();
            pet.setDataCastracao(dataCastracao);
            if(pet.getAdotanteCpf() == null){
                throw new PreexistingEntityException("Pet nao adotado");
            }
            pet.setDataAdocao(dataAdocao);
            pet.setDataVacinas(dataVacinas);

            em.getTransaction().commit(); 
            
            System.out.println("Data de adocao castraçao e vacinas atualizada com sucesso");

        } catch (EntityNotFoundException enfe) {
            throw new NonexistentEntityException("Nenhum pet encontrado!", enfe);
        } finally {
            if (em != null) {
                em.close();
            }
        }

        return pet;
    }

    public synchronized Pets updatePeso(Integer codigoMicrochip, BigDecimal peso) throws NonexistentEntityException {
        EntityManager em = null;
        Pets pet;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            
            try {
                pet = em.createQuery("SELECT p FROM Pets p WHERE p.codigoMicrochip = :codigoMicrochip", Pets.class)
                        .setParameter("codigoMicrochip", codigoMicrochip)
                        .getSingleResult();
            } catch (NoResultException nre) {
                throw new NonexistentEntityException("Pet nao encontrado");
            }
            pet.getCodigoMicrochip();
            pet.setPeso(peso);

            em.getTransaction().commit(); 
            
            System.out.println("Peso atualizado com sucesso");

        } catch (EntityNotFoundException enfe) {
            throw new NonexistentEntityException("Nenhum pet encontrado!", enfe);
        } finally {
            if (em != null) {
                em.close();
            }
        }

        return pet;
    }

    public synchronized Pets updateCodigo(Integer codigoMicrochipVelho, Integer codigoMicrochipNovo) throws NonexistentEntityException {
        EntityManager em = null;
        Pets pet;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            pet = em.find(Pets.class, codigoMicrochipVelho);
            if (pet == null) {
                throw new NonexistentEntityException("Pet nao encontrado com o Codigo: " + codigoMicrochipVelho);
            }
    
            Pets novoPet = new Pets();
            novoPet.setCodigoMicrochip(codigoMicrochipNovo);
            novoPet.setDataAdocao(pet.getDataAdocao());
            novoPet.setDataCastracao(pet.getDataCastracao());
            novoPet.setPeso(pet.getPeso());
            novoPet.setAdotanteCpf(pet.getAdotanteCpfDiferente());
            em.persist(novoPet);
            em.remove(pet);
            em.getTransaction().commit();
            
            System.out.println("Codigo atualizado com sucesso");
    
        } catch (Exception e) {
            System.err.println("Erro ao atualizar código: " + e.getMessage());
            throw new NonexistentEntityException("Erro ao atualizar codigo", e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    
        return pet;
    }
    
    public synchronized List<Pets> selectAll(){  
        List<Pets> list = new ArrayList<>();
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            list = em.createQuery("SELECT p FROM Pets p", Pets.class).getResultList();
            
            if(list.isEmpty()){
            System.out.println("Ainda nao foi inserido nenhum pet");
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
  
    public synchronized Pets verificar(Integer codigoMicrochip) throws NonexistentEntityException {
        EntityManager em = null;
        Pets pet = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
    
            try {
                pet = em.createQuery("SELECT p FROM Pets p WHERE p.codigoMicrochip = :codigoMicrochip", Pets.class)
                        .setParameter("codigoMicrochip", codigoMicrochip)
                        .getSingleResult();
            } catch (NoResultException nre) {
            }
    
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return pet;
    }
    
    public synchronized Pets adotar(Integer codigoMicrochip, Date dataAdocao, String cpf) throws NonexistentEntityException {
        EntityManager em = null;
        Pets pet;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            
            try {
                pet = em.createQuery("SELECT p FROM Pets p WHERE p.codigoMicrochip = :codigoMicrochip", Pets.class)
                        .setParameter("codigoMicrochip", codigoMicrochip)
                        .getSingleResult();
            } catch (NoResultException nre) {
                throw new NonexistentEntityException("Pet nao encontrado");
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
