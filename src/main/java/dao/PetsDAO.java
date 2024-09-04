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
import model.Pets;

 public class PetsDAO extends GenericDAO<Pets, String>{
        @Override
          public void insert(Pets pet) {
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
         public void delete(String key) {
					    Connection c1 = null;
	         try {
		                c1 = getConnection();
		                c1.setAutoCommit(false);
		                PreparedStatement ps = getConnection().prepareStatement(
					"DELETE FROM PETS WHERE ID = ?");
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
	      public List<Pets> selectAll() {
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
	         public Pets select(String key) {
		                Pets pet = null;
		                try {
		                     	  PreparedStatement ps = 
				                 getConnection().prepareStatement(
				                 "SELECT * FROM PETS WHERE NAME = ?");
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
               public void update(Pets entity) {
                             try {
                                PreparedStatement ps = getConnection().prepareStatement(
                                 "UPDATE PETS SET NAME = ?, ADOPTION_DATE = ? "+" WHERE ID = ?");
                               ps.setString(2, entity.name);
                               ps.setInt(3, entity.id);
                               ps.executeUpdate();
                               closeStatement(ps);
                             } catch (Exception e) { }
               } 
							}
