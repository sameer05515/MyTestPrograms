package com.spp.demo.modules.tasks;

import com.spp.demo.modules.common.SmartDescription;
import java.time.Instant;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

@Data
@NoArgsConstructor
public class Activity {

  private ObjectId id; // Maps to "_id" in MongoDB
  private String uniqueId; // Unique identifier for the activity
  private String type; // Activity type, e.g., "comment"
  private Instant createdDate;
  private Instant updatedDate;
  private SmartDescription description; // Embedded description object
  private UserDetails userDetails; // User details object
}
