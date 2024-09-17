package br.edu.sistemapet;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.zip.DataFormatException;

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

  private static Pets pets = new Pets();
  private static Adotante adotante = new Adotante();
  private static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
  
  private static int codigo_microchip;
  private static String nome;
  private static String CPF;
  
  private static String endereco;
  private static String CEP;
  private static String telefone;

  private java.sql.Date dataNascimentoSQL;
  private static BigDecimal peso;
  private java.sql.Date dataCastracaoSQL;
  private java.sql.Date dataAdocaoSQL;

  private void exists(int codigo_microchip) throws NonexistentEntityException{
    try {
      var encontrado= dao.select(codigo_microchip);
      if(encontrado == null){
      throw new NonexistentEntityException("Pet inexistente");
      }
    } catch (Exception e) {
      throw new NonexistentEntityException("Pet inexistente");
    }
  }

  private void existsAdotante(String CPF) throws NonexistentEntityException{
    try {
      var encontrado= daoAdotante.select(CPF);
      if(encontrado == null){
      throw new NonexistentEntityException("Adotante inexistente");
      }
    } catch (Exception e) {
      throw new NonexistentEntityException("Adotante inexistente");
    }
  }
  
  private void verifyIfExists(int codigo_microchip) throws PreexistingEntityException, NonexistentEntityException{
    var encontrado= dao.verify(codigo_microchip);
      if(encontrado != null){
        throw new PreexistingEntityException("Pet já existente");
      }
      pets.setCodigoMicrochip(codigo_microchip);
  }

  private void verifyIfAdotanteExists(String CPF) throws PreexistingEntityException, NonexistentEntityException{
    var encontrado= daoAdotante.verify(CPF);
      if(encontrado != null){
        throw new PreexistingEntityException("Adotante já existente");
      }
      adotante.setCpf(CPF);
  }

  private void inputCPF() throws Exception{
    try {
        System.out.println("Informe o CPF:");
        CPF = entry.readLine();
        if(CPF.length() < 11){
          throw new NumberFormatException("CPF inválido. É necessário 11 digitos.");
        }
    } catch (IOException e) {
        System.err.println("Erro ao ler o CPF: " + e.getMessage());
        System.exit(1);
    }
}
 
private void inputEndereco() throws Exception{
  try {
      System.out.println("Informe o Endereço:");
      endereco = entry.readLine();
      adotante.setEndereco(endereco);
  } catch (IOException e) {
      System.err.println("Erro ao ler o Endereco: " + e.getMessage());
      System.exit(1);
  } catch (Exception e) {
      System.exit(1);
      throw new Exception("Endereço inválido.");
  }
}

private void inputCEP() throws Exception{
  try {
      System.out.println("Informe o CEP:");
      CEP = entry.readLine();
      if(CEP.length() < 8){
        throw new NumberFormatException("CEP inválido. É necessário 8 digitos.");
      }
      adotante.setCep(CEP);
  } catch (IOException e) {
      System.err.println("Erro ao ler o CEP: " + e.getMessage());
      System.exit(1);
  }
}

