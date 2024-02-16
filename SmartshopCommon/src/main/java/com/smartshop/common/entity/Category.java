package com.smartshop.common.entity;

import java.beans.Transient;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "categories")
public class Category {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(length = 128, nullable = false, unique = true)
	private String name;

	@Column(length = 64, nullable = false, unique = true)
	private String alias;

	@Column(length = 128, nullable = false)
	private String image;

	private boolean enabled;

	@OneToOne
	@JoinColumn(name = "parent_id")
	private Category parentCategory;

	@OneToMany(
			mappedBy = "parentCategory",
			//orphanRemoval = true,
			fetch = FetchType.LAZY
			)
	private Set<Category> childrencategories = new HashSet<>();

	public Category() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Category(Integer id) {

		this.id = id;
	}

	public Category(String name) {

		this.name = name;
		this.alias = name;
		this.image = "default.png";
	}

	public static Category categoryIdAndName(Category category) {
		Category copyCategory=new Category();
		copyCategory.setId(category.getId());
		copyCategory.setName(category.getName());
		return copyCategory;
	}
	public static Category categoryIdAndName(Integer id,String name) {
		Category copyCategory=new Category();
		copyCategory.setId(id);
		copyCategory.setName(name);
		return copyCategory;
	}
	public Category(String name, Category parentCategory) {
		this(name);
		this.parentCategory = parentCategory;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Category getParentCategory() {
		return parentCategory;
	}

	public void setParentCategory(Category parentCategory) {
		this.parentCategory = parentCategory;
	}

	public Set<Category> getChildrencategories() {
		return childrencategories;
	}

	public void setChildrencategories(Set<Category> childrencategories) {
		this.childrencategories = childrencategories;
	}
	
	  
	/*
	 * @Override public String toString() { return "Category [id=" + id + ", name="
	 * + name + ", alias=" + alias + ", image=" + image + ", enabled=" + enabled +
	 * ", parentCategory=" + parentCategory + ", childrencategories=" +
	 * childrencategories + "]"; }
	 */
	 

	@Transient
	public String getPhotosImagePath() {
		if (id== null || image == null)
			return "/images/default-user.jpg";
		//String imagePath="/category-images/" + this.id + "/" + this.image;
		
		return "/category-images/" + this.id + "/" + this.image;
	}
}