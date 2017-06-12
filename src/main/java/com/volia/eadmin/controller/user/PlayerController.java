package com.volia.eadmin.controller.user;

import com.volia.eadmin.core.controller.AbstractViewController;
import com.volia.eadmin.domain.Player;
import com.volia.eadmin.repository.PlayerRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("player")
public class PlayerController extends AbstractViewController<Player, PlayerRepository> {
}
