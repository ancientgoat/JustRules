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

    public void setId(Long id) {
        this.id = id;
    }

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }
}
