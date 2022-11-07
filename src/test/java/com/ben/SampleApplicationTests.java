package com.ben;

import com.ben.dto.request.BatteryRequestDto;
import com.ben.service.BatteryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
public class SampleApplicationTests {

    ObjectMapper objectMapper = new ObjectMapper();
    private BatteryService service;

    @Autowired
    private MockMvc mockMvc;

    @Before
    public void setup() {
        service = mock(BatteryService.class);
    }

    @Test
    public void it_should_return_create_battery() throws Exception {
        List<BatteryRequestDto> batteryDtos = createBatteryDtoInput();
        service.savingData(batteryDtos);
        mockMvc.perform(post("/api/v1/batteries")
                .content(objectMapper.writeValueAsString(batteryDtos))
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated());
    }

    @Test
    public void retrieved_battery_and_validate_total_and_average_capacity() throws Exception {
        long postCodeFrom = 0L;
        long postCodeTo = 6L;

        List<BatteryRequestDto> batteryDtos = createBatteryDtoInput();

        MockHttpServletRequestBuilder requestBuilder = get("/api/v1/batteries");
        requestBuilder.param("postcodeFrom", String.valueOf(postCodeFrom));
        requestBuilder.param("postcodeTo", String.valueOf(postCodeTo));
        mockMvc.perform(requestBuilder.contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("batteryDtos[0]").value(batteryDtos.get(0)))
                .andExpect(jsonPath("totalCapacity").value(9))
                .andExpect(jsonPath("averageCapacity").value(4.5));
    }

    private List<BatteryRequestDto> createBatteryDtoInput() {
        List<BatteryRequestDto> batteries = new ArrayList<>();
        batteries.add(new BatteryRequestDto().setName("Bat1").setPostcode(1L).setCapacity(3.3));
        batteries.add(new BatteryRequestDto().setName("New Bat").setPostcode(5L).setCapacity(5.7));
        batteries.add(new BatteryRequestDto().setName("Old Bat").setPostcode(9L).setCapacity(10.7));
        return batteries;
    }

}


