package com.shoploc.shoploc.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.shoploc.shoploc.domain.account.AccountEntity;
import com.shoploc.shoploc.domain.achat.AchatEntity;
import com.shoploc.shoploc.domain.card.CardEntity;
import com.shoploc.shoploc.domain.card.CardRepository;
import com.shoploc.shoploc.domain.card.CardServiceImpl;
import com.shoploc.shoploc.domain.client.ClientEntity;
import com.shoploc.shoploc.domain.client.ClientRepository;
import com.shoploc.shoploc.domain.product.Product;
import com.shoploc.shoploc.domain.product.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.Optional;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Date;
import java.util.stream.Collectors;

class CardServiceImplTest {

    @Test
    void testCreateCard() {
        // Mock dependencies
        CardRepository cardRepository = mock(CardRepository.class);
        ClientRepository repository = mock(ClientRepository.class);
        ProductRepository productRepository = mock(ProductRepository.class);

        CardServiceImpl cardService = new CardServiceImpl(cardRepository, repository, productRepository);

        // Stubbing behavior
        CardEntity savedCard = new CardEntity();
        when(cardRepository.save(any(CardEntity.class))).thenReturn(savedCard);

        // Call method under test
        CardEntity createdCard = cardService.createCard(new AccountEntity());

        // Verify behavior
        assertNotNull(createdCard);
        assertEquals(0, createdCard.getMontant());
    }

    @Test
    void testBuyWithFidelityPoints_ClientExists() {
        // Mock dependencies
        ClientRepository clientRepository = mock(ClientRepository.class);
        ProductRepository productRepository = mock(ProductRepository.class);
        CardRepository cardRepository = mock(CardRepository.class);
        CardServiceImpl cardService = new CardServiceImpl(cardRepository, clientRepository, productRepository);

        // Test data
        String email = "test@example.com";
        AchatEntity achatEntity = new AchatEntity();
        List<Product> products = new ArrayList<>();
        // Adding sample products to the list
        Product product1 = new Product();
        product1.setId(1L);
        product1.setPoints(10); // Set some points for the product
        // Add more products as needed
        products.add(product1);
        // Set other required properties of achatEntity

        // Stubbing behavior
        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setProducts(products);
        clientEntity.setFidelityPoints(100); // Set initial fidelity points
        when(clientRepository.findByEmail(email)).thenReturn(Optional.of(clientEntity));
        // Stub other method calls as needed
        achatEntity.setCartItems(products);
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(new Product()));
        // Call method under test
        ResponseEntity<ClientEntity> response = cardService.buyWithFidelityPoints(email, achatEntity);

