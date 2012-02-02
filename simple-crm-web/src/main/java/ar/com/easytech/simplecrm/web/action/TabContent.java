package ar.com.easytech.simplecrm.web.action;

import ar.com.easytech.simplecrm.model.AbstractEntity;
import ar.com.easytech.simplecrm.web.enums.TabType;
import ar.com.easytech.simplecrm.web.tabmodel.BaseTabObject;

import java.io.Serializable;
import java.util.logging.Logger;

/**
 *
 * @author FMQ
 */
public class TabContent<T extends BaseTabObject<? extends AbstractEntity>> implements Serializable, Comparable<T> {

    private static final long serialVersionUID = 5639480191519728227L;

	private static final Logger logger = Logger.getLogger(TabContent.class.toString());
    
    private TabType tabType;
    
    private T object;
    private int tabId;
    private String name;
    
    public TabContent(T object, TabType tabType) {
        this.name = object.getClass().getSimpleName() + ":" + object.getEntity().getId().toString();
        this.object = object;
        this.tabType = tabType;
    }
    
    /* Getters & Setters */

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTabId() {
        return tabId;
    }

    public void setTabId(int tabId) {
        this.tabId = tabId;
    }

    public TabType getTabType() {
        return tabType;
    }

    public void setTabType(TabType tabType) {
        this.tabType = tabType;
    }
    
    public String getDisplayName() {
        return object.toString();
    }

    public T getObject() {
        return object;
    }

    public void setObject(T object) {
        this.object = object;
    }

    @Override
    public int compareTo(T t) {
        
        final int DIFFERENT = -1;
        final int EQUAL = 0;

        if ( object == t ) return EQUAL;
        
        if (object.getEntity().getId() == t.getEntity().getId()) return EQUAL;
        
        return DIFFERENT;
        
    }
    
}
