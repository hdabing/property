package com.wuye.action;

import java.util.List;

import org.apache.log4j.Logger;

import com.wuye.common.MyBaseAction;
import com.wuye.model.Managers;
import com.wuye.service.IManagersService;
import com.wuye.util.MD5;

public class LoginAction extends MyBaseAction {
	private static final Logger logger = Logger.getLogger(LoginAction.class);

	private String userName;
	private String password;

	private IManagersService managersService;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public IManagersService getManagersService() {
		return managersService;
	}

	public void setManagersService(IManagersService managersService) {
		this.managersService = managersService;
	}

	public String login() {
		List managerList = managersService.findByUserName(userName);

		if (managerList == null || managerList.size() <= 0) {
			addFieldError("loginFail", getText("用户名不正确！"));
			return "loginFail";
		}

		Managers manager = (Managers) managerList.get(0);
		
		if(manager.getUserName().equals(userName)){
			
		}
		
		if (manager.getPassword().equals(MD5.getMD5Code(password))) {
			return "loginSuccess";
		} else {
			addFieldError("loginFail", getText("密码不正确！"));
			return "loginFail";
		}

	}
}
