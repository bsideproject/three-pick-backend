package com.bside.threepick.domain.account.reposiroty;

import com.bside.threepick.domain.account.entity.Account;
import com.bside.threepick.domain.account.entity.SignUpType;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AccountRepository extends JpaRepository<Account, Long> {

  @Query("select a from Account a where a.email =:email")
  Optional<Account> findByEmail(@Param("email") String email);

  @Query("select a from Account a where a.email =:email and a.signUpType =:signUpType")
  Optional<Account> findByEmailAndSignUpType(@Param("email") String email, @Param("signUpType") SignUpType signUpType);
}
