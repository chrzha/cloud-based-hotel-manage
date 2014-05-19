/**
 * 
 */
package com.hotel.backend.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hotel.backend.entity.Traffic;
import com.hotel.backend.entity.User;
import com.hotel.backend.service.HotelTrafficService;
import com.hotel.backend.service.LHotelTrafficService;
import com.hotel.backend.service.UserService;
import com.hotel.backend.utility.Mail;
import com.hotel.backend.view.UserView;

/**
 * @author chrzha
 * 
 */
@Controller
@Path("/")
public class TrafficController {

	@Autowired
	private LHotelTrafficService lHotelTrafficService;

	@Autowired
	private HotelTrafficService hotelTrafficService;
	
	@Autowired
	private UserService userService;

	@RequestMapping("/hotelTrafficList")
	public ModelAndView hotelTrafficList(HttpServletRequest request,
			HttpServletResponse response) {

		UserView userView = (UserView) request.getSession()
				.getAttribute("user");

		String hotelId = userView.getHotelId();

		List<Traffic> list = hotelTrafficService
				.getTrafficListByHotelId(hotelId);

		return new ModelAndView("hotelTrafficList", "list", list);
	}

	@RequestMapping("/addTraffic")
	public @ResponseBody
	String addTraffic(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute("traffic") Traffic traffic) {

		String result = "success";
		String trafficId = "";
		String temp = "";

		UserView userView = (UserView) request.getSession()
				.getAttribute("user");

		String hotelId = userView.getHotelId();

		int size = hotelTrafficService.getAllTrafficList().size();

		if (size == 0) {

			trafficId = "0001";

		} else {

			temp = hotelTrafficService.getAllTrafficList().get(size - 1)
					.getId();
			int total = Integer.parseInt(temp) + 1;

			if (total < 10) {
				trafficId = "000" + total;
			} else if (total >= 10 && total < 100) {
				trafficId = "00" + total;
			} else if (total >= 100 && total < 1000) {
				trafficId = "0" + total;
			} else {
				trafficId = "" + total;
			}
		}


		Map<String, String> map = new HashMap<String, String>();

		map.put("hotelId", hotelId);
		map.put("trafficId", trafficId);

		traffic.setId(trafficId);

		hotelTrafficService.addTraffic(traffic);
		lHotelTrafficService.insertLink(map);
		return result;
	}

	@RequestMapping("/updateTraffic")
	public @ResponseBody
	String updateTraffic(HttpServletRequest request,
			HttpServletResponse response, Traffic traffic) {

		String result = "success";
		hotelTrafficService.updateTraffic(traffic);
		return result;
	}

	@RequestMapping("/deleteTrafficById")
	public @ResponseBody
	String deleteTrafficById(HttpServletRequest request,
			HttpServletResponse response, String trafficId) {

		String result = "success";

	/*	UserView userView = (UserView) request.getSession()
				.getAttribute("user");

		String hotelId = userView.getHotelId();

		Map<String, String> map = new HashMap<String, String>();

		map.put("hotelId", hotelId);
		map.put("trafficId", trafficId);*/

		lHotelTrafficService.deleteLink(trafficId);

		hotelTrafficService.deleteTrafficById(trafficId);
		return result;
	}
	
	//deleteTrafficByIDC
	@RequestMapping("/changeTrafficStatus")
	public @ResponseBody
	String changeTrafficStatus(HttpServletRequest request,
			HttpServletResponse response, String trafficId,String status) {
		
		String result = "success";
		
		if (status.equals("0")) {
			status = "1";
		}else {
			status = "0";
		}

		Map<String, String> map = new HashMap<String, String>();

		map.put("id", trafficId);
		map.put("status", status);
		
		hotelTrafficService.changeStatusById(map);
 
		UserView userView = (UserView) request.getSession()
				.getAttribute("user");

		
		User user = userService.getAdminByTrafficId(trafficId);
		
		if (userView.getRoleId().equals("0001")) {
			//如果是IDC管理员，则发邮件通知酒店，信息未通过审核
			    String smtp = "smtp.163.com";// smtp服务器
			    String from = "15251327856@163.com";// 邮件显示名称
			    String to = user.getEmail();// 收件人的邮件地址，必须是真实地址
			    String copyto = "";// 抄送人邮件地址
			    String subject = "信息审核未通过";// 邮件标题
			    String content = "你好！您所在酒店编号为："+trafficId+"的交通信息未通过审核，已被删除！";// 邮件内容
			    String username = "15251327856";// 发件人真实的账户名
			    String password = "piano0713";// 发件人密码
			    
			    if (Mail.sendAndCc(smtp, from, to, copyto, subject, content, username, password)) {
		           result = "success";
		        } else {
		           result = "error";
		        }
			
		}
		
		 
		return result;
	}

}
