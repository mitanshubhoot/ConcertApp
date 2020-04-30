package com.example.eventhomescreen;

public class Event {
    String eventtitle;
    String eventcategory;
    Integer eventpicture;

    public Event() {
    }

    public Event(String eventtitle, String eventcategory, Integer eventpicture) {
        this.eventtitle = eventtitle;
        this.eventcategory = eventcategory;
        this.eventpicture = eventpicture;
    }

    public String getEventtitle() {
        return eventtitle;
    }

    public void setEventtitle(String eventtitle) {
        this.eventtitle = eventtitle;
    }

    public String getEventcategory() {
        return eventcategory;
    }

    public void setEventcategory(String eventcategory) {
        this.eventcategory = eventcategory;
    }

    public Integer getEventpicture() {
        return eventpicture;
    }

    public void setEventpicture(Integer eventpicture) {
        this.eventpicture = eventpicture;
    }
}
