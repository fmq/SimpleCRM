package ar.com.easytech.simplecrm.web.tabmodel;

import java.io.Serializable;

import ar.com.easytech.simplecrm.model.AbstractEntity;

/**
 *
 * @author FMQ
 */
public abstract class BaseTabObject<T extends AbstractEntity> implements Serializable {
    
	private static final long serialVersionUID = 6631809039114498677L;
	protected T entity;
	protected long id;
	protected String tabContent;
	
	public abstract void init();
	public abstract void doRefresh(long id);
	
	public T getEntity() {
		return entity;
	}

	public void setEntity(T entity) {
		this.entity = entity;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTabContent() {
		return tabContent;
	}

	public void setTabContent(String tabContent) {
		this.tabContent = tabContent;
	}
	
}
