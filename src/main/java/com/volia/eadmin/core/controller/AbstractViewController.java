package com.volia.eadmin.core.controller;

import com.volia.eadmin.core.mapper.EntityMapper;
import com.volia.eadmin.core.meta.ColumnInfo;
import com.volia.eadmin.core.meta.RowInfo;
import com.volia.eadmin.core.meta.ViewMetaInfo;
import com.volia.eadmin.core.service.CommonService;
import com.volia.eadmin.core.service.CrudService;
import com.volia.eadmin.core.service.MessageSender;
import com.volia.eadmin.domain.AbstractEntity;
import com.volia.eadmin.event.SocketMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.volia.eadmin.core.meta.CrudOperation.read;
import static com.volia.eadmin.util.JsonUtil.fromObject;
import static com.volia.eadmin.util.ReflectionUtil.createEntity;
import static com.volia.eadmin.util.ReflectionUtil.getNameOfEntityByGenericSuperclass;
import static org.springframework.web.bind.annotation.RequestMethod.*;

@Slf4j
@Controller
public abstract class AbstractViewController<T extends AbstractEntity, R extends PagingAndSortingRepository<T, Long>> extends AbstractRestController<T, R> {
    private final String NAME_OF_ENTITY = getNameOfEntityByGenericSuperclass(getClass());
    private static final String MULTI_SELECT = "multiSelect";
    private static final boolean WAS_DELETED = true;
    @Autowired
    private CommonService commonService;
    @Autowired
    private MessageSender messageSender;
    @Autowired
    private CrudService crudService;
    @Autowired
    private EntityMapper mapper;

    @RequestMapping(value = "", method = GET)
    public ModelAndView readAll(HttpServletRequest request) {
        crudService.checkCrudAccess(request.getServletPath(), read);
        return new ModelAndView("/content/database-table")
                .addObject("viewInfo", mapper.toModel(super.getAllRest(), NAME_OF_ENTITY));
    }

    @RequestMapping(value = "/view", method = GET)
    public ModelAndView readView(@RequestParam Map<String,String> requestParams) {
        return new ModelAndView("/component/sample/modal")
                .addObject("viewInfo", mapper.toModel(super.getAllRest(), NAME_OF_ENTITY))
                .addObject("modalId", requestParams.get("modalId"))
                .addObject(MULTI_SELECT, requestParams.containsKey(MULTI_SELECT));
    }

    @RequestMapping(value = "/{id}", method = GET)
    public ModelAndView readById(@PathVariable long id, @RequestParam Optional<Boolean> modify, HttpServletRequest request) {
        T entity = super.getByIdRest(id);
        if(entity == null){
            throw new RuntimeException(String.format("Entity by id = %s not found", id));
        }
        return buildModelAndView("/content/item-editor", entity, commonService.getUserName(request))
                .addObject("modify", modify.isPresent() ? modify.get() : false)
                .addObject("changeUrl", request.getRequestURI());
    }

    @RequestMapping(value = "/create", method = GET)
    public ModelAndView getCreateForm(HttpServletRequest request) {
        return buildModelAndView("/content/item-editor", (T)createEntity(getClass()), commonService.getUserName(request));
    }

    private ModelAndView buildModelAndView(String url, T entity, String currentUser) {
        ViewMetaInfo viewMetaInfo = mapper.toModel(entity, entity.getClass().getSimpleName());
        RowInfo rowInfo = viewMetaInfo.getTableViewInfo().getRows().get(0);
        List<ColumnInfo> columns = viewMetaInfo.getTableViewInfo().getColumns();
        return new ModelAndView(url)
                .addObject("columnInfos", columns)
                .addObject("rowInfo", rowInfo)
                .addObject("backUrl", "/" + viewMetaInfo.getRequestMappingPrefix())
                .addObject("modify", true)
                .addObject("currentUser", currentUser)
                .addObject("oldJsonView", fromObject(entity))
                .addObject("prefixNameOfSubEntities", fromObject(commonService.getRequestMappingPrefixOfSubEntities(rowInfo, columns)));
    }

    @RequestMapping(value = "", method = DELETE)
    public ResponseEntity deleteById(@RequestBody long id, HttpServletRequest request) {
        super.deleteByIdRest(id);
        String currentUser = commonService.getUserName(request);
        messageSender.sendEvent(request.getServletPath(), new SocketMessage(currentUser, WAS_DELETED));
        messageSender.sendEvent(request.getServletPath() + "/onDelete", new SocketMessage(currentUser, WAS_DELETED));
        messageSender.sendEvent(request.getServletPath() + "/" + id, new SocketMessage(currentUser, WAS_DELETED));
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "", method = {POST, PUT})
    public ResponseEntity<T> updateEntity(@RequestBody T entity, HttpServletRequest request) {
        super.updateEntityRest(entity);
        String userName = commonService.getUserName(request);
        messageSender.sendEvent(request.getServletPath(), new SocketMessage(userName, "Data from main table was changed"));
        messageSender.sendEvent(request.getServletPath() + "/" + entity.getId(), new SocketMessage(userName, fromObject(entity)));
        return ResponseEntity.ok().build();
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView handleViewException(Exception ex) {
        log.error("Exception: {}", ex);
        ModelAndView exception = new ModelAndView("/content/exception").addObject("exception", ex.getMessage());
        exception.setStatus(HttpStatus.UNPROCESSABLE_ENTITY);
        return exception;
    }
}

