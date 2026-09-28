// package com.spp.demo.modules.memorymaps;
//
// import java.util.List;
// import java.util.Optional;
// import org.bson.types.ObjectId;
// import org.springframework.data.mongodb.repository.MongoRepository;
// import org.springframework.data.mongodb.repository.Query;
//
// public interface MemoryMapRepository extends MongoRepository<MemoryMap, ObjectId> {
//
//  @Query(value = "{}", fields = "{ 'uniqueId' : 1, 'name' : 1, 'parentId': 1 }")
//  List<MemoryMap> findAllMemoryMapsForSectionList();
//
//  // Find a topic by its unique identifier
//  Optional<MemoryMap> findByUniqueId(String uniqueId);
// }
