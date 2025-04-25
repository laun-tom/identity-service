package com.own.identity_service.service.richmenu;

import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.action.MessageAction;
import com.linecorp.bot.model.richmenu.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class RichMenuService {
    @Value("${line.bot.channel-token}")
    private String lineBotChannelToken;

    private final LineMessagingClient lineMessagingClient;

    public void createAndLinkRichMenu() throws Exception {
        // 1. Create Rich Menu
        RichMenu richMenu = RichMenu.builder()
                .size(new RichMenuSize(2500, 1686))
                .selected(true)
                .name("Rich Menu 1")
                .chatBarText("Menu")
                .areas(Arrays.asList(
                        new RichMenuArea(
                                new RichMenuBounds(81, 111, 711, 621),
                                new MessageAction("Clean", "Clean")
                        ),
                        new RichMenuArea(
                                new RichMenuBounds(915, 140, 674, 631),
                                new MessageAction("Repair", "Repair")
                        ),
                        new RichMenuArea(
                                new RichMenuBounds(1695, 140, 703, 623),
                                new MessageAction("Tuition", "Tuition")
                        ),
                        new RichMenuArea(
                                new RichMenuBounds(119, 945, 665, 631),
                                new MessageAction("Knowledge", "Knowledge")
                        ),
                        new RichMenuArea(
                                new RichMenuBounds(911, 962, 670, 619),
                                new MessageAction("Travel", "Travel")
                        ),
                        new RichMenuArea(
                                new RichMenuBounds(1720, 911, 674, 682),
                                new MessageAction("Rent", "Rent")
                        )
                ))
                .build();

        // 2. Request LINE to create Rich Menu ID
        CompletableFuture<RichMenuIdResponse> response = lineMessagingClient.createRichMenu(richMenu);
        String richMenuId = response.get().getRichMenuId();

        // 3. Upload image
        uploadRichMenuImage(richMenuId, "C:\\Users\\lamnguyen\\Downloads\\rich_menu.png");
        // 4. Send to all users
        lineMessagingClient.setDefaultRichMenu(richMenuId);
    }

    public void uploadRichMenuImage(String richMenuId, String imagePath) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        headers.setBearerAuth(lineBotChannelToken);

        FileSystemResource imageFile = new FileSystemResource(imagePath);

        String url = "https://api-data.line.me/v2/bot/richmenu/" + richMenuId + "/content";
        ResponseEntity<String> response = new RestTemplate().exchange(
                url,
                HttpMethod.POST,
                new HttpEntity<>(imageFile, headers),
                String.class
        );

        if (response.getStatusCode() == HttpStatus.OK) {
            log.info("Tải ảnh lên thành công!");
        } else {
            log.error("Lỗi: {}", response.getBody());
        }
    }
}
