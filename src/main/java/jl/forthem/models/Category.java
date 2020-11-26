package jl.forthem.models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="tdl_category")
public class Category implements Comparable {	
	
	@Id
	@Column(name="CATEGORY_ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Column(name="CATEGORY_NAME")
	private String name;
	
	// Example using OneToMany
	//https://github.com/spring-projects/spring-data-examples/blob/ccae97890f85a3eaf8f4e05a1a07696e2b1e78a4/rest/projections/src/main/java/example/springdata/rest/projections/Order.java
	
	//One category has many items. 
	//An item (beyond a simple name description) belongs only to one category.
	// The deletion of the category should not imply the deletion of the items.
	// Those orphans items should be mapped to the category "Misc."
	@OneToMany(targetEntity = Item.class, mappedBy = "item_id", orphanRemoval = false)	
	private List<Item> items;

	protected Category() {
		super();
	}
	
	public Category(Integer id, String name, List<Item> items) {
		super();
		this.id = id;
		this.name = name;
		this.items = items;
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
	
	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}	

	@Override
	public String toString() {
		return "Category [id=" + id + ", name=" + name + ", items=" + items + "]";
	}

	public int compareTo(Object category) {
		return this.getName().compareTo(((Category)category).getName());
		
	}
	
	

}
