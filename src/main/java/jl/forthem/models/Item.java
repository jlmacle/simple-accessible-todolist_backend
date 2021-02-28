package jl.forthem.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="tdl_item")
public class Item implements Comparable<Object>{
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)	
	@Column(name="ITEM_ID")
	private Integer itemId;
	
	
	@Column(name="ITEM_NAME")
	private String itemName;
	
	@Column(name="CATEGORY_ID")
	private Integer categoryId;	
	
	//  "The default constructor exists only for the sake of JPA. 
	//  You do not use it directly, so it is designated as protected"
	//  Source: https://spring.io/guides/gs/accessing-data-jpa/
	protected Item() {
		super();
	}
	
	public Item(String name, Integer categoryId) {
		super();
		this.itemName = name;
		this.categoryId = categoryId;
	}
	
	public Item(Integer itemId, String itemName, Integer categoryId) {
		super();
		this.itemId = itemId;
		this.itemName = itemName;
		this.categoryId = categoryId;
	}

	public Integer getId() {
		return itemId;
	}
	
	public void setId(Integer id) {
		this.itemId = id;
	}

	public String getName() {
		return itemName;
	}

	public void setName(String name) {
		this.itemName = name;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	@Override
	public String toString() {
		return "Item [id=" + itemId + ", name=" + itemName + ", categoryId=" + categoryId + "]";
	}		
	
	public int compareTo(Object item) {
		return this.getName().compareTo(((Item)item).getName());
	}
	
	@Override
	public boolean equals(Object obj)
	{
		return (this.getId().equals(((Item)obj).getId())  && this.getName().equals(((Item)obj).getName())  
				&&  this.getCategoryId().equals(((Item)obj).getCategoryId())  );
	}
	
}
