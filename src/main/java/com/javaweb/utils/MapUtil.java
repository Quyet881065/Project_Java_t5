package com.javaweb.utils;

import java.util.Map;

public class MapUtil {
    public static<T> T getObject(Map<String, Object> params , String key , Class<T> tClass) {
    // Đây là một phương thức generic (dùng <T>) có thể trả về bất kỳ kiểu dữ liệu nào bạn muốn.
    // params: một Map chứa các giá trị key-value.
    // key: khóa để lấy giá trị từ Map.
    // tClass: class đại diện cho kiểu dữ liệu bạn muốn ép về
    	
       Object obj = params.getOrDefault(key, null);
    // Lấy giá trị tương ứng với key trong params.
    // Nếu không có thì trả về null.
       
       if(obj != null) { // Chỉ xử lý tiếp nếu giá trị lấy được không phải là null.
    	   if(tClass.getTypeName().equals("java.lang.Long")) {//Nếu tClass là Long.class, thì chuyển đổi obj sang Long.
    		   obj = !"".equals(obj) ? Long.valueOf(obj.toString()) : null ;
    		   //So sánh chuỗi như obj != "" nên được thay bằng !obj.equals("") để chính xác hơn.
    	   }else if(tClass.getTypeName().equals("java.lang.Float")) { // nếu kiểu mong muốn là Float, thì ép kiểu obj thành Float.
    		   obj = !"".equals(obj) ? Float.valueOf(obj.toString()) : null ;
    	   }else if(tClass.getTypeName().equals("java.lang.String")) {
    		   obj = obj != "" ? obj.toString() : null ;
    	   }
    	   return tClass.cast(obj);
    	   // Ép kiểu obj về kiểu T và trả về.
       }
    	return null;
    	// Nếu obj là null ngay từ đầu, thì trả null.
    }
}
