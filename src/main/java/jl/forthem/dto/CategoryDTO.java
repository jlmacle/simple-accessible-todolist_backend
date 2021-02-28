package jl.forthem.dto;

import jl.forthem.models.Item;

/**
 * @author jl
 * Class added to suppress a potential vulnerability detected by SonarQube
 *
 */
public class CategoryDTO {
	@Override
	public String toString() {
		return "CategoryDTO [id=" + id + ", name=" + name + "]";
	}

	private Integer id;	

	private String name;
	
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

	
}
