package com.springboot.util;

import java.io.File;
import java.util.List;

import javax.annotation.Resource;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.springboot.biz.ItemBiz;
import com.springboot.biz.ManholeBiz;
import com.springboot.entity.Item;
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
				workbook = AppUtils.getWorkbook(files[i]);
				break;
			}
		}
		if (workbook == null)
			return false;
		Item item = null;
		List<Item> items = itemBiz.findListItem(manhole);
		XSSFSheet sheet = workbook.getSheetAt(0);
		for (int i = 1; i < sheet.getLastRowNum() + 1; i++) {
			XSSFRow row = sheet.getRow(i);
			if (i % 2 != 0) {
				item = new Item();
				item.setNo(items.size() + i / 2);
				item.setPhoto1(row.getCell(1).getStringCellValue());
				item.setLocation1(row.getCell(2).getStringCellValue());
				item.setExplain1(row.getCell(3).getStringCellValue());
				item.setRemark1(row.getCell(4).getStringCellValue());
				item.setPath1(getPath(item.getPhoto1(), files));
			} else {
				item.setPhoto2(row.getCell(1).getStringCellValue());
				item.setLocation2(row.getCell(2).getStringCellValue());
				item.setExplain2(row.getCell(3).getStringCellValue());
				item.setRemark2(row.getCell(4).getStringCellValue());
				item.setPath2(getPath(item.getPhoto2(), files));
			}
			if (i % 2 == 0 || i == sheet.getLastRowNum()) {
				item.setManhole(manhole);
				itemBiz.insertItem(item);
			}
			System.out.println("--");
		}
		return true;
	}

	private String getPath(String name, MultipartFile[] files) {
		if (StringUtils.isEmpty(name))
			return "";
		for (int i = 0; files != null && i < files.length; i++) {
			System.out.println(files[i].getOriginalFilename());
			if (files[i].getOriginalFilename().contains(name)) {
				String path = AppUtils.UUIDCode();
				File file = new File(myfile + "/ItemImage/" + name + ".png");
				AppUtils.moveFile(files[i], file);
				return path;
			}
		}
		return "";
	}

}
