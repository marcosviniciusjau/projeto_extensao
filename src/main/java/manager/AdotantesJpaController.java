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
import model.Adotantes;
import model.Pets;
public class AdotantesJpaController implements Serializable {
    public AdotantesJpaController(EntityManagerFactory emf){
        this.emf = emf;
    }
    
    private EntityManagerFactory emf = null;
    
    public synchronized EntityManager getEntityManager(){
        return emf.createEntityManager();
    }
       public synchronized Adotantes verify(String CPF) throws NonexistentEntityException, PreexistingEntityException {
        EntityManager em = null;
        Adotantes pet_adopter = null; 
        try {
            em = getEntityManager();
            em.getTransaction().begin();
    
            try {
                pet_adopter = em.createQuery("SELECT a FROM Adotantes a WHERE a.cpf = :cpf", Adotantes.class)
                        .setParameter("cpf", CPF)
                        .getSingleResult();
                      throw new  PreexistingEntityException("Adotantes ja existente");
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
    public synchronized void create(Adotantes pet_adopter) throws NonexistentEntityException, Exception{
          EntityManager em = null;
	        try {
                    em = getEntityManager();
                    em.getTransaction().begin();
                    em.persist(pet_adopter);
                    em.getTransaction().commit();
                    System.out.println("Adotantes inserido com sucesso");
               } catch(Exception ex){
                   if(pet_adopter.getNome()!= null){
                       throw new PreexistingEntityException("Adotantes" + pet_adopter + "já existente", ex);
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
		               Adotantes Adotantes;
		               try {
                        Adotantes = (Adotantes) em.createQuery("SELECT a FROM Adotantes a WHERE a.cpf = :cpf", Adotantes.class).setParameter("cpf", cpf)
                        .getSingleResult();
		                   } catch (EntityNotFoundException enfe) {
			                  throw new NonexistentEntityException("Nenhum Adotantes encontrado!", enfe);
		            }	
		               em.remove(Adotantes);
                   System.out.println("Adotantes excluido com sucesso");
		               em.getTransaction().commit();
	        } finally {
		               if (em != null)
		               em.close();
	        }
  }
  public Adotantes updateAll(String cpf, String nome, Date dataNascimento, String endereco, String cep, String telefone) throws NonexistentEntityException
  {
    EntityManager em = null;
    Adotantes Adotantes;
    try {
               em = getEntityManager();
               em.getTransaction().begin();
               try {

                Adotantes = (Adotantes) em.createQuery("SELECT a FROM Adotantes a WHERE a.cpf = :cpf", Adotantes.class).setParameter("cpf", cpf)
                .getSingleResult();
         
                Adotantes.getCpf();

                Adotantes.setNome(nome);
                Adotantes.setDataNascimento(dataNascimento);
                Adotantes.setEndereco(endereco);
                Adotantes.setCep(cep);
                Adotantes.setTelefone(telefone);
                } catch (EntityNotFoundException enfe) {
                           throw new NonexistentEntityException("Nenhum Adotantes encontrado!", enfe);
                }	
               em.getTransaction().commit();
               
               System.out.println("Adotantes atualizado com sucesso");
    } finally {
               if (em != null)
               em.close();
    }
    return Adotantes;
  }

  public Adotantes updateNome(String cpf, String nome) throws NonexistentEntityException
  {
    EntityManager em = null;
    Adotantes Adotantes;
    try {
               em = getEntityManager();
               em.getTransaction().begin();
               try {

                Adotantes = (Adotantes) em.createQuery("SELECT a FROM Adotantes a WHERE a.cpf = :cpf", Adotantes.class).setParameter("cpf", cpf)
                .getSingleResult();
         
                Adotantes.getCpf();

                Adotantes.setNome(nome);
                } catch (EntityNotFoundException enfe) {
                           throw new NonexistentEntityException("Nenhum Adotantes encontrado!", enfe);
                }	
               em.getTransaction().commit();
    } finally {
               if (em != null)
               em.close();
    }
    return Adotantes;
  }

  public Adotantes updateCPF(String cpf, String cpfNovo) throws NonexistentEntityException
  {
    EntityManager em = null;
    Adotantes Adotantes;
    try {
               em = getEntityManager();
               em.getTransaction().begin();
               Adotantes = em.find(Adotantes.class, cpf);
               if (Adotantes == null) {
                   throw new NonexistentEntityException("Adotantes nao encontrado com o CPF: " + cpf);
               }
       
               Adotantes novoAdotantes = new Adotantes();
               novoAdotantes.setCpf(cpfNovo);
               novoAdotantes.setNome(Adotantes.getNome());
               novoAdotantes.setCep(Adotantes.getCep());
               novoAdotantes.setEndereco(Adotantes.getEndereco());
               novoAdotantes.setTelefone(Adotantes.getTelefone());
               novoAdotantes.setDataNascimento(Adotantes.getDataNascimento());
               
               em.persist(novoAdotantes);
               em.flush();
               List<Pets> petsList = Adotantes.getPetsList();
               for (Pets pet : petsList) {
                  pet.setAdotanteCpf(novoAdotantes.cpf);
                  em.merge(pet); 
               }
               em.flush();
               em.remove(Adotantes);
               em.getTransaction().commit();
               System.out.println("CPF atualizado com sucesso");
    } finally {
               if (em != null)
               em.close();
    }
    return Adotantes;
  }

  public Adotantes updateDataNascimento(String cpf, Date dataNascimento) throws NonexistentEntityException
  {
    EntityManager em = null;
    Adotantes Adotantes;
    try {
               em = getEntityManager();
               em.getTransaction().begin();
               try {

                Adotantes = (Adotantes) em.createQuery("SELECT a FROM Adotantes a WHERE a.cpf = :cpf", Adotantes.class).setParameter("cpf", cpf)
                .getSingleResult();
         
                Adotantes.getCpf();

                Adotantes.setDataNascimento(dataNascimento);
                } catch (EntityNotFoundException enfe) {
                           throw new NonexistentEntityException("Nenhum Adotantes encontrado!", enfe);
                }	
               em.getTransaction().commit();
               
               System.out.println("Data de nascimento atualizado com sucesso");
    } finally {
               if (em != null)
               em.close();
    }
    return Adotantes;
  }

  public Adotantes updateEndereco(String cpf, String endereco) throws NonexistentEntityException
  {
    EntityManager em = null;
    Adotantes Adotantes;
    try {
               em = getEntityManager();
               em.getTransaction().begin();
               try {

                Adotantes = (Adotantes) em.createQuery("SELECT a FROM Adotantes a WHERE a.cpf = :cpf", Adotantes.class).setParameter("cpf", cpf)
                .getSingleResult();
         
                Adotantes.getCpf();

                Adotantes.setEndereco(endereco);
                } catch (EntityNotFoundException enfe) {
                           throw new NonexistentEntityException("Nenhum Adotantes encontrado!", enfe);
                }	
               em.getTransaction().commit();
               
               System.out.println("Endereco atualizado com sucesso");
    } finally {
               if (em != null)
               em.close();
    }
    return Adotantes;
  }

  public Adotantes updateCEP(String cpf, String CEP) throws NonexistentEntityException
  {
    EntityManager em = null;
    Adotantes Adotantes;
    try {
               em = getEntityManager();
               em.getTransaction().begin();
               try {

                Adotantes = (Adotantes) em.createQuery("SELECT a FROM Adotantes a WHERE a.cpf = :cpf", Adotantes.class).setParameter("cpf", cpf)
                .getSingleResult();
         
                Adotantes.getCpf();

                Adotantes.setCep(CEP);
                } catch (EntityNotFoundException enfe) {
                           throw new NonexistentEntityException("Nenhum Adotantes encontrado!", enfe);
                }	
               em.getTransaction().commit();
               
               System.out.println("CEP atualizado com sucesso");
    } finally {
               if (em != null)
               em.close();
    }
    return Adotantes;
  }

  public Adotantes updateTelefone(String cpf, String telefone) throws NonexistentEntityException
  {
    EntityManager em = null;
    Adotantes Adotantes;
    try {
               em = getEntityManager();
               em.getTransaction().begin();
               try {

                Adotantes = (Adotantes) em.createQuery("SELECT a FROM Adotantes a WHERE a.cpf = :cpf", Adotantes.class).setParameter("cpf", cpf)
                .getSingleResult();
         
                Adotantes.getCpf();

                Adotantes.setTelefone(telefone);
                } catch (EntityNotFoundException enfe) {
                           throw new NonexistentEntityException("Nenhum Adotantes encontrado!", enfe);
                }	
               em.getTransaction().commit();
               
               System.out.println("Telefone atualizado com sucesso");
    } finally {
               if (em != null)
               em.close();
    }
    return Adotantes;
  }

  public synchronized Adotantes selectById(Integer id) throws NonexistentEntityException
  {
    EntityManager em = null;
    Adotantes pet_adopter;
    try {
               em = getEntityManager();
               em.getTransaction().begin();
               try {

                pet_adopter = (Adotantes) em.createQuery("SELECT a FROM Adotantes a WHERE a.id = :id", Adotantes.class).setParameter("id", id)
                .getSingleResult();
         
                pet_adopter.getCpf();
                   } catch (NoResultException enfe) {
                           throw new NonexistentEntityException("Nenhum Adotantes encontrado!", enfe);
                   }	
               em.getTransaction().commit();
    } finally {
               if (em != null)
               em.close();
    }
    return pet_adopter;
  }

  public synchronized Adotantes select(String cpf) throws NonexistentEntityException
  {
    EntityManager em = null;
    Adotantes pet_adopter;
    try {
               em = getEntityManager();
               em.getTransaction().begin();
               try {
                pet_adopter = (Adotantes) em.createQuery("SELECT a FROM Adotantes a WHERE a.cpf = :cpf", Adotantes.class).setParameter("cpf", cpf)
                .getSingleResult();
         
                pet_adopter.getCpf();
                   } catch (EntityNotFoundException enfe) {
                           throw new NonexistentEntityException("Nenhum Adotantes encontrado!", enfe);
                   }	
               em.getTransaction().commit();
    } finally {
               if (em != null)
               em.close();
    }
    return pet_adopter;
  }

      public synchronized List<Adotantes> selectAll(){  
        List<Adotantes> list = new ArrayList<>();
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            list = em.createQuery("SELECT a FROM Adotantes a", Adotantes.class).getResultList();
            
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
