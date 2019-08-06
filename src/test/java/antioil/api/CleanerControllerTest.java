package antioil.api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CleanerControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    void testCorrectRequest() throws Exception {
        mvc.perform(post("/cleanup")
                .content("{\n" +
                        "  \"areaSize\" : [5, 5],\n" +
                        "  \"startingPosition\" : [1, 2],\n" +
                        "  \"oilPatches\" : [\n" +
                        "    [1, 0],\n" +
                        "    [2, 2],\n" +
                        "    [2, 3]\n" +
                        "  ],\n" +
                        "  \"navigationInstructions\" : \"NNESEESWNWW\"\n" +
                        "}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\n" +
                        "  \"finalPosition\" : [1, 3],\n" +
                        "  \"oilPatchesCleaned\" : 1\n" +
                        "}"));
    }

    @Test
    void navigationalExceptionIsHandledCorrectly() throws Exception {
        mvc.perform(post("/cleanup")
                .content("{\n" +
                        "  \"areaSize\" : [5, 5],\n" +
                        "  \"startingPosition\" : [1, 2],\n" +
                        "  \"oilPatches\" : [\n" +
                        "    [1, 0],\n" +
                        "    [2, 2],\n" +
                        "    [2, 3]\n" +
                        "  ],\n" +
                        "  \"navigationInstructions\" : \"NNNNNNNN\"\n" +
                        "}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{ \"message\" : \"Navigation command is out of range\" }"));
    }

    @Test
    void pointSizeProblems() throws Exception {
        mvc.perform(post("/cleanup")
                .content("{\n" +
                        "  \"areaSize\" : [5],\n" +
                        "  \"startingPosition\" : [1, 2],\n" +
                        "  \"oilPatches\" : [\n" +
                        "    [1, 0],\n" +
                        "    [2, 2],\n" +
                        "    [2, 3]\n" +
                        "  ],\n" +
                        "  \"navigationInstructions\" : \"NNESEESWNWW\"\n" +
                        "}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{ \"message\" : \"Incorrect dimensional array size\" }"));

        mvc.perform(post("/cleanup")
                .content("{\n" +
                        "  \"areaSize\" : [5, 5],\n" +
                        "  \"startingPosition\" : [1],\n" +
                        "  \"oilPatches\" : [\n" +
                        "    [1, 0],\n" +
                        "    [2, 2],\n" +
                        "    [2, 3]\n" +
                        "  ],\n" +
                        "  \"navigationInstructions\" : \"NNESEESWNWW\"\n" +
                        "}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{ \"message\" : \"Incorrect dimensional array size\" }"));

        mvc.perform(post("/cleanup")
                .content("{\n" +
                        "  \"areaSize\" : [5, 5],\n" +
                        "  \"startingPosition\" : [1, 2],\n" +
                        "  \"oilPatches\" : [\n" +
                        "    [1, 0],\n" +
                        "    [2],\n" +
                        "    [2, 3]\n" +
                        "  ],\n" +
                        "  \"navigationInstructions\" : \"NNESEESWNWW\"\n" +
                        "}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{ \"message\" : \"Incorrect dimensional array size\" }"));
    }

    @Test
    void requiredFields() throws Exception {
        mvc.perform(post("/cleanup")
                .content("{\n" +
                        "  \"startingPosition\" : [1, 2],\n" +
                        "  \"oilPatches\" : [\n" +
                        "    [1, 0],\n" +
                        "    [2, 0],\n" +
                        "    [2, 3]\n" +
                        "  ],\n" +
                        "  \"navigationInstructions\" : \"NNESEESWNWW\"\n" +
                        "}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        mvc.perform(post("/cleanup")
                .content("{\n" +
                        "  \"areaSize\" : [5, 5],\n" +
                        "  \"oilPatches\" : [\n" +
                        "    [1, 0],\n" +
                        "    [2, 0],\n" +
                        "    [2, 3]\n" +
                        "  ],\n" +
                        "  \"navigationInstructions\" : \"NNESEESWNWW\"\n" +
                        "}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        mvc.perform(post("/cleanup")
                .content("{\n" +
                        "  \"areaSize\" : [5, 5],\n" +
                        "  \"startingPosition\" : [1, 2],\n" +
                        "  \"navigationInstructions\" : \"NNESEESWNWW\"\n" +
                        "}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        mvc.perform(post("/cleanup")
                .content("{\n" +
                        "  \"areaSize\" : [5, 5],\n" +
                        "  \"startingPosition\" : [1, 2],\n" +
                        "  \"oilPatches\" : [\n" +
                        "    [1, 0],\n" +
                        "    [2, 0],\n" +
                        "    [2, 3]\n" +
                        "  ]\n" +
                        "}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}