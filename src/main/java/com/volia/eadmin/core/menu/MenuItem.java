package com.volia.eadmin.core.menu;

import lombok.*;
import java.util.List;
import java.util.ArrayList;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MenuItem {
    private String nameOfItem;
    private int order;
    private String link;
    private boolean leaf;
    private List<MenuItem> subItems = new ArrayList<>();
}
