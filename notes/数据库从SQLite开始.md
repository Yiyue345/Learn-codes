### 论SQLite的操作

ROOM是谁，我的大脑这会大概还无法接受吧

操作基本靠exeSQL（就是直接用SQLite

SQL是不分大小写的

#### 首先

操作数据库那就得先有一个，原始的办法是弄个SQLiteOpenHelper，比如下面这样

```kotlin
class NoteDatabaseHelper(val context: Context, name: String, version: Int) :
SQLiteOpenHelper(context, name, null, version)
```

这样在别的文件中，可以通过两行代码来用这个Helper

```kotlin
val dbHelper = NoteDatabaseHelper(context, "数据库名", 版本)
val db = dbHelper.writableDatabase
```

这个`db`就是可以操作的数据库了

#### 增

创建一个新表可以像下面这样：

```sqlite
create table Note (
        id integer primary key autoincrement,
        title text,
        content text,
        editTime)
```

也就是`create table 表名 (一堆值的名及类型(可选))`

往表里面插入新的数据如下

```kotlin
val value = ContentValues().apply {
    put("title", binding.titleBox.text.toString())
    put("content", binding.editBox.text.toString())
    put("editTime", System.currentTimeMillis())
}
db.insert("Note", null, value)
```

在kt里面可以这样组装一个数据再插入，插入不过就是`db.insert(表名, null, 值)`

直接用SQL那就是`insert into 表名 (键) values (值)`后面再填值，例如

```kotlin
db.execSQL("insert into Book (name, author, pages, price) values(?, ?, ?, ?)", 
    arrayOf("The Da Vinci Code", "Dan Brown", "454", "16.96") 
) 
```

看得出来一个?对应一个值

#### 删

就是`db.delete(表名, 条件, 条件的值)`，比如

```kotlin
db.delete("Book", "pages > ? ", arrayOf("500"))
```

用SQL则是`delete from 表名 where 条件`，后面跟上一串条件，如

```kotlin
db.execSQL("delete from Book where pages > ?", arrayOf("500")) 
```

删表则是`drop table [条件] 表名` ，像是

```sql
drop table Note
```

#### 改

修改数据用的是`update`，像`db.update("Book", values, "name = ?", arrayOf("The Da Vinci Code"))` 

即`db.update(表名, 要修改的列, 条件),  arrayOf(值)`，其中要修改的列和条件和值都可以不止一个

用SQL就变成了

还没写完

