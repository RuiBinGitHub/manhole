package com.springboot.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.biz.ManholeBiz;
import com.springboot.biz.ProjectBiz;
import com.springboot.entity.Manhole;
import com.springboot.entity.Project;
import com.springboot.util.MyHelper;
import com.springboot.util.PDFHelper;
import com.springboot.util.ZipFileHelper;

@RestController
public class DownFileController {

	@Value("${myfile}")
	private String myfile;
	@Resource
	private ProjectBiz projectBiz;
	@Resource
	private ManholeBiz manholeBiz;
	@Resource
	private PDFHelper pdfHelper;

	@RequestMapping(value = "/project/downfile")
	public void downProject(@RequestParam(defaultValue = "0") int id) {
		Project project = projectBiz.findInfoProject(id, null);
		if (StringUtils.isEmpty(project))
			return; // 查询项目为空
		String name = MyHelper.UUIDCode();
		String srcPath = myfile + "/report/";
		String zipPath = myfile + "/compre/";
		File srcFile = new File(srcPath + name + "/");
		srcFile.mkdirs();
		List<Manhole> manholes = manholeBiz.findListManhole(project);
		for (Manhole manhole : manholes)
			pdfHelper.initPDF(manhole, srcFile.getPath(), manhole.getNode());

		HttpServletResponse response = MyHelper.getResponse();
		String fileName = project.getDate() + "_" + project.getName();
		File zipFile = ZipFileHelper.toZip(srcPath + name, zipPath, fileName);
		response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".zip");
		response.setContentType("application/octet-stream");
		try {
			int len = -1;
			byte[] buffer = new byte[1024];
			InputStream fstream = new FileInputStream(zipFile.getPath());
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
		for (File file : srcFile.listFiles())
			file.delete();
		srcFile.delete();
		zipFile.delete();
	}

	@RequestMapping(value = "/manhole/downfile")
	public void downManhole(@RequestParam(defaultValue = "0") int id) {
		Manhole manhole = manholeBiz.findInfoManhole(id, null);
		if (StringUtils.isEmpty(manhole))
			return; // 查询项目为空
		String name = MyHelper.UUIDCode();
		String srcPath = myfile + "/report/";
		File srcFile = new File(srcPath + name + "/");
		srcFile.mkdirs();

		pdfHelper.initPDF(manhole, srcFile.getPath(), manhole.getNode());
		HttpServletResponse response = MyHelper.getResponse();
		File zipFile = new File(srcFile.getPath() + "/" + manhole.getNode() + ".pdf");
		response.setHeader("Content-disposition", "attachment;filename=" + manhole.getNode() + ".pdf");
		response.setContentType("application/octet-stream");
		try {
			int len = -1;
			byte[] buffer = new byte[1024];
			InputStream fstream = new FileInputStream(zipFile.getPath());
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
		zipFile.delete();
		srcFile.delete();
	}
}
