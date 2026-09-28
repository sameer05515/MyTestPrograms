// package com.p.api.node.service.nodes;
//
// import static org.junit.jupiter.api.Assertions.*;
// import static org.mockito.Mockito.*;
//
// import java.util.HashMap;
// import java.util.List;
// import java.util.NoSuchElementException;
// import java.util.Optional;
// import javax.management.openmbean.KeyAlreadyExistsException;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import org.mockito.MockitoAnnotations;
//
// class NodeServiceTest {
//
//  @Mock private NodeRepository nodeRepository;
//
//  @InjectMocks private NodeServiceImpl nodeService;
//
//  @BeforeEach
//  void setUp() {
//    MockitoAnnotations.openMocks(this);
//  }
//
//  @Test
//  void testFindAllOnlyUniqueId() {
//    // Mock findAllUniqueIds to return empty
//    when(nodeRepository.findAllUniqueIds()).thenReturn(List.of());
//
//    List<Node> nodes = nodeService.getAllNodes();
//
//    assertTrue(nodes.isEmpty(), "Expected no nodes, but found some");
//    verify(nodeRepository, times(1)).findAllUniqueIds();
//  }
//
//  @Test
//  void testGetAllNodes() {
//    Node node1 = Node.builder().uniqueId("uniqueId1").build(); // new Node("uniqueId1", null);
//    Node node2 = Node.builder().uniqueId("uniqueId2").build(); // new Node("uniqueId2", null);
//
//    when(nodeRepository.findAllUniqueIds()).thenReturn(List.of(node1, node2));
//
//    List<Node> nodes = nodeService.getAllNodes();
//
//    assertEquals(2, nodes.size(), "Expected 2 nodes, but found " + nodes.size());
//    verify(nodeRepository, times(1)).findAllUniqueIds();
//  }
//
//  @Test
//  void testSaveNode() {
//    Node updatedNode =
//        Node.builder()
//            .uniqueId("uniqueId1")
//            .name("New Name")
//            .build(); // new Node("uniqueId1", null, "New Name", new HashMap<>());
//
//    when(nodeRepository.findByUniqueId("uniqueId1")).thenReturn(Optional.empty());
//    when(nodeRepository.save(any(Node.class))).thenAnswer(invocation ->
// invocation.getArgument(0));
//
//    Node result = nodeService.saveNode(updatedNode);
//
//    assertEquals("New Name", result.getName(), "Name was not updated correctly");
//    //    assertEquals("Old Type", result.getType(), "Type should not have been updated");
//    verify(nodeRepository, times(1)).findByUniqueId("uniqueId1");
//    verify(nodeRepository, times(1)).save(updatedNode);
//  }
//
//  @Test
//  void testSaveNode_NodeExists() {
//    Node existingNode =
//        Node.builder()
//            .uniqueId("uniqueId1")
//            .name("Old Name")
//            .type("Old Type")
//            .build(); // ("uniqueId1", "Old Type", "Old Name", new HashMap<>());
//
//    when(nodeRepository.findByUniqueId("uniqueId1")).thenReturn(Optional.of(existingNode));
//    when(nodeRepository.save(any(Node.class))).thenAnswer(invocation ->
// invocation.getArgument(0));
//
//    Exception exception =
//        assertThrows(KeyAlreadyExistsException.class, () -> nodeService.saveNode(existingNode));
//
//    assertEquals("uniqueId Already Exists: uniqueId1", exception.getMessage());
//    verify(nodeRepository, times(1)).findByUniqueId("uniqueId1");
//  }
//
//  @Test
//  void testUpdateNode() {
//    Node existingNode =
//        Node.builder()
//            .uniqueId("uniqueId1")
//            .name("Old Name")
//            .type("Old Type")
//            .build(); // ("uniqueId1", "Old Type", "Old Name", new HashMap<>());
//    Node updatedNode =
//        Node.builder()
//            .uniqueId("uniqueId1")
//            .name("New Name")
//            .build(); // new Node("uniqueId1", null, "New Name", new HashMap<>());
//
//    when(nodeRepository.findByUniqueId("uniqueId1")).thenReturn(Optional.of(existingNode));
//    when(nodeRepository.save(any(Node.class))).thenAnswer(invocation ->
// invocation.getArgument(0));
//
//    Node result = nodeService.updateNode(updatedNode);
//
//    assertEquals("New Name", result.getName(), "Name was not updated correctly");
//    assertEquals("Old Type", result.getType(), "Type should not have been updated");
//    verify(nodeRepository, times(1)).findByUniqueId("uniqueId1");
//    verify(nodeRepository, times(1)).save(existingNode);
//  }
//
//  @Test
//  void testUpdateNode_NodeDoesNotExist() {
//    Node updatedNode =
//        Node.builder()
//            .uniqueId("uniqueId1")
//            .name("New Name")
//            .build(); // new Node("uniqueId1", null, "New Name", new HashMap<>());
//    when(nodeRepository.findByUniqueId("uniqueId1")).thenReturn(Optional.empty());
//
//    Exception exception =
//        assertThrows(NoSuchElementException.class, () -> nodeService.updateNode(updatedNode));
//
//    assertEquals("No node found for uniqueId: uniqueId1", exception.getMessage());
//    verify(nodeRepository, times(1)).findByUniqueId("uniqueId1");
//  }
//
//  @Test
//  void testGetNodeById_NodeExists() {
//    Node node = new Node("uniqueId1", "Type1", "Node1", new HashMap<>());
//
//    when(nodeRepository.findByUniqueId("uniqueId1")).thenReturn(Optional.of(node));
//
//    Node result = nodeService.getNodeById("uniqueId1");
//
//    assertNotNull(result, "Node should not be null");
//    assertEquals("uniqueId1", result.getUniqueId(), "UniqueId mismatch");
//    verify(nodeRepository, times(1)).findByUniqueId("uniqueId1");
//  }
//
//  @Test
//  void testGetNodeById_NodeDoesNotExist() {
//    when(nodeRepository.findByUniqueId("uniqueId1")).thenReturn(Optional.empty());
//
//    Exception exception =
//        assertThrows(NoSuchElementException.class, () -> nodeService.getNodeById("uniqueId1"));
//
//    assertEquals("No node found for uniqueId: uniqueId1", exception.getMessage());
//    verify(nodeRepository, times(1)).findByUniqueId("uniqueId1");
//  }
//
//  @Test
//  void testDeleteNode_NodeExists() {
//    // Arrange
//    String uniqueId = "uniqueId1";
//    doNothing().when(nodeRepository).deleteById(uniqueId);
//
//    // Act
//    nodeService.deleteNode(uniqueId);
//
//    // Assert
//    verify(nodeRepository, times(1)).deleteById(uniqueId);
//  }
//
//  @Test
//  void testDeleteNode_NodeDoesNotExist() {
//    // Arrange
//    String uniqueId = "nonExistentUniqueId";
//    doThrow(new NoSuchElementException("No node found for uniqueId: " + uniqueId))
//        .when(nodeRepository)
//        .deleteById(uniqueId);
//
//    // Act & Assert
//    Exception exception =
//        assertThrows(NoSuchElementException.class, () -> nodeService.deleteNode(uniqueId));
//
//    assertEquals("No node found for uniqueId: nonExistentUniqueId", exception.getMessage());
//    verify(nodeRepository, times(1)).deleteById(uniqueId);
//  }
// }
