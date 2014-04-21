package com.hotel.backend.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.hotel.backend.entity.Food;
import com.hotel.backend.entity.Market;
 
import com.hotel.backend.entity.View;
import com.hotel.backend.service.HotelFoodService;
import com.hotel.backend.service.HotelMarketService;
import com.hotel.backend.service.HotelViewService;
import com.hotel.backend.service.UserViewService;
import com.hotel.backend.view.UserView;

/**
 * //TODO Description
 * <p>
 * Copyright (c) 2012 Acxiom Corporation. All Rights Reserved.
 * </p>
 * 
 * @author chrzha date: Mar 21, 2014
 *         <p>
 *         Last updated by chrzha date:Mar 21, 2014
 *         </p>
 */
@Controller
@Path("/")
public class AdminController {

	@Autowired
	UserViewService userViewService;

	@Autowired
	HotelViewService hotelViewService;
	
	@Autowired
	HotelMarketService hotelMarketService;
	
	@Autowired
	HotelFoodService hotelFoodService;

	@RequestMapping("/adminHome")
	public ModelAndView goAdminHomePage(String hotelId) {

		return new ModelAndView("adminHome");
	}

	@RequestMapping("/operatorValidate")
	public ModelAndView operatorValidate(HttpServletRequest request,
			HttpServletResponse response, String hotelId) {
		UserView userView = (UserView) request.getSession()
				.getAttribute("user");

		hotelId = userView.getHotelId();

		List<UserView> list = userViewService.getOperatorUserViewList(hotelId);
		return new ModelAndView("operatorValidate", "list", list);
	}

	@RequestMapping("/hotelViewList")
	public ModelAndView hotelViewList(HttpServletRequest request,
			HttpServletResponse response, String hotelId) {

		UserView userView = (UserView) request.getSession()
				.getAttribute("user");

		hotelId = userView.getHotelId();

		List<View> list = hotelViewService.getViewListByHotelId(hotelId);

		return new ModelAndView("hotelViewList", "list", list);
	}

	@RequestMapping("/hotelMktList")
	public ModelAndView hotelMktList(HttpServletRequest request,
			HttpServletResponse response, String hotelId) {

		UserView userView = (UserView) request.getSession()
				.getAttribute("user");

		hotelId = userView.getHotelId();

		List<Market> list = hotelMarketService.getMarketListByHotelId(hotelId);

		return new ModelAndView("hotelMktList", "list", list);
	}
	@RequestMapping("/hotelFoodList")
	public ModelAndView hotelFoodList(HttpServletRequest request,
			HttpServletResponse response, String hotelId) {

		UserView userView = (UserView) request.getSession()
				.getAttribute("user");

		hotelId = userView.getHotelId();

		List<Food> list = hotelFoodService.getFoodListByHotelId(hotelId);

		return new ModelAndView("hotelFoodList", "list", list);
	}
	

	
}