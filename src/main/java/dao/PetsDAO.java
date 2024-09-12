package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import manager.exceptions.NonexistentEntityException;
import model.Pets;

 public class PetsDAO extends GenericDAOPets<Pets, String>{
        @Override
          public synchronized void insertPet(Pets pet) {
						EntityManagerFactory emf = Persistence.createEntityManagerFactory("ExemploJavaDB01PU");
						EntityManager em = emf.createEntityManager();
	        	em.getTransaction().begin();
						try {
									em.persist(pet);
								em.getTransaction().commit();
						} catch (Exception e) {
								em.getTransaction().rollback();
						} finally {
							em.close();
						}
      }
       @Override
         public synchronized void deletePet(String key) {
					    Connection c1 = null;
	         try {
		         	c1 = getConnection();
							c1.setAutoCommit(false);
							PreparedStatement ps = getConnection().prepareStatement(
												"DELETE FROM PETS WHERE CODIGO_MICROCHIP = ?");
							ps.setString(1, key);
							ps.executeUpdate();
							c1.commit();
							closeStatement(ps);
	         } catch (Exception e) {
							if(c1!=null)
			         try {
				          c1.rollback();
				          c1.close();
			         } catch (SQLException e2){}
	         }
  }      
	      @Override
	      public synchronized List<Pets> selectAllPets() {
		      List<Pets> list = new ArrayList<>();
		      try {
					ResultSet r1 = getStatement().executeQuery("SELECT * FROM PETS");
					while (r1.next())
					list.add(new Pets());
					}catch(Exception e){ 
	        }     
					return list;      
	    }
	         @Override
	         public synchronized Pets selectPet(String key) {
		                Pets pet = null;
		                try {
		                     	  PreparedStatement ps = 
				                 getConnection().prepareStatement(
				                 "SELECT * FROM PETS WHERE NOME = ?");
			                      ps.setString(1, key);
			                      ResultSet r1 = ps.executeQuery();
			                      if (r1.next())
				                 pet = new Pets();
			                      closeStatement(ps);
	             	   	} catch (Exception e) {
		              }
		              return pet;
	         }
                 
               @Override
               public synchronized void updatePet(Pets entity) {
                             try {
                                PreparedStatement ps = getConnection().prepareStatement(
                                 "UPDATE PETS SET CODIGO_MICROCHIP = ?, DATA_CASTRACAO = ? ,DATA_ADOCAO = ?, PESO= ? "+" WHERE ID = ?");
															 ps.setInt(1, entity.codigoMicrochip);
															 ps.setDate(2, new java.sql.Date(entity.dataCastracao.getTime()));
															 ps.setDate(3, new java.sql.Date(entity.dataAdocao.getTime()));
															 ps.setBigDecimal(4, entity.peso);
														
                               ps.executeUpdate();
                               closeStatement(ps);
                             } catch (Exception e) { }
               } 

@Override
							public synchronized void insertPetAdopter(Pets entity,int adotante, Date dataAdocao) {
														try {
															 PreparedStatement ps = getConnection().prepareStatement(
																"UPDATE PETS SET ADOTANTE = ?, DATA_ADOCAO = ?"+" WHERE CODIGO_MICROCHIP = ?");
																 if(entity.getAdotanteCpf()== null){
                        throw new NonexistentEntityException("Adotante não encontrado");
                    }
															ps.setInt(1, adotante);
															ps.setInt(2, entity.codigoMicrochip);
															ps.setDate(3, new java.sql.Date(entity.dataAdocao.getTime()));
                              
															ps.executeUpdate();
															closeStatement(ps);ps.setInt(1, adotante);
															ps.setInt(2, entity.codigoMicrochip);
														} catch (Exception e) { }
							} 
						 
}