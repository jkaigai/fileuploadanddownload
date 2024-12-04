package com.fileconsumer.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "File")
@Getter
@Setter
@ToString
@AllArgsConstructor
public class File {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id")
        long id;
        @Column(name = "name")
        String fileName;
        @Column(name = "filetype")
        private String fileType;
        @Column(name = "ref")
        private String ref;
        @Column(name = "downloadUri")
        private String downloadUri;

        public File() {

        }
}
