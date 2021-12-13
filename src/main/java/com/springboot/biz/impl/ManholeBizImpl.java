package com.springboot.biz.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.springboot.biz.NoteBiz;
import com.springboot.entity.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.github.pagehelper.PageHelper;
import com.springboot.biz.ManholeBiz;
import com.springboot.biz.PipeBiz;
import com.springboot.dao.ManholeDao;
import com.springboot.util.AppUtils;

@Service
@Transactional
public class ManholeBizImpl implements ManholeBiz {

	@Value("${myfile}")
	private String myfile;

	@Resource
	private ManholeDao manholeDao;
	@Resource
	private PipeBiz pipeBiz;
	@Resource
	private NoteBiz noteBiz;
	private Map<String, Object> map = null;

	public void insertManhole(Manhole manhole) {
		manholeDao.insertManhole(manhole);
	}

	public void updateManhole(Manhole manhole) {
		manholeDao.updateManhole(manhole);
	}

	public void deleteManhole(Manhole manhole) {
		manholeDao.deleteManhole(manhole);
	}

	public Manhole findLastManhole(Project project) {
		PageHelper.startPage(1, 1);
		map = AppUtils.getMap("project", project);
		List<Manhole> manholes = manholeDao.findListManhole(map);
		if (manholes != null && manholes.size() != 0)
			return manholes.get(0);
		else
			return null;
	}

	public Manhole findInfoManhole(int id, User user) {
		map = AppUtils.getMap("id", id, "user", user);
		return manholeDao.findInfoManhole(map);
	}

	public Manhole findInfoManhole(Map<String, Object> map) {
		return manholeDao.findInfoManhole(map);
	}

	public List<Manhole> findListManhole(Project project) {
		map = AppUtils.getMap("project", project);
		return manholeDao.findListManhole(map);
	}

	public List<Manhole> findListManhole(Map<String, Object> map) {
		if (!StringUtils.isEmpty(map.get("page")))
			PageHelper.startPage((int) map.get("page"), 15);
		return manholeDao.findListManhole(map);
	}

	public int replacManhole(Manhole manhole, User user) {
		String path = myfile + "/ItemImage/";
		String path1 = manhole.getPath1();
		String path2 = manhole.getPath2();
		if (!StringUtils.isEmpty(path1) && path1.length() > 40) {
			String name = AppUtils.UUIDCode();
			AppUtils.saveImage(path1, path, name);
			manhole.setPath1(name);
		}
		if (!StringUtils.isEmpty(path2) && path2.length() > 40) {
			String name = AppUtils.UUIDCode();
			AppUtils.saveImage(path2, path, name);
			manhole.setPath2(name);
		}
		int no = 0;
		if (manhole.getId() == 0) {
			this.insertManhole(manhole);
			for (Pipe pipe : manhole.getPipes()) {
				pipe.setNo(no++);
				pipe.setManhole(manhole);
				pipeBiz.insertPipe(pipe);
			}
		} else {
			this.updateManhole(manhole);
			for (Pipe pipe : manhole.getPipes()) {
				pipe.setNo(no++);
				pipe.setManhole(manhole);
				pipeBiz.updatePipe(pipe);
			}
		}
		return manhole.getId();
	}

