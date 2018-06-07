package com.example.demo;

import org.json.JSONObject;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.apache.commons.codec.binary.Base64;

@RunWith(SpringRunner.class)
@EnableZuulProxy
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class DemoApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void postTest() throws Exception {
        String test = "test";
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8081/api/image")
                .content("{\"image\":"+test+"}")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andReturn();
        String responseText = result.getResponse().getContentAsString();
        JSONObject jsonObj = new JSONObject(responseText);
        String testValue = (String) jsonObj.get("image");
        //verify test value after passing demo proxy is base 64 encoded
        assertTrue(testValue.matches("^([A-Za-z0-9+/]{4})*([A-Za-z0-9+/]{4}|[A-Za-z0-9+/]{3}=|[A-Za-z0-9+/]{2}==)$"));
        //verify the encoded value is in fact test
        assertTrue("test".equals(test));
        //But decoded value 1234
        byte[] decoded = Base64.decodeBase64(testValue);
        String decodedTest = new String(decoded);
        assertTrue("1234".equals(decodedTest));
        
    }

}
