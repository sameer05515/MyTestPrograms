package com.p.api.node.service.nodes;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

class NodeControllerTest {

  private final ObjectMapper objectMapper = new ObjectMapper();
  @Mock private NodeService nodeService;
  @InjectMocks private NodeController nodeController;
  private MockMvc mockMvc;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    mockMvc = MockMvcBuilders.standaloneSetup(nodeController).build();
  }

  @Test
  void testCreateNode() throws Exception {
    Node node =
        Node.builder()
            .uniqueId("uniqueId1")
            .build(); // new Node("uniqueId1", null);new Node("uniqueId1", null);
    when(nodeService.saveNode(any(Node.class))).thenReturn(node);

    mockMvc
        .perform(
            post("/api/nodes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(node)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.status").value("success"))
        .andExpect(
            jsonPath("$.message").value("Request was successful: Successfully created new Node"))
        .andExpect(jsonPath("$.data.uniqueId").value("uniqueId1"));

    verify(nodeService, times(1)).saveNode(any(Node.class));
  }

  @Test
  void testGetAllNodes() throws Exception {
    Node node1 =
        Node.builder()
            .uniqueId("uniqueId1")
            .build(); // new Node("uniqueId1", null);new Node("uniqueId1", null);
    Node node2 =
        Node.builder()
            .uniqueId("uniqueId2")
            .build(); // new Node("uniqueId1", null);new Node("uniqueId2", null);
    when(nodeService.getAllNodes()).thenReturn(Arrays.asList(node1, node2));

    mockMvc
        .perform(get("/api/nodes"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.status").value("success"))
        .andExpect(
            jsonPath("$.message").value("Request was successful: Successfully retrieved all Nodes"))
        .andExpect(jsonPath("$.data[0].uniqueId").value("uniqueId1"))
        .andExpect(jsonPath("$.data[1].uniqueId").value("uniqueId2"));

    verify(nodeService, times(1)).getAllNodes();
  }

  @Test
  void testGetNodeById_NodeExists() throws Exception {
    Node node =
        Node.builder()
            .uniqueId("uniqueId1")
            .build(); // new Node("uniqueId1", null);new Node("uniqueId1", null);
    when(nodeService.getNodeById("uniqueId1")).thenReturn(node);

    mockMvc
        .perform(get("/api/nodes/{id}", "uniqueId1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.status").value("success"))
        .andExpect(jsonPath("$.message").value("Request was successful"))
        .andExpect(jsonPath("$.data.uniqueId").value("uniqueId1"));

    verify(nodeService, times(1)).getNodeById("uniqueId1");
  }

  //  @Test
  //  void testGetNodeById_NodeDoesNotExist() throws Exception {
  //    when(nodeService.getNodeById("uniqueId1"))
  //        .thenThrow(new NoSuchElementException("Node not found"));
  //
  //    mockMvc
  //        .perform(get("/api/nodes/{id}", "uniqueId1"))
  //        .andExpect(status().isNotFound())
  //        .andExpect(jsonPath("$.status").value("error"))
  //        .andExpect(jsonPath("$.data.message").value("Node not found"));
  //
  //    verify(nodeService, times(1)).getNodeById("uniqueId1");
  //  }

  @Test
  void testDeleteNode() throws Exception {
    doNothing().when(nodeService).deleteNode("uniqueId1");

    mockMvc
        .perform(delete("/api/nodes/{id}", "uniqueId1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.status").value("success"))
        .andExpect(
            jsonPath("$.message")
                .value("Request was successful: Successfully deleted data for id: uniqueId1"));

    verify(nodeService, times(1)).deleteNode("uniqueId1");
  }
}
