package com.perfectganesh.ganeshchatbeserver;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class GaneshEMFService {
	private static final EntityManagerFactory emfInstance = Persistence.createEntityManagerFactory("transactions-optional");

	private GaneshEMFService() {
	}

	public static EntityManagerFactory get() {
		return emfInstance;
	}
}
