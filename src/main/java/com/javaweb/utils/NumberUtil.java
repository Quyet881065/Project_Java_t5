package com.javaweb.utils;

public class NumberUtil {
	public static boolean isNumber(String value) {
		// Phương thức tĩnh (static), nghĩa là có thể gọi mà không cần khởi tạo đối
		// tượng NumberUtil.
		// Trả về kiểu boolean, xác định xem value có phải là số hay không.
		if (value == null)
			return false;
		try {
			Long number = Long.parseLong(value);
			// Thử chuyển đổi value thành kiểu Long bằng Long.parseLong(value).

		} catch (Exception e) {
			return false;
		}
		return true;
	}
}
