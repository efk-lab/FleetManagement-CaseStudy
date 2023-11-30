package com.mycomp.fleetmanager.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mycomp.fleetmanager.conf.security.UserService;
import com.mycomp.fleetmanager.document.DistributionLog;
import com.mycomp.fleetmanager.mapper.DistributionLogMapper;
import com.mycomp.fleetmanager.model.GetDistributionLogRequest;
import com.mycomp.fleetmanager.model.GetDistributionLogResponse;
import com.mycomp.fleetmanager.repository.DistributionLogRepository;
import com.mycomp.fleetmanager.validator.DistributionLogValidator;


@ExtendWith(MockitoExtension.class)
public class DistributionLogServiceTest {
	
	@Mock
	private DistributionLogRepository distributionLogRepository;

	@Mock
	private UserService userService;
	
	@Mock
	private DistributionLogMapper distributionLogMapper;

	@Mock
	private DistributionLogValidator distributionLogValidator;

	@InjectMocks
	private DistributionLogService distributionLogService;

	@Test
	public void getDistributionLog() throws Exception {
		
		GetDistributionLogRequest getDistributionLogRequest = prepareGetDistributionLogRequest();
		GetDistributionLogResponse getDistributionLogResponseExpected = prepareGetDistributionLogResponse();
		DistributionLog distributionLog = prepareDistributionLog();
	
		doNothing().when(distributionLogValidator).validateGetDistributionLogRequest(getDistributionLogRequest);
		given(distributionLogRepository.findByDistributionId(getDistributionLogRequest.getDistributionId())).willReturn(distributionLog);
		given(distributionLogMapper.toGetDistributionLogResponse(distributionLog)).willReturn(getDistributionLogResponseExpected);

		GetDistributionLogResponse setDistributionLogResponseActual = distributionLogService.getDistributionLog(getDistributionLogRequest);

		assertThat(setDistributionLogResponseActual.getPlate()).isEqualTo(getDistributionLogResponseExpected.getPlate());
		
	}
	
	private GetDistributionLogRequest prepareGetDistributionLogRequest() {
		
		GetDistributionLogRequest getDistributionLogRequest = new GetDistributionLogRequest();
		getDistributionLogRequest.setDistributionId(new ObjectId("62d322ddf9f5e01864bed242"));
		
		return getDistributionLogRequest;
	}
	
	private GetDistributionLogResponse prepareGetDistributionLogResponse() {
		
		GetDistributionLogResponse getDistributionLogResponse = new GetDistributionLogResponse();
		
		getDistributionLogResponse.setPlate("34 TL 34");
		
		return getDistributionLogResponse;
		
	}
	
	private DistributionLog prepareDistributionLog() {
		
		DistributionLog distributionLog = new DistributionLog();
		
		distributionLog.setDistributionId(new ObjectId("62d322ddf9f5e01864bed242"));
		distributionLog.setPlate("34 TL 34");
	
		return distributionLog;
		
	}
}
