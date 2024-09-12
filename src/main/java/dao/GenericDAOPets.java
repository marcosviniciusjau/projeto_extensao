package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.List;

import model.Pets;

public abstract class GenericDAOPets<E,K> {
  protected Connection getConnection()throws Exception{
      Class.forName("org.apache.derby.jdbc.ClientDriver");
      return DriverManager.getConnection(
        "jdbc:postgresql://ep-lively-bonus-a54izdda-pooler.us-east-2.aws.neon.tech/projeto_extensao?user=projeto_extensao&password=l9gjzavHwse4&sslmode=require",
        "projeto_extensao", "l9gjzavHwse4");
  }
  
  protected Statement getStatement() throws Exception{
   	return getConnection().createStatement();
  }
  
  protected void closeStatement(Statement st) throws Exception{
   	((java.sql.Statement) st).getConnection().close();
  }
  
  public abstract void insertPet(E entity);
  public abstract void deletePet(K key);
  public abstract void updatePet(E entity);
  public abstract E selectPet(K key);
  public abstract List<E> selectAllPets();
  public abstract void insertPetAdopter(Pets entity,int pet_adopter_id, Date adoptionDate);
  } 