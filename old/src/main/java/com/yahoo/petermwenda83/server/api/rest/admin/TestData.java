package com.yahoo.petermwenda83.server.api.rest.admin;

public class TestData {

	public TestData() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		
		String data = "{\"Roll\":\"-13.20123\",\"Pitch\":\"13.21731\",\"Yaw\":\"-0.6401809\"}";
		
		System.out.println("**************");
		System.out.println(data);
		System.out.println("****************");
		
		AdminService adminService = new AdminService();
		
		adminService.putData(data);
		

	}

}
