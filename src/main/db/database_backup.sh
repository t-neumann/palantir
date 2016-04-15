# backup
mysqldump testdb --user=root -p > export_testdb.sql
# restore
mysql -u root -p testdb < export_testdb.sql
