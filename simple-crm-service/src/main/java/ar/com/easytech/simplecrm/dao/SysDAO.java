package ar.com.easytech.simplecrm.dao;

import ar.com.easytech.simplecrm.model.enums.StatusTypes;
import ar.com.easytech.simplecrm.model.sys.SysUser;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author FMQ
 */
public class SysDAO {
   
   @PersistenceContext(name = "SimpleCRM")
   EntityManager em;

   private CriteriaQuery query;
   private CriteriaBuilder queryBuilder;

   public long createUser(SysUser user) {
       em.persist(user);
       return user.getId();
   }
   
   public void updateUser(SysUser user) {
       em.merge(user);
   }
   
   public void deleteUser(SysUser user) {
       user.setRecordStatus(StatusTypes.DELETED);
       updateUser(user);
   }
   
   public SysUser getUser(String userName) {
       try {
        queryBuilder = em.getCriteriaBuilder();
        query = queryBuilder.createQuery();
        Root<SysUser> object = query.from(SysUser.class);
        query.select(object);
        query.where(queryBuilder.and(queryBuilder.equal(object.get("recordStatus"), StatusTypes.ACTIVE),
                queryBuilder.equal(object.get("userName"), userName)));
        TypedQuery<SysUser> q = em.createQuery(query);
        return q.getSingleResult();
       } catch (NoResultException _ex) {
           return new SysUser();
       }
   }
   
}
