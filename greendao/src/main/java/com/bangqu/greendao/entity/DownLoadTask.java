package com.bangqu.greendao.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Administrator on 2018-6-28 0028.
 */
@Entity
public class DownLoadTask {
    @Id(autoincrement = true)
    private Long id;//自增长主键
    private String download_url;
    private long download_id;
    private String save_path;
    private String file_name;
    @Generated(hash = 155904464)
    public DownLoadTask(Long id, String download_url, long download_id,
            String save_path, String file_name) {
        this.id = id;
        this.download_url = download_url;
        this.download_id = download_id;
        this.save_path = save_path;
        this.file_name = file_name;
    }
    @Generated(hash = 2145799500)
    public DownLoadTask() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getDownload_url() {
        return this.download_url;
    }
    public void setDownload_url(String download_url) {
        this.download_url = download_url;
    }
    public long getDownload_id() {
        return this.download_id;
    }
    public void setDownload_id(long download_id) {
        this.download_id = download_id;
    }
    public String getSave_path() {
        return this.save_path;
    }
    public void setSave_path(String save_path) {
        this.save_path = save_path;
    }
    public String getFile_name() {
        return this.file_name;
    }
    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }
}
