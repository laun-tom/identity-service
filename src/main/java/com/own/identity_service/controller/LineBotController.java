package com.own.identity_service.controller;

import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.Broadcast;
import com.linecorp.bot.model.PushMessage;
import com.linecorp.bot.model.ReplyMessage;
import com.linecorp.bot.model.event.FollowEvent;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.StickerMessage;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;
import com.own.identity_service.domain.User;
import com.own.identity_service.domain.UserFollowed;
import com.own.identity_service.repository.UserFollowedResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@LineMessageHandler
@RequiredArgsConstructor
@RequestMapping("/callback")
public class LineBotController {
    private final LineMessagingClient lineMessagingClient;
    private final UserFollowedResponse userFollowedResponse;

    @EventMapping

    public void handleTextMessage(MessageEvent<TextMessageContent> event) {
        String senderId = event.getSource().getUserId();
        String replyToken = event.getReplyToken();
        String userMessage = event.getMessage().getText();

//        String targetUserId = "Ude043a49067ad6dbdbe69b322a54a812";
//
//        if (!senderId.equals("U002d69a0aff66534f6f942a41a6eb68f")) {
//            targetUserId = "U002d69a0aff66534f6f942a41a6eb68f";
//        }
//        log.info("sender: " + senderId + " replyToken: " + replyToken + " userMessage: " + userMessage);
//        // Phản hồi tin nhắn
//        lineMessagingClient.replyMessage(
//                new ReplyMessage(replyToken, new StickerMessage("11537", "52002735"))
//        );

//        lineMessagingClient.pushMessage(
//                new PushMessage(
//                        targetUserId,
//                        new TextMessage(userMessage)
//                )
//        );
        TextMessage message = new TextMessage(userMessage);
        lineMessagingClient.broadcast( new Broadcast(message));
    }

    @EventMapping
    public void handleFollowEvent(FollowEvent event) {
        String userId = event.getSource().getUserId();
        UserFollowed userFollowed = UserFollowed.builder()
                .userId(userId)
                .build();
        this.userFollowedResponse.save(userFollowed);
    }
}
