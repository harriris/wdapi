package com.github.harriris.wdapi;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.harriris.wdapi.restapi.DisruptionInfoController;
import com.github.harriris.wdapi.services.routes.HslRouteApiService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = {"digitransit.api.key=1234"})
class WdapiApplicationTests {
	@Autowired
	private DisruptionInfoController disruptionInfoController;
	@Autowired
	private HslRouteApiService hslRouteApiService;
	@Autowired
	private WdapiErrorController wdapiErrorController;

	@Test
	void controllersLoad() {
		assertThat(disruptionInfoController).isNotNull();
		assertThat(hslRouteApiService).isNotNull();
		assertThat(wdapiErrorController).isNotNull();
	}

}
