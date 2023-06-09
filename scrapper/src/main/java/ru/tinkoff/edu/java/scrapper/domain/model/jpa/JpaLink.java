package ru.tinkoff.edu.java.scrapper.domain.model.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.sql.Timestamp;
import java.util.Map;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(schema = "app", name = "links")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
public class JpaLink {

    @Id
    @SequenceGenerator(name = "id_generator", sequenceName = "links_id_seq", schema = "app")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_generator")
    @Column(name = "id")
    private long id;

    @Column(name = "link")
    private String link;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @Column(name = "update_info")
    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, Object> updateInfo;

    @ManyToMany
    @JoinTable(
            schema = "app",
            name = "trackings",
            joinColumns = @JoinColumn(name = "link_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "tg_chat_id", referencedColumnName = "tg_chat_id")
    )
    private Set<JpaChat> trackingJpaChats;
}
