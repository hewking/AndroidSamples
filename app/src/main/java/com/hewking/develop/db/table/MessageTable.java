package com.hewking.develop.db.table;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

@Entity(nameInDb = "Message")
public class MessageTable {

  @Id(autoincrement = true)
  @Property(nameInDb = "Id")
  private Long id;

  @Property(nameInDb = "message")
  public String message = "";

  @Property(nameInDb = "time")
  public long time;

  @Generated(hash = 429502521)
public MessageTable(Long id, String message, long time) {
    this.id = id;
    this.message = message;
    this.time = time;
}

@Generated(hash = 1805713138)
  public MessageTable() {
  }

  public Long getId() {
      return this.id;
  }

  public void setId(Long id) {
      this.id = id;
  }

  public String getMessage() {
      return this.message;
  }

  public void setMessage(String message) {
      this.message = message;
  }

public long getTime() {
    return this.time;
}

public void setTime(long time) {
    this.time = time;
}


}
