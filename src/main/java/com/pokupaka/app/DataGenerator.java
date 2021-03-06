package com.pokupaka.app;

import com.pokupaka.backend.data.Role;
import com.pokupaka.backend.data.entity.*;
import com.pokupaka.backend.repositories.*;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import static com.pokupaka.backend.data.entity.Status.NOT_STARTED;

@SpringComponent
public class DataGenerator implements HasLogger {

	private static final String[] FILLING = new String[] { "Strawberry", "Chocolate", "Blueberry", "Raspberry",
			"Vanilla" };
	private static final String[] TYPE = new String[] { "Cake", "Pastry", "Tart", "Muffin", "Biscuit", "Bread", "Bagel",
			"Bun", "Brownie", "Cookie", "Cracker", "Cheese Cake" };
	private static final String[] FIRST_NAME = new String[] { "Ori", "Amanda", "Octavia", "Laurel", "Lael", "Delilah",
			"Jason", "Skyler", "Arsenio", "Haley", "Lionel", "Sylvia", "Jessica", "Lester", "Ferdinand", "Elaine",
			"Griffin", "Kerry", "Dominique" };
	private static final String[] LAST_NAME = new String[] { "Carter", "Castro", "Rich", "Irwin", "Moore", "Hendricks",
			"Huber", "Patton", "Wilkinson", "Thornton", "Nunez", "Macias", "Gallegos", "Blevins", "Mejia", "Pickett",
			"Whitney", "Farmer", "Henry", "Chen", "Macias", "Rowland", "Pierce", "Cortez", "Noble", "Howard", "Nixon",
			"Mcbride", "Leblanc", "Russell", "Carver", "Benton", "Maldonado", "Lyons" };

	private final Random random = new Random(1L);
	private Logger logger = getLogger();

	private OrderRepository orderRepository;
	private DealRepository dealRepository;
	private CategoryRepository categoryRepository;
	private ProductRepository productRepository;
	private UserRepository userRepository;
	private PasswordEncoder passwordEncoder;

	@Autowired
	public DataGenerator(OrderRepository orderRepository,
						 DealRepository dealRepository,
						 UserRepository userRepository,
						 ProductRepository productRepository,
						 CategoryRepository categoryRepository,
						 PasswordEncoder passwordEncoder
						 ) {
		this.orderRepository = orderRepository;
		this.userRepository = userRepository;
		this.dealRepository = dealRepository;
		this.passwordEncoder = passwordEncoder;
		this.categoryRepository = categoryRepository;
		this.productRepository = productRepository;

	}

	@PostConstruct
	public void loadData() {

		User admin1 = createAdmin(userRepository, passwordEncoder);
		User admin2 = createAdmin2(userRepository, passwordEncoder);
		User man1 = createManager(userRepository,passwordEncoder);
		User doNotDeleteMePlease = createRegularUserForDelete(userRepository,passwordEncoder);
		User regUser = createRegularUser(userRepository,passwordEncoder);
		getLogger().info("Users created");


		Category stationery = new Category("Stationery","Сategory for some stationery stuff");
		Category householdGoods = new Category("Household Goods","Сategory for household products");
		Category furniture = new Category("Furniture","Сategory for different elemenst of furniture");

		Product pen = new Product("Pen","5","Just a pen", stationery);
		Product pencil = new Product("Pencil","4","Just a pencil", stationery);
		Product ruler = new Product("Ruler","3","Just a ruler", stationery);

		Product mop = new Product("Mop","15","Mop description", householdGoods);
		Product vacuumСleaner = new Product("Vacuum cleaner","150","Vacuum cleaner description", householdGoods);
		Product cup = new Product("Cup","10","Cup description", householdGoods);

		Product table = new Product("Table","100","Table description", furniture);
		Product chair = new Product("Chair","50","Chair description", furniture);
		Product sofa = new Product("Sofa","250","Sofa description", furniture);

		Deal deal1 = new Deal("Deal for stationery", NOT_STARTED,stationery);
		Deal deal2 = new Deal("Deal for Household Goods",NOT_STARTED,householdGoods);
		Deal deal3 = new Deal("Deal for Furniture",NOT_STARTED,furniture);

		Order order1 = new Order(NOT_STARTED,deal1,admin1,pen,3);
		Order order2 = new Order(NOT_STARTED,deal1,admin1,pen,3);
		Order order3 = new Order(NOT_STARTED,deal1,admin1,pen,3);
		Order order4 = new Order(NOT_STARTED,deal1,regUser,pen,3);
		Order order5 = new Order(NOT_STARTED,deal1,regUser,pen,3);
		Order order6 = new Order(NOT_STARTED,deal1,man1,pen,3);

		// saving the data

		categoryRepository.saveAll(new ArrayList<>(Arrays.asList(stationery,householdGoods,furniture)) );
		productRepository.saveAll(new ArrayList<>(Arrays.asList(pen,pencil,ruler, mop, vacuumСleaner,cup,table, chair,sofa)));
		dealRepository.saveAll(new ArrayList<>(Arrays.asList(deal1,deal2,deal3)));
		orderRepository.saveAll(new ArrayList<>(Arrays.asList(order1,order2,order3,order4,order5,order6)));

		//fillMyData();
		logger.info("My data generated");
	}

