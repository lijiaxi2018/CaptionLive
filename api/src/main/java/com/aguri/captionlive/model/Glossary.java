package com.aguri.captionlive.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "glossary")
public class Glossary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long glossaryId;

    private Long organizationId;

    private String source;

    private String romanization;

    private String term;

    private String explanation;

    private String remark;

    private String category;

}
