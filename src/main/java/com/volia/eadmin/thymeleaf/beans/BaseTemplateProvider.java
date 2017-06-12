package com.volia.eadmin.thymeleaf.beans;

import com.volia.eadmin.core.menu.Menu;
import com.volia.eadmin.core.menu.MenuItem;
import com.volia.eadmin.thymeleaf.ThymeleafBean;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ThymeleafBean("baseProvider")
public class BaseTemplateProvider {
    @Autowired
    private Menu menu;

    public List<MenuItem> mainMenu(){
        return menu.buildMenu().getMenuItems();
    }

    public Map searchItems() {
        return mainMenu().stream()
                .collect(Collectors.toMap(MenuItem::getLink, MenuItem::getNameOfItem));
    }

}
