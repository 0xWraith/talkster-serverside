package com.server.talkster.dto;

public class FileDTO {

    protected String filename;
    protected String sizeMsg;

    public FileDTO(String filename, String sizeMsg) {
        this.filename = filename;
        this.sizeMsg = sizeMsg;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getSizeMsg() {
        return sizeMsg;
    }

    public void setSizeMsg(String sizeMsg) {
        this.sizeMsg = sizeMsg;
    }
}