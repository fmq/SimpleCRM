package ar.com.easytech.simplecrm.web.action;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.solder.logging.Log;
import org.jboss.solder.logging.Logger;

import ar.com.easytech.simplecrm.ifaces.CurrentUser;
import ar.com.easytech.simplecrm.model.sys.SysUser;
import ar.com.easytech.simplecrm.web.authenticator.AuthenticatorImpl;

@Named
@SessionScoped
public class SessionInfo implements Serializable {

	private static final long serialVersionUID = -8596658065801452359L;

	private SysUser user;
	
	@Inject private Logger logger;
	
	@Produces @CurrentUser
	public SysUser getUser() {
		logger.infov("Getting user to {0} with id {1}",user.getUserName(), user.getId());
		return user;
	}

	public void setUser(SysUser user) {
		this.user = user;
	}

}
