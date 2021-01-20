package jl.forthem.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.sun.istack.NotNull;

@Entity
@Table(name="tdl_item")
public class Item implements Comparable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)	
	@Column(name="ITEM_ID")
	private Integer item_id;
	
	
	@Column(name="ITEM_NAME")
	private String item_name;
	
	@Column(name="CATEGORY_ID")
	private Integer category_id;	
	
	//  "The default constructor exists only for the sake of JPA. 
	//  You do not use it directly, so it is designated as protected"
	//  Source: https://spring.io/guides/gs/accessing-data-jpa/
	protected Item() {
		super();
	}
	
	public Item(String name, Integer categoryId) {
		super();
		this.item_name = name;
		this.category_id = categoryId;
	}
	
	public Item(Integer item_id, String item_name, Integer categoryId) {
		super();
		this.item_id = item_id;
		this.item_name = item_name;
		this.category_id = categoryId;
	}

	public Integer getId() {
		return item_id;
	}
	
	public void setId(Integer id) {
		this.item_id = id;
	}

	public String getName() {
		return item_name;
	}

	public void setName(String name) {
		this.item_name = name;
	}

	public Integer getCategoryId() {
		return category_id;
	}

	public void setCategoryId(Integer categoryId) {
		this.category_id = categoryId;
	}

	@Override
	public String toString() {
		return "Item [id=" + item_id + ", name=" + item_name + ", categoryId=" + category_id + "]";
	}		
	
	public int compareTo(Object item) {
		return this.getName().compareTo(((Item)item).getName());
	}
	
}
