package com.example.ushort.domain;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "short_links")
@Data
public class ShortLink {

    @Id
    private String track;

    @NotNull
    private String host;

    @NotNull
    private String location;

    private long redirectCount;

    @CreationTimestamp
    private Date createdAt;
}
