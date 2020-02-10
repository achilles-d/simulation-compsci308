package Controller;

public class ControllerException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public ControllerException (String message, Object ... values){
    super(String.format(message,values));
  }

  public ControllerException(Throwable cause, String message, Object ... values){
    super(String.format(message,values),cause);

  }

  public ControllerException(Throwable exception){
    super(exception);
  }

}
