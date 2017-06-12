package com.volia.eadmin;

import com.google.gson.Gson;
import com.volia.eadmin.core.menu.MenuItem;
import com.volia.eadmin.core.menu.MenuContainer;
import org.junit.Test;

import java.util.Arrays;

public class MainMenuTest {

    @Test
    public void buildMainMenuTest(){
        Gson gson = new Gson();

        MenuContainer container = new MenuContainer();

        MenuItem firstRoot = new MenuItem();
        firstRoot.setNameOfItem("Корневая-1");
        firstRoot.setOrder(1);
        firstRoot.setLink("");
        firstRoot.getSubItems().add(MenuItem.builder().nameOfItem("Подкатегория-1").order(1).link("").build());
        firstRoot.getSubItems().add(MenuItem.builder().nameOfItem("Подкатегория-2").order(2).link("").subItems(
                Arrays.asList(
                        MenuItem.builder().nameOfItem("Подкатегория-3").build(),
                        MenuItem.builder().nameOfItem("Лист-1").order(1).leaf(true).link("/url-1").build(),
                        MenuItem.builder().nameOfItem("Лист-2").order(2).leaf(true).link("/url-2").build()
                        ))
                .build());


        MenuItem secondRoot = new MenuItem();
        secondRoot.setNameOfItem("Корневая-2");
        secondRoot.setOrder(2);
        secondRoot.setLink("");
        secondRoot.getSubItems().add(MenuItem.builder().nameOfItem("Подкатегория-4").order(1).link("").build());
        secondRoot.getSubItems().add(MenuItem.builder().nameOfItem("Подкатегория-5").order(2).link("").build());
        secondRoot.getSubItems().add(MenuItem.builder().nameOfItem("Лист-3").order(1).leaf(true).link("/url-3").build());
        secondRoot.getSubItems().add(MenuItem.builder().nameOfItem("Лист-4").order(2).leaf(true).link("/url-4").build());

        container.getMenuItems().add(firstRoot);
        container.getMenuItems().add(secondRoot);

        System.out.println(gson.toJson(container));
    }
}
