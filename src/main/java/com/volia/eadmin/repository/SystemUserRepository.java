package com.volia.eadmin.repository;

import com.volia.eadmin.domain.SystemUser;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SystemUserRepository extends PagingAndSortingRepository<SystemUser, Long> {
    SystemUser findByLogin(String login);
}
