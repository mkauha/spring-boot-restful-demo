package fi.mkauha.restfuldemo;


import org.springframework.data.repository.CrudRepository;

interface CustomerRepository extends CrudRepository<Customer, Long> {

}