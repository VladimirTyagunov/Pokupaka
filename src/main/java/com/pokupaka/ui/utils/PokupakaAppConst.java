package com.pokupaka.ui.utils;

import org.springframework.data.domain.Sort;

import java.util.Locale;

public class PokupakaAppConst {

	public static final Locale APP_LOCALE = Locale.US;

	public static final String PAGE_ROOT = "";
	public static final String PAGE_PRODUCTS = "products";
	public static final String PAGE_ORDERS = "orders";
	public static final String PAGE_STOREFRONT_EDIT = "storefront/edit";
	public static final String PAGE_DEALS = "deals";
	public static final String PAGE_DEAL_DETAILS = "deal_details";
	public static final String PAGE_USERS = "users";

	public static final String TITLE_MY_ORDERS = "My orders";
	public static final String TITLE_DEALS = "Deals";
	public static final String TITLE_DEAL_DETAILS = "Deal Details";
	public static final String TITLE_USERS = "Users";
	public static final String TITLE_PRODUCTS = "Products";
	public static final String TITLE_LOGOUT = "Logout";
	public static final String TITLE_NOT_FOUND = "Page was not found";
	public static final String TITLE_ACCESS_DENIED = "Access denied";

	public class Labels {
		public static final String PRODUCT = "Product";
		public static final String DEALS = "Deals";
		public static final String QUANTITY = "Quantity";
		public static final String STATUS = "Status";
		public static final String CATEGORY = "Category";
		public static final String PRICE = "Price";
		public static final String ORDER_NUM = "Order №";
		public static final String CONFIRM = "Confirm";
	}

	public static final String[] ORDER_SORT_FIELDS = {"dueDate", "dueTime", "id"};
	public static final Sort.Direction DEFAULT_SORT_DIRECTION = Sort.Direction.ASC;

	public static final String VIEWPORT = "width=device-width, minimum-scale=1, initial-scale=1, user-scalable=yes";

	// Mutable for testing.
	public static int NOTIFICATION_DURATION = 4000;

}