        // Verify behavior
        assertEquals(ResponseEntity.ok().build(), response);
        // Add more assertions as needed
    }
    @Test
    void testCreditCard_ClientExists() {
        // Mock dependencies
        ClientRepository clientRepository = mock(ClientRepository.class);
        ProductRepository productRepository = mock(ProductRepository.class);
        CardRepository cardRepository = mock(CardRepository.class);
        CardServiceImpl cardService = new CardServiceImpl(cardRepository, clientRepository, productRepository);

        // Test data
        String email = "test@example.com";
        Double amount = 100.0;

        // Stubbing behavior
        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setEmail(email);
        CardEntity cardEntity = new CardEntity();
        cardEntity.setId(1l);
        cardEntity.setMontant(1000);
        cardEntity.setDate(null);
        clientEntity.setCardEntity(cardEntity); // Set card entity to null
        Optional<ClientEntity> optionalClient = Optional.of(clientEntity);
        when(clientRepository.findByEmail(email)).thenReturn(optionalClient);
        Optional<CardEntity> card = Optional.of(cardEntity);
        when(cardRepository.findById(1L)).thenReturn(card);


        // Call method under test
        ResponseEntity<ClientEntity> response = cardService.creditCard(email, amount);

        // Verify behavior
        assertEquals(ResponseEntity.ok().build(), response); // Expecting a bad request response
    }


    @Test
    void testGetCardInformation_ClientExists() {
        // Mock dependencies
        ClientRepository clientRepository = mock(ClientRepository.class);
        ProductRepository productRepository = mock(ProductRepository.class);
        CardRepository cardRepository = mock(CardRepository.class);
        CardServiceImpl cardService = new CardServiceImpl(cardRepository, clientRepository, productRepository);

        // Test data
        String email = "test@example.com";
        Double amount = 100.0;

        // Stubbing behavior
        AchatEntity achatEntity = new AchatEntity();
        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setEmail(email);
        CardEntity cardEntity = new CardEntity();
        cardEntity.setId(1l);
        cardEntity.setMontant(1000);
        cardEntity.setDate(null);
        clientEntity.setCardEntity(cardEntity); // Set card entity to null
        Optional<ClientEntity> optionalClient = Optional.of(clientEntity);
        when(clientRepository.findByEmail(email)).thenReturn(optionalClient);
        Optional<CardEntity> card = Optional.of(cardEntity);
        when(cardRepository.findById(1L)).thenReturn(card);
        // Call method under test
        ResponseEntity<CardEntity> response = cardService.getCardInformation(email);

        // Verify behavior
        assertEquals(ResponseEntity.ok(cardEntity), response);
        verify(clientRepository, times(1)).findByEmail(email);
        verify(cardRepository, times(1)).findById(anyLong());
    }

    @Test
    void testBuyWithCreditCard_ClientExists() {
        // Mock dependencies
        ClientRepository clientRepository = mock(ClientRepository.class);
        ProductRepository productRepository = mock(ProductRepository.class);
        CardRepository cardRepository = mock(CardRepository.class);
        CardServiceImpl cardService = new CardServiceImpl(cardRepository, clientRepository, productRepository);

        // Test data
        String email = "test@example.com";
        Double amount = 100.0;
        List<Product> products = new ArrayList<>();
        // Adding sample products to the list
        Product product1 = new Product();
        product1.setId(1L);
        product1.setPoints(10); // Set some points for the product
        // Add more products as needed
        products.add(product1);

        // Stubbing behavior
        AchatEntity achatEntity = new AchatEntity();
        achatEntity.setCartItems(products);
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(new Product()));

        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setEmail(email);
        CardEntity cardEntity = new CardEntity();
        cardEntity.setId(1l);
        cardEntity.setMontant(1000);
        cardEntity.setDate(null);
        clientEntity.setCardEntity(cardEntity); // Set card entity to null
        clientEntity.setFidelityPoints(100);// Set card entity to null

        Optional<ClientEntity> optionalClient = Optional.of(clientEntity);
        when(clientRepository.findByEmail(email)).thenReturn(optionalClient);
        Optional<CardEntity> card = Optional.of(cardEntity);
        when(cardRepository.findById(1L)).thenReturn(card);

        // Call method under test
        ResponseEntity<ClientEntity> response = cardService.buyWithCreditCard(email, achatEntity);

        // Verify behavior
        assertEquals(ResponseEntity.ok().build(), response);
        // Add more assertions as needed
    }

    @Test
    void testBuyWithFidelityCard_ClientExists() {
        // Mock dependencies
        ClientRepository clientRepository = mock(ClientRepository.class);
        ProductRepository productRepository = mock(ProductRepository.class);
        CardRepository cardRepository = mock(CardRepository.class);
        CardServiceImpl cardService = new CardServiceImpl(cardRepository, clientRepository, productRepository);

        // Test data
        String email = "test@example.com";
        List<Product> products = new ArrayList<>();
        // Adding sample products to the list
        Product product1 = new Product();
        product1.setId(1L);
        product1.setPoints(10); // Set some points for the product
        // Add more products as needed
        products.add(product1);

        AchatEntity achatEntity = new AchatEntity();

        // Stubbing behavior
        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setEmail(email);
        achatEntity.setCartItems(products);
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(new Product()));

        CardEntity cardEntity = new CardEntity();
        cardEntity.setId(1l);
        cardEntity.setMontant(1000);
        cardEntity.setDate(null);
        clientEntity.setCardEntity(cardEntity);
        clientEntity.setFidelityPoints(100);// Set card entity to null
        Optional<ClientEntity> optionalClient = Optional.of(clientEntity);
        when(clientRepository.findByEmail(email)).thenReturn(optionalClient);
        Optional<CardEntity> card = Optional.of(cardEntity);
        when(cardRepository.findById(1L)).thenReturn(card);

        // Call method under test
        ResponseEntity<ClientEntity> response = cardService.buyWithFidelityCard(email, achatEntity, false); // Assuming not using fidelity points

        // Verify behavior
        assertEquals(ResponseEntity.ok().build(), response);
        // Add more assertions as needed
    }
}
