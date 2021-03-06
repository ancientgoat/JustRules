package rule.action;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import rule.action.custom.SkActionCustomImpl;
import rule.action.enums.SkActionContext;
import rule.run.SkRuleRunner;

/**
 * Action Central.  Every action extends this class.  And the sub-types below allow us to
 * 	deserialize json into subclasses.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "actiontype")
@JsonSubTypes({//
		@JsonSubTypes.Type(value = SkActionCustomImpl.class, name = "CUSTOM"),
		@JsonSubTypes.Type(value = SkActionData.class, name = "DATA"),
		@JsonSubTypes.Type(value = SkActionLog.class, name = "LOG"),
		@JsonSubTypes.Type(value = SkActionPrint.class, name = "PRINT"),
		@JsonSubTypes.Type(value = SkActionReference.class, name = "REF"),
		@JsonSubTypes.Type(value = SkActionReadPropertyFileLocal.class, name = "READPROPERTYFILE_LOCAL"),
		@JsonSubTypes.Type(value = SkActionReadPropertyFileGlobal.class, name = "READPROPERTYFILE_GLOBAL") //
})
@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class SkAction {

	private String name;
	private String actionRef;
	private SkActionContext actionContext = SkActionContext.NORMAL;

	public abstract void run(SkRuleRunner inRunner);

	public String getName() {
		return name;
	}

	public void setName(final String inName) {
		name = inName;
	}

	public String getActionRef() {
		return actionRef;
	}

	public void setActionRef(final String inActionRef) {
		actionRef = inActionRef;
	}

	@JsonProperty("context")
	public void setActionContext(final SkActionContext inActionContext) {
		actionContext = inActionContext;
	}

	@JsonProperty("context")
	public SkActionContext getActionContext() {
		return actionContext;
	}
}
