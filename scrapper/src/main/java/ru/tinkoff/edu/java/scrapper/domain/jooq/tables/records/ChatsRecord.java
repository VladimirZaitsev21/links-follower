/*
 * This file is generated by jOOQ.
 */
package ru.tinkoff.edu.java.scrapper.domain.jooq.tables.records;


import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record2;
import org.jooq.Row2;
import org.jooq.impl.UpdatableRecordImpl;

import ru.tinkoff.edu.java.scrapper.domain.jooq.tables.Chats;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class ChatsRecord extends UpdatableRecordImpl<ChatsRecord> implements Record2<Long, String> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>app.chats.tg_chat_id</code>.
     */
    public void setTgChatId(Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>app.chats.tg_chat_id</code>.
     */
    public Long getTgChatId() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>app.chats.nickname</code>.
     */
    public void setNickname(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>app.chats.nickname</code>.
     */
    public String getNickname() {
        return (String) get(1);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<Long> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record2 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row2<Long, String> fieldsRow() {
        return (Row2) super.fieldsRow();
    }

    @Override
    public Row2<Long, String> valuesRow() {
        return (Row2) super.valuesRow();
    }

    @Override
    public Field<Long> field1() {
        return Chats.CHATS.TG_CHAT_ID;
    }

    @Override
    public Field<String> field2() {
        return Chats.CHATS.NICKNAME;
    }

    @Override
    public Long component1() {
        return getTgChatId();
    }

    @Override
    public String component2() {
        return getNickname();
    }

    @Override
    public Long value1() {
        return getTgChatId();
    }

    @Override
    public String value2() {
        return getNickname();
    }

    @Override
    public ChatsRecord value1(Long value) {
        setTgChatId(value);
        return this;
    }

    @Override
    public ChatsRecord value2(String value) {
        setNickname(value);
        return this;
    }

    @Override
    public ChatsRecord values(Long value1, String value2) {
        value1(value1);
        value2(value2);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached ChatsRecord
     */
    public ChatsRecord() {
        super(Chats.CHATS);
    }

    /**
     * Create a detached, initialised ChatsRecord
     */
    public ChatsRecord(Long tgChatId, String nickname) {
        super(Chats.CHATS);

        setTgChatId(tgChatId);
        setNickname(nickname);
    }
}