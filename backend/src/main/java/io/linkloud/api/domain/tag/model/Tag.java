package io.linkloud.api.domain.tag.model;

import io.linkloud.api.global.audit.Auditable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table
public class Tag extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50)
    private String name;

    @Builder
    public Tag(String name) {
        this.name = name;
    }

    @AllArgsConstructor
    public enum SortBy {
        popularity("popularity"),
        latest("createdAt"),
        name("name");

        @Getter
        private String sortBy;
    }
}
