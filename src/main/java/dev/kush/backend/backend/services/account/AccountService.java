package dev.kush.backend.backend.services.account;

import dev.kush.backend.backend.models.wrapper.SendDetailWrapper;
import org.springframework.http.ResponseEntity;

public interface AccountService {
    ResponseEntity<SendDetailWrapper> getByAccountId(Long accountId);
}
