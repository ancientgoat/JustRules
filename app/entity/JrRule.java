package entity;

/**
 *
 */
@javax.persistence.Entity
@javax.persistence.Table(name = "rule")
public class JrRule {

    @javax.persistence.Id
    @javax.persistence.GeneratedValue
    @javax.persistence.Column(name="id")
    private Long id;

    private String ruleName;

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
