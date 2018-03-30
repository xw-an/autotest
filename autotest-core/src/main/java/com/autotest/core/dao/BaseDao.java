package com.autotest.core.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class BaseDao {

	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	
	public void insert(Class<?> c,Object o) {
		sqlSessionTemplate.insert(c.getName()+".insert", o);
	}

	public void delete(Class<?> c,Object o) {
		sqlSessionTemplate.delete(c.getName()+".delete", o);
	}
	

	public void update(Class<?> c,Object o) {
		sqlSessionTemplate.update(c.getName()+".update", o);
	}
	
	public Object select(String name,Object o) {
		return sqlSessionTemplate.selectOne(name,o);
	}
	
	public Object select(Class<?> c,Object o) {
		return this.select(c.getName()+".select", o);
	}
	
	public Object select(Class<?> c,Map<String,Object> maps) {
		return this.select(c.getName()+".selectByMap", maps);
	}
	
	public List<?> selectList(String name,Object o) {
		return sqlSessionTemplate.selectList(name,o);
	}
	
	public List<?> selectList(Class<?> c,Map<String,Object> maps) {
		return this.selectList(c.getName()+".selectList",maps);
	}
}
