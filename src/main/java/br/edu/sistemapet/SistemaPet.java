package br.edu.sistemapet;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.zip.DataFormatException;

import javax.persistence.Persistence;

import manager.AdotantesJpaController;
import manager.PetsJpaController;
import manager.exceptions.NonexistentEntityException;
import manager.exceptions.PreexistingEntityException;
import model.Adotantes;
import model.Pets;
public class SistemaPet {
  private final PetsJpaController dao = new PetsJpaController(
                    Persistence.createEntityManagerFactory(
                    "PetEntityJPAPU"));  

  private final AdotantesJpaController daoAdotantes = new AdotantesJpaController(
                      Persistence.createEntityManagerFactory(
                      "AdotanteEntityJPAPU"));

  private static final BufferedReader entry =   
      new BufferedReader(new InputStreamReader(System.in));

  private static Pets pets = new Pets();
  private static Adotantes Adotantes = new Adotantes();
  private static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
  
  private static int codigoMicrochip;
  private static int codigoMicrochipNovo;
  private static BigDecimal peso;
  private java.sql.Date dataCastracaoSQL;
  private java.sql.Date dataVacinasSQL;
  private java.sql.Date dataAdocaoSQL;

  private static String nome;
  private static String CPF;
  private static String CPFNovo;
  private static String endereco;
  private static String CEP;
  private static String telefone;
  private java.sql.Date dataNascimentoSQL;
  

  private void exists(int codigoMicrochip) throws NonexistentEntityException{
    try {
      var encontrado= dao.select(codigoMicrochip);
      if(encontrado == null){
      throw new NonexistentEntityException("Pet inexistente");
      }
    } catch (Exception e) {
      throw new NonexistentEntityException("Pet inexistente");
    }
  }

  private void existsAdotantes(String CPF) throws NonexistentEntityException{
    try {
      var encontrado= daoAdotantes.select(CPF);
      if(encontrado == null){
      throw new NonexistentEntityException("Adotantes inexistente");
      }
    } catch (Exception e) {
      throw new NonexistentEntityException("Adotantes inexistente");
    }
  }
  
  private void verifyIfExists(int codigoMicrochip) throws PreexistingEntityException, NonexistentEntityException{
    var encontrado= dao.verificar(codigoMicrochip);
      if(encontrado != null){
        throw new PreexistingEntityException("Pet já existente");
      }
      pets.setCodigoMicrochip(codigoMicrochip);
  }

