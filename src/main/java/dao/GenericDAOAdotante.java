package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.List;

public abstract class GenericDAOAdotante<E,K> {
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
  
  public abstract void insertAdotante(E entity);
  public abstract void deleteAdotante(K key);
  public abstract void updateAdotante(E entity);
  public abstract E selectAdotante(K key);
  public abstract List<E> selectAllAdotantes();
}
