package com.springboot.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.springboot.entity.Company;
import com.springboot.entity.Operator;

@Mapper
public interface OperatorDao {

	void insertOperator(Operator operator);

	void updateOperator(Operator operator);

	void deleteOperator(Operator operator);

	Operator findInfoOperator(Map<String, Object> map);

	List<Operator> findListOperator(Map<String, Object> map);

	List<String> findListName(Company company);

}
