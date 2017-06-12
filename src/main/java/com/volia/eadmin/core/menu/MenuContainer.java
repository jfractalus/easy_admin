package com.volia.eadmin.core.menu;

import lombok.Data;
import java.util.LinkedList;
import java.util.List;

@Data
public class MenuContainer {
    private List<MenuItem> menuItems = new LinkedList<>();
}
