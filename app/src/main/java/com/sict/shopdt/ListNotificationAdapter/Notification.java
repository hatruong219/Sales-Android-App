package com.sict.shopdt.ListNotificationAdapter;


public class Notification {
    private String Title;
    private String Context;
    private String Date;

    public Notification(String title, String context, String date) {
        this.Title = title;
        this.Context = context;
        this.Date = date;
    }
    public Notification(){

    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getContext() {
        return Context;
    }

    public void setContext(String context) {
        Context = context;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }
}