  private void inputCodigo() throws Exception{
    try {
        System.out.println("Informe o código microchip:");
        codigoMicrochip = Integer.parseInt(entry.readLine());
        if(codigoMicrochip < 15){
        throw new NumberFormatException("Código microchip inválido.");
        }
    } catch (IOException e) {
      throw new IOException("Erro ao ler o código microchip: " + e.getMessage());
    } catch (NumberFormatException e) {
        throw new NumberFormatException("Código microchip inválido.");
    }
}

private void inputUpdateCodigo() throws Exception{
  try {
      System.out.println("Informe o código microchip antigo:");
      codigoMicrochip = Integer.parseInt(entry.readLine());
      if(codigoMicrochip < 15){
        throw new NumberFormatException("Código microchip inválido.");
        }
      exists(codigoMicrochip);
        
      System.out.println("Informe o código microchip novo:");
      codigoMicrochipNovo = Integer.parseInt(entry.readLine());
      if(codigoMicrochipNovo < 15){
        throw new NumberFormatException("Código microchip inválido.");
        }
      verifyIfExists(codigoMicrochipNovo);
      pets.setCodigoMicrochip(codigoMicrochipNovo);
  } catch (IOException e) {
    throw new IOException("Erro ao ler o código microchip: " + e.getMessage());
  } catch (NumberFormatException e) {
      throw new NumberFormatException("Código microchip inválido.");
  }
}

private void inputDataCastracao() throws Exception{
  try {
    System.out.println("Data de castracao e vacinas:");
    var dataCastracao = entry.readLine();
    if(dataCastracao == null){
      throw new Exception("Campo obrigatório");
    }
    java.util.Date date = sdf.parse(dataCastracao);
    dataCastracaoSQL = new java.sql.Date(date.getTime());     
    pets.setDataCastracao(dataCastracaoSQL);
  } catch (IOException e) {
    throw new IOException("Erro ao ler a data de castracao: " + e.getMessage());
  } catch (DataFormatException e) {
    throw new DataFormatException("Data no formato inválido. O formato aceito é dd/mm/aaaa");
  }  catch (ParseException e) {
    throw new ParseException("Data no formato inválido. O formato aceito é dd/mm/aaaa", 0);
  }
}

private void inputDataAdocao() throws Exception{
try {
  System.out.println("Data de adocao:");
  var dataAdocao = entry.readLine();
  if(dataAdocao == null){
    throw new Exception("Campo obrigatório");
  }
  java.util.Date date = sdf.parse(dataAdocao);
  dataAdocaoSQL = new java.sql.Date(date.getTime());     
  pets.setDataAdocao(dataAdocaoSQL);
} catch (IOException e) {
  throw new IOException("Erro ao ler a data de adocao: " + e.getMessage());
} catch (DataFormatException e) {
  throw new DataFormatException("Data no formato inválido. O formato aceito é dd/mm/aaaa");
}   catch (ParseException e) {
  throw new ParseException("Data no formato inválido. O formato aceito é dd/mm/aaaa", 0);
}
}

private void inputDataVacinas() throws Exception{
  try {
    System.out.println("Data de Vacinas:");
    var dataVacinas = entry.readLine();
    if(dataVacinas == null){
      throw new Exception("Campo obrigatório");
    }
    java.util.Date date = sdf.parse(dataVacinas);
    dataVacinasSQL = new java.sql.Date(date.getTime());     
    pets.setDataVacinas(dataVacinasSQL);
  } catch (IOException e) {
    throw new IOException("Erro ao ler a data de Vacinas: " + e.getMessage());
  } catch (DataFormatException e) {
    throw new DataFormatException("Data no formato inválido. O formato aceito é dd/mm/aaaa");
  }   catch (ParseException e) {
    throw new ParseException("Data no formato inválido. O formato aceito é dd/mm/aaaa", 0);
  }
  }
  
  
private void inputPeso() throws IOException{
try {
    System.out.println("Peso");
     peso = new BigDecimal(entry.readLine());
    pets.setPeso(peso);
} catch (IOException e) {
    throw new IOException("Erro ao ler o peso: " + e.getMessage());
} catch (NumberFormatException e) {
  throw new NumberFormatException("Peso inválido, não digite virgulas, use pontos");
}
}

  private void verifyIfAdotantesExists(String CPF) throws PreexistingEntityException, NonexistentEntityException{
    var encontrado= daoAdotantes.verify(CPF);
      if(encontrado != null){
        throw new PreexistingEntityException("Adotantes já existente");
      }
      Adotantes.setCpf(CPF);
  }

