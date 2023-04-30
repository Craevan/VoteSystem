package com.crevan.votesystem.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Component
public class DateTimeUtil {

    @Value("${vote.deadline}")
    private String deadline;

    public LocalTime getDeadline() {
        return LocalTime.parse(deadline);
    }
}
