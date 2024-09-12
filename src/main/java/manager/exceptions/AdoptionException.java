package manager.exceptions;

import java.time.DateTimeException;

public class AdoptionException extends DateTimeException {
  public AdoptionException ( String msg_erro) {
      super( msg_erro );
}          
  
  public void atribuirCausa ( Throwable causa ) {
      initCause ( causa );
  }        
  @Override
  public String toString ( ) {
      return "ErroValidacao: " + this.getMessage();
  }
}