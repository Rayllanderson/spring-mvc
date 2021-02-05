package com.rayllanderson.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.servlet.http.HttpServletResponse;

@Entity
public class File implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Long peopleId;

    @Lob
    private byte[] bytes;
    private String name;
    private String contentType;

    public File(byte[] bytes, Long peopleId) {
	this.bytes = bytes;
	this.peopleId = peopleId;
    }
    
    public File(Long peopleId, byte[] bytes, String name, String contentType) {
	super();
	this.peopleId = peopleId;
	this.bytes = bytes;
	this.name = name;
	this.contentType = contentType;
    }

    public File() {
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public byte[] getBytes() {
	return bytes;
    }

    public void setBytes(byte[] bytes) {
	this.bytes = bytes;
    }

    public Long getPeopleId() {
	return peopleId;
    }

    public void setPeopleId(Long peopleId) {
	this.peopleId = peopleId;
    }

    public void download(HttpServletResponse response) {
	try {
	    String contentType = this.contentType == null ? "application/pdf" : this.contentType; 
	    String fileType = contentType.split("/")[1];
	    String fileName = this.name == null ? "Currículo" : this.name.split(fileType)[0];
	    response.setContentLength(this.bytes.length);
	    response.setContentType(contentType);
	    String headerKey = "Content-Disposition";
	    String headerValue = String.format("attachment; filename=\"%s\"", (fileName + "." + fileType));
	    response.setHeader(headerKey, headerValue);
	    response.getOutputStream().write(bytes);
	    response.getOutputStream().close();
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }
    
    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((peopleId == null) ? 0 : peopleId.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	File other = (File) obj;
	if (peopleId == null) {
	    if (other.peopleId != null)
		return false;
	} else if (!peopleId.equals(other.peopleId))
	    return false;
	return true;
    }

}
