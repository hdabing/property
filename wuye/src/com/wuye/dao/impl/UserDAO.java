package com.wuye.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.wuye.dao.IUserDAO;
import com.wuye.model.User;
import com.wuye.util.Tools;

/**
 * A data access object (DAO) providing persistence and search support for User
 * entities. Transaction control of the save(), update() and delete() operations
 * can directly support Spring container-managed transactions or they can be
 * augmented to handle user-managed Spring transactions. Each of these methods
 * provides additional information for how to configure it for the desired type
 * of transaction control.
 * 
 * @see com.wuye.dao.impl.User
 * @author MyEclipse Persistence Tools
 */

public class UserDAO extends HibernateDaoSupport implements IUserDAO {
	private static final Logger log = LoggerFactory.getLogger(UserDAO.class);
	// property constants
	public static final String NAME = "name";
	public static final String PASSWORD = "password";
	public static final String PHONE = "phone";
	public static final String MIAL = "mial";
	public static final String HOUSE_ID = "houseId";
	public static final String DESCRIPTION = "description";

	protected void initDao() {
		// do nothing
	}

	public void save(User transientInstance) {
		log.debug("saving User instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(User persistentInstance) {
		log.debug("deleting User instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public User findById(java.lang.Integer id) {
		log.debug("getting User instance with id: " + id);
		try {
			User instance = (User) getHibernateTemplate().get(
					"com.wuye.model.User", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(User instance) {
		log.debug("finding User instance by example");
		try {
			List results = getHibernateTemplate().findByExample(instance);
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding User instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from User as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByName(Object name) {
		return findByProperty(NAME, name);
	}

	public List findByPassword(Object password) {
		return findByProperty(PASSWORD, password);
	}

	public List findByPhone(Object phone) {
		return findByProperty(PHONE, phone);
	}

	public List findByMial(Object mial) {
		return findByProperty(MIAL, mial);
	}

	public List findByHouseId(Object houseId) {
		return findByProperty(HOUSE_ID, houseId);
	}

	public List findByDescription(Object description) {
		return findByProperty(DESCRIPTION, description);
	}

	public List findAll() {
		log.debug("finding all User instances");
		try {
			String queryString = "from User";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public User merge(User detachedInstance) {
		log.debug("merging User instance");
		try {
			User result = (User) getHibernateTemplate().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(User instance) {
		log.debug("attaching dirty User instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(User instance) {
		log.debug("attaching clean User instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static UserDAO getFromApplicationContext(ApplicationContext ctx) {
		return (UserDAO) ctx.getBean("UserDAO");
	}

	/*
	 * 按参数查询用户 (non-Javadoc)
	 * 
	 * @see com.wuye.dao.IUserDAO#findByParam(java.lang.String,
	 * java.lang.Integer, java.util.Date, java.util.Date, int, int)
	 */
	public List findByParam(String name, Integer houseId, Date startDate,
			Date endDate, int start, int size) {
		// TODO Auto-generated method stub
		List list = new ArrayList();
		try {
			Session session = this.getHibernateTemplate().getSessionFactory()
					.getCurrentSession();
			String queryHQL = "from User u where 1=1";
			if (Tools.isEmptyString(name) == false) {
				queryHQL += " and u.name like :name";
			}
			if (houseId > 0 && Tools.isEmptyString(String.valueOf(houseId))) {
				queryHQL += " and u.house.id =:houseId";
			}
			if (startDate != null) {
				queryHQL += " and u.createTime >= :startDate";
			}
			if (endDate != null) {
				queryHQL += " and u.updateTime <= :endDate";
			}

			queryHQL += " order by u.id asc";
			Query query = session.createQuery(queryHQL);

			if (Tools.isEmptyString(name) == false) {
				query.setParameter("name", "%" + name + "%");
			}
			if (houseId > 0 && Tools.isEmptyString(String.valueOf(houseId))) {
				query.setParameter("houseId", houseId);
			}
			if (startDate != null) {
				query.setParameter("startDate", startDate);
			}
			if (endDate != null) {
				query.setParameter("endDate", endDate);
			}
			query.setFirstResult(start);
			query.setMaxResults(size);
			list = query.list();

		} catch (Exception e) {
			// TODO: handle exception
			log.error("按参数查询用户出错，原因:", e);
		}
		return list;
	}

	/*
	 * 按参数查询用户总计路数 (non-Javadoc)
	 * 
	 * @see com.wuye.dao.IUserDAO#findByCount(java.lang.String,
	 * java.lang.Integer, java.util.Date, java.util.Date)
	 */
	public int findByCount(String name, Integer houseId, Date startDate,
			Date endDate) {
		// TODO Auto-generated method stub
		List list = new ArrayList();
		int countNum = 0;
		try {
			Session session = this.getHibernateTemplate().getSessionFactory()
					.getCurrentSession();
			String queryHQL = "select count(u.id) from User u where 1=1";
			if (Tools.isEmptyString(name) == false) {
				queryHQL += " and u.name like :name";
			}
			if (houseId > 0 && Tools.isEmptyString(String.valueOf(houseId))) {
				queryHQL += " and u.house.id =:houseId";
			}
			if (startDate != null) {
				queryHQL += " and u.createTime >= :startDate";
			}
			if (endDate != null) {
				queryHQL += " and u.updateTime <= :endDate";
			}

			queryHQL += " order by u.id asc";
			Query query = session.createQuery(queryHQL);

			if (Tools.isEmptyString(name) == false) {
				query.setParameter("name", "%" + name + "%");
			}
			if (houseId > 0 && Tools.isEmptyString(String.valueOf(houseId))) {
				query.setParameter("houseId", houseId);
			}
			if (startDate != null) {
				query.setParameter("startDate", startDate);
			}
			if (endDate != null) {
				query.setParameter("endDate", endDate);
			}
			list = query.list();

		} catch (Exception e) {
			// TODO: handle exception
			log.error("按参数查询用户总计路数出错，原因:", e);
		}
		if (list != null && list.size() > 0) {
			countNum = Integer.parseInt(list.get(0).toString());
		}

		return countNum;
	}

}