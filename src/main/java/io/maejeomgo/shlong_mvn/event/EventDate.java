package io.maejeomgo.shlong_mvn.event;

import lombok.Data;

@Data
public class EventDate {
    private int month;
    private int day;
    private String weekday;
    private String time;
}
