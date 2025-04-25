package com.own.identity_service.service.richmenu;

import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.PushMessage;
import com.linecorp.bot.model.action.MessageAction;
import com.linecorp.bot.model.action.URIAction;
import com.linecorp.bot.model.message.FlexMessage;
import com.linecorp.bot.model.message.flex.component.*;
import com.linecorp.bot.model.message.flex.container.Bubble;
import com.linecorp.bot.model.message.flex.container.Carousel;
import com.linecorp.bot.model.message.flex.unit.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;

@Slf4j
@Service
@RequiredArgsConstructor
public class FlexMessageService {
    private final LineMessagingClient lineMessagingClient;

    public FlexMessage sendCameraFlexMessage() throws URISyntaxException {
        Bubble bubble1 = BubbleUtil.createFlexMessage("https://images.pexels.com/photos/90946/pexels-photo-90946.jpeg",
                "Fujifilm XT-10",
                "Cảm biến ảnh (APS-C) X-Trans CMOS II",
                "https://www.pexels.com/photo/black-fujifilm-dslr-camera-90946/",
                "123.3"
                );

        Bubble bubble2 = BubbleUtil.createFlexMessage("https://images.pexels.com/photos/15673108/pexels-photo-15673108/free-photo-of-fujifilm-x-s10-digital-camera-with-lens-on-a-desk.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1",
                "Fujifilm X-S10",
                "Độ nhạy ISO 200- 6400 mở rộng 51200",
                "https://www.thegioimayanhso.vn/fujifilm-x-s10-body",
                "179"
        );

        Carousel carousel = Carousel.builder()
                .contents(Arrays.asList(bubble1, bubble2))
                .build();
        return new FlexMessage(
                "Thông tin",
                carousel
        );

    }
}
