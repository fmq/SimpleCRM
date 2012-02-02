package ar.com.easytech.simplecrm.model.enums;

/**
 *
 * @author FMQ
 */
public enum CurrencyCode {
   
   ARS("Peso Argentino"), USD("Dolares Americanos");
   
   private String description;

   private CurrencyCode(String description) {
      this.description = description;
   }
   
   public String getDescription() {
      return description;
   }
   
}
