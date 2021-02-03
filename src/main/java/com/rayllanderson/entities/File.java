package com.rayllanderson.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;

@Entity
public class File implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Long peopleId;

    @Lob
    private byte[] bytes;

    public File(byte[] bytes, Long peopleId) {
	this.bytes = bytes;
	this.peopleId = peopleId;
    }

    public File() {
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
