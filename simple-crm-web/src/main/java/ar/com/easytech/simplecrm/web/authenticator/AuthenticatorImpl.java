package ar.com.easytech.simplecrm.web.authenticator;

import ar.com.easytech.simplecrm.dao.SysDAO;
import ar.com.easytech.simplecrm.model.sys.SysUser;
import ar.com.easytech.simplecrm.web.action.SessionInfo;

import java.io.IOException;
import java.io.Serializable;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;
import org.jboss.seam.security.Authenticator;
import org.jboss.seam.security.BaseAuthenticator;
import org.jboss.seam.security.Credentials;
import org.jboss.seam.security.Identity;
import org.jboss.solder.logging.Logger;

/**
 *
 * @author FMQ
 */
@Named
@RequestScoped
@WebFilter("/view")
public class AuthenticatorImpl extends BaseAuthenticator implements Authenticator, Filter, Serializable {

    private static final long serialVersionUID = -2564031884483676327L;
    @Inject private SysDAO sysService;
    @Inject private Credentials credentials;
    @Inject private Identity identity;
    @Inject private SessionInfo sessionInfo;
    
    private boolean outdatedPass = false;
    private SysUser sysUser;
    
    @Override
    public void authenticate() {
        //
    	sysUser = sysService.getUser(credentials.getUsername());
        if (sysUser == null) {
            setStatus(AuthenticationStatus.FAILURE);
        } else {
            setStatus(AuthenticationStatus.SUCCESS);
            setUser(new SiteUser(sysUser));
            sessionInfo.setUser(sysUser);
        }
    }

    public boolean isOutdatedPass() {
        return outdatedPass;
    }

    public void setOutdatedPass(boolean outdatedPass) {
        this.outdatedPass = outdatedPass;
    }

    @Override
    public void init(FilterConfig fc) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (identity.isLoggedIn()) {
            chain.doFilter(request, response);
        } else {
            ((HttpServletResponse) response).sendRedirect("/simple-crm/login.jsf");
        }
    }

    @Override
    public void destroy() {
    }

    public SysUser getSysUser() {
        return sysUser;
    }

    public void setSysUser(SysUser sysUser) {
        this.sysUser = sysUser;
    }
    
}