	private User createAdmin(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		return userRepository.save(createUser("admin@vaadin.com", "admin1", "admin1",
				passwordEncoder.encode("admin"),
				Role.ADMIN, true));
	}

	private User createAdmin2(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		return userRepository.save(createUser("admin2@vaadin.com", "admin12", "admin1",
				passwordEncoder.encode("admin"),
				Role.ADMIN, true));
	}

	private User createManager(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		return userRepository.save(createUser("man1@mail.ru", "man1", "man1",
				passwordEncoder.encode("man1"),
				Role.MANAGER, true));
	}

	private User createRegularUser(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		return userRepository.save(createUser("user@pokupaka.com", "userName1", "userName2",
				passwordEncoder.encode("user"),
				Role.USER, true));
	}

	private User createRegularUserForDelete(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		return userRepository.save(
				createUser("doNotDeleteMePlease@pokupaka.com",
						"doNotDeleteMePlease",
						"doNotDeleteMePlease",
				passwordEncoder.encode("user"),
				Role.MANAGER, false));
	}

	private User createUser(String email, String firstName, String lastName, String passwordHash, String role,
							boolean locked) {
		User user = new User();
		user.setEmail(email);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setPasswordHash(passwordHash);
		user.setRole(role);
		user.setLocked(locked);
		return user;
	}

	private void fillMyData() {

		// Filling the DB with some data


		// fetch all categories
		logger.info("Categories found with findAll():");
		logger.info("-------------------------------");
		for (Category category: categoryRepository.findAll()) {
			logger.info(category.toString());
		}
		logger.info("");

		// fetch an individual category by ID
		categoryRepository.findById(1L)
				.ifPresent(category -> {
					logger.info("Category found with findById(1L):");
					logger.info("--------------------------------");
					logger.info(category.toString());
					logger.info("");
				});

		// fetch all products
		logger.info("Products found with findAll():");
		logger.info("-------------------------------");
		for (Product product: productRepository.findAll()) {
			logger.info(product.toString());
		}
		logger.info("");

		// fetch an individual product by ID
		productRepository.findById(3L)
				.ifPresent(product -> {
					logger.info("Product found with findById(1L):");
					logger.info("--------------------------------");
					logger.info(product.toString());
					logger.info("");
				});

	}

