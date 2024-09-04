package br.edu.petsystem;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.persistence.Persistence;

import manager.PetsJpaController;
import model.Pets;

public class SystemPet {
  private final PetsJpaController dao = new PetsJpaController(
                    Persistence.createEntityManagerFactory(
                    "PetEntityJPAPU"));
  private static final BufferedReader entry =   
      new BufferedReader(new InputStreamReader(System.in));
      
    Pets pet = new Pets();
  private void select(Pets pet){
           System.out.println("Pet: "+pet.getName()+
                    "\tID: " +pet.getId()+
                     "\tData de adoção: "+pet.getAdoptionDate()+
                     "\n==========================");
  }
  public void selectAll(){
          dao.selectAll().forEach(pet->select(pet));
  }

  public void insertId() throws Exception{
    System.out.println("ID:");
    try {
        var id = Integer.parseInt(entry.readLine());
        pet.setId(id);
    } catch (NumberFormatException e) { 
        System.out.println("O ID precisa ser um número");
        System.exit(400);
    } 
  }
  public void insertName() throws Exception{
    var name = entry.readLine();
    try {
        System.out.println("Nome:");
        pet.setName(name); 
    } catch (Exception e) { 
        if(name == null){
            System.out.println("Nome obrigatório");
        }
        System.out.println(e.getLocalizedMessage());
    } 
  }
  public void insertDate() throws Exception{
    try {
        System.out.println("Data de adoção:");
        String dataEntrada = entry.readLine();
        SimpleDateFormat formatoBrasileiro = new SimpleDateFormat("dd/MM/yyyy");

         java.util.Date data = formatoBrasileiro.parse(dataEntrada);

         java.sql.Date dataSQL = new java.sql.Date(data.getTime());

         pet.setAdoptionDate(dataSQL);
     } catch (ParseException e) {
         System.out.println("Data no formato inválido");
     }
  }
  public void insertPet() throws Exception{  
      insertId();
      insertName();
      insertDate();
      dao.create(pet);
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
                                       "1-Listar\t2-Inserir\t3-Excluir\t0-Sair");
                     int opcao = Integer.parseInt(entry.readLine());
                     if(opcao==0)
                        break;
                     switch(opcao){
                        case 1: systemPet.selectAll(); break;
                        case 2: systemPet.insertPet(); break;
                        case 3: systemPet.deletePet(); break;
                        default:  System.out.println("Opção inválida"); break;
                    }
           }
     }	
} 