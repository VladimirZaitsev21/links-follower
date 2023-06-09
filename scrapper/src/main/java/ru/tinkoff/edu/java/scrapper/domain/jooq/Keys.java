/*
 * This file is generated by jOOQ.
 */
package ru.tinkoff.edu.java.scrapper.domain.jooq;


import org.jooq.ForeignKey;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.Internal;

import ru.tinkoff.edu.java.scrapper.domain.jooq.tables.Chats;
import ru.tinkoff.edu.java.scrapper.domain.jooq.tables.Links;
import ru.tinkoff.edu.java.scrapper.domain.jooq.tables.Trackings;
import ru.tinkoff.edu.java.scrapper.domain.jooq.tables.records.ChatsRecord;
import ru.tinkoff.edu.java.scrapper.domain.jooq.tables.records.LinksRecord;
import ru.tinkoff.edu.java.scrapper.domain.jooq.tables.records.TrackingsRecord;


/**
 * A class modelling foreign key relationships and constraints of tables in app.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Keys {

    // -------------------------------------------------------------------------
    // UNIQUE and PRIMARY KEY definitions
    // -------------------------------------------------------------------------

    public static final UniqueKey<ChatsRecord> CHATS_PKEY = Internal.createUniqueKey(Chats.CHATS, DSL.name("chats_pkey"), new TableField[] { Chats.CHATS.TG_CHAT_ID }, true);
    public static final UniqueKey<ChatsRecord> UNIQUE_NAME = Internal.createUniqueKey(Chats.CHATS, DSL.name("unique_name"), new TableField[] { Chats.CHATS.NICKNAME }, true);
    public static final UniqueKey<LinksRecord> LINKS_PKEY = Internal.createUniqueKey(Links.LINKS, DSL.name("links_pkey"), new TableField[] { Links.LINKS.ID }, true);
    public static final UniqueKey<LinksRecord> UNIQUE_LINK = Internal.createUniqueKey(Links.LINKS, DSL.name("unique_link"), new TableField[] { Links.LINKS.LINK }, true);
    public static final UniqueKey<TrackingsRecord> TRACKINGS_PK = Internal.createUniqueKey(Trackings.TRACKINGS, DSL.name("trackings_PK"), new TableField[] { Trackings.TRACKINGS.TG_CHAT_ID, Trackings.TRACKINGS.LINK_ID }, true);

    // -------------------------------------------------------------------------
    // FOREIGN KEY definitions
    // -------------------------------------------------------------------------

    public static final ForeignKey<TrackingsRecord, ChatsRecord> TRACKINGS__CHAT_FK = Internal.createForeignKey(Trackings.TRACKINGS, DSL.name("chat_FK"), new TableField[] { Trackings.TRACKINGS.TG_CHAT_ID }, Keys.CHATS_PKEY, new TableField[] { Chats.CHATS.TG_CHAT_ID }, true);
    public static final ForeignKey<TrackingsRecord, LinksRecord> TRACKINGS__LINK_FK = Internal.createForeignKey(Trackings.TRACKINGS, DSL.name("link_FK"), new TableField[] { Trackings.TRACKINGS.LINK_ID }, Keys.LINKS_PKEY, new TableField[] { Links.LINKS.ID }, true);
}
