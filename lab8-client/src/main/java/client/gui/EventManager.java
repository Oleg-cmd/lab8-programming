package client.gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

import java.util.HashMap;
import java.util.Map;

public class EventManager {
    private Map<String, EventHandler<ActionEvent>> eventMap;

    public EventManager() {
        eventMap = new HashMap<>();
    }

    public void addEvent(String eventName, EventHandler<ActionEvent> eventHandler) {
        eventMap.put(eventName, eventHandler);
    }

    public void bindEvent(Button button, String eventName) {
        EventHandler<ActionEvent> eventHandler = eventMap.get(eventName);
        if (eventHandler != null) {
            button.setOnAction(eventHandler);
        }
    }
}
