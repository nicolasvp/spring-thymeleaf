package com.spring.test.models.entity;

import java.io.Serializable;
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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import javax.validation.constraints.NotEmpty;

@Entity
@Table(name="bills")
public class Bill implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty
	private String description;
	
	private String observation;
	
	@Column(name="created_at")
	@Temporal(TemporalType.DATE)
	private Date createdAt;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Client client;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name="bill_id")
	private List<ItemBill> items;
	
	public Bill() {
		this.items = new ArrayList<ItemBill>();
	}

	// Esta fecha se genera automaticamente y no se ingresa por formulario
	@PrePersist
	public void prePersist() {
		createdAt = new Date();
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getObservation() {
		return observation;
	}

	public void setObservation(String observation) {
		this.observation = observation;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}
	
	public List<ItemBill> getItems() {
		return items;
	}

	public void setItems(List<ItemBill> items) {
		this.items = items;
	}
	
	public void addItemBill(ItemBill item) {
		this.items.add(item);
	}
	
	public Double getTotal() {
		Double total = 0.0;
		
		int size = items.size();
		
		for(int i=0;i<size;i++) {
			total += items.get(i).calculateImport();
		}
		
		return total;
	}
}
