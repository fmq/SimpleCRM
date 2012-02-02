package ar.com.easytech.simplecrm.web.action;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.solder.logging.Logger;



/**
 *
 * @author FMQ
 */
@Named
@SessionScoped
public class TabsBean implements Serializable {
    
    private static final long serialVersionUID = 1233212234234231235L;
    
    @Inject Logger logger;
    
    int tabCount = 0;
    
    private List<TabContent> tabs = new ArrayList<TabContent>();
    private String activeItem = "mainTab";

    public void addTab(TabContent tab) {
        boolean doAdd = true;
        for (TabContent aTab : tabs) {
        	if ( aTab.getTabType().equals(tab.getTabType()) &&
        			aTab.getObject().getEntity().getId() == tab.getObject().getEntity().getId()) {
                doAdd = false;
                break;
            }
        }
        logger.infov("Tab type is {0} and object class is {1}",tab.getTabType(), tab.getObject().getClass().getSimpleName());
        if (doAdd) {
            tab.setTabId(tabCount++);
            tabs.add(tab);
        }
        activeItem = tab.getName();
    }
    
    public void removeTab(TabContent tab) {
        tabs.remove(tab);
        tabCount--;
    }
    
    public int getTabCount() {
        return tabCount;
    }
    
    public void refreshData(TabContent tab) {
    	tab.getObject().doRefresh(tab.getObject().getId());
    }
    
    /* Getters & Setters */
    public List<TabContent> getTabs() {
        return tabs;
    }

    public void setTabs(List<TabContent> tabs) {
        this.tabs = tabs;
    }

    public String getActiveItem() {
        return activeItem;
    }

    public void setActiveItem(String activeItem) {
        this.activeItem = activeItem;
    }
    
}
