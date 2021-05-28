package com.example.flight_price_tracker_telegram.bot.utils;

import com.vdurmont.emoji.EmojiParser;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Emojis {

    PLANE(EmojiParser.parseToUnicode(":airplane:")),
    DATE(EmojiParser.parseToUnicode(":date:")),
    EARTH(EmojiParser.parseToUnicode(":earth_americas:")),
    MONEYBAG(EmojiParser.parseToUnicode(":moneybag:")),
    GB_FLAG(EmojiParser.parseToUnicode(":gb:")),
    RUS_FLAG(EmojiParser.parseToUnicode(":ru:")),
    PUSHPIN(EmojiParser.parseToUnicode(":pushpin:")),
    MAG_RIGHT(EmojiParser.parseToUnicode(":mag_right:"));


    private String emojiName;

    @Override
    public String toString() {
        return emojiName;
    }
    }
