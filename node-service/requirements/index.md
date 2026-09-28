# Requirements

- `Done` Requirement 1: A node should have below fields
    - Fields as below
      ```java
      String uniqueId;
      Map<String,String> metadata;
      ```
- `In-Progress`: Requirement 2:
    - Node's uniqueId should be generated in backend.
    - On save, user can only send `name` and `type` of node. both fields should be non-null and non-empty
    - `metadata` will be `upserted` in a node later.

- `Grooming-In-Progress`: Requirement 3:
    - A node may have zero or more descriptions
    - descriptions can be re-arranged later.