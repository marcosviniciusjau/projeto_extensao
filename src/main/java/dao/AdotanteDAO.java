package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import model.Adotante;

 public class AdotanteDAO extends GenericDAOAdotante<Adotante, String>{
        @Override
          public synchronized void insertAdotante(Adotante pet) {
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
         public synchronized void deleteAdotante(String key) {
					    Connection c1 = null;
	         try {
		                c1 = getConnection();
		                c1.setAutoCommit(false);
		                PreparedStatement ps = getConnection().prepareStatement(
					"DELETE FROM ADOTANTE_PET WHERE ID = ?");
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
	      public synchronized List<Adotante> selectAllAdotantes() {
		      List<Adotante> list = new ArrayList<>();
		      try {
					ResultSet r1 = getStatement().executeQuery("SELECT * FROM ADOTANTE_PET");
					while (r1.next())
					list.add(new Adotante());
					}catch(Exception e){ 
	        }     
					return list;      
	    }
	         @Override
	         public synchronized Adotante selectAdotante(String key) {
						Adotante adotante_pet = null;
		                try {
		                     	  PreparedStatement ps = 
				                 getConnection().prepareStatement(
				                 "SELECT * FROM ADOTANTE_PET WHERE NAME = ?");
			                      ps.setString(1, key);
			                      ResultSet r1 = ps.executeQuery();
			                      if (r1.next())
														adotante_pet = new Adotante();
			                      closeStatement(ps);
	             	   	} catch (Exception e) {
		              }
		              return adotante_pet;
	         }
                 
               @Override
               public synchronized void updateAdotante(Adotante entity) {
                             try {
                                PreparedStatement ps = getConnection().prepareStatement(
                                 "UPDATE ADOTANTE_PET SET NOME = ?, DATA_NASCIMENTO = ?, ENDERECO=?, TELEFONE=?, CEP=? "+" WHERE CPF = ?");
                               ps.setString(1, entity.nome);
															 ps.setDate(2, new java.sql.Date(entity.dataNascimento.getTime()));
															 ps.setString(3, entity.endereco);
															 ps.setString(4, entity.telefone);
															 ps.setString(5, entity.cep);
                               ps.setString(6, entity.cpf); 
															 ps.executeUpdate();
                               closeStatement(ps);
                             } catch (Exception e) { }
               }  
					
}