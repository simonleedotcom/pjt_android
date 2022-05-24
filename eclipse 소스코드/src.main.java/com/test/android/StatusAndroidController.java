package com.test.android;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.test.entity.Status;
import com.test.mapper.BoardMapper;

@RestController
public class StatusAndroidController {

	@Autowired
	BoardMapper mapper;
	
	@GetMapping("/android/statusList")
	List<Status> getStatusList(){
	
	return mapper.selectStatusList();
	
	}
	
	@GetMapping("/android/updateONTemp/{temperature}")
	void getUpdateTemp1(@PathVariable("temperature") int temperature) {
		
		String status = "ON";
		mapper.updateTemp1(temperature, status);
	}
	
	@GetMapping("/android/updateOFFTemp/{temperature}")
	void getUpdateTemp2(@PathVariable("temperature") int temperature) {
		
		String status = "OFF";
		mapper.updateTemp2(temperature, status);
	}
	
}
