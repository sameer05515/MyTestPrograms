To copy a local MongoDB database to a new database, you can use the following approach. Unfortunately, MongoDB does not
provide a direct built-in command to copy one database to another. Instead, you can achieve this by leveraging
`mongodump` and `mongorestore` commands, which are part of the MongoDB tools suite.

---

### Steps to Copy a Database

#### 1. **Dump the Existing Database**

This creates a backup of the existing database.

```bash
mongodump --db <source_database_name> --out <backup_directory>
```

- Replace `<source_database_name>` with the name of your existing database.
- Replace `<backup_directory>` with the directory path where you want the dump files stored (e.g., `./backup`).

#### Example:

```bash
mongodump --db my_existing_db --out ./backup
```

---

#### 2. **Restore to a New Database**

This restores the dumped data to a new database.

```bash
mongorestore --db <target_database_name> <backup_directory>/<source_database_name>
```

- Replace `<target_database_name>` with the name of the new database.
- Replace `<backup_directory>/<source_database_name>` with the path to the dumped data.

#### Example:

```bash
mongorestore --db my_new_db ./backup/my_existing_db
```

---

### Verify the Copy

After restoring, you can verify the data in the new database by connecting to MongoDB and checking the collections:

```bash
mongo
use my_new_db
show collections
```

---

### Notes:

- Make sure the MongoDB server is running when using these commands.
- If authentication is enabled, use the `--username`, `--password`, and `--authenticationDatabase` options in the
  `mongodump` and `mongorestore` commands.
- Ensure you have sufficient disk space for the backup and restore operations.

This approach is safe and ensures your original data remains intact while creating a duplicate database for testing.