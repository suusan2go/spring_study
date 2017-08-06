package com.example.demo;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@RestController
@RequestMapping("messages")
public class MessagesController {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @RequestMapping(method = RequestMethod.GET)
    public List<Message> getMessages() {
        return jdbcTemplate.query("SELECT text From messages ORDER BY id", (rs, i) -> {
            Message m = new Message();
            m.setText(rs.getString("text"));
            return m;
        });
    }

    @RequestMapping(method = RequestMethod.POST)
    public Message postMessages(@RequestBody Message message) {
        jdbcTemplate.update("INSERT INTO messages(text) VALUES (?)", message.getText());
        return message;
    }
}
