package com.brodygaudel.bankoperationmanagement.services.implementations;

import com.brodygaudel.bankoperationmanagement.dtos.*;
import com.brodygaudel.bankoperationmanagement.entities.Operation;
import com.brodygaudel.bankoperationmanagement.enums.Status;
import com.brodygaudel.bankoperationmanagement.enums.Type;
import com.brodygaudel.bankoperationmanagement.exceptions.BalanceNotSufficientException;
import com.brodygaudel.bankoperationmanagement.exceptions.BankAccountNotActivatedException;
import com.brodygaudel.bankoperationmanagement.exceptions.BankAccountNotFoundException;
import com.brodygaudel.bankoperationmanagement.exceptions.OperationNotFoundException;
import com.brodygaudel.bankoperationmanagement.mapping.Mappers;
import com.brodygaudel.bankoperationmanagement.repositories.OperationRepository;
import com.brodygaudel.bankoperationmanagement.services.OperationService;
import com.brodygaudel.bankoperationmanagement.services.feign.BankAccountService;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@Slf4j
public class OperationServiceImpl implements OperationService {

    private static final String BANK_ACCOUNT_NOT_FOUND = "Bank Account Not Found";

    private final OperationRepository operationRepository;
    private final Mappers mappers;
    private final BankAccountService bankAccountService;

    public OperationServiceImpl(OperationRepository operationRepository, Mappers mappers, BankAccountService bankAccountService) {
        this.operationRepository = operationRepository;
        this.mappers = mappers;
        this.bankAccountService = bankAccountService;
    }

    /**
     * debit a bank account
     *
     * @param debitDTO basic information to debit a bank account
     * @return basic information for a bank account debited
     * @throws BankAccountNotFoundException     raises this exception if the bank account you want to debit does not exist.
     * @throws BalanceNotSufficientException    raises this exception if balance not sufficient
     * @throws BankAccountNotActivatedException raises this exception if bank account's status is not activated
     */
    @Override
    public DebitDTO debit(@NotNull DebitDTO debitDTO) throws BankAccountNotFoundException, BalanceNotSufficientException, BankAccountNotActivatedException {
        BankAccountDTO bankAccountDTO = bankAccountService.getBankAccountById(debitDTO.accountId());
        if(bankAccountDTO == null){
            throw new BankAccountNotFoundException( BANK_ACCOUNT_NOT_FOUND);
        } else if (bankAccountDTO.status().equals(Status.SUSPENDED)) {
            throw new BankAccountNotActivatedException("Bank Account Not Activated");
        }else if (debitDTO.amount().compareTo(bankAccountDTO.amount()) > 0){
            throw new BalanceNotSufficientException("Balance not sufficient");
        }else {
            Operation operation = operationRepository.save(
                    Operation.builder()
                            .description(debitDTO.description())
                            .accountId(debitDTO.accountId())
                            .amount(debitDTO.amount())
                            .currency(bankAccountDTO.currency())
                            .type(Type.DEBIT)
                            .date(new Date())
                            .id(UUID.randomUUID().toString())
                            .build()
            );
            Form form = new Form(bankAccountDTO.amount().subtract(debitDTO.amount()), bankAccountDTO.status());
            bankAccountService.updateBankAccount(bankAccountDTO.id(), form);
            log.info("account debited");
            operationRepository.save(operation);
            log.info("debit operation saved");
            return debitDTO;
        }
    }




    /**
     * credit a bank account
     *
     * @param creditDTO basic information to credit a bank account
     * @return basic information of bank account credited
     * @throws BankAccountNotFoundException     raises this exception if the bank account you want to credit does not exist.
     * @throws BankAccountNotActivatedException raises this exception if bank account status is not activated
     */
    @Override
    public CreditDTO credit(@NotNull CreditDTO creditDTO) throws BankAccountNotFoundException, BankAccountNotActivatedException {
        BankAccountDTO bankAccountDTO = bankAccountService.getBankAccountById(creditDTO.accountId());
        if(bankAccountDTO == null){
            throw new BankAccountNotFoundException( BANK_ACCOUNT_NOT_FOUND);
        } else if (bankAccountDTO.status().equals(Status.SUSPENDED)) {
            throw new BankAccountNotActivatedException("Bank Account Not Activated");
        }else{
            Operation operation = Operation.builder()
                    .id(UUID.randomUUID().toString())
                    .accountId(bankAccountDTO.id())
                    .amount(creditDTO.amount())
                    .type(Type.CREDIT)
                    .description(creditDTO.description())
                    .currency(bankAccountDTO.currency())
                    .date(new Date())
                    .build();
            Form form = new Form(bankAccountDTO.amount().add(operation.getAmount()), bankAccountDTO.status());
            bankAccountService.updateBankAccount(creditDTO.accountId(), form);
            log.info("account credited");
            operationRepository.save(operation);
            log.info("credit operation saved");
            return creditDTO;
        }
    }



