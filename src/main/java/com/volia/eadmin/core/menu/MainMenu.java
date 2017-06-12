package com.volia.eadmin.core.menu;

import com.volia.eadmin.core.service.CrudService;
import com.volia.eadmin.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.google.common.base.Charsets.UTF_8;
import static java.util.Collections.emptyList;
import static org.springframework.util.CollectionUtils.isEmpty;

@Slf4j
@Component
public class MainMenu implements Menu {
    @Value("${application.main_menu.path}")
    private String mainMenuPath;
    @Autowired
    private CrudService crudService;

    public MenuContainer buildMenu(){
        MenuContainer menuContainer = readFromFileMenuContainer();
        return filterOnlyVisible(menuContainer);
    }

    private MenuContainer readFromFileMenuContainer(){
        MenuContainer result = new MenuContainer();
        File file = new File(getClass().getClassLoader().getResource(mainMenuPath).getFile());
        try{
            byte[] bytes = Files.readAllBytes(file.toPath());
            result = JsonUtil.toObject(new String(bytes , UTF_8), MenuContainer.class);
        }catch (IOException ex){
            log.error("Can't read menu file");
        }
        return result;
    }

    private MenuContainer filterOnlyVisible(MenuContainer menuContainer) {
        Set<String> availableLinks = crudService.availableLinks();
        if(!isEmpty(availableLinks)){
            List<MenuItem> filteredMenuItems = menuContainer.getMenuItems().stream()
                    .filter(mi -> availableLinks.contains(mi.getLink()))
                    .collect(Collectors.toList());
            menuContainer.setMenuItems(filteredMenuItems);
        } else {
            menuContainer.setMenuItems(emptyList());
        }
        return menuContainer;
    }
}
