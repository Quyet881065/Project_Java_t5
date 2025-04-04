package com.javaweb.utils;

public final class StringUtils {
   public static boolean checkData(String data) {
		//static: Phương thức này thuộc về lớp (class) thay vì đối tượng (instance). 
   	    // Bạn có thể gọi phương thức này mà không cần phải tạo một đối tượng của lớp.
	   if(data != null && !data.equals("")) {
		   return true;
	   }else {		   
		   return false;
	   }
   }
}
