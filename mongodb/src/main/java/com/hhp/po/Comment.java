package com.hhp.po;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.Date;

@Data
@Document(collection = "comment")
public class Comment implements Serializable {

    @Id
    private String id;
    @Field("content")
    private String content;
    @Field
    private Date publishTime;

}