private void inputTelefone() throws Exception{
  try {
      System.out.println("Informe o telefone:");
      telefone = entry.readLine();   
      if(telefone.length() < 11){
        throw new NumberFormatException("Telefone inválido.");
      }
      adotante.setTelefone(telefone);
  } catch (IOException e) {
      System.err.println("Erro ao ler o telefone: " + e.getMessage());
      System.exit(1);
  }
}

  private void inputNome() throws Exception{
    try {
    System.out.println("Nome:");
    nome = entry.readLine();
    var encontrado= daoAdotante.verify(nome);
    if(encontrado != null){
      throw new PreexistingEntityException("Adotante já existente");
    }
    adotante.setNome(nome);
    } catch (Exception e) {
      throw new Exception("Nome inválido.");
    }
}
  
  private void inputDataNascimento() throws Exception{
  try {
    System.out.println("Data de nascimento:");
    var dataNascimento = entry.readLine();
    if(dataNascimento == null){
      throw new Exception("Campo obrigatório");
    }
    java.util.Date date = sdf.parse(dataNascimento);
    dataNascimentoSQL = new java.sql.Date(date.getTime());     
    adotante.setDataNascimento(dataNascimentoSQL);
  } catch (IOException e) {
    System.err.println("Erro ao ler a data de castração: " + e.getMessage());
    System.exit(1);
  }  catch (DataFormatException e) {
    System.err.println("Data no formato inválido. O formato aceito é dd/mm/aaaa");
    System.exit(1);
  }  catch (ParseException e) {
  System.err.println("Data no formato inválido. O formato aceito é dd/mm/aaaa");
  System.exit(1);
}
}

  private void inputCodigo() throws Exception{
      try {
          System.out.println("Informe o código microchip:");
          codigo_microchip = Integer.parseInt(entry.readLine());
      } catch (IOException e) {
          System.err.println("Erro ao ler o código microchip: " + e.getMessage());
          System.exit(1);
      } catch (NumberFormatException e) {
          System.err.println("Código microchip inválido.");
          System.exit(1);
      }
  }

  private void inputDataCastracao() throws Exception{
    try {
      System.out.println("Data de castração e vacinas:");
      var dataCastracao = entry.readLine();
      if(dataCastracao == null){
        throw new Exception("Campo obrigatório");
      }
      java.util.Date date = sdf.parse(dataCastracao);
      dataCastracaoSQL = new java.sql.Date(date.getTime());     
      pets.setDataCastracao(dataCastracaoSQL);
    } catch (IOException e) {
      System.err.println("Erro ao ler a data de castração: " + e.getMessage());
      System.exit(1);
    } catch (DataFormatException e) {
      System.err.println("Data no formato inválido. O formato aceito é dd/mm/aaaa");
      System.exit(1);
    }  catch (ParseException e) {
    System.err.println("Data no formato inválido. O formato aceito é dd/mm/aaaa");
    System.exit(1);
  }
}
  
  private void inputDataAdocao() throws Exception{
  try {
    System.out.println("Data de castração e vacinas:");
    var dataAdocao = entry.readLine();
    if(dataAdocao == null){
      throw new Exception("Campo obrigatório");
    }
    java.util.Date date = sdf.parse(dataAdocao);
    dataAdocaoSQL = new java.sql.Date(date.getTime());     
    pets.setDataAdocao(dataAdocaoSQL);
  } catch (IOException e) {
    System.err.println("Erro ao ler a data de castração: " + e.getMessage());
    System.exit(1);
  } catch (DataFormatException e) {
    System.err.println("Data no formato inválido. O formato aceito é dd/mm/aaaa");
    System.exit(1);
  }  catch (ParseException e) {
  System.err.println("Data no formato inválido. O formato aceito é dd/mm/aaaa");
  System.exit(1);
}
}

  private void inputPeso(){
  try {
      System.out.println("Peso");
       peso = new BigDecimal(entry.readLine());
      pets.setPeso(peso);
    } catch (IOException e) {
      System.err.println("Erro ao ler o peso: " + e.getMessage());
      System.exit(1);
  } catch (NumberFormatException e) {
    System.err.println("Peso inválido, não digite virgulas, use pontos");
    System.exit(1);
}
}

  private void selectModelPets(Pets pet){
      System.out.println("Pet: "+pet.getCodigoMicrochip()+
                    "\tCodigo Microchip: " +pet.getCodigoMicrochip()+
                     "\tData de castração: "+pet.getDataCastracao()+
                     "\tData adoção: "+pet.getDataAdocao()+
                     "\n==========================");
    }
  
  public synchronized void selectPet() throws Exception{
      inputCodigo();
      pets = dao.select(codigo_microchip);
      System.out.println("Pet: "+pets.getCodigoMicrochip()+
      "\tID: " +pets.getCodigoMicrochip()+
      "\tData de castração: "+pets.getDataCastracao()+
      "\tData adoção: "+pets.getDataAdocao()+
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
      inputCodigo();
      verifyIfExists(codigo_microchip);
      inputPeso();
      inputDataCastracao();
      dao.create(pets);
}
  private void selectModelAdotante(Adotante adotante){
      System.out.println("Adotante: "+adotante.getNome()+
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
    while (true) {
      System.out.println(
        "Quais campos voce deseja alterar?: " +
        "1-Data castração: " +
        "\t2- Data de adoção: " +
        "\t3-Peso: " +
        "\t4-Datas de castração e adoção: " +
        "\t5-Atualizar tudo: " +
        "\t6-Sair: " +
        "\n==========================");
  
    int opcao = Integer.parseInt(entry.readLine());

    if (opcao == 6) {
      return;
    }
    switch (opcao) {
        case 1:    
          inputCodigo();
          exists(codigo_microchip);
    
          inputDataCastracao();
          dao.updateCastrateDate(codigo_microchip, dataCastracaoSQL);
        break;
  
        case 2:
          inputCodigo();
          exists(codigo_microchip);
          inputDataAdocao();
          dao.updateAdoptionDate(codigo_microchip, dataAdocaoSQL);
        break;
  
        case 3: 
          inputCodigo();
          exists(codigo_microchip);
          inputPeso();
          dao.updateWeight(codigo_microchip, peso);
        break;
  
        case 4: 
         inputCodigo();
          exists(codigo_microchip);
          inputDataCastracao();
          inputDataAdocao();
          dao.updateDates(codigo_microchip, dataCastracaoSQL, dataAdocaoSQL);
         break;
  
        case 5: 
          inputCodigo();
          exists(codigo_microchip);
          inputDataCastracao();
          inputDataAdocao();
          inputPeso();
          dao.updateAll(codigo_microchip, dataCastracaoSQL, dataAdocaoSQL, peso);
         break;
  
        default: System.out.println("Opção inválida"); break;
    }
  }  
  }
  
  public synchronized void updateAdotante() throws Exception{  
    while (true) {
      System.out.println(
        "Quais campos voce deseja alterar?: " +
        "1-Data nascimento: " +
        "\t2-Nome: " +
        "\t3-CEP: " +
        "\t4-Endereço: " +
        "\t5-Telefone: " +
        "\t6-Atualizar tudo: " +
        "\t7-Sair: " +
        "\n==========================");
  
    int opcao = Integer.parseInt(entry.readLine());

    if (opcao == 7) {
      return;
    }
    switch (opcao) {
        case 1:    
          inputCPF();
          existsAdotante(CPF);
    
          inputDataNascimento();
          daoAdotante.updateDataNascimento(CPF, dataNascimentoSQL);
        break;

        case 2:
          inputCPF();
          existsAdotante(CPF);
          inputNome();
          daoAdotante.updateNome(CPF, nome);
       break;

        case 3:
          inputCPF();
          existsAdotante(CPF);
          inputCEP();
          daoAdotante.updateCEP(CPF, CEP);
        break;
  
        case 4: 
          inputCPF();
          existsAdotante(CPF);
          inputEndereco();
          daoAdotante.updateEndereco(CPF, endereco);
        break;
  
        case 5: 
          inputCPF();
          existsAdotante(CPF);
          inputTelefone();
          daoAdotante.updateTelefone(CPF, telefone);
        break;

        case 6: 
          inputCPF();
          existsAdotante(CPF);
          inputDataNascimento();
          inputEndereco();
          inputCEP();
          inputTelefone();
          daoAdotante.updateAll(CPF, nome,dataNascimentoSQL, endereco, CEP, telefone);
         break;
  
        default: System.out.println("Opção inválida"); break;
    }
  }  
  }
    
  public synchronized void selectAdotante() throws Exception{
    System.out.println("CPF");
    inputCPF();
    adotante = daoAdotante.select(CPF);
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
    inputNome();
    inputCPF();
    verifyIfAdotanteExists(CPF);
    inputDataNascimento();
    inputCEP();
    inputEndereco();
    inputTelefone();
    daoAdotante.create(adotante);
  }

  public synchronized void adotate() throws Exception{ 
    inputCodigo();
    exists(codigo_microchip);
    inputCPF();
    existsAdotante(CPF);
    inputDataAdocao();
    dao.adopt(codigo_microchip, dataAdocaoSQL,CPF);
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
                "1-Listar pets:" +
                "\t2-Listar pet por codigo microchip:" +
                "\t3-Inserir pet:" +
                "\t4-Atualizar pet:" +
                "\t5-Listar adotantes:" +
                "\t6-Listar adotante por CPF:" +
                "\t7-Inserir adotante:" +
                "\t8-Atualizar adotante:" +
                "\t9-Marcar pet como adotado:" +
                "\t10-Excluir pet:" +
                "\t11-Excluir adotante:" +
                "\t0-Sair:" +
                "\n==========================");

                      int opcao = Integer.parseInt(entry.readLine());
                      if(opcao==0)
                          break;
                      switch(opcao){
                          case 1: systemPet.selectAllPets(); break;
                          case 2: systemPet.selectPet(); break;
                          case 3: systemPet.insertPet(); break;
                          case 4: systemPet.updatePet(); break;
                          case 5: systemPet.selectAllAdotantes(); break;
                          case 6: systemPet.selectAdotante(); break;
                          case 7: systemPet.insertAdotante(); break;
                          case 8: systemPet.updateAdotante(); break;
                          case 9: systemPet.adotate(); break;
                          case 10: systemPet.deletePet(); break;
                          case 11: systemPet.deleteAdotante();break;
                          default:  System.out.println("Opção inválida"); break;
                      }
            }
  }	
  }