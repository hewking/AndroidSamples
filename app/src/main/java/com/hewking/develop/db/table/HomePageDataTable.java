package com.hewking.develop.db.table;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

@Entity(nameInDb = "HomePageData")
public class HomePageDataTable {

  @Id(autoincrement = true)
  @Property(nameInDb = "Id")
  private Long id;

  @Property(nameInDb = "requestKey")
  public String requestKey = "";

  @Property(nameInDb = "dataJson")
  public String dataJson = "";

  @Generated(hash = 1964379075)
  public HomePageDataTable(Long id, String requestKey, String dataJson) {
      this.id = id;
      this.requestKey = requestKey;
      this.dataJson = dataJson;
  }

  @Generated(hash = 1617481336)
  public HomePageDataTable() {
  }

  public Long getId() {
      return this.id;
  }

  public void setId(Long id) {
      this.id = id;
  }

  public String getRequestKey() {
      return this.requestKey;
  }

  public void setRequestKey(String requestKey) {
      this.requestKey = requestKey;
  }

  public String getDataJson() {
      return this.dataJson;
  }

  public void setDataJson(String dataJson) {
      this.dataJson = dataJson;
  }

}
