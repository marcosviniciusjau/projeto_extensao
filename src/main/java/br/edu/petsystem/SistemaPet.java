package br.edu.petsystem;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;

import javax.persistence.Persistence;

import manager.AdotanteJpaController;
import manager.PetsJpaController;
import manager.exceptions.NonexistentEntityException;
import manager.exceptions.PreexistingEntityException;
import model.Adotante;
import model.Pets;
public class SistemaPet {
  private final PetsJpaController dao = new PetsJpaController(
                    Persistence.createEntityManagerFactory(
                    "PetEntityJPAPU"));  

  private final AdotanteJpaController daoAdotante = new AdotanteJpaController(
                      Persistence.createEntityManagerFactory(
                      "AdotanteEntityJPAPU"));

  private static final BufferedReader entry =   
      new BufferedReader(new InputStreamReader(System.in));
  
  private void selectModelPets(Pets pet){
      System.out.println("Pet: "+pet.getCodigoMicrochip()+
                    "\tCodigo Microchip: " +pet.getCodigoMicrochip()+
                     "\tData de castração: "+pet.getDataCastracao()+
                     "\tData adoção: "+pet.getDataAdocao()+
                     "\n==========================");
    }
  
  public synchronized void selectPet() throws Exception{
      Pets pet = new Pets();
      System.out.println("Codigo Microchip:");
      var codigo_microchip = Integer.parseInt(entry.readLine());
      pet = dao.select(codigo_microchip);
      System.out.println("Pet: "+pet.getCodigoMicrochip()+
      "\tID: " +pet.getCodigoMicrochip()+
      "\tData de castração: "+pet.getDataCastracao()+
      "\tData adoção: "+pet.getDataAdocao()+
      "\n==========================");
  }
    
  public synchronized void selectAllPets() throws Exception{
      try {
        dao.selectAll().forEach(pet->{
          selectModelPets(pet);
          });
      } catch (Exception e) {
        throw new NonexistentEntityException("Nenhum pet existente");
      }        
    }
  
    public synchronized void insertPet() throws Exception{  
      Pets pets = new Pets();
      System.out.println("Codigo Micro chip:");
      
      var codigo_microchip = Integer.parseInt(entry.readLine());
      var encontrado= dao.verify(codigo_microchip);
      if(encontrado != null){
        throw new PreexistingEntityException("Pet já existente");
      }

      pets.setCodigoMicrochip(codigo_microchip);
      
      System.out.println("Peso");
      var peso = new BigDecimal(entry.readLine());
      pets.setPeso(peso);
  
      System.out.println("Data de castração e vacinas:");
      var dataCastracao = entry.readLine();
      if(dataCastracao == null){
        throw new Exception("Campo obrigatório");
      }
      SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
      java.util.Date date = sdf.parse(dataCastracao);
      java.sql.Date dataCastracaoSQL = new java.sql.Date(date.getTime());     
      pets.setDataCastracao(dataCastracaoSQL);

      dao.create(pets);
}

  private void selectModelAdotante(Adotante adotante){
      System.out.println("Adotante: "+adotante.getCpf()+
    "\tCPF: " +adotante.getCpf()+
    "\tData de nascimento: "+adotante.getDataNascimento()+
    "\tEndereço: "+adotante.getEndereco()+
    "\tCEP: "+adotante.getCep()+
    "\tTelefone: "+adotante.getTelefone()+
    "\n==========================");
    if (!adotante.getPetsList().isEmpty()) {
      System.out.println("Pets Adotados: " + adotante.getPetsList().size()
      );
  }
  }

  public synchronized void updatePet() throws Exception{  
    Pets pets = new Pets();  
    System.out.println("Código Microchip:");
    var codigo_microchip = Integer.parseInt(entry.readLine());
    dao.select(codigo_microchip);
    System.out.println("Data de castração:");
    var dataEntrada = entry.readLine();

    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    java.util.Date date = sdf.parse(dataEntrada);
    java.sql.Date dataSQL = new java.sql.Date(date.getTime());
    pets.setDataCastracao(dataSQL);

    System.out.println("Data de adoção:");
    var dataAdocao = entry.readLine();
  
    SimpleDateFormat sdfAdocao = new SimpleDateFormat("dd/MM/yyyy");
    java.util.Date dateAdocao = sdfAdocao.parse(dataAdocao);
    java.sql.Date dataSQLAdocao = new java.sql.Date(dateAdocao.getTime());
      pets.setDataAdocao(dataSQLAdocao);
    
      System.out.println("Peso");
      var peso = new BigDecimal(entry.readLine());
      pets.setPeso(peso);
    dao.update(codigo_microchip,dataSQL, dataSQLAdocao, peso);
  }

