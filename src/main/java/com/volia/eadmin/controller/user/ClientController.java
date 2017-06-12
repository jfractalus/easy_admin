package com.volia.eadmin.controller.user;

import com.volia.eadmin.core.controller.AbstractViewController;
import com.volia.eadmin.domain.Client;
import com.volia.eadmin.domain.Message;
import com.volia.eadmin.repository.ClientRepository;
import com.volia.eadmin.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("client")
public class ClientController extends AbstractViewController<Client, ClientRepository> {
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private MessageRepository messageRepository;

    @Override
    public ResponseEntity<Client> updateEntity(@RequestBody Client entity, HttpServletRequest request) {
        Long entityId = entity.getId();
        if(entityId != null){
            Client client = clientRepository.findOne(entityId);
            for (Message mess: client.getMessages()) {
                mess.setClient(null);
                messageRepository.save(mess);
            }
        }
        return super.updateEntity(entity, request);
    }

}
