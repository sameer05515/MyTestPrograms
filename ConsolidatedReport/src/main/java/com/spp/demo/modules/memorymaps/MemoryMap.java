// package com.spp.demo.modules.memorymaps;
//
// import com.spp.demo.modules.common.Node;
// import java.time.Instant;
// import lombok.Data;
// import lombok.NoArgsConstructor;
// import org.bson.types.ObjectId;
// import org.springframework.data.annotation.Id;
// import org.springframework.data.mongodb.core.mapping.Document;
//
// @Data
// @NoArgsConstructor
// @Document(collection = "memorymaps")
// public class MemoryMap implements Node {
//  @Id private ObjectId id; // Maps to "_id" in MongoDB
//
//  private String name;
//  private String parentId;
//  private String uniqueId;
//  private Instant createdDate;
//  private Instant updatedDate;
//  private String description;
//  private String skeleton;
//  private String skeletonTextType;
//  //  private SmartDescription smartContent;
//  private boolean softDelete;
//  private int __v; // Mongo version field
// }