  private void inputCPF() throws Exception{
    try {
        System.out.println("Informe o CPF:");
        CPF = entry.readLine();
        if(CPF.length() < 11){
          throw new NumberFormatException("CPF inválido. É necessário 11 digitos.");
        }
    } catch (IOException e) {
        throw new IOException("Erro ao ler o CPF: " + e.getMessage());
    }
}
private void inputCPFAdocao() throws Exception{
  try {
      System.out.println("Informe o CPF:");
      CPF = entry.readLine();
      if(CPF.length() < 11){
        throw new NumberFormatException("CPF inválido. É necessário 11 digitos.");
      }
      existsAdotantes(CPF);
  } catch (IOException e) {
      throw new IOException("Erro ao ler o CPF: " + e.getMessage());
  }
}

private void inputUpdateCPF() throws Exception{
  try {
      System.out.println("Informe o CPF:");
      CPF = entry.readLine();
      if(CPF.length() < 11){
        throw new NumberFormatException("CPF inválido. É necessário 11 digitos.");
      }
      existsAdotantes(CPF);
      System.out.println("Informe o CPF novo:");
      CPFNovo = entry.readLine();
      if(CPF.length() < 11){
        throw new NumberFormatException("CPF inválido. É necessário 11 digitos.");
      }
      if(CPFNovo == CPF){
        throw new NumberFormatException("Não há nada para atualizar");
      }
      verifyIfAdotantesExists(CPFNovo);
      Adotantes.setCpf(CPFNovo);
  } catch (IOException e) {
      throw new IOException("Erro ao ler o CPF: " + e.getMessage());
  }
}
private void inputEndereco() throws Exception{
  try {
      System.out.println("Informe o Endereço:");
      endereco = entry.readLine();
      Adotantes.setEndereco(endereco);
  } catch (IOException e) {
      throw new IOException("Erro ao ler o Endereco: " + e.getMessage());
  } catch (Exception e) {
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
      Adotantes.setCep(CEP);
  } catch (IOException e) {
      throw new IOException("Erro ao ler o CEP: " + e.getMessage());
  }
}

private void inputTelefone() throws Exception{
  try {
      System.out.println("Informe o telefone:");
      telefone = entry.readLine();   
      if(telefone.length() < 11){
        throw new NumberFormatException("Telefone inválido.");
      }
      Adotantes.setTelefone(telefone);
  } catch (IOException e) {
      throw new IOException("Erro ao ler o telefone: " + e.getMessage());
  }
}

  private void inputNome() throws Exception{
    try {
    System.out.println("Nome:");
    nome = entry.readLine();
    if(nome.length() < 10){
      throw new NumberFormatException("Nome inválido. É necessário ter no mínimo  10 digitos.");
    }
    Adotantes.setNome(nome);
    } catch (Exception e) {
      throw new Exception("Nome inválido. É necessário ter no mínimo  10 digitos.");
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
    Adotantes.setDataNascimento(dataNascimentoSQL);
  } catch (IOException e) {
    throw new IOException("Erro ao ler a data de castracao: " + e.getMessage());
  }  catch (DataFormatException e) {
    throw new DataFormatException("Data no formato inválido. O formato aceito é dd/mm/aaaa");
  }  catch (ParseException e) {
  throw new ParseException("Data no formato inválido. O formato aceito é dd/mm/aaaa", 0);
  }
}

  private void selectModelPets(Pets pet){
      System.out.println("Pet: "+pet.getCodigoMicrochip()+
                    "\tPeso: " +pet.getPeso()+
                    "\tCodigo Microchip: " +pet.getCodigoMicrochip()+
                     "\tData de castracao: "+pet.getDataCastracao()+ 
                     "\tData das vacinas: "+pets.getDataVacinas()+
                     "\tData adocao: "+pet.getDataAdocao()+
                     "\n==========================");
}
  