  public synchronized void updateAdotante() throws Exception{  
    Adotante adotante = new Adotante();  
    System.out.println("CPF:");
    var cpf = entry.readLine();
    daoAdotante.select(cpf);

    System.out.println("Nome:");
    var nome = entry.readLine();
    adotante.setNome(nome);

    System.out.println("Data de nascimento:");
    var dataEntrada = entry.readLine();

    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    java.util.Date date = sdf.parse(dataEntrada);
    java.sql.Date dataSQL = new java.sql.Date(date.getTime());
    adotante.setDataNascimento(dataSQL);

    System.out.println("Endereço:");
    var endereco = entry.readLine();
    adotante.setEndereco(endereco);

      System.out.println("Cep:");
      var cep = entry.readLine();
      adotante.setCep(cep);
      System.out.println("Telefone:");
      var telefone = entry.readLine();
      adotante.setTelefone(telefone);

    daoAdotante.update(cpf,dataSQL,endereco, nome, cep, telefone);
  }
    
  public synchronized void selectAdotante() throws Exception{
    Adotante adotante = new Adotante();
    System.out.println("CPF");
    var cpf = entry.readLine();
    adotante = daoAdotante.select(cpf);
    System.out.println("Adotante: "+adotante.getCpf()+
    "\tCPF: " +adotante.getCpf()+
    "\tData de nascimento: "+adotante.getDataNascimento()+
    "\tEndereço: "+adotante.getEndereco()+
    "\tCEP: "+adotante.getCep()+
    "\tTelefone: "+adotante.getTelefone()+
    "\n==========================");
    if (!adotante.getPetsList().isEmpty()) {
      System.out.println("Pets Adotados: " + adotante.getPetsList());
  }
  }

  public synchronized void selectAllAdotantes() throws Exception{
    try {
      daoAdotante.selectAll().forEach(adotante->{
        selectModelAdotante(adotante);
        });
    } catch (Exception e) {
      throw new NonexistentEntityException("Nenhum adotante existente");
    }        
  }

  public synchronized void insertAdotante() throws Exception{  
    Adotante pet_adopter = new Adotante();
    System.out.println("Nome:");
    var nome = entry.readLine();
    var encontrado= daoAdotante.verify(nome);
    pet_adopter.setNome(nome);
        if(encontrado != null){
          throw new PreexistingEntityException("Adotante já existente");
        }
    System.out.println("Data de nascimento:");
    var entryDate = entry.readLine();
  
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    java.util.Date date = sdf.parse(entryDate);
    java.sql.Date SQLDate = new java.sql.Date(date.getTime()); 
    pet_adopter.setDataNascimento(SQLDate);

    System.out.println("CPF:");
    pet_adopter.setCpf(entry.readLine());

    System.out.println("Endereço:");
    pet_adopter.setEndereco(entry.readLine());

    System.out.println("Telefone:");
    pet_adopter.setTelefone(entry.readLine());

    System.out.println("CEP:");
    pet_adopter.setCep(entry.readLine());

    daoAdotante.create(pet_adopter);
  }

  public synchronized void adotate() throws Exception{  
    Pets pets = new Pets();  
    System.out.println("Codigo Microchip:");
    var codigo_microchip = Integer.parseInt(entry.readLine());
    dao.select(codigo_microchip);
    System.out.println("CPF do adotante:");
    var cpf =entry.readLine();
    daoAdotante.select(cpf);
    System.out.println("Data de adoção:");
    var entryDate = entry.readLine();

    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    java.util.Date date = sdf.parse(entryDate);
    java.sql.Date SQLDate = new java.sql.Date(date.getTime());
    pets.setDataAdocao(SQLDate);
    dao.adopt(codigo_microchip, SQLDate,cpf);
  }
          
  public synchronized void deletePet() throws Exception{
            System.out.println("Codigo Microchip:");
            int codigoMicrochip = Integer.parseInt(entry.readLine());
            dao.destroy(codigoMicrochip);
  }

  public synchronized void deleteAdotante() throws Exception{
    System.out.println("CPF:");
    var cpf = entry.readLine();
    daoAdotante.destroy(cpf);
  }

  public synchronized static void main(String args[]) throws Exception{
            SistemaPet systemPet = new SistemaPet();
            while(true){
                      System.out.println(
                                        "1-Listar pets\t2-Listar pet por código microchip\t3-Atualizar pet\t4-Atualizar adotante\t5-Listar adotantes\t6-Listar adotantes por CPF\t7-Inserir Adotante\t8-Inserir Pet\t9-Marcar como adotado\t10-Excluir pet\t11-Excluir adotante\\t0-Sair");
                      int opcao = Integer.parseInt(entry.readLine());
                      if(opcao==0)
                          break;
                      switch(opcao){
                          case 1: systemPet.selectAllPets(); break;
                          case 2: systemPet.selectPet(); break;
                          case 3: systemPet.updatePet(); break;
                          case 4: systemPet.updateAdotante(); break;
                          case 5: systemPet.selectAllAdotantes(); break;
                          case 6: systemPet.selectAdotante(); break;
                          case 7: systemPet.insertAdotante(); break;
                          case 8: systemPet.insertPet(); break;
                          case 9: systemPet.adotate(); break;
                          case 10: systemPet.deletePet(); break;
                          case 11: systemPet.deleteAdotante();break;
                          default:  System.out.println("Opção inválida"); break;
                      }
            }
  }	
  }