package com.springboot.util;

import javax.annotation.Resource;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.springboot.biz.ItemBiz;
import com.springboot.biz.ManholeBiz;
import com.springboot.entity.Manhole;

@Component
@Transactional
public class ItemHelper {

	@Value(value = "${myfile}")
	private String myfile;

	@Resource
	private ManholeBiz manholeBiz;
	@Resource
	private ItemBiz itemBiz;

	public boolean ItemMode(Manhole manhole, MultipartFile[] files) {

		XSSFWorkbook workbook = null;
		String type = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
		for (int i = 0; files != null && i < files.length; i++) {
			if (type.equals(files[i].getContentType())) {
				workbook = MyHelper.getWorkbook(files[i]);
				break;
			}
		}
		if (workbook == null)
			return false;
		XSSFSheet sheet = workbook.getSheetAt(0);
		for (int i = 1; i < sheet.getLastRowNum() + 1; i++) {
			XSSFRow row = sheet.getRow(i);
			
		}
		
		return true;
	}

}
