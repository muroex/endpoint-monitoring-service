package cz.applifting.endpointmonitoringservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import cz.applifting.endpointmonitoringservice.entity.MonitoredEndpoint;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class MonitoredEndpointControllerTest {

    private static final String URI = "/api/v1/monitoredEndpoints/";
    private static final String DEFAULT_URL = "http://www.google.com";
    private static final String bearerToken="Bearer 93f39e2f-80de-4033-99ee-249d92736a25";

    @Autowired
    private MockMvc mvc;

    @Test
    public void checkWithoutAuthorization() throws Exception {
        int status = mvc.perform(MockMvcRequestBuilders.get(URI)).andReturn().getResponse().getStatus();
        assertEquals(HttpStatus.UNAUTHORIZED.value(), status);
    }

    @Test
    public void checkWithWrongAuthorization() throws Exception {

        int status = mvc.perform(MockMvcRequestBuilders.get(URI).header("Authorization", bearerToken+1))
                .andReturn().getResponse().getStatus();
        assertEquals(HttpStatus.UNAUTHORIZED.value(), status);
    }

    @Test
    public void checkWithCorrectAuthorization() throws Exception {
        int status = mvc.perform(MockMvcRequestBuilders.get(URI).header("Authorization", bearerToken))
                .andReturn().getResponse().getStatus();
        assertEquals(HttpStatus.OK.value(), status);
    }


    @Test
    public void testAddMonitoredEndpoint() throws Exception {
        MockHttpServletResponse monitoredEndpointResponse = addMonitoredEndpoint();
        assertEquals(monitoredEndpointResponse.getStatus(), HttpStatus.CREATED.value());

        JSONObject responseJson = new JSONObject(monitoredEndpointResponse.getContentAsString());

        Assertions.assertNotEquals("null", responseJson.getString("id"));
        Assertions.assertEquals(DEFAULT_URL, responseJson.getString("url"));
        Assertions.assertEquals("null", responseJson.getString("name"));
        Assertions.assertNotEquals("null", responseJson.getString("dateOfCreation"));
        Assertions.assertEquals(100, responseJson.getInt("monitoredInterval"));

    }

    @Test
    void deleteMonitoredEndpoint() throws Exception {
        String id = new JSONObject(addMonitoredEndpoint().getContentAsString()).getString("id");
        MockHttpServletResponse response = mvc.perform(MockMvcRequestBuilders.delete(URI + id)
                .header("Authorization", bearerToken)).andReturn().getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    public void updateMonitoredEndpoint() throws Exception {
        JSONObject responseJson = new JSONObject(addMonitoredEndpoint().getContentAsString());
        String id = responseJson.getString("id");
        String updatedUrl = "http://www.bing.com";
        String updatedName = "Bing";
        int updatedInterval = 110;

        MonitoredEndpoint monitoredEndpoint = new MonitoredEndpoint();
        monitoredEndpoint.setUrl(updatedUrl);
        monitoredEndpoint.setName(updatedName);
        monitoredEndpoint.setMonitoredInterval(updatedInterval);

        String requestJson = new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(monitoredEndpoint);
        MockHttpServletResponse response = mvc.perform(MockMvcRequestBuilders
                .put(URI + id).contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", bearerToken)
                .content(requestJson)).andReturn().getResponse();

        responseJson = new JSONObject(response.getContentAsString());

        Assertions.assertEquals(id, responseJson.getString("id"));
        Assertions.assertEquals(updatedUrl, responseJson.getString("url"));
        Assertions.assertEquals(updatedName, responseJson.getString("name"));
        Assertions.assertEquals(updatedInterval, responseJson.getInt("monitoredInterval"));
    }

    @Test
    public void updateNotFoundMonitoredEndpoint() throws Exception {
        JSONObject responseJson = new JSONObject(addMonitoredEndpoint().getContentAsString());
        String id = responseJson.getString("id") + 10;
        String updatedUrl = "http://www.bing.com";
        String updatedName = "Bing";
        int updatedInterval = 110;
        String message = "monitored endpoint with ID: " + id + " was not found";

        MonitoredEndpoint monitoredEndpoint = new MonitoredEndpoint();
        monitoredEndpoint.setUrl(updatedUrl);
        monitoredEndpoint.setName(updatedName);
        monitoredEndpoint.setMonitoredInterval(updatedInterval);

        String requestJson = new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(monitoredEndpoint);
        MockHttpServletResponse response = mvc.perform(MockMvcRequestBuilders
                .put(URI + id).contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", bearerToken)
                .content(requestJson)).andReturn().getResponse();

        assertEquals(response.getStatus(), HttpStatus.NOT_FOUND.value());

        responseJson = new JSONObject(response.getContentAsString());

        Assertions.assertEquals(message, responseJson.getString("message"));
    }

    private MockHttpServletResponse addMonitoredEndpoint() throws Exception {
        MonitoredEndpoint monitoredEndpoint = new MonitoredEndpoint();
        monitoredEndpoint.setUrl(DEFAULT_URL);
        monitoredEndpoint.setMonitoredInterval(100);
        String requestJson = new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(monitoredEndpoint);
        return mvc.perform(MockMvcRequestBuilders.post(URI).contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", bearerToken)
                .content(requestJson)).andReturn().getResponse();
    }

}