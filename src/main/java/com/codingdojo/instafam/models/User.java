package com.codingdojo.instafam.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
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
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    
    @Size(min=2, message="First name required")
    private String firstName;
    @Size(min=2, message="Last name required")
    private String lastName;
    @Email(message="Email must be valid")
    private String email;
    @Size(min=8, message="Password must be greater than 8 characters")
    private String password;
    @Transient
    private String passwordConfirmation;
    
    @Column(updatable=false)
    private Date createdAt;
    private Date updatedAt;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="family_id")
    private Family family;
    
    @OneToMany(mappedBy="uploader", fetch = FetchType.LAZY)
    private List<Photo> photos;
    
    @OneToOne(mappedBy="user", cascade=CascadeType.ALL, fetch=FetchType.LAZY)
    private Photo profilePic;
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
    		name = "likes",
    		joinColumns = @JoinColumn(name = "user_id"),
    		inverseJoinColumns = @JoinColumn(name = "photo_id")
    )
    private List<Photo> likedPhotos;
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
    		name = "likes_comments",
    		joinColumns = @JoinColumn(name = "user_id"),
    		inverseJoinColumns = @JoinColumn(name = "comment_id")
    )
    private List<Comment> likedComments;
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
    		name = "tags",
    		joinColumns = @JoinColumn(name = "user_id"),
    		inverseJoinColumns = @JoinColumn(name = "photo_id")
    )
    private List<Photo> taggedPhotos;
    
    public User() {
    	this.photos = new ArrayList<Photo>();
    	this.likedPhotos = new ArrayList<Photo>();
    	this.taggedPhotos = new ArrayList<Photo>();
    	this.likedComments = new ArrayList<Comment>();
    }
    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordConfirmation() {
		return passwordConfirmation;
	}

	public void setPasswordConfirmation(String passwordConfirmation) {
		this.passwordConfirmation = passwordConfirmation;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public List<Photo> getPhotos() {
		return photos;
	}

	public void setPhotos(List<Photo> photos) {
		this.photos = photos;
	}
	
	public void setPhoto(Photo photo) {
		this.photos.add(photo);
	}

	public Photo getProfilePic() {
		return profilePic;
	}

	public void setProfilePic(Photo profilePic) {
		this.profilePic = profilePic;
	}

	public List<Photo> getLikedPhotos() {
		return likedPhotos;
	}

	public void setLikedPhotos(List<Photo> likedPhotos) {
		this.likedPhotos = likedPhotos;
	}
	
	public void setLikedPhoto(Photo photo) {
		this.likedPhotos.add(photo);
	}

	public List<Photo> getTaggedPhotos() {
		return taggedPhotos;
	}

	public void setTaggedPhotos(List<Photo> taggedPhotos) {
		this.taggedPhotos = taggedPhotos;
	}

	public void setTaggedPhoto(Photo photo) {
		this.taggedPhotos.add(photo);
	}
	
	public List<Comment> getLikedComments() {
		return likedComments;
	}

	public void setLikedComments(List<Comment> likedComments) {
		this.likedComments = likedComments;
	}
	
	public void setLikedComment(Comment comment) {
		this.likedComments.add(comment);
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
