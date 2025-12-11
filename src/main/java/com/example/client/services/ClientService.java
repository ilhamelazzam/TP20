package com.example.client.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.client.entities.Client;
import com.example.client.repositories.ClientRepository;

@Service
public class ClientService {

	@Autowired
	private ClientRepository clientRepository;

	public List<Client> findAll() {
		return clientRepository.findAll();
	}

	public Client findById(Long id) throws Exception {
		return clientRepository.findById(id)
				.orElseThrow(() -> new Exception("Client non trouvé avec l'ID: " + id));
	}

	public Client addClient(Client client) {
		return clientRepository.save(client);
	}
}
