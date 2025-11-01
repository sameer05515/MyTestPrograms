# To copy and restore existing database

## 1. **Take backup of existing database**

```shell
mongodump --db mongodb_test --out ./backup
```

## 2. **Restore to a New Database**

```shell
mongorestore --db consolidated_report ./backup/mongodb_test
```
