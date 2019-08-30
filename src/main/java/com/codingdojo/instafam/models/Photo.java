package com.codingdojo.instafam.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

import com.codingdojo.instafam.models.Comment;
import com.codingdojo.instafam.models.User;

@Entity
@Table(name="photos")
public class Photo  implements Serializable{
	
	private static final long serialVersionUID = 74458L;
	
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
	
	@Size(min = 5, message = "Describe your photo")
    private String caption;
	
    @Transient
    private MultipartFile image;
    private String imageFileName;
    
    @Column(updatable=false)
    private Date createdAt;
    private Date updatedAt;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="uploader_id")
    private User uploader;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="family_id")
    private Family family;
    
    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;
    
	@ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
    		name = "likes",
    		joinColumns = @JoinColumn(name = "photo_id"),
    		inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> likedUsers;
	
	@ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
    		name = "tags",
    		joinColumns = @JoinColumn(name = "photo_id"),
    		inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> taggedUsers;
	
    @OneToMany(mappedBy="photo", fetch = FetchType.LAZY)
    private List<Comment> comments;
    
    public Photo() {
    	this.likedUsers = new ArrayList<User>();
    	this.taggedUsers = new ArrayList<User>();
    	this.comments = new ArrayList<Comment>();
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public MultipartFile getImage() {
		return image;
	}

	public void setImage(MultipartFile image) {
		this.image = image;
	}

	public String getImageFileName() {
		return imageFileName;
	}

	public void setImageFileName(String imageFileName) {
		this.imageFileName = imageFileName;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public User getUploader() {
		return uploader;
	}

	public void setUploader(User uploader) {
		this.uploader = uploader;
		this.family = this.uploader.getFamily();
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<User> getLikedUsers() {
		return likedUsers;
	}

	public void setLikedUsers(List<User> likedUsers) {
		this.likedUsers = likedUsers;
	}
	
	public void setLikedUser(User user) {
		this.likedUsers.add(user);
	}

	public List<User> getTaggedUsers() {
		return taggedUsers;
	}

	public void setTaggedUsers(List<User> taggedUsers) {
		this.taggedUsers = taggedUsers;
	}
	
	public void setTaggedUser(User user) {
		this.taggedUsers.add(user);
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}
	
	public void setComment(Comment comment) {
		this.comments.add(comment);
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public Family getFamily() {
		return family;
	}

	public void setFamily(Family family) {
		this.family = family;
	}

	@PrePersist
    protected void onCreate(){
        this.createdAt = new Date();
    }
    
    @PreUpdate
    protected void onUpdate(){
        this.updatedAt = new Date();
    }
}
