/*
 * This file is generated by jOOQ.
 */
package ru.tinkoff.edu.java.scrapper.domain.jooq.tables.records;


import org.jooq.Field;
import org.jooq.Record2;
import org.jooq.Row2;
import org.jooq.impl.UpdatableRecordImpl;

import ru.tinkoff.edu.java.scrapper.domain.jooq.tables.Trackings;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class TrackingsRecord extends UpdatableRecordImpl<TrackingsRecord> implements Record2<Long, Long> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>app.trackings.tg_chat_id</code>.
     */
    public void setTgChatId(Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>app.trackings.tg_chat_id</code>.
     */
    public Long getTgChatId() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>app.trackings.link_id</code>.
     */
    public void setLinkId(Long value) {
        set(1, value);
    }

    /**
     * Getter for <code>app.trackings.link_id</code>.
     */
    public Long getLinkId() {
        return (Long) get(1);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record2<Long, Long> key() {
        return (Record2) super.key();
    }

    // -------------------------------------------------------------------------
    // Record2 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row2<Long, Long> fieldsRow() {
        return (Row2) super.fieldsRow();
    }

    @Override
    public Row2<Long, Long> valuesRow() {
        return (Row2) super.valuesRow();
    }

    @Override
    public Field<Long> field1() {
        return Trackings.TRACKINGS.TG_CHAT_ID;
    }

    @Override
    public Field<Long> field2() {
        return Trackings.TRACKINGS.LINK_ID;
    }

    @Override
    public Long component1() {
        return getTgChatId();
    }

    @Override
    public Long component2() {
        return getLinkId();
    }

    @Override
    public Long value1() {
        return getTgChatId();
    }

    @Override
    public Long value2() {
        return getLinkId();
    }

    @Override
    public TrackingsRecord value1(Long value) {
        setTgChatId(value);
        return this;
    }

    @Override
    public TrackingsRecord value2(Long value) {
        setLinkId(value);
        return this;
    }

    @Override
    public TrackingsRecord values(Long value1, Long value2) {
        value1(value1);
        value2(value2);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached TrackingsRecord
     */
    public TrackingsRecord() {
        super(Trackings.TRACKINGS);
    }

    /**
     * Create a detached, initialised TrackingsRecord
     */
    public TrackingsRecord(Long tgChatId, Long linkId) {
        super(Trackings.TRACKINGS);

        setTgChatId(tgChatId);
        setLinkId(linkId);
    }
}
