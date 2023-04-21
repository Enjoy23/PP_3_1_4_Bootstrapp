package ru.kata.spring.boot_security.demo.model;

import javax.persistence.*;

@Entity
@Table(name = "p6spy_log")
public class P6Spy {
    public P6Spy(byte[] reportContent) {
        this.reportContent = reportContent;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Lob
    private byte[] reportContent;

    public P6Spy() {

    }
}
