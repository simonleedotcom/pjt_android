package com.test.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.test.entity.Status;

@Mapper
public interface BoardMapper {

	@Select("select status, temperature from tbl_boiler order by status desc")
	public List<Status> selectStatusList();
	
	@Update("update tbl_boiler set temperature = ${temperature} where status = \'${status}\'")
	public void updateTemp1(@Param("temperature") int temperature, @Param("status") String status);
	
	@Update("update tbl_boiler set temperature = ${temperature} where status = \'${status}\'")
	public void updateTemp2(@Param("temperature") int temperature, @Param("status") String status);
	
}
