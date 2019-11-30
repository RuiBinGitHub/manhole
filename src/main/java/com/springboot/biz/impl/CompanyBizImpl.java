package com.springboot.biz.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.springboot.biz.CompanyBiz;
import com.springboot.dao.CompanyDao;
import com.springboot.entity.Company;
import com.springboot.util.MyHelper;

@Service
@Transactional
public class CompanyBizImpl implements CompanyBiz {

	@Resource
	private CompanyDao companyDao;

	private Map<String, Object> map = null;

	public void insertCompany(Company company) {
		companyDao.insertCompany(company);
	}

	public void updateCompany(Company company) {
		companyDao.updateCompany(company);
	}

	public void deleteCompany(Company company) {
		companyDao.deleteCompany(company);
	}

	public Company findInfoCompany(int id) {
		map = MyHelper.getMap("id", id);
		return companyDao.findInfoCompany(map);
	}

	public Company findInfoCompany(Map<String, Object> map) {
		return companyDao.findInfoCompany(map);
	}

	public PageInfo<Company> findListCompany(Map<String, Object> map) {
		if (!StringUtils.isEmpty(map.get("page")))
			PageHelper.startPage((int) map.get("page"), 15);
		if (!StringUtils.isEmpty(map.get("name")))
			map.put("name", "%" + map.get("name") + "%");
		List<Company> companys = companyDao.findListCompany(map);
		PageInfo<Company> info = new PageInfo<Company>(companys);
		return info;
	}

	public int appendCompany(Company company) {
		company.setDate(MyHelper.getDate(null));
		companyDao.insertCompany(company);
		return company.getId();
	}

	public int repeatCompany(Company company) {
		company.setDate(MyHelper.getDate(null));
		companyDao.updateCompany(company);
		return company.getId();
	}

}
