package com.altimetrik.graphql.controller;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTests {
    @Autowired
    private MockMvc mockMvc;

    private UserController UserController;

    private static final String GRAPHQL_PATH = "/graphql";
    @Before
    public void setup() {
        UserController = new UserController();
        mockMvc = MockMvcBuilders.standaloneSetup(UserController).build();
    }

    @DisplayName("Create user test")
    @Test
    public void givenAddUser_when_then() throws Exception {
        String query = "mutation {\n" +
                "  addUser(\n" +
                "    user: {\n" +
                "      name: \"Sachin Patil\"\n" +
                "      email: \"sachin@altimetrik.com\"\n" +
                "      salary: 2013.89\n" +
                "      monthlyExpenses: 400.56\n" +
                "    }\n" +
                "  ) {\n" +
                "    res{\n" +
                "        userId\n" +
                "    } \n" +
                "    message\n" +
                "    error\n" +
                "  }\n" +
                "}\n";
        String response = "{\n" +
                "    \"data\": {\n" +
                "        \"addUser\": {\n" +
                "            \"res\": {\n" +
                "                \"userId\": \"4\"\n" +
                "            },\n" +
                "            \"message\": \"Operation Successful\",\n" +
                "            \"error\": null\n" +
                "        }\n" +
                "    }\n" +
                "}";

        ResultActions postResult = performGraphQlPost(query);
        postResult.andExpect(status().isOk())
                .andExpect(jsonPath("$.errors").doesNotExist());
    }

    private ResultActions performGraphQlPost(String query) throws Exception {
        return performGraphQlPost(query, null);
    }

    private ResultActions performGraphQlPost(String query, Map variables) throws Exception {
        return mockMvc.perform(post("/graphql")
                .contentType(MediaType.APPLICATION_JSON)
                .content(generateRequest(query, variables))
        );
    }

    private static String toJSON(String query) {
        try {
            return new JSONObject().put("query", query).toString();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    private String generateRequest(String query, Map variables) throws JSONException {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("query", query);

        if (variables != null) {
            jsonObject.put("variables", Collections.singletonMap("input", variables));
        }

        return jsonObject.toString();
    }

}
