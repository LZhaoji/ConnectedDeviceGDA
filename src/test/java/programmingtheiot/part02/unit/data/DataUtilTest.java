/**
 * This class is part of the Programming the Internet of Things
 * project, and is available via the MIT License, which can be
 * found in the LICENSE file at the top level of this repository.
 * 
 * Copyright (c) 2020 by Andrew D. King
 */ 

package programmingtheiot.part02.unit.data;

import static org.junit.Assert.*;

import java.util.logging.Logger;

import org.junit.Test;

import programmingtheiot.data.*;

/**
 * This test case class contains very basic unit tests for
 * DataUtil. It should not be considered complete,
 * but serve as a starting point for the student implementing
 * additional functionality within their Programming the IoT
 * environment.
 *
 */
public class DataUtilTest
{
	// static
	
	public static final String DEFAULT_NAME = "DataUtilTestName";
	public static final String DEFAULT_LOCATION = "DataUtilTestLocation";
	public static final int DEFAULT_STATUS = 1;
	public static final int DEFAULT_CMD = 1;
	public static final float DEFAULT_VAL = 12.5f;
	
	private static final Logger _Logger =
		Logger.getLogger(DataUtilTest.class.getName());
	
	// member var's
	
	
	// test setup methods
	
	
	// test methods
	
	@Test
	public void testActuatorDataToJsonAndBack()
	{
		DataUtil dataUtil = DataUtil.getInstance();
		
		ActuatorData data = new ActuatorData();
		data.setName(DEFAULT_NAME);
		data.setStatusCode(DEFAULT_STATUS);
		data.setCommand(DEFAULT_CMD);
		data.setValue(DEFAULT_VAL);
		
		String jsonData = dataUtil.actuatorDataToJson(data);
		
		assertNotNull(jsonData);
				
		ActuatorData data2 = dataUtil.jsonToActuatorData(jsonData);
		
		assertEquals(data.getName(), data2.getName());
		assertTrue(data.getStatusCode() == data2.getStatusCode());
		assertTrue(data.getCommand() == data2.getCommand());
		assertTrue(data.getValue() == data2.getValue());
	}
	
	@Test
	public void testSensorDataToJsonAndBack()
	{
		DataUtil dataUtil = DataUtil.getInstance();
		
		SensorData data = new SensorData();
		data.setName(DEFAULT_NAME);
		data.setStatusCode(DEFAULT_STATUS);
		data.setValue(DEFAULT_VAL);
		
		String jsonData = dataUtil.sensorDataToJson(data);
		
		assertNotNull(jsonData);
		
		SensorData data2 = dataUtil.jsonToSensorData(jsonData);
		
		assertEquals(data.getName(), data2.getName());
		assertTrue(data.getStatusCode() == data2.getStatusCode());
		assertTrue(data.getValue() == data2.getValue());
	}
	
	@Test
	public void testSystemPerformanceDatatoJsonAndBack()
	{
		DataUtil dataUtil = DataUtil.getInstance();
		
		SystemPerformanceData data = new SystemPerformanceData();
		data.setName(DEFAULT_NAME);
		data.setStatusCode(DEFAULT_STATUS);
		data.setCpuUtil(DEFAULT_VAL);
		data.setDiskUtil(DEFAULT_VAL);
		data.setMemUtil(DEFAULT_VAL);
		
		String jsonData = dataUtil.systemPerformanceDataToJson(data);
		
		assertNotNull(jsonData);
		
		SystemPerformanceData data2 = dataUtil.jsonToSystemPerformanceData(jsonData);
		
		assertEquals(data.getName(), data2.getName());
		assertTrue(data.getStatusCode() == data2.getStatusCode());
		assertTrue(data.getCpuUtil() == data2.getCpuUtil());
		assertTrue(data.getDiskUtil() == data2.getDiskUtil());
		assertTrue(data.getMemUtil() == data2.getMemUtil());
	}
	
	@Test
	public void testSystemStateDatatoJsonAndBack()
	{
		DataUtil dataUtil = DataUtil.getInstance();
		
		SystemStateData data = new SystemStateData();
		data.setName(DEFAULT_NAME);
		data.setStatusCode(DEFAULT_STATUS);
		data.setCommand(DEFAULT_CMD);
		
		String jsonData = dataUtil.systemStateDataToJson(data);
		
		assertNotNull(jsonData);
		
		SystemStateData data2 = dataUtil.jsonToSystemStateData(jsonData);
		
		assertEquals(data.getName(), data2.getName());
		assertTrue(data.getStatusCode() == data2.getStatusCode());
		assertTrue(data.getCommand() == data2.getCommand());
	}
	
}