  public synchronized void selectPet() throws Exception{
      inputCodigo();
      pets = dao.select(codigoMicrochip);
      System.out.println("Pet: "+pets.getCodigoMicrochip()+
      "\tPeso: " +pets.getPeso()+
      "\tData de castracao: "+pets.getDataCastracao()+
      "\tData das vacinas: "+pets.getDataVacinas()+
      "\tData adocao: "+pets.getDataAdocao()+
      "\tCPF do Adotantes: "+pets.getAdotanteCpf()+
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
      verifyIfExists(codigoMicrochip);
      inputPeso();
      inputDataCastracao();
      dao.create(pets);
}

  private void selectModelAdotantes(Adotantes Adotantes){
      System.out.println("Adotantes: "+Adotantes.getNome()+
    "\tCPF: " +Adotantes.getCpf()+
    "\tData de nascimento: "+Adotantes.getDataNascimento()+
    "\tEndereço: "+Adotantes.getEndereco()+
    "\tCEP: "+Adotantes.getCep()+
    "\tTelefone: "+Adotantes.getTelefone()+
    "\n==========================");
    if (!Adotantes.getPetsList().isEmpty()) {
      System.out.println("Pets Adotados: " + Adotantes.getPetsList().size()
      );
  }
}

  public synchronized void updatePet() throws Exception{
    while (true) {
      System.out.println(
        "Quais campos voce deseja alterar?: " +
        "1-Código Microchip: " +
        "\t2-Data castracao: " +
        "\t3- Data de adocao: " +
        "\t4- Data das vacinas: "+
        "\t4-Peso: " +
        "\t5-Datas de castracao e adocao: " +
        "\t6-Atualizar tudo: " +
        "\t7-Sair: " +
        "\n==========================");
  
    int opcao = Integer.parseInt(entry.readLine());

    if (opcao == 8) {
      return;
    }
    switch (opcao) {
        case 1:    
          inputUpdateCodigo();
          dao.updateCodigo(codigoMicrochip, codigoMicrochipNovo);
        break;

        case 2:    
          inputCodigo();
          exists(codigoMicrochip);
    
          inputDataCastracao();
          dao.updateDataCastracao(codigoMicrochip, dataCastracaoSQL);
       break;
  
        case 3:
          inputCodigo();
          exists(codigoMicrochip);
          inputDataAdocao();
          dao.updateDataAdocao(codigoMicrochip, dataAdocaoSQL);
        break;
  
        case 4: 
          inputCodigo();
          exists(codigoMicrochip);
          inputDataVacinas();
          dao.updateDataVacinas(codigoMicrochip, dataVacinasSQL);
        break;

        case 5: 
          inputCodigo();
          exists(codigoMicrochip);
          inputPeso();
          dao.updatePeso(codigoMicrochip, peso);
        break;
  
        case 6: 
         inputCodigo();
          exists(codigoMicrochip);
          inputDataCastracao();
          inputDataAdocao();
          inputDataVacinas();
          dao.updateDatas(codigoMicrochip, dataCastracaoSQL, dataAdocaoSQL,dataVacinasSQL);
         break;
  
        case 7: 
          inputCodigo();
          exists(codigoMicrochip);
          inputDataCastracao();
          inputDataAdocao();
          inputPeso();
          dao.updateAll(codigoMicrochip, dataCastracaoSQL, dataAdocaoSQL, peso);
         break;
  
        default: System.out.println("Opcao invalida"); break;
    }
  }  
}
  
  public synchronized void updateAdotantes() throws Exception{  
    while (true) {
      System.out.println(
        "Quais campos voce deseja alterar?: " +
        "1-CPF: " +
        "\t2-Nome: " +
        "\t3-Data nascimento: " +
        "\t4-CEP: " +
        "\t5-Endereço: " +
        "\t6-Telefone: " +
        "\t7-Atualizar tudo: " +
        "\t8-Sair: " +
        "\n==========================");
  
    int opcao = Integer.parseInt(entry.readLine());

    if (opcao == 8) {
      return;
    }
    switch (opcao) {
        case 1:    
          inputUpdateCPF();
          daoAdotantes.updateCPF(CPF, CPFNovo);
        break;
          case 2:    
          inputCPF();
          existsAdotantes(CPF);
          inputNome();
          daoAdotantes.updateNome(CPF, nome);
          break;

        case 3:
        inputCPF();
        existsAdotantes(CPF);
        inputDataNascimento();
        daoAdotantes.updateDataNascimento(CPF, dataNascimentoSQL);
    
       break;

        case 4:
          inputCPF();
          existsAdotantes(CPF);
          inputCEP();
          daoAdotantes.updateCEP(CPF, CEP);
        break;
  
        case 5: 
          inputCPF();
          existsAdotantes(CPF);
          inputEndereco();
          daoAdotantes.updateEndereco(CPF, endereco);
        break;
  
        case 6: 
          inputCPF();
          existsAdotantes(CPF);
          inputTelefone();
          daoAdotantes.updateTelefone(CPF, telefone);
        break;

        case 7: 
          inputCPF();
          existsAdotantes(CPF);
          inputDataNascimento();
          inputEndereco();
          inputCEP();
          inputTelefone();
          daoAdotantes.updateAll(CPF, nome,dataNascimentoSQL, endereco, CEP, telefone);
         break;
  
        default: System.out.println("Opcao invalida"); break;
    }
  }  
  }
    
  public synchronized void selectAdotantes() throws Exception{
    System.out.println("CPF");
    inputCPF();
    Adotantes = daoAdotantes.select(CPF);
    System.out.println("Adotantes: "+Adotantes.getCpf()+
    "\tCPF: " +Adotantes.getCpf()+
    "\tData de nascimento: "+Adotantes.getDataNascimento()+
    "\tEndereco: "+Adotantes.getEndereco()+
    "\tCEP: "+Adotantes.getCep()+
    "\tTelefone: "+Adotantes.getTelefone()+
    "\n==========================");
    if (!Adotantes.getPetsList().isEmpty()) {
      System.out.println("Pets Adotados: " + Adotantes.getPetsList());
  }
  }

  public synchronized void selectAllAdotantess() throws Exception{
    try {
      daoAdotantes.selectAll().forEach(Adotantes->{
        selectModelAdotantes(Adotantes);
        });
    } catch (Exception e) {
      throw new NonexistentEntityException("Nenhum Adotantes existente");
    }        
  }

  public synchronized void insertAdotantes() throws Exception{
    inputNome();
    inputCPF();
    verifyIfAdotantesExists(CPF);
    inputDataNascimento();
    inputCEP();
    inputEndereco();
    inputTelefone();
    daoAdotantes.create(Adotantes);
  }

  public synchronized void adotar() throws Exception{ 
    inputCodigo();
    exists(codigoMicrochip);
    inputCPFAdocao();
    inputDataAdocao();
    dao.adotar(codigoMicrochip, dataAdocaoSQL,CPF);
  }
          
  public synchronized void deletePet() throws Exception{
            System.out.println("Codigo Microchip:");
            int codigoMicrochip = Integer.parseInt(entry.readLine());
            dao.destroy(codigoMicrochip);
  }

  public synchronized void deleteAdotantes() throws Exception{
    System.out.println("CPF:");
    var cpf = entry.readLine();
    daoAdotantes.destroy(cpf);
  }

  public synchronized static void main(String args[]) throws Exception{
            SistemaPet systemPet = new SistemaPet();
            while(true){
                System.out.println(
                "1-Listar pets:" +
                "\t2-Listar pet por codigo microchip:" +
                "\t3-Inserir pet:" +
                "\t4-Atualizar pet:" +
                "\t5-Listar Adotantess:" +
                "\t6-Listar Adotantes por CPF:" +
                "\t7-Inserir Adotantes:" +
                "\t8-Atualizar Adotantes:" +
                "\t9-Marcar pet como adotado:" +
                "\t10-Excluir pet:" +
                "\t11-Excluir Adotantes:" +
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
                          case 5: systemPet.selectAllAdotantess(); break;
                          case 6: systemPet.selectAdotantes(); break;
                          case 7: systemPet.insertAdotantes(); break;
                          case 8: systemPet.updateAdotantes(); break;
                          case 9: systemPet.adotar(); break;
                          case 10: systemPet.deletePet(); break;
                          case 11: systemPet.deleteAdotantes();break;
                          default:  System.out.println("Opcao invalida"); break;
                      }
            }
  }	
  }