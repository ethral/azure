package com.tefloncode.azure.api;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.tefloncode.azure.dto.UserResponse;
import com.tefloncode.azure.model.AuthenticationRequest;
import com.tefloncode.azure.model.AuthenticationResponse;
import com.tefloncode.azure.model.Order;
import com.tefloncode.azure.model.Product;
import com.tefloncode.azure.model.User;
import com.tefloncode.azure.model.UserDeleteResponse;
import com.tefloncode.azure.service.AccountingService;
import com.tefloncode.azure.service.OrderService;
import com.tefloncode.azure.service.ProductService;
import com.tefloncode.azure.service.UserService;
import com.tefloncode.azure.util.JwtUtil;

import io.swagger.annotations.ApiOperation;



@CrossOrigin
@RestController
@RequestMapping("/api")
public class AzureApi {
	
	
	@Autowired
	private ProductService prodSvc;
	
	@Autowired
	private OrderService orderSvc;
	
	@Autowired
	private AccountingService accountSvc;
	
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private UserService userSvc;

	@Autowired
	private JwtUtil jwtTokenUtil;
	
	
	Logger logger = LoggerFactory.getLogger(AzureApi.class);
	
	
	@RequestMapping(value="/products" , method=RequestMethod.GET)
	@ApiOperation(value = "Get all products",
	notes = "Retrieve all products of the azure company")
	public List<Product> getAllProds() {
		
		return prodSvc.getAllProducts();
	}
	
	@RequestMapping(value="/orders" , method=RequestMethod.GET)
	@ApiOperation(value = "Get all orders",
	notes = "Retrieve all orders of the azure company")
	public List<Order> getAllOrders() {
		
		return orderSvc.getAllOrders();
	}
	
	
	@RequestMapping(value="/products" , method=RequestMethod.POST)
	@ApiOperation(value = "Create product",
	notes = "Creates an product on behalf of the azure company")
	public Product createProduct(@RequestBody Product prod) {
		
		return prodSvc.createProduct(prod);
	}
	
	@RequestMapping(value="/orders" , method=RequestMethod.POST)
	@ApiOperation(value = "Create order",
	notes = "Creates an order on behalf of the azure company")
	public Order createOrder(@RequestBody Order order) {
		
//	    Timestamp ts  = new Timestamp(System.currentTimeMillis());
//	    
//	    order.setOrderTs(ts);
		
		return orderSvc.createOrder(order);
	}
	
	@RequestMapping(value="/netamount" , method=RequestMethod.GET)
	@ApiOperation(value = "Calculate net amount",
	notes = "Calculates the net amount by adding credit and subtracting debit over the time period provided")
	public BigDecimal calculateNetAmount(@RequestParam("fromDate") String fromDate, @RequestParam("toDate") String toDate) throws ParseException{
		
		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:SS.s");

		Timestamp fromTs = new Timestamp(((java.util.Date)df.parse(fromDate)).getTime());
		
		Timestamp toTs = new Timestamp(((java.util.Date)df.parse(toDate)).getTime());
		
		
		BigDecimal netAmt = new BigDecimal(accountSvc.calcNetAmt(fromTs, toTs));
		
		
		return netAmt.setScale(2, RoundingMode.CEILING);
	}
	
	
	@PostMapping("/authenticate")
	@ApiOperation(value = "Validates User",
	notes = "Authenticates users to access the admin portal and also provides a jwt for OAuth service to service secure communication",
	response = UserResponse.class)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authReq) throws Exception {

		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(authReq.getUsername(), authReq.getPassword()));
		} catch (BadCredentialsException e) {
			// TODO Auto-generated catch block
			//throw new Exception("Incorrect username or password", e);
			
			return ResponseEntity.ok(new AuthenticationResponse("Incorrect username (or) password",false));
		}

		final UserDetails userDetails = userDetailsService
				.loadUserByUsername(authReq.getUsername());

		final String jwt = jwtTokenUtil.generateToken(userDetails);

		return ResponseEntity.ok(new AuthenticationResponse(jwt,true));

	}
	
	@RequestMapping(value = "/users" , method = RequestMethod.DELETE)
	@ApiOperation(value = "Delete user by providing user id",
	notes = "Delete user",
	response = UserDeleteResponse.class)
	public UserDeleteResponse delUser(@RequestParam("id")  int id) {



		return userSvc.deleteUser(id);
	
	}
	
	
	@RequestMapping(value="/users" , method=RequestMethod.GET)
	@ApiOperation(value = "Get users who have azure portal access",
	notes = "Retrieves all the users for the azure services")
	public @ResponseBody List<User> getUsers() throws ParseException {



		return userSvc.getUsers();


	}
	
	@PostMapping("/users")
	@ApiOperation(value = "Create or update a user to access the azure portal access",
	notes = "Create (or) update a user with access to the azure services")
	public User createUser(@RequestBody User user) {



		return userSvc.createUser(user);


	}
	
	
	


}
