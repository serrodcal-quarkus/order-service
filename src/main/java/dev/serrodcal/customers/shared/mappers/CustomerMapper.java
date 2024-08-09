package dev.serrodcal.customers.shared.mappers;

import dev.serrodcal.customers.application.dtos.AddOrderCommand;
import dev.serrodcal.customers.application.dtos.CustomerDTO;
import dev.serrodcal.customers.application.dtos.NewCustomerCommand;
import dev.serrodcal.customers.application.dtos.UpdateCustomerCommand;
import dev.serrodcal.customers.domain.Customer;
import dev.serrodcal.customers.infrastructure.dtos.AddOrderRequest;
import dev.serrodcal.customers.infrastructure.dtos.CustomerResponse;
import dev.serrodcal.customers.infrastructure.dtos.NewCustomerRequest;
import dev.serrodcal.customers.infrastructure.dtos.UpdateCustomerRequest;
import dev.serrodcal.shared.mappers.QuarkusMappingConfig;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.ArrayList;

@Mapper(config = QuarkusMappingConfig.class)
public interface CustomerMapper {

    NewCustomerCommand mapNewCustomerRequestToNewCustomerCommand(NewCustomerRequest newCustomerRequest);

    CustomerResponse mapCustomerDTOToCustomerResponse(CustomerDTO customerDTO);

    CustomerDTO mapCustomerToCustomerDTO(Customer customer);

    default Customer mapNewCustomerCommandToCustomer(NewCustomerCommand newCustomerCommand) {
        return new Customer(
                null,
                newCustomerCommand.name(),
                newCustomerCommand.email(),
                new ArrayList<>(),
                null,
                null
        );
    }

    UpdateCustomerCommand mapUpdateCustomerRequestToUpdateCustomerCommand(UpdateCustomerRequest updateCustomerRequest);

    AddOrderCommand mapAddOrderRequestToAddOrderCommand(AddOrderRequest addOrderRequest);
    
}
