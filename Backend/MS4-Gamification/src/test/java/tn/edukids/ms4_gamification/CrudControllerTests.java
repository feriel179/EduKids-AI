package tn.edukids.ms4_gamification;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tn.edukids.ms4_gamification.controller.BadgeController;
import tn.edukids.ms4_gamification.dto.BadgeRequest;
import tn.edukids.ms4_gamification.dto.BadgeResponse;
import tn.edukids.ms4_gamification.exception.ResourceNotFoundException;
import tn.edukids.ms4_gamification.service.BadgeService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BadgeController.class)
class CrudControllerTests {
    @Autowired MockMvc mvc;
    @MockitoBean BadgeService badges;

    @Test
    void createsBadgeWithLocation() throws Exception {
        given(badges.create(any(BadgeRequest.class)))
                .willReturn(new BadgeResponse(7L, "Étoile", "Premier badge", "star.svg", 10));

        mvc.perform(post("/api/badges").contentType("application/json")
                .content("{\"nom\":\"Étoile\",\"description\":\"Premier badge\",\"icone\":\"star.svg\",\"pointsRequis\":10}"))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "http://localhost/api/badges/7"))
                .andExpect(jsonPath("$.id").value(7));
    }

    @Test
    void rejectsInvalidBadge() throws Exception {
        mvc.perform(post("/api/badges").contentType("application/json")
                .content("{\"nom\":\"\",\"pointsRequis\":-1}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void returns404ForMissingBadge() throws Exception {
        given(badges.findById(99L)).willThrow(new ResourceNotFoundException("Badge", 99L));
        mvc.perform(get("/api/badges/99"))
                .andExpect(status().isNotFound());
    }
}
