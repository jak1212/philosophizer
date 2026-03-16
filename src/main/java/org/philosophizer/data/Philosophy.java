package org.philosophizer.data;


import jakarta.persistence.*;

@Entity
@Table(name = "philosophy")
public class Philosophy {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "quote", nullable = false, unique = true, length = 2000)
    public String quote;
    @Column(name = "said_by", nullable = false, length = 100)
    public String saidBy;

    public Philosophy(String quote, String saidBy){
        this.quote = quote;
        this.saidBy = saidBy;
    }

    public Philosophy(){}

    public String getQuote() {
        return quote;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }

    public String getSaidBy() {
        return saidBy;
    }

    public void setSaidBy(String saidBy) {
        this.saidBy = saidBy;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
