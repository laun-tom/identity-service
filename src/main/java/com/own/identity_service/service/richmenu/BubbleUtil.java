package com.own.identity_service.service.richmenu;

import com.linecorp.bot.model.action.MessageAction;
import com.linecorp.bot.model.action.URIAction;
import com.linecorp.bot.model.message.flex.component.*;
import com.linecorp.bot.model.message.flex.container.Bubble;
import com.linecorp.bot.model.message.flex.unit.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;

public class  BubbleUtil {

    public static Bubble createFlexMessage(String imgHeader, String productName, String description, String productUrl, String price) throws URISyntaxException {
        return Bubble.builder()
                .direction(FlexDirection.LTR)
                .hero(Image.builder()
                        .url(new URI(imgHeader))
                        .size("full")
                        .aspectRatio(Image.ImageAspectRatio.R1_51TO1)
                        .aspectMode(Image.ImageAspectMode.Cover)
                        .build())
                .body(Box.builder()
                        .layout(FlexLayout.VERTICAL)
                        .contents(Arrays.asList(
                                Text.builder()
                                        .text(productName)
                                        .weight(Text.TextWeight.BOLD)
                                        .size("xl")
                                        .color("#000000")
                                        .align(FlexAlign.START)
                                        .build(),
                                Box.builder()
                                        .layout(FlexLayout.BASELINE)
                                        .margin(FlexMarginSize.MD)
                                        .contents(Arrays.asList(
                                                Icon.builder()
                                                        .url(new URI("https://scdn.line-apps.com/n/channel_devcenter/img/fx/review_gold_star_28.png"))
                                                        .size(FlexFontSize.SM)
                                                        .build(),
                                                Icon.builder()
                                                        .url(new URI("https://scdn.line-apps.com/n/channel_devcenter/img/fx/review_gold_star_28.png"))
                                                        .size(FlexFontSize.SM)
                                                        .build(),
                                                Icon.builder()
                                                        .url(new URI("https://scdn.line-apps.com/n/channel_devcenter/img/fx/review_gold_star_28.png"))
                                                        .size(FlexFontSize.SM)
                                                        .build(),
                                                Icon.builder()
                                                        .url(new URI("https://scdn.line-apps.com/n/channel_devcenter/img/fx/review_gold_star_28.png"))
                                                        .size(FlexFontSize.SM)
                                                        .build(),
                                                Icon.builder()
                                                        .url(new URI("https://scdn.line-apps.com/n/channel_devcenter/img/fx/review_gold_star_28.png"))
                                                        .size(FlexFontSize.SM)
                                                        .build(),
                                                Text.builder()
                                                        .text("$" + price)
                                                        .weight(Text.TextWeight.BOLD)
                                                        .size(FlexFontSize.XL)
                                                        .color("#FF0000")
                                                        .align(FlexAlign.END)
                                                        .build()
                                        ))
                                        .build(),
                                Box.builder()
                                        .layout(FlexLayout.VERTICAL)
                                        .spacing("sm")
                                        .margin(FlexMarginSize.LG)
                                        .contents(Arrays.asList(
                                                Box.builder()
                                                        .layout(FlexLayout.BASELINE)
                                                        .spacing("sm")
                                                        .contents(Arrays.asList(
                                                                Text.builder()
                                                                        .text("Mô tả")
                                                                        .size(FlexFontSize.SM)
                                                                        .color("#AAAAAA")
                                                                        .flex(1)
                                                                        .build(),
                                                                Text.builder()
                                                                        .text(description)
                                                                        .size(FlexFontSize.SM)
                                                                        .color("#000000")
                                                                        .flex(5)
                                                                        .wrap(true)
                                                                        .build()
                                                        ))
                                                        .build(),

                                                Box.builder()
                                                        .layout(FlexLayout.BASELINE)
                                                        .spacing("sm")
                                                        .contents(Arrays.asList(
                                                                Text.builder()
                                                                        .text("Time")
                                                                        .size(FlexFontSize.SM)
                                                                        .color("#AAAAAA")
                                                                        .flex(1)
                                                                        .build(),
                                                                Text.builder()
                                                                        .text("10:00 - 23:00")
                                                                        .size(FlexFontSize.SM)
                                                                        .color("#000000")
                                                                        .flex(5)
                                                                        .wrap(true)
                                                                        .build()
                                                        ))
                                                        .build()
                                        ))
                                        .build()
                        ))
                        .build())
                .footer(Box.builder()
                        .layout(FlexLayout.VERTICAL)
                        .spacing("sm")
                        .contents(Arrays.asList(
                                Button.builder()
                                        .action(new MessageAction("CALL", "Calling 097xxxxx08"))
                                        .height(Button.ButtonHeight.SMALL)
                                        .style(Button.ButtonStyle.LINK)
                                        .build(),
                                Button.builder()
                                        .action(new URIAction("WEBSITE",
                                                new URI(productUrl),
                                                new URIAction.AltUri(new URI(productUrl))))
                                        .height(Button.ButtonHeight.SMALL)
                                        .style(Button.ButtonStyle.LINK)
                                        .build()
                        ))
                        .build())
                .build();
    }
}
