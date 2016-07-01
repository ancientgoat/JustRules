package rule.breadcrumbs;

import ch.qos.logback.classic.Level;


/**
 * Different levels of breadcrumbs.  Might be helpful by filtering.
 */
public enum SkBreadcrumbType {
	BC_ERROR(Level.ERROR), BC_WARN(Level.WARN), BC_INFO(Level.INFO), BC_DEBUG(Level.DEBUG),
	BC_TRACE(Level.TRACE), BC_ALERT(Level.ERROR);

	private Level log4jLevel;

	SkBreadcrumbType(Level inLevel) {
		this.log4jLevel = inLevel;
	}

	public Level getLoggerLevel() {
		return log4jLevel;
	}
}
