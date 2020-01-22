package fi.mkauha.restfuldemo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.PostConstruct;


@RestController
public class MyRestController {

    private static Log logger = LogFactory.getLog(MyRestController.class);

    @Autowired
    private CustomerRepository customersRepository;

    @PostConstruct
    public void init() {
        logger.info("Init");
    }

    @RequestMapping(value = "/customers", method= RequestMethod.POST)
    public ResponseEntity<Void> addCustomer(@RequestBody Customer customer, UriComponentsBuilder b) {
        logger.info("Add customer ");
        customersRepository.save(customer);

        UriComponents uriComponents = b.path("/customers/{id}").buildAndExpand(customer.getId());
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uriComponents.toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/customers", method= RequestMethod.GET)
    public Iterable<Customer> fetch() {
        logger.info("Fetch customer ");
        return customersRepository.findAll();
    }

    @RequestMapping(value = "/customers/{customerId}", method= RequestMethod.GET)
    public Customer fetchCustomer(@PathVariable long customerId) {
        logger.info("Fetch customer ");
        Customer c = null;
        if(customersRepository.findById(customerId).isPresent()){
            c = (Customer) customersRepository.findById(customerId).get();
        }
        return c;
    }

    @RequestMapping(value = "/customers/{customerId}", method= RequestMethod.DELETE)
    public void deleteCustomer(@PathVariable long customerId) {
        logger.info("Delete customer " + customerId);
        customersRepository.deleteById(customerId);
    }
}