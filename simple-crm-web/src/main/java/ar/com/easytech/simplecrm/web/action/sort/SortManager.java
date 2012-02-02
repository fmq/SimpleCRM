package ar.com.easytech.simplecrm.web.action.sort;

import java.io.Serializable;
import java.lang.reflect.Method;

import javax.faces.component.UIComponent;
import javax.faces.event.ActionEvent;

import org.richfaces.component.SortOrder;

/**
 * @author Enes Tahan
 * @author FMQ
 */
public abstract class SortManager implements Serializable {

   private static final long serialVersionUID = 4992476755394025941L;

   public abstract  void sortAction(ActionEvent ae);
   
   public void sorter(ActionEvent ae, Object obj) {
      
      String compId = ((UIComponent) ae.getSource()).getId();// i have city
      String setterMethodName = "set" + compId.substring(0, 1).toUpperCase() + compId.substring(1); //now i have   setSort_city
      String getterMethodName = "get" + compId.substring(0, 1).toUpperCase() + compId.substring(1);// //now i have getSort_city
      
      Method[] methods = obj.getClass().getDeclaredMethods();

      for (Method meth : methods) {
         if (!meth.getName().equals(setterMethodName) && meth.getName().startsWith("set") ) {
            // If it receives a SortOrder then it's a sorter setter..
            
            Class[] params = meth.getParameterTypes();
            if (params[0].getCanonicalName().equals("org.richfaces.component.SortOrder"))
            //we are calling all other sortOrder fields and setting them unsorted
               callSetter(meth.getName(), SortOrder.unsorted, obj);
         }
      }
      if (callGetter(getterMethodName, obj).equals(SortOrder.ascending)) {
         callSetter(setterMethodName, SortOrder.descending, obj);
      } else {
         callSetter(setterMethodName, SortOrder.ascending, obj);
      }
   }

   public void resetAllSortAttr(Object obj) {
      Method[] methods = obj.getClass().getDeclaredMethods();

      for (Method meth : methods) {
         if (meth.getName().startsWith("set")) {

            callSetter(meth.getName(), SortOrder.unsorted, obj);
         }
      }
   }

   private void callSetter(String methodName, SortOrder value, Object obj) {
      Class oClass = obj.getClass();
      Method method = null;
      try {
         method = oClass.getMethod(methodName, SortOrder.class);
      } catch (Exception e) {
         e.printStackTrace();
         return;
      }
      try {

         method.invoke(obj, value);

      } catch (Exception e) {
         e.printStackTrace();

      }
   }

   private SortOrder callGetter(String methodName, Object obj) {
      Class oClass = obj.getClass();
      SortOrder sortOrder = null;
      Method method = null;
      try {
         method = oClass.getMethod(methodName, null);
      } catch (Exception e) {
         e.printStackTrace();
         return null;
      }
      try {
         sortOrder = (SortOrder) method.invoke(obj, null);
      } catch (Exception e) {
         e.printStackTrace();
      }
      return sortOrder;
   }
}
