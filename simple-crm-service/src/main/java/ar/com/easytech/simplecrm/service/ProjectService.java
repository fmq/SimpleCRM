package ar.com.easytech.simplecrm.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;

import ar.com.easytech.simplecrm.common.exceptions.ServiceException;
import ar.com.easytech.simplecrm.model.pa.Project;


@Stateless
public class ProjectService {

	private static final Logger logger = Logger.getLogger(ProjectService.class.toString());
	Map<String, Object> params = new HashMap<String, Object>();
	
	@Inject DaoFactory factory;
	
	public long createProject(Project Project) throws ServiceException {
		return factory.getProjectDAO().createProject(Project);
	}
	
	public List<Project> getAllProjects() throws ServiceException {
		return factory.getProjectDAO().getAll();
	}
	
	public List<Project> getProjectsForCustomerId(long customerId) throws ServiceException {
		return factory.getProjectDAO().getForCustomerId(customerId);		
	}
	
	public Project getProjectById(long ProjectId) throws ServiceException {
		return factory.getProjectDAO().getById(ProjectId);
	}
	
	public void updateProject(Project project) {
		factory.getProjectDAO().update(project);
	}
	
	public Project getProjectData(long ProjectId) throws ServiceException {
		Project project = getProjectById(ProjectId);
		project.getContacts().size();
		project.getCustomer();
		project.getNotes().size();
		project.getMilestones().size();
		return project;
	}
}
