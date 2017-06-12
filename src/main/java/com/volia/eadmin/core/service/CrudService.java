package com.volia.eadmin.core.service;

import com.volia.eadmin.core.meta.CrudOperation;
import com.volia.eadmin.domain.AccessTable;
import com.volia.eadmin.domain.UserRole;
import com.volia.eadmin.repository.AccessTableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
public class CrudService {
    @Autowired
    private AccessTableRepository accessTableRepository;
    @Autowired
    private SecurityService securityService;

    public void checkCrudAccess(String path, CrudOperation crud){
        Set<String> disabledUrls = getDisabledUrlsForCrudOperation(securityService.getUserRoles(), crud);
        if(disabledUrls.contains(path)){
            throw new RuntimeException("Access denied!");
        }
    }


    public Set<String> getDisabledUrlsForCrudOperation(List<UserRole> roles, CrudOperation crudOperation){
        Set<String> disabledUrls = toLinks(accessTableRepository.findByRoleNameNotIn(roles).stream().collect(toList()));
        Set<String> enabledUrls = toLinks(accessTableRepository.findByRoleNameIn(roles).stream()
                .filter(getAccessTablePredicate(crudOperation))
                .collect(toList()));
        return disabledUrls.stream()
                .filter(url -> !enabledUrls.contains(url))
                .collect(Collectors.toSet());
    }

    private Predicate<AccessTable> getAccessTablePredicate(CrudOperation crudOperation) {
        switch (crudOperation){
            case create:
                return i->i.getCrud().isCreate();
            case read:
                return i->i.getCrud().isRead();
            case update:
                return i->i.getCrud().isUpdate();
            case delete:
                return i->i.getCrud().isDelete();
            case visible:
                return i->i.getCrud().isVisible();
        }
        return i->true;
    }

    private Set<String> toLinks(List<AccessTable> records){
        return records.stream()
                .map(at -> at.getAccessTableName())
                .distinct()
                .collect(Collectors.toSet());
    }

    public Set<String> availableLinks(){
        return toLinks(accessTableRepository.findByRoleNameInAndCrudVisible(securityService.getUserRoles(), Boolean.TRUE));
    }
}
