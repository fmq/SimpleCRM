package ar.com.easytech.simplecrm.web.tabmodel;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;

import ar.com.easytech.simplecrm.common.exceptions.ServiceException;
import ar.com.easytech.simplecrm.model.pa.Project;
import ar.com.easytech.simplecrm.web.action.sort.ProjectSortingBean;
import ar.com.easytech.simplecrm.web.util.EjbUtil;

/**
 *
 * @author FMQ
 */
public class ProjectTab extends BaseTabObject<Project> implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(OpportunityTab.class.toString());
    
    private Project project;
    private ProjectSortingBean projectSortingBean;
    
    public void doRefresh(long projectId) {
    	init();
        try {
            project = EjbUtil.retrieveProjectService().getProjectById(projectId);
            setEntity(project);
        } catch (ServiceException ex) {
            logger.log(Level.INFO,"Excepcion obteniendo datos de los proyectos: {0}", ex.getCompleteMessage());
        }
    }

    public void init() {
    	tabContent = "project/projectDetailsRG.xhtml";
    }
    /*
     * Getters & Seters
     */

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

	public ProjectSortingBean getProjectSortingBean() {
		return projectSortingBean;
	}

	public void setProjectSortingBean(ProjectSortingBean projectSortingBean) {
		this.projectSortingBean = projectSortingBean;
	}
    
}
