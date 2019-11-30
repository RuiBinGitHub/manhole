package com.springboot.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.biz.ManholeBiz;
import com.springboot.entity.Manhole;
import com.springboot.util.MyHelper;
import com.springboot.util.HelperPDF;

@RestController
public class HelperController {

	@Value("${myfile}")
	private String myfile;
	@Resource
	private ManholeBiz manholeBiz;
	@Resource
	private HelperPDF helperPDF;

	@RequestMapping(value = "/downfile")
	public void findUserList(@RequestParam(defaultValue = "0") int id) {
		Manhole manhole = manholeBiz.findInfoManhole(id, null);
		if (StringUtils.isEmpty(manhole))
			return; // 查询项目为空
		HttpServletResponse response = MyHelper.getResponse();
		response.setHeader("Content-disposition", "attachment;filename=manhole.pdf");
		response.setContentType("application/octet-stream");

		String name = MyHelper.UUIDCode();
		helperPDF.initPDF(manhole, myfile + "report/", name);
		File file = new File(myfile + "report/" + name + ".pdf");
		try {
			int len = -1;
			byte[] buffer = new byte[1024];
			InputStream fstream = new FileInputStream(file.getPath());
			InputStream bstream = new BufferedInputStream(fstream);
			OutputStream outputStream = response.getOutputStream();
			while ((len = bstream.read(buffer)) > 0) {
				outputStream.write(buffer, 0, len);
				outputStream.flush();
			}
			bstream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
