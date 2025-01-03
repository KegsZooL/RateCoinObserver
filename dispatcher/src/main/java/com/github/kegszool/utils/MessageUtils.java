package com.github.kegszool.utils;

import com.github.kegszool.menu.Menu;
import com.github.kegszool.menu.MenuRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

@Component
public class MessageUtils {

    private final MenuRegistry menuRegistry;

    @Autowired
    public MessageUtils(MenuRegistry menuRegistry) {
        this.menuRegistry = menuRegistry;
    }

    public SendMessage createSendMessageByText(Update update, String text) {
        Message message = update.getMessage();
        String chatId = message.getChatId().toString();
        var sendMessage = new SendMessage(chatId, text);
        return sendMessage;
    }

    public EditMessageText createEditMessageText (
            CallbackQuery query, String text, InlineKeyboardMarkup keyboard
    ) {
        var message = query.getMessage();
        Long chatId = message.getChatId();
        Integer messageId = message.getMessageId();
        return EditMessageText.builder()
                .text(text)
                .chatId(chatId)
                .messageId(messageId)
                .replyMarkup(keyboard)
                .build();
    }

    public EditMessageText createEditMessageText(CallbackQuery query, String text) {
        return createEditMessageText(query, text, null);
    }

    public EditMessageText createEditMessageTextByMenuName(CallbackQuery query, String menuName) {
        Menu menu = menuRegistry.getMenu(menuName);
        return createEditMessageText(query, menu.getTitle(), menu.get());
    }
}