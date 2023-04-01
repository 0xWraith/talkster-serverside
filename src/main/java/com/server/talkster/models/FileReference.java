package com.server.talkster.models;

import jakarta.persistence.*;

import java.time.OffsetDateTime;

@Entity
@Table(name = "files")
public class FileReference {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long ID;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private String type;

    public FileReference() {}

    public FileReference(String name, String type)
    {
        this.name = name;
        this.type = type;
    }


    public long getID() {return ID;}
    public void setID(long ID) {this.ID = ID;}
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    public String getType() {return type;}
    public void setType(String type) {this.type = type;}
}
