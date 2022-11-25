package com.example.demo.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "tutorials")
@Data
public class Tutorial {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "title")
    private Double title;

    @Column(name = "description")
    private String description;

//    @OneToOne(fetch = FetchType.LAZY)
//    @MapsId
//    @JoinColumn(name = "tutorial_detail_id")
//    private TutorialDetail tutorialDetail;

    @Override
    public String toString() {
        return "Tutorial{" +
                "id= tri" + id +
                ", title=" + title +
                ", description='" + description + '\'' +
                '}';
    }

    // @OneToMany(fetch = FetchType.LAZY ,mappedBy="tutorial")
    // private List<Comment> comments;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(name = "tutorial_tags",
            joinColumns = { @JoinColumn(name = "tutorial_id") },
            inverseJoinColumns = { @JoinColumn(name = "tag_id") })
    private List<Tag> tags;
}
