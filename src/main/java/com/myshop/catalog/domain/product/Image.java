package com.myshop.catalog.domain.product;

import javax.persistence.*;
import java.util.Date;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "image_type")
@Table(name = "image")
public abstract class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Long id;

    @Column(name = "image_path")
    private String path;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "upload_time")
    private Date uploadTime;

    protected Image() {}
    public Image(String path) {
        this.path = path;
        this.uploadTime = new Date();
    }

    protected String getPath() {
        return path;
    }

    public Date getUploadTime() {
        return uploadTime;
    }

    public abstract String getUrl();
    public abstract boolean hasThumbnail();
    public abstract String getThumbnailUrl();

}