    /**
     * make a transfer from a bank account to another bank account
     *
     * @param transferDTO basic information to make a transfer
     * @return basic information of transfer done
     * @throws BankAccountNotFoundException     raises this exception if the bank account(s) you want to transfer does not exist.
     * @throws BalanceNotSufficientException    raises this exception if balance not sufficient to make a transfer
     * @throws BankAccountNotActivatedException raises this exception if bank account status is not activated
     */
    @Override
    public TransferDTO transfer(@NotNull TransferDTO transferDTO) throws BankAccountNotFoundException, BalanceNotSufficientException, BankAccountNotActivatedException {
        String description1 = "Transfer to :"+transferDTO.accountIdDestination()+"| Amount :"+transferDTO.amount()+"| Description :"+transferDTO.description();
        DebitDTO debitDTO = debit( new DebitDTO(transferDTO.accountIdSource(), transferDTO.amount(), description1));
        String description2 = "Transfer from :"+debitDTO.accountId()+"| Amount :"+debitDTO.amount()+"| Description :"+transferDTO.description();
        CreditDTO creditDTO = credit( new CreditDTO(transferDTO.accountIdDestination(), transferDTO.amount(), description2));
        return new TransferDTO(debitDTO.accountId(), creditDTO.accountId(), creditDTO.amount(), transferDTO.description());
    }

    /**
     * get all operations by account id
     *
     * @param accountId id of bank account that you want to retrieve operations
     * @return a list of AccountOperation where accountId equal id
     */
    @Override
    public List<OperationDTO> getAllOperationsByAccountId(String accountId) {
        List<Operation> operations = operationRepository.findByAccountId(accountId);
        log.info("return all operation for bank account with id equals "+accountId);
        return mappers.fromListOfOperations(operations);
    }

    /**
     * get AccountOperation by id
     *
     * @param id the id of AccountOperation you want to get
     * @return AccountOperation found
     * @throws OperationNotFoundException raises this exception if AccountOperation not found
     */
    @Override
    public OperationDTO getOperationById(String id) throws OperationNotFoundException {
        Operation operation = operationRepository.findById(id)
                .orElseThrow( () -> new OperationNotFoundException("Operation not found"));
        log.info("operation found");
        return mappers.fromOperation(operation);
    }

    /**
     * get the transaction history (credit, debit) of a bank account.
     *
     * @param accountId the id of the bank account whose history you want to read
     * @param page      the number of a page you want to get
     * @param size      the size of a page
     * @return bank account history
     * @throws BankAccountNotFoundException raises this exception if the bank account whose history you want to consult does not exist.
     */
    @Override
    public HistoryDTO getAccountHistory(String accountId, int page, int size) throws BankAccountNotFoundException {
        BankAccountDTO bankAccount = bankAccountService.getBankAccountById(accountId);
        if(bankAccount == null) {
            throw new BankAccountNotFoundException( BANK_ACCOUNT_NOT_FOUND);
        }
        Page<Operation> accountOperations = operationRepository
                .findByAccountIdOrderByDateDesc(accountId, PageRequest.of(page, size));
        List<OperationDTO> accountOperationDTOS = accountOperations.getContent()
                .stream().map(mappers::fromOperation)
                .toList();
        return new HistoryDTO(
                accountId,
                bankAccount.amount(),
                page,
                accountOperations.getTotalPages(),
                size,
                accountOperationDTOS
        );
    }
}
