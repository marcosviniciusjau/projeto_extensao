package br.edu.petsystem;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.persistence.Persistence;

import manager.PetsJpaController;
import manager.exceptions.NonexistentEntityException;
import model.Pets;

public class SystemPet {
  private final PetsJpaController dao = new PetsJpaController(
                    Persistence.createEntityManagerFactory(
                    "PetEntityJPAPU"));
  private static final BufferedReader entry =   
      new BufferedReader(new InputStreamReader(System.in));
      
  private void selectModel(Pets pet){
      System.out.println("Pet: "+pet.getName()+
                    "\tID: " +pet.getId()+
                     "\tData de adoção: "+pet.getAdoptionDate()+
                     "\n==========================");
    }
  

  public void select() throws Exception{
    Pets pet = new Pets();
    System.out.println("Nome:");
    var name = entry.readLine();
    pet = dao.select(name);
      System.out.println("Pet: "+pet.getName()+
                    "\tID: " +pet.getId()+
                     "\tData de adoção: "+pet.getAdoptionDate()+
                     "\n==========================");
    }
  
  public void selectAll() throws Exception{
    try {
      dao.selectAll().forEach(pet->{
        selectModel(pet);
         });
    } catch (Exception e) {
      throw new NonexistentEntityException("Nenhum pet existente");
    }        
  }

  public void insertPet() throws Exception{  
      Pets pets = new Pets();
      System.out.println("ID:");
      pets.setId(Integer.parseInt(entry.readLine()));
      System.out.println("Nome:");
      pets.setName(entry.readLine());
      System.out.println("Data de adoção:");
      var dataEntrada = entry.readLine();
    
      SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
      java.util.Date date = sdf.parse(dataEntrada);
      java.sql.Date dataSQL = new java.sql.Date(date.getTime());
       pets.setAdoptionDate(dataSQL);

      dao.create(pets);
}

public void insertCastrateDate() throws Exception{  
  Pets pets = new Pets();  
  System.out.println("Nome:");
  var name = entry.readLine();
  System.out.println("Data de castração:");
  var dataEntrada = entry.readLine();

  SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
  java.util.Date date = sdf.parse(dataEntrada);
  java.sql.Date dataSQL = new java.sql.Date(date.getTime());
   pets.setCastrateDate(dataSQL);

  dao.castrate(name,dataSQL);
}
             
  public void deletePet() throws Exception{
          System.out.println("ID:");
          int id = Integer.parseInt(entry.readLine());
          dao.destroy(id);
  }
  public static void main(String args[]) throws Exception{
           SystemPet systemPet = new SystemPet();
           while(true){
                     System.out.println(
                                       "1-Listar\t2-Listar por nome\t3-Inserir\t4-Excluir\t0-Sair");
                     int opcao = Integer.parseInt(entry.readLine());
                     if(opcao==0)
                        break;
                     switch(opcao){
                        case 1: systemPet.selectAll(); break;
                        case 2: systemPet.select(); break;
                        case 3: systemPet.insertPet(); break;
                        case 4: systemPet.insertCastrateDate(); break;
                        case 5: systemPet.deletePet(); break;
                        default:  System.out.println("Opção inválida"); break;
                    }
           }
     }	
    }