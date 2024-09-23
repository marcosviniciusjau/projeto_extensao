package manager;

import java.io.Serializable;
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
import model.Adotante;
import model.Pets;
public class AdotanteJpaController implements Serializable {
    public AdotanteJpaController(EntityManagerFactory emf){
        this.emf = emf;
    }
    
    private EntityManagerFactory emf = null;
    
    public synchronized EntityManager getEntityManager(){
        return emf.createEntityManager();
    }
       public synchronized Adotante verify(String CPF) throws NonexistentEntityException, PreexistingEntityException {
        EntityManager em = null;
        Adotante pet_adopter = null; 
        try {
            em = getEntityManager();
            em.getTransaction().begin();
    
            try {
                pet_adopter = em.createQuery("SELECT a FROM Adotante a WHERE a.cpf = :cpf", Adotante.class)
                        .setParameter("cpf", CPF)
                        .getSingleResult();
                      throw new  PreexistingEntityException("Adotante ja existente");
            } catch (NoResultException nre) {
            }
    
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return pet_adopter;
    }
    public synchronized void create(Adotante pet_adopter) throws NonexistentEntityException, Exception{
          EntityManager em = null;
	        try {
                    em = getEntityManager();
                    em.getTransaction().begin();
                    em.persist(pet_adopter);
                    em.getTransaction().commit();
                    System.out.println("Adotante inserido com sucesso");
               } catch(Exception ex){
                   if(pet_adopter.getNome()!= null){
                       throw new PreexistingEntityException("Adotante" + pet_adopter + "já existente", ex);
                   }
                   throw ex;
               } finally {
                    if (em != null){
                        em.close();
                    }
                }
    }
    public void destroy(String cpf) throws NonexistentEntityException
		{
	        EntityManager em = null;
	        try {
		               em = getEntityManager();
		               em.getTransaction().begin();
		               Adotante adotante;
		               try {
                        adotante = (Adotante) em.createQuery("SELECT a FROM Adotante a WHERE a.cpf = :cpf", Adotante.class).setParameter("cpf", cpf)
                        .getSingleResult();
		                   } catch (EntityNotFoundException enfe) {
			                  throw new NonexistentEntityException("Nenhum adotante encontrado!", enfe);
		            }	
		               em.remove(adotante);
                   System.out.println("Adotante excluido com sucesso");
		               em.getTransaction().commit();
	        } finally {
		               if (em != null)
		               em.close();
	        }
  }
  public Adotante updateAll(String cpf, String nome, Date dataNascimento, String endereco, String cep, String telefone) throws NonexistentEntityException
  {
    EntityManager em = null;
    Adotante adotante;
    try {
               em = getEntityManager();
               em.getTransaction().begin();
               try {

                adotante = (Adotante) em.createQuery("SELECT a FROM Adotante a WHERE a.cpf = :cpf", Adotante.class).setParameter("cpf", cpf)
                .getSingleResult();
         
                adotante.getCpf();

                adotante.setNome(nome);
                adotante.setDataNascimento(dataNascimento);
                adotante.setEndereco(endereco);
                adotante.setCep(cep);
                adotante.setTelefone(telefone);
                } catch (EntityNotFoundException enfe) {
                           throw new NonexistentEntityException("Nenhum adotante encontrado!", enfe);
                }	
               em.getTransaction().commit();
               
               System.out.println("Adotante atualizado com sucesso");
    } finally {
               if (em != null)
               em.close();
    }
    return adotante;
  }

  public Adotante updateNome(String cpf, String nome) throws NonexistentEntityException
  {
    EntityManager em = null;
    Adotante adotante;
    try {
               em = getEntityManager();
               em.getTransaction().begin();
               try {

                adotante = (Adotante) em.createQuery("SELECT a FROM Adotante a WHERE a.cpf = :cpf", Adotante.class).setParameter("cpf", cpf)
                .getSingleResult();
         
                adotante.getCpf();

                adotante.setNome(nome);
                } catch (EntityNotFoundException enfe) {
                           throw new NonexistentEntityException("Nenhum adotante encontrado!", enfe);
                }	
               em.getTransaction().commit();
    } finally {
               if (em != null)
               em.close();
    }
    return adotante;
  }

  public Adotante updateCPF(String cpf, String cpfNovo) throws NonexistentEntityException
  {
    EntityManager em = null;
    Adotante adotante;
    try {
               em = getEntityManager();
               em.getTransaction().begin();
               adotante = em.find(Adotante.class, cpf);
               if (adotante == null) {
                   throw new NonexistentEntityException("Adotante nao encontrado com o CPF: " + cpf);
               }
       
               Adotante novoAdotante = new Adotante();
               novoAdotante.setCpf(cpfNovo);
               novoAdotante.setNome(adotante.getNome());
               novoAdotante.setCep(adotante.getCep());
               novoAdotante.setEndereco(adotante.getEndereco());
               novoAdotante.setTelefone(adotante.getTelefone());
               novoAdotante.setDataNascimento(adotante.getDataNascimento());
               
               em.persist(novoAdotante);
               em.flush();
               List<Pets> petsList = adotante.getPetsList();
               for (Pets pet : petsList) {
                  pet.setAdotanteCpf(novoAdotante.cpf);
                  em.merge(pet); 
               }
               em.flush();
               em.remove(adotante);
               em.getTransaction().commit();
               System.out.println("CPF atualizado com sucesso");
    } finally {
               if (em != null)
               em.close();
    }
    return adotante;
  }

  public Adotante updateDataNascimento(String cpf, Date dataNascimento) throws NonexistentEntityException
  {
    EntityManager em = null;
    Adotante adotante;
    try {
               em = getEntityManager();
               em.getTransaction().begin();
               try {

                adotante = (Adotante) em.createQuery("SELECT a FROM Adotante a WHERE a.cpf = :cpf", Adotante.class).setParameter("cpf", cpf)
                .getSingleResult();
         
                adotante.getCpf();

                adotante.setDataNascimento(dataNascimento);
                } catch (EntityNotFoundException enfe) {
                           throw new NonexistentEntityException("Nenhum adotante encontrado!", enfe);
                }	
               em.getTransaction().commit();
               
               System.out.println("Data de nascimento atualizado com sucesso");
    } finally {
               if (em != null)
               em.close();
    }
    return adotante;
  }

  public Adotante updateEndereco(String cpf, String endereco) throws NonexistentEntityException
  {
    EntityManager em = null;
    Adotante adotante;
    try {
               em = getEntityManager();
               em.getTransaction().begin();
               try {

                adotante = (Adotante) em.createQuery("SELECT a FROM Adotante a WHERE a.cpf = :cpf", Adotante.class).setParameter("cpf", cpf)
                .getSingleResult();
         
                adotante.getCpf();

                adotante.setEndereco(endereco);
                } catch (EntityNotFoundException enfe) {
                           throw new NonexistentEntityException("Nenhum adotante encontrado!", enfe);
                }	
               em.getTransaction().commit();
               
               System.out.println("Endereco atualizado com sucesso");
    } finally {
               if (em != null)
               em.close();
    }
    return adotante;
  }

  public Adotante updateCEP(String cpf, String CEP) throws NonexistentEntityException
  {
    EntityManager em = null;
    Adotante adotante;
    try {
               em = getEntityManager();
               em.getTransaction().begin();
               try {

                adotante = (Adotante) em.createQuery("SELECT a FROM Adotante a WHERE a.cpf = :cpf", Adotante.class).setParameter("cpf", cpf)
                .getSingleResult();
         
                adotante.getCpf();

                adotante.setCep(CEP);
                } catch (EntityNotFoundException enfe) {
                           throw new NonexistentEntityException("Nenhum adotante encontrado!", enfe);
                }	
               em.getTransaction().commit();
               
               System.out.println("CEP atualizado com sucesso");
    } finally {
               if (em != null)
               em.close();
    }
    return adotante;
  }

  public Adotante updateTelefone(String cpf, String telefone) throws NonexistentEntityException
  {
    EntityManager em = null;
    Adotante adotante;
    try {
               em = getEntityManager();
               em.getTransaction().begin();
               try {

                adotante = (Adotante) em.createQuery("SELECT a FROM Adotante a WHERE a.cpf = :cpf", Adotante.class).setParameter("cpf", cpf)
                .getSingleResult();
         
                adotante.getCpf();

                adotante.setTelefone(telefone);
                } catch (EntityNotFoundException enfe) {
                           throw new NonexistentEntityException("Nenhum adotante encontrado!", enfe);
                }	
               em.getTransaction().commit();
               
               System.out.println("Telefone atualizado com sucesso");
    } finally {
               if (em != null)
               em.close();
    }
    return adotante;
  }

  public synchronized Adotante selectById(Integer id) throws NonexistentEntityException
  {
    EntityManager em = null;
    Adotante pet_adopter;
    try {
               em = getEntityManager();
               em.getTransaction().begin();
               try {

                pet_adopter = (Adotante) em.createQuery("SELECT a FROM Adotante a WHERE a.id = :id", Adotante.class).setParameter("id", id)
                .getSingleResult();
         
                pet_adopter.getCpf();
                   } catch (NoResultException enfe) {
                           throw new NonexistentEntityException("Nenhum adotante encontrado!", enfe);
                   }	
               em.getTransaction().commit();
    } finally {
               if (em != null)
               em.close();
    }
    return pet_adopter;
  }

  public synchronized Adotante select(String cpf) throws NonexistentEntityException
  {
    EntityManager em = null;
    Adotante pet_adopter;
    try {
               em = getEntityManager();
               em.getTransaction().begin();
               try {
                pet_adopter = (Adotante) em.createQuery("SELECT a FROM Adotante a WHERE a.cpf = :cpf", Adotante.class).setParameter("cpf", cpf)
                .getSingleResult();
         
                pet_adopter.getCpf();
                   } catch (EntityNotFoundException enfe) {
                           throw new NonexistentEntityException("Nenhum adotante encontrado!", enfe);
                   }	
               em.getTransaction().commit();
    } finally {
               if (em != null)
               em.close();
    }
    return pet_adopter;
  }

      public synchronized List<Adotante> selectAll(){  
        List<Adotante> list = new ArrayList<>();
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            list = em.createQuery("SELECT a FROM Adotante a", Adotante.class).getResultList();
            
            if(list.isEmpty()){
            System.out.println("Ainda nao foi inserido nenhum adotante");
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