	public void checkManhole(Manhole manhole, User user) {
		map = AppUtils.getMap("id", manhole.getId());
		Manhole cust = manholeDao.findInfoManhole(map);
		if (StringUtils.isEmpty(cust))
			return;
		StringBuffer context = new StringBuffer("");
		if (!isEquals(cust.getNode(), manhole.getNode()))
			context.append("*.修改Node ref为：" + manhole.getNode() + "\n");
		if (!isEquals(cust.getGridx(), manhole.getGridx()))
			context.append("*.修改Grid ref X为：" + manhole.getGridx() + "\n");
		if (!isEquals(cust.getGridy(), manhole.getGridy()))
			context.append("*.修改Grid ref Y为：" + manhole.getGridy() + "\n");
		if (!isEquals(cust.getAreacode(), manhole.getAreacode()))
			context.append("*.修改Drainage area code为：" + manhole.getAreacode() + "\n");
		if (!isEquals(cust.getSurveyname(), manhole.getSurveyname()))
			context.append("*.修改Survey Name：" + manhole.getSurveyname() + "\n");
		if (!isEquals(cust.getSurveydate(), manhole.getSurveydate()))
			context.append("*.修改Survey Date为：" + manhole.getSurveydate() + "\n");
		if (!isEquals(cust.getProjectno(), manhole.getProjectno()))
			context.append("*.修改Project no为：" + manhole.getProjectno() + "\n");
		if (!isEquals(cust.getWorkorder(), manhole.getWorkorder()))
			context.append("*.修改WO no为：" + manhole.getWorkorder() + "\n");
		if (!isEquals(cust.getManholeid(), manhole.getManholeid()))
			context.append("*.修改IDMS Manhole ID为：" + manhole.getManholeid() + "\n");
		if (!isEquals(cust.getDrainage(), manhole.getDrainage()))
			context.append("*.修改DSD ref为：" + manhole.getDrainage() + "\n");
		if (!isEquals(cust.getLocation(), manhole.getLocation()))
			context.append("*.修改Location为：" + manhole.getLocation() + "\n");
		if (!isEquals(cust.getYearlaid(), manhole.getYearlaid()))
			context.append("*.修改Year laid为：" + manhole.getYearlaid() + "\n");
		if (!isEquals(cust.getStatus(), manhole.getStatus()))
			context.append("*.修改Status为：" + manhole.getStatus() + "\n");
		if (!isEquals(cust.getMethod(), manhole.getMethod()))
			context.append("*.修改Function为：" + manhole.getMethod() + "\n");
		if (!isEquals(cust.getNodetype(), manhole.getNodetype()))
			context.append("*.修改Node type为：" + manhole.getNodetype() + "\n");
		if (!isEquals(cust.getShape(), manhole.getShape()))
			context.append("*.修改Shape为：" + manhole.getShape() + "\n");
		if (!isEquals(cust.getHinge(), manhole.getHinge()))
			context.append("*.修改Hinged为：" + manhole.getHinge() + "\n");
		if (!isEquals(cust.getLocker(), manhole.getLocker()))
			context.append("*.修改Lock为：" + manhole.getLocker() + "\n");
		if (!isEquals(cust.getDuties(), manhole.getDuties()))
			context.append("*.修改Duty为：" + manhole.getDuties() + "\n");
		if (!isEquals(cust.getCover1(), manhole.getCover1()))
			context.append("*.修改Cover size 1为：" + manhole.getCover1() + "\n");
		if (!isEquals(cust.getCover2(), manhole.getCover2()))
			context.append("*.修改Cover size 2为：" + manhole.getCover2() + "\n");
		if (!isEquals(cust.getSide(), manhole.getSide()))
			context.append("*.修改Side entry为：" + manhole.getSide() + "\n");
		if (!isEquals(cust.getCourse(), manhole.getCourse()))
			context.append("*.修改Regular course为：" + manhole.getCourse() + "\n");
		if (!isEquals(cust.getDepths(), manhole.getDepths()))
			context.append("*.修改Depth (mm)为：" + manhole.getDepths() + "\n");
		if (!isEquals(cust.getShaft1(), manhole.getShaft1()))
			context.append("*.修改Shaft size 1为：" + manhole.getShaft1() + "\n");
		if (!isEquals(cust.getShaft2(), manhole.getShaft2()))
			context.append("*.修改Shaft size 2为：" + manhole.getShaft2() + "\n");
		if (!isEquals(cust.getSoffit(), manhole.getSoffit()))
			context.append("*.修改Soffit为：" + manhole.getSoffit() + "\n");
		if (!isEquals(cust.getSteps(), manhole.getSteps()))
			context.append("*.修改Steps为：" + manhole.getSteps() + "\n");
		if (!isEquals(cust.getLadder(), manhole.getLadder()))
			context.append("*.修改Ladders为：" + manhole.getLadder() + "\n");
		if (!isEquals(cust.getLndgs(), manhole.getLndgs()))
			context.append("*.修改LNDGS为：" + manhole.getLndgs() + "\n");
		if (!isEquals(cust.getCsize1(), manhole.getCsize1()))
			context.append("*.修改Chamber size 1为：" + manhole.getCsize1() + "\n");
		if (!isEquals(cust.getCsize2(), manhole.getCsize2()))
			context.append("*.修改Chamber size 2为：" + manhole.getCsize2() + "\n");
		if (!isEquals(cust.getToxic(), manhole.getToxic()))
			context.append("*.修改Toxic atmosphere为：" + manhole.getToxic() + "\n");
		if (!isEquals(cust.getEvidences(), manhole.getEvidences()))
			context.append("*.修改Evidence of vermin为：" + manhole.getEvidences() + "\n");
		if (!isEquals(cust.getConstruct(), manhole.getConstruct()))
			context.append("*.修改Construct code为：" + manhole.getConstruct() + "\n");
		if (!isEquals(cust.getDepthflow(), manhole.getDepthflow()))
			context.append("*.修改Depth of flow (mm)为：" + manhole.getDepthflow() + "\n");
		if (!isEquals(cust.getDepthsilt(), manhole.getDepthsilt()))
			context.append("*.修改Depth of silt (mm)为：" + manhole.getDepthsilt() + "\n");
		if (!isEquals(cust.getHeight(), manhole.getHeight()))
			context.append("*.修改Height surch (mm)为：" + manhole.getHeight() + "\n");
		if (!isEquals(cust.getMdepth(), manhole.getMdepth()))
			context.append("*.修改MH depth (m)为：" + manhole.getMdepth() + "\n");
		if (!isEquals(cust.getMcover(), manhole.getMcover()))
			context.append("*.修改Cover level (mPD)为：" + manhole.getMcover() + "\n");
		if (!isEquals(cust.getCond(), manhole.getCond()))
			context.append("*.修改CCTV COND为：" + manhole.getCond() + "\n");
		if (!isEquals(cust.getCrit(), manhole.getCrit()))
			context.append("*.修改CRITY为：" + manhole.getCrit() + "\n");
		if (!isEquals(cust.getCover(), manhole.getCover()))
			context.append("*.修改Cover为：" + manhole.getCover() + "\n");
		if (!isEquals(cust.getIron(), manhole.getIron()))
			context.append("*.修改Iron / Ladder为：" + manhole.getIron() + "\n");
		if (!isEquals(cust.getShaft(), manhole.getShaft()))
			context.append("*.修改Shaft为：" + manhole.getShaft() + "\n");
		if (!isEquals(cust.getChambers(), manhole.getChambers()))
			context.append("*.修改Chamber为：" + manhole.getChambers() + "\n");
		if (!isEquals(cust.getBenching(), manhole.getBenching()))
			context.append("*.修改Benching为：" + manhole.getBenching() + "\n");
		if (!isEquals(cust.getOther(), manhole.getOther()))
			context.append("*.修改Others为：" + manhole.getOther() + "\n");

		if (!isEquals(cust.getUtil1(), manhole.getUtil1()))
			context.append("*.修改UTR为：" + manhole.getUtil1() + "\n");
		if (!isEquals(cust.getUtil2(), manhole.getUtil2()))
			context.append("*.修改UTL为：" + manhole.getUtil2() + "\n");
		if (!isEquals(cust.getUtil3(), manhole.getUtil3()))
			context.append("*.修改UTGA为：" + manhole.getUtil3() + "\n");
		if (!isEquals(cust.getUtil4(), manhole.getUtil4()))
			context.append("*.修改UTS为：" + manhole.getUtil4() + "\n");
		if (!isEquals(cust.getUtil5(), manhole.getUtil5()))
			context.append("*.修改JETTING为：" + manhole.getUtil5() + "\n");
		if (!isEquals(cust.getUtil6(), manhole.getUtil6()))
			context.append("*.修改ON-SLOPE为：" + manhole.getUtil6() + "\n");

		if (!isEquals(cust.getPhotono1(), manhole.getPhotono1()))
			context.append("*.修改Location photo no.为：" + manhole.getPhotono1() + "\n");
		if (!isEquals(cust.getPhotono2(), manhole.getPhotono2()))
			context.append("*.修改Internal photo no为：" + manhole.getPhotono2() + "\n");
		if (!isEquals(cust.getSlopeno(), manhole.getSlopeno()))
			context.append("*.修改Slope No为：" + manhole.getSlopeno() + "\n");
		if (!isEquals(cust.getRemarks(), manhole.getRemarks()))
			context.append("*.修改Remarks为：" + manhole.getRemarks() + "\n");


		// 保存日志
		if (!StringUtils.isEmpty(context.toString())) {
			Note note = new Note();
			note.setContext(context.toString());
			note.setDate(LocalDate.now() + "");
			note.setUser(user);
			note.setManhole(manhole);
			noteBiz.insertNote(note);
		}
	}

	/**
	 * 判断字符串是否相等
	 */
	private boolean isEquals(String text1, String text2) {
		if (StringUtils.isEmpty(text1) && StringUtils.isEmpty(text2))
			return true;
		text1 = text1 == null ? "" : text1;
		return text1.equals(text2);
	}
}
