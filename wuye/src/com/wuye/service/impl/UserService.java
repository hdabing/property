package com.wuye.service.impl;

import java.util.Date;
import java.util.List;

import com.wuye.dao.IUserDAO;
import com.wuye.model.User;
import com.wuye.service.IUserService;

public class UserService implements IUserService {
	
	private IUserDAO userDAO;
	
	public IUserDAO getUserDAO() {
		return userDAO;
	}

	public void setUserDAO(IUserDAO userDAO) {
		this.userDAO = userDAO;
	}

	public void attachClean(User instance) {
		// TODO Auto-generated method stub
		userDAO.attachClean(instance);
	}

	public void attachDirty(User instance) {
		// TODO Auto-generated method stub
		userDAO.attachDirty(instance);
	}

	public void delete(User persistentInstance) {
		// TODO Auto-generated method stub
		userDAO.delete(persistentInstance);
	}

	public List findAll() {
		// TODO Auto-generated method stub
		return userDAO.findAll();
	}

	public List findByDescription(Object description) {
		// TODO Auto-generated method stub
		return userDAO.findByDescription(description);
	}

	public List findByExample(User instance) {
		// TODO Auto-generated method stub
		return userDAO.findByExample(instance);
	}

	public List findByHouseId(Object houseId) {
		// TODO Auto-generated method stub
		return userDAO.findByHouseId(houseId);
	}

	public User findById(Integer id) {
		// TODO Auto-generated method stub
		return userDAO.findById(id);
	}

	public List findByMail(Object mail) {
		// TODO Auto-generated method stub
		return userDAO.findByMail(mail);
	}

	public List findByName(Object name) {
		// TODO Auto-generated method stub
		return userDAO.findByName(name);
	}

	public List findByPassword(Object password) {
		// TODO Auto-generated method stub
		return userDAO.findByPassword(password);
	}

	public List findByPhone(Object phone) {
		// TODO Auto-generated method stub
		return userDAO.findByPhone(phone);
	}

	public List findByProperty(String propertyName, Object value) {
		// TODO Auto-generated method stub
		return userDAO.findByProperty(propertyName, value);
	}

	public User merge(User detachedInstance) {
		// TODO Auto-generated method stub
		return userDAO.merge(detachedInstance);
	}

	public void save(User transientInstance) {
		// TODO Auto-generated method stub
		userDAO.save(transientInstance);
	}

	public int findCount(String name, String userHouse, Date startDate,
			Date endDate) {
		// TODO Auto-generated method stub
		return userDAO.findCount(name, userHouse, startDate, endDate);
	}

	public List findByParam(String name, String userHouse, Date startDate,
			Date endDate, int start, int size) {
		// TODO Auto-generated method stub
		return userDAO.findByParam(name, userHouse, startDate, endDate, start, size);
	}

	public List findByUserName(Object userName) {
		// TODO Auto-generated method stub
		return userDAO.findByUserName(userName);
	}

	public List findByUserHouse(Object userHouse) {
		// TODO Auto-generated method stub
		return userDAO.findByUserHouse(userHouse);
	}

}
