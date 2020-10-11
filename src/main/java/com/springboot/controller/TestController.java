package com.springboot.controller;

import java.io.File;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.springboot.biz.ItemBiz;
import com.springboot.biz.ManholeBiz;
import com.springboot.biz.PipeBiz;
import com.springboot.biz.ProjectBiz;
import com.springboot.entity.Item;
import com.springboot.entity.Manhole;
import com.springboot.entity.Pipe;
import com.springboot.entity.Project;
import com.springboot.util.MyHelper;

@RestController
public class TestController {

	@Value("${myfile}")
	private String myfile;
	@Resource
	private ProjectBiz projectBiz;
	@Resource
	private ManholeBiz manholeBiz;
	@Resource
	private PipeBiz pipeBiz;
	@Resource
	private ItemBiz itemBiz;

	@RequestMapping(value = "/hello")
	public String hello() {
		return "0000";
	}

	@RequestMapping(value = "/find", method = RequestMethod.POST)
	public String append(Manhole manhole, MultipartFile file) {
		System.out.println(manhole);
		System.out.println(manhole.getNode());
		System.out.println(file.getOriginalFilename());
		return "123";
	}

	@RequestMapping(value = "/test", method = RequestMethod.POST)
	public Map<String, Object> append(Manhole manhole, MultipartFile file1, MultipartFile file2, String[] explain,
			String[] remark, MultipartFile[] files) {
		String path = myfile + "/ItemImage/";
		Project project = new Project();
		project.setId(8);
		String name1 = MyHelper.UUIDCode();
		String name2 = MyHelper.UUIDCode();
		manhole.setPath1(name1);
		manhole.setPath2(name2);
		File dest1 = new File(path + name1 + ".png");
		File dest2 = new File(path + name2 + ".png");
		MyHelper.moveFile(file1, dest1);
		MyHelper.moveFile(file2, dest2);
		manhole.setProject(project);
		manholeBiz.insertManhole(manhole);

		for (int i = 0; i < 10; i++) {
			Pipe pipe = new Pipe();
			pipe.setNo(i);
			pipe.setManhole(manhole);
			pipeBiz.insertPipe(pipe);
		}

		Item item = null;
		for (int i = 0; explain != null && i < explain.length; i++) {
			String name = MyHelper.UUIDCode();
			String photo = manhole.getNode() + "-P0" + (i + 3);
			if (i % 2 == 0) {
				item = new Item();
				item.setPath1(name);
				item.setPhoto1(photo);
				item.setExplain1(explain[i]);
				item.setRemark1(remark[i]);
			} else {
				item.setPath2(name);
				item.setPhoto2(photo);
				item.setExplain2(explain[i]);
				item.setRemark2(remark[i]);
			}
			File dest = new File(path + name + ".png");
			MyHelper.moveFile(files[i], dest);
			if (i % 2 == 1 || i == explain.length - 1) {
				item.setManhole(manhole);
				itemBiz.insertItem(item);
			}
		}
		Map<String, Object> map = MyHelper.getMap("id", manhole.getId(), "node", manhole.getNode());
		return map;
	}

	/** type：O表示出水 */
	@RequestMapping(value = "/appendpipe", method = RequestMethod.POST)
	public int abcd(int id, Pipe pipe, String type) {
		Manhole manhole = manholeBiz.findInfoManhole(id, null);
		List<Pipe> pipes = pipeBiz.findListPipe(manhole);
		int index = 0;
		if ("O".equals(type))
			index = 8;
		for (int i = index; i < 10; i++) {
			Pipe temp = pipes.get(i);
			if (StringUtils.isEmpty(temp.getUpstream())) {
				pipe.setId(temp.getId());
				pipe.setNo(i);
				break;
			}
		}
		pipe.setManhole(manhole);
		pipeBiz.updatePipe(pipe);
		return 0;
	}
}
