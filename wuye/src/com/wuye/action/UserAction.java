package com.wuye.action;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.wuye.common.MyBaseAction;
import com.wuye.model.User;
import com.wuye.service.IUserService;
import com.wuye.util.AppConstants;
import com.wuye.util.PagingTool;
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
	 * 
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
		Integer houseIdInt = 0;
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
		
		//分页
		HttpServletRequest request = this.getRequest();
		PagingTool.paging(request, backCount, start, size);
		
		return "userList";
	}

	/**
	 * 转到修改页面
	 * 
	 * @return
	 */
	public String toUpdate() {
		String id = this.getRequest().getParameter("id");
		User user = userService.findById(Integer.parseInt(id));
		if (user == null) {
			addFieldError(id, getText("该用户不存在！"));
			return "error";
		}
		
		this.getRequest().setAttribute("id", id);
		this.getRequest().setAttribute("user", user);
		this.getRequest().setAttribute("name", user.getName());
		this.getRequest().setAttribute("phone", user.getPhone());
		this.getRequest().setAttribute("mial", user.getMial());
		this.getRequest().setAttribute("houseId", user.getHouseId());
		this.getRequest().setAttribute("description", user.getDescription());
		
		return "userUpdate";
	}

	public String updateData(){
		String id = this.getRequest().getParameter("id");
		String name = this.getRequest().getParameter("name");
		String phone = this.getRequest().getParameter("phone");
		String mial = this.getRequest().getParameter("mial");
		String houseId = this.getRequest().getParameter("houseId");
		String description = this.getRequest().getParameter("description");
		
		this.getRequest().setAttribute("id", id);
		this.getRequest().setAttribute("name", name);
		this.getRequest().setAttribute("phone", phone);
		this.getRequest().setAttribute("mial", mial);
		this.getRequest().setAttribute("houseId", houseId);
		this.getRequest().setAttribute("description", description);
		
		User user = userService.findById(Integer.parseInt(id));
		List userList = userService.findByName(name);
		if(userList != null && userList.size() > 0){
			int flag = 0;
			for (int i = 0; i < userList.size(); i++) {
				User userTemp = (User) userList.get(i);
				if(userTemp.getId() != Integer.parseInt(id)){
					flag++;
				}
			}
			if(flag > 0){
				addFieldError("name", getText("用户"+name+"已存在！"));
				return "error";
			}
		}
		
		if(Tools.isEmptyString(phone) == true){
			addFieldError("phone", getText("电话号码不能为空！"));
			return "error";
		}
		if(Tools.isEmptyString(mial) == true){
			addFieldError("", getText("邮箱不能为空！"));
			return "error";
		}
		
		user.setName(name);
		user.setPhone(phone);
		user.setMial(mial);
		user.setHouseId(Integer.parseInt(houseId));
		user.setDescription(description);
		user.setUpdateTime(new Date());
		
		userService.merge(user);
		
		return "toList";
	}
}
