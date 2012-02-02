package ar.com.easytech.simplecrm.common.exceptions;

/**
 *
 * @author FMQ
 */
public class ServiceException extends GenericException {

   private static final long serialVersionUID = -5749585935194072396L;
   
   public ServiceException() {
      super();
   }

   public ServiceException(String msg) {
      super(msg);
   }

   public ServiceException(String methodName, String msg) {
      super(methodName , msg);
   }

   public ServiceException(String originalExceptionName, String methodName, String msg) {
      super(originalExceptionName, methodName, msg);
   }
   
}
