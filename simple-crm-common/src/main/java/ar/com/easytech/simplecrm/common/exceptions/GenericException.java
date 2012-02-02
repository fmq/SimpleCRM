package ar.com.easytech.simplecrm.common.exceptions;

/**
 *
 * @author FMQ
 */
public class GenericException extends Exception {

   /**
	 * 
	 */
	private static final long serialVersionUID = 2968073380706339823L;
	protected String methodName;
   protected String originalExceptionName;
   
   public GenericException() {
      super();
   }

   public GenericException(String msg) {
      super(msg);
   }

   public GenericException(String methodName, String msg) {
      super(msg);
      this.methodName = methodName;
   }

   public GenericException(String originalExceptionName, String methodName, String msg) {
      super(msg);
      this.originalExceptionName = originalExceptionName;
      this.methodName = methodName;
   }

   public String getMethodName() {
      return methodName;
   }

   public String getOriginalExceptionName() {
      return originalExceptionName;
   }
   
   public String getCompleteMessage() {
      String msg = super.getMessage();
      return constructMessage(msg);   
      
   }
   
   private String constructMessage(String msg) {
      
      StringBuilder str = new StringBuilder();
      
      if (originalExceptionName != null) {
         str.append(originalExceptionName);
         str.append(":");
      }
      
      if (methodName != null) {
         str.append(methodName);
         str.append(":");
      }
      
      str.append(msg);
      
      return str.toString();
      
   }
   
}
