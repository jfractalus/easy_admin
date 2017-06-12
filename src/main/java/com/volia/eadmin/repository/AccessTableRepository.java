package com.volia.eadmin.repository;

import com.volia.eadmin.domain.AccessTable;
import com.volia.eadmin.domain.UserRole;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccessTableRepository extends PagingAndSortingRepository<AccessTable, Long> {
    List<AccessTable> findByRoleNameNotIn(List<UserRole> roles);
    List<AccessTable> findByRoleNameIn(List<UserRole> roles);
    List<AccessTable> findByRoleNameInAndCrudVisible(List<UserRole> roles, boolean visible);
}
