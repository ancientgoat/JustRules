package entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;

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

    public JrRule setId(final Long id) {
        this.id = id;
        return this;
    }

    public String getRuleName() {
        return ruleName;
    }

    public JrRule setRuleName(final String ruleName) {
        this.ruleName = ruleName;
        return this;
    }

    public String getRule() {
        return rule;
    }

    public JrRule setRule(final String rule) {
        this.rule = rule;
        return this;
    }

    @Override public String toString() {
        return "JrRule{" +
               "id=" + id +
               ", ruleName='" + ruleName + '\'' +
               ", rule='" + rule + '\'' +
               '}';
    }

    @Override public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof JrRule))
            return false;

        JrRule jrRule = (JrRule)o;

        if (getId() != null ? !getId().equals(jrRule.getId()) : jrRule.getId() != null)
            return false;
        if (getRuleName() != null ? !getRuleName().equals(jrRule.getRuleName()) : jrRule.getRuleName() != null)
            return false;
        return getRule() != null ? getRule().equals(jrRule.getRule()) : jrRule.getRule() == null;
    }

    @Override public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getRuleName() != null ? getRuleName().hashCode() : 0);
        result = 31 * result + (getRule() != null ? getRule().hashCode() : 0);
        return result;
    }
}