		/*if (userRepository.count() != 0L) {
			getLogger().info("Using existing database");
			return;
		}

		getLogger().info("Generating demo data");

		getLogger().info("... generating users");
		//User baker = createBaker(userRepository, passwordEncoder);
		//User barista = createBarista(userRepository, passwordEncoder);
		createAdmin(userRepository, passwordEncoder);
		// A set of products without constrains that can be deleted
		createDeletableUsers(userRepository, passwordEncoder);

		getLogger().info("... generating products");
		// A set of products that will be used for creating orders.
		Supplier<Product> productSupplier = createProducts(productRepository, 8);
		// A set of products without relationships that can be deleted
		createProducts(productRepository, 4);

		getLogger().info("... generating pickup locations");
		Supplier<PickupLocation> pickupLocationSupplier = createPickupLocations(pickupLocationRepository);

		getLogger().info("... generating orders");
		//createOrders(orderRepository, productSupplier, pickupLocationSupplier, barista, baker);

		getLogger().info("Generated demo data");*/

/*	private void fillCustomer(Customer customer) {
		String first = getRandom(FIRST_NAME);
		String last = getRandom(LAST_NAME);
		customer.setFullName(first + " " + last);
		customer.setPhoneNumber(getRandomPhone());
		if (random.nextInt(10) == 0) {
			customer.setDetails("Very important customer");
		}
	}

	private String getRandomPhone() {
		return "+1-555-" + String.format("%04d", random.nextInt(10000));
	}

	private void createOrders(OrderRepository orderRepo, Supplier<Product> productSupplier,
			Supplier<PickupLocation> pickupLocationSupplier, User barista, User baker) {

		int yearsToInclude = 2;
		LocalDate now = LocalDate.now();
		LocalDate oldestDate = LocalDate.of(now.getYear() - yearsToInclude, 1, 1);
		LocalDate newestDate = now.plusMonths(1L);

		// Create first today's order
		Order order = createOrder(productSupplier, pickupLocationSupplier, barista, baker, now);
		order.setDueTime(LocalTime.of(8, 0));
		order.setHistory(order.getHistory().subList(0, 1));
		order.setItems(order.getItems().subList(0, 1));
		orderRepo.save(order);

		for (LocalDate dueDate = oldestDate; dueDate.isBefore(newestDate); dueDate = dueDate.plusDays(1)) {
			// Create a slightly upwards trend - everybody wants to be
			// successful
			int relativeYear = dueDate.getYear() - now.getYear() + yearsToInclude;
			int relativeMonth = relativeYear * 12 + dueDate.getMonthValue();
			double multiplier = 1.0 + 0.03 * relativeMonth;
			int ordersThisDay = (int) (random.nextInt(10) + 1 * multiplier);
			for (int i = 0; i < ordersThisDay; i++) {
				orderRepo.save(createOrder(productSupplier, pickupLocationSupplier, barista, baker, dueDate));
			}
		}
	}

	private Order createOrder(Supplier<Product> productSupplier, Supplier<PickupLocation> pickupLocationSupplier,
			User barista, User baker, LocalDate dueDate) {
		Order order = new Order(barista);

		fillCustomer(order.getCustomer());
		order.setPickupLocation(pickupLocationSupplier.get());
		order.setDueDate(dueDate);
		order.setDueTime(getRandomDueTime());
		order.changeState(barista, getRandomState(order.getDueDate()));

		int itemCount = random.nextInt(3);
		List<OrderItem> items = new ArrayList<>();
		for (int i = 0; i <= itemCount; i++) {
			OrderItem item = new OrderItem();
			Product product;
			do {
				product = productSupplier.get();
			} while (containsProduct(items, product));
			item.setProduct(product);
			item.setQuantity(random.nextInt(10) + 1);
			if (random.nextInt(5) == 0) {
				if (random.nextBoolean()) {
					item.setComment("Lactose free");
				} else {
					item.setComment("Gluten free");
				}
			}
			items.add(item);
		}
		order.setItems(items);

		order.setHistory(createOrderHistory(order, barista, baker));

		return order;
	}

	private List<HistoryItem> createOrderHistory(Order order, User barista, User baker) {
		ArrayList<HistoryItem> history = new ArrayList<>();
		HistoryItem item = new HistoryItem(barista, "Order placed");
		item.setNewState(OrderState.NEW);
		LocalDateTime orderPlaced = order.getDueDate().minusDays(random.nextInt(5) + 2L).atTime(random.nextInt(10) + 7,
				00);
		item.setTimestamp(orderPlaced);
		history.add(item);
		if (order.getState() == OrderState.CANCELLED) {
			item = new HistoryItem(barista, "Order cancelled");
			item.setNewState(OrderState.CANCELLED);
			item.setTimestamp(orderPlaced.plusDays(random
					.nextInt((int) orderPlaced.until(order.getDueDate().atTime(order.getDueTime()), ChronoUnit.DAYS))));
			history.add(item);
		} else if (order.getState() == OrderState.CONFIRMED || order.getState() == OrderState.DELIVERED
				|| order.getState() == OrderState.PROBLEM || order.getState() == OrderState.READY) {
			item = new HistoryItem(baker, "Order confirmed");
			item.setNewState(OrderState.CONFIRMED);
			item.setTimestamp(orderPlaced.plusDays(random.nextInt(2)).plusHours(random.nextInt(5)));
			history.add(item);

			if (order.getState() == OrderState.PROBLEM) {
				item = new HistoryItem(baker, "Can't make it. Did not get any ingredients this morning");
				item.setNewState(OrderState.PROBLEM);
				item.setTimestamp(order.getDueDate().atTime(random.nextInt(4) + 4, 0));
				history.add(item);
			} else if (order.getState() == OrderState.READY || order.getState() == OrderState.DELIVERED) {
				item = new HistoryItem(baker, "Order ready for pickup");
				item.setNewState(OrderState.READY);
				item.setTimestamp(order.getDueDate().atTime(random.nextInt(2) + 8, random.nextBoolean() ? 0 : 30));
				history.add(item);
				if (order.getState() == OrderState.DELIVERED) {
					item = new HistoryItem(baker, "Order delivered");
					item.setNewState(OrderState.DELIVERED);
					item.setTimestamp(order.getDueDate().atTime(order.getDueTime().minusMinutes(random.nextInt(120))));
					history.add(item);
				}
			}
		}

		return history;
	}

	private boolean containsProduct(List<OrderItem> items, Product product) {
		for (OrderItem item : items) {
			if (item.getProduct() == product) {
				return true;
			}
		}
		return false;
	}

	private LocalTime getRandomDueTime() {
		int time = 8 + 4 * random.nextInt(3);

		return LocalTime.of(time, 0);
	}

	private OrderState getRandomState(LocalDate due) {
		LocalDate today = LocalDate.now();
		LocalDate tomorrow = today.plusDays(1);
		LocalDate twoDays = today.plusDays(2);

		if (due.isBefore(today)) {
			if (random.nextDouble() < 0.9) {
				return OrderState.DELIVERED;
			} else {
				return OrderState.CANCELLED;
			}
		} else {
			if (due.isAfter(twoDays)) {
				return OrderState.NEW;
			} else if (due.isAfter(tomorrow)) {
				// in 1-2 days
				double resolution = random.nextDouble();
				if (resolution < 0.8) {
					return OrderState.NEW;
				} else if (resolution < 0.9) {
					return OrderState.PROBLEM;
				} else {
					return OrderState.CANCELLED;
				}
			} else {
				double resolution = random.nextDouble();
				if (resolution < 0.6) {
					return OrderState.READY;
				} else if (resolution < 0.8) {
					return OrderState.DELIVERED;
				} else if (resolution < 0.9) {
					return OrderState.PROBLEM;
				} else {
					return OrderState.CANCELLED;
				}
			}

		}
	}

	private <T> T getRandom(T[] array) {
		return array[random.nextInt(array.length)];
	}

	private Supplier<PickupLocation> createPickupLocations(PickupLocationRepository pickupLocationRepository) {
		List<PickupLocation> pickupLocations = Arrays.asList(
				pickupLocationRepository.save(createPickupLocation("Store")),
				pickupLocationRepository.save(createPickupLocation("Bakery")));
		return () -> pickupLocations.get(random.nextInt(pickupLocations.size()));
	}

	private PickupLocation createPickupLocation(String name) {
		PickupLocation store = new PickupLocation();
		store.setName(name);
		return store;
	}

	private Supplier<Product> createProducts(ProductRepository productsRepo, int numberOfItems) {
		List<Product> products  = new ArrayList<>();
		for (int i = 0; i < numberOfItems; i++) {
			Product product = new Product();
			product.setName(getRandomProductName());
			double doublePrice = 2.0 + random.nextDouble() * 100.0;
			product.setPrice((int) (doublePrice * 100.0));
			products.add(productsRepo.save(product));
		}
		return () -> {
			double cutoff = 2.5;
			double g = random.nextGaussian();
			g = Math.min(cutoff, g);
			g = Math.max(-cutoff, g);
			g += cutoff;
			g /= (cutoff * 2.0);
			return products.get((int) (g * (products.size() - 1)));
		};
	}

	private String getRandomProductName() {
		String firstFilling = getRandom(FILLING);
		String name;
		if (random.nextBoolean()) {
			String secondFilling;
			do {
				secondFilling = getRandom(FILLING);
			} while (secondFilling.equals(firstFilling));

			name = firstFilling + " " + secondFilling;
		} else {
			name = firstFilling;
		}
		name += " " + getRandom(TYPE);

		return name;
	}

	private User createBaker(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		return userRepository.save(
				createUser("baker@vaadin.com", "Heidi", "Carter", passwordEncoder.encode("baker"), Role.BAKER, false));
	}

	private User createBarista(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		return userRepository.save(createUser("barista@vaadin.com", "Malin", "Castro",
				passwordEncoder.encode("barista"), Role.BARISTA, true));
	}

	private User createAdmin(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		return userRepository.save(
				createUser("admin@vaadin.com", "Göran", "Rich", passwordEncoder.encode("admin"), Role.ADMIN, true));
	}

	private void createDeletableUsers(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		userRepository.save(
				createUser("peter@vaadin.com", "Peter", "Bush", passwordEncoder.encode("peter"), Role.BARISTA, false));
		userRepository
				.save(createUser("mary@vaadin.com", "Mary", "Ocon", passwordEncoder.encode("mary"), Role.BAKER, true));
	}

	private User createUser(String email, String firstName, String lastName, String passwordHash, String role,
			boolean locked) {
		User user = new User();
		user.setEmail(email);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setPasswordHash(passwordHash);
		user.setRole(role);
		user.setLocked(locked);
		return user;
	}*/

}
