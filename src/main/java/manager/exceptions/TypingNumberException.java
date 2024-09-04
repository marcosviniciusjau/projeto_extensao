package manager.exceptions;

public class TypingNumberException extends NumberFormatException {
  TypingNumberException ( String msg_erro) {
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