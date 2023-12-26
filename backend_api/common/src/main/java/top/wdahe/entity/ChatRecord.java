package top.wdahe.entity;

import lombok.Data;

@Data
public class ChatRecord {
    private String toUserId;

    private String body;

    private String formatDate;

    private String toClient;
}
