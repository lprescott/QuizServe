## ICSI418-Group-Project-JSP-Example
* The following database was used to illustrate a simple JSP example. See Henry Database MySQL initialization.txt.
* Also as an example, two simple Java Servlets are included -- one POST, one GET.

```
mysql> Use Henry;
Database changed

mysql> Show tables;
+-----------------+
| Tables_in_henry |
+-----------------+
| author          |
| book            |
| branch          |
| inventory       |
| publisher       |
| wrote           |
+-----------------+
6 rows in set (0.00 sec)

mysql> Select * from AUTHOR;
+------------+--------------+--------------+
| AUTHOR_NUM | AUTHOR_LAST  | AUTHOR_FIRST |
+------------+--------------+--------------+
|          1 | Morrison     | Toni         |
|          2 | Solotaroff   | Paul         |
|          3 | Vintage      | Vernor       |
|          4 | Francis      | Dick         |
|          5 | Straub       | Peter        |
|          6 | King         | Stephen      |
|          7 | Pratt        | Philip       |
|          8 | Chase        | Truddi       |
|          9 | Collins      | Bradley      |
|         10 | Heller       | Joseph       |
|         11 | Wills        | Gary         |
|         12 | Hofstadter   | Douglas R.   |
|         13 | Lee          | Harper       |
|         14 | Ambrose      | Stephen E.   |
|         15 | Rowling      | J.K.         |
|         16 | Salinger     | J.D.         |
|         17 | Heaney       | Seamus       |
|         18 | Camus        | Albert       |
|         19 | Collins, Jr. | Bradley      |
|         20 | Steinbeck    | John         |
|         21 | Castelman    | Riva         |
|         22 | Owen         | Barbara      |
|         23 | O'Rourke     | Randy        |
|         24 | Kidder       | Tracy        |
|         25 | Schleining   | Lon          |
+------------+--------------+--------------+
25 rows in set (0.01 sec)
```
