package ar.com.easytech.simplecrm.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author FMQ
 */
@Entity
@Table(name="sys_notes")
public class Note extends AbstractEntity implements Serializable , Cloneable {
   
   private static final long serialVersionUID = 7526472295622776147L;

   public Note() {
   }

   public Note(String note) {
      this.note = note;
   }
   
   private String note;

   public String getNote() {
      return note;
   }

   public void setNote(String note) {
      this.note = note;
   }
   
}
