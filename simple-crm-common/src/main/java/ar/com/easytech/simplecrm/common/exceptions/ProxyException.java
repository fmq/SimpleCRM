package ar.com.easytech.simplecrm.common.exceptions;

/**
 *
 * @author FMQ
 */
public class ProxyException extends GenericException {

   private static final long serialVersionUID = -5749585935194072396L;
   
   public ProxyException() {
      super();
   }

   public ProxyException(String msg) {
      super(msg);
   }

   public ProxyException(String methodName, String msg) {
      super(methodName , msg);
   }

   public ProxyException(String originalExceptionName, String methodName, String msg) {
      super(originalExceptionName, methodName, msg);
   }
   
}
