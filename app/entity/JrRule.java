package entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;

/**
 *
 */
@javax.persistence.Entity
@javax.persistence.Table(name = "rule")
public class JrRule {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "rulename")
    private String ruleName;

    @Lob
    private String rule;

    public Long getId() {
        return id;
    }

    public JrRule setId(Long id) {
        this.id = id;
        return this;
    }

    public String getRuleName() {
        return ruleName;
    }

    public JrRule setRuleName(String ruleName) {
        this.ruleName = ruleName;
        return this;
    }

    public String getRule() {
        return rule;
    }

    public JrRule setRule(String rule) {
        this.rule = rule;
        return this;
    }
}
