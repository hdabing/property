package com.wuye.action;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.wuye.common.MyBaseAction;
import com.wuye.service.IUserService;
import com.wuye.util.AppConstants;
import com.wuye.util.Tools;

public class UserAction extends MyBaseAction {
	private static final Logger logger = Logger.getLogger(UserAction.class);

	private IUserService userService;
	private List backList;
	private String dateStart;
	private String dateEnd;

	public IUserService getUserService() {
		return userService;
	}

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	public List getBackList() {
		return backList;
	}

	public void setBackList(List backList) {
		this.backList = backList;
	}

	public String getDateStart() {
		return dateStart;
	}

	public void setDateStart(String dateStart) {
		this.dateStart = dateStart;
	}

	public String getDateEnd() {
		return dateEnd;
	}

	public void setDateEnd(String dateEnd) {
		this.dateEnd = dateEnd;
	}
	
	/**
	 * 查询用户
	 * @return
	 */
	public String queryData() {
		Date startDate = null;
		Date endDate = null;
		try {
			if (dateStart != null && dateStart.length() > 0) {
				startDate = Tools.STRING_TO_DATE(dateStart);
			}
		} catch (Exception e) {
			startDate = new Date();
		}
		try {
			if (dateEnd != null && dateEnd.length() > 0) {
				endDate = Tools.STRING_TO_DATE(dateEnd);
				endDate = Tools.GET_TOMORROW(endDate);
			}
		} catch (Exception e) {
			endDate = new Date();
		}
		String name = this.getRequest().getParameter("name");
		String houseId = this.getRequest().getParameter("houseId");
		Integer houseIdInt = -1;
		try {
			houseIdInt = Integer.parseInt(houseId);
		} catch (Exception e) {
			logger.error("houseId类型转换出错", e);
		}
		
		int start = 0;
		try {
			start = Integer.parseInt(this.getRequest().getParameter("start"));
		} catch (Exception e) {
			// TODO: handle exception
		}
		int size = AppConstants.DATA_SIZE;
		backList = userService.findByParam(name, houseIdInt, startDate,
				endDate, start, size);
		int backCount = userService.findByCount(name, houseIdInt, startDate,
				endDate);
		
		this.getRequest().setAttribute("name", name);
		this.getRequest().setAttribute("houseId", houseId);

		return "userList";
	}

}
